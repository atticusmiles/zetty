package cc.techport.zetty.client.util;

import cc.techport.zetty.client.HttpRequest;
import cc.techport.zetty.client.HttpResponse;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;

public class RequestUtil {

    public static FullHttpRequest convertRequest(HttpRequest request){
        return new DefaultFullHttpRequest(request.getVersion(), request.getMethod(),
                request.getUri(), Unpooled.wrappedBuffer(request.getBody()), request.getHeaders(), HttpHeaders.EMPTY_HEADERS);
    }

}
