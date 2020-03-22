package cc.techport.zetty.client;

import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

public class HttpRequest {

    private final HttpMethod method;

    private final String uri;

    private final HttpHeaders headers;

    private final byte[] body;

    private final HttpVersion version;

    public HttpRequest(HttpMethod method, String uri) {
        this(method, uri, HttpHeaders.EMPTY_HEADERS, new byte[0], HttpVersion.HTTP_1_1);
    }

    public HttpRequest(HttpMethod method, String uri, byte[] body) {
        this(method, uri, HttpHeaders.EMPTY_HEADERS, body, HttpVersion.HTTP_1_1);
    }

    public HttpRequest(HttpMethod method, String uri, HttpHeaders headers, byte[] body) {
        this(method, uri, headers, body, HttpVersion.HTTP_1_1);
    }

    public HttpRequest(HttpMethod method, String uri, HttpHeaders headers, byte[] body, HttpVersion version) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.body = body;
        this.version = version;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public HttpVersion getVersion() {
        return version;
    }
}
