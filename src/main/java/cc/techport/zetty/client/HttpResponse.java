package cc.techport.zetty.client;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpResponse {

    private final HttpVersion version;
    private final HttpResponseStatus status;
    private final HttpHeaders headers;
    private byte[] content;

    public HttpResponse(HttpVersion version, HttpResponseStatus status) {
        this(version, status, HttpHeaders.EMPTY_HEADERS);
    }

    public HttpResponse(HttpVersion version, HttpResponseStatus status, HttpHeaders headers) {
        this(version, status, headers, new byte[0]);
    }

    public HttpResponse(HttpVersion version, HttpResponseStatus status, HttpHeaders headers,  byte[] content) {
        this.version = version;
        this.status = status;
        this.headers = headers;
        this.content = content;
    }

    public HttpVersion getVersion() {
        return version;
    }

    public HttpResponseStatus getStatus() {
        return status;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }


    @Override
    public String toString() {
        return "HttpResponse{" +
                "version=" + version +
                ", status=" + status +
                ", headers=" + headers +
                ", content=" + new String(content, StandardCharsets.UTF_8) +
                '}';
    }
}
