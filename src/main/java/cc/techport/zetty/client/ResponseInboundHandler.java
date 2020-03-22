package cc.techport.zetty.client;

import cc.techport.zetty.client.util.RequestUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;

public class ResponseInboundHandler extends ChannelInboundHandlerAdapter {

    private final HttpResponseFuture future;
    private final HttpResponseHandler handler;

    public ResponseInboundHandler(HttpResponseFuture future, HttpResponseHandler handler) {
        this.future = future;
        this.handler = handler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        cc.techport.zetty.client.HttpResponse resp = null;
        try {
            if (!HttpResponse.class.isAssignableFrom(msg.getClass())) {
                throw new IllegalStateException("Not a http response!");
            }

            HttpResponse converted = (HttpResponse) msg;
            resp = new cc.techport.zetty.client.HttpResponse(converted.protocolVersion(), converted.status(), converted.headers());

            if (msg instanceof HttpContent) {
                HttpContent content = (HttpContent) msg;
                ByteBuf buf = content.content();
                resp.setContent(ByteBufUtil.getBytes(buf));
                buf.release();
            }
        } catch (Exception e) {
            this.future.setException(e);
        } finally {
            this.future.setResponse(resp);
            if (null != this.handler) {
                this.handler.handle(resp);
            }
        }

    }
}