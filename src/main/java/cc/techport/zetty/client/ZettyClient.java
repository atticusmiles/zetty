package cc.techport.zetty.client;

import cc.techport.zetty.client.util.RequestUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.Future;

public class ZettyClient implements IHttpClient, Closeable {

    private Bootstrap bootstrap;
    private EventLoopGroup clientEventGroup;
    private EventLoopGroup responseHandleGroup;

    public ZettyClient() {
        this.bootstrap = new Bootstrap();
        this.clientEventGroup = new NioEventLoopGroup();
        this.responseHandleGroup = new NioEventLoopGroup();
        this.bootstrap.group(clientEventGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new HttpResponseDecoder());
                        ch.pipeline().addLast(new HttpObjectAggregator(50000));
                        ch.pipeline().addLast(new HttpRequestEncoder());
                    }
                });
    }

    public Future<HttpResponse> send(HttpRequest request) {
        return this.send(request, null);
    }

    public Future<HttpResponse> send(HttpRequest request, HttpResponseHandler handler) {
        Channel channel = null;
        try {
            URI uri = new URI(request.getUri());
            ChannelFuture channelFuture = this.bootstrap.connect(uri.getHost(), uri.getPort() <= 0 ? 80 : uri.getPort()).sync();
            channel = channelFuture.channel();
            HttpResponseFuture future = new HttpResponseFuture();
            channel.pipeline().addLast(responseHandleGroup, "RESP_HANDLER", new ResponseInboundHandler(future, handler));
            channel.writeAndFlush(RequestUtil.convertRequest(request));
            return future;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() throws IOException {
        this.clientEventGroup.shutdownGracefully();
    }
}
