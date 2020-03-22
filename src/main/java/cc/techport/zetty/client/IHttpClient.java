package cc.techport.zetty.client;

import java.util.concurrent.Future;

public interface IHttpClient {

    Future<HttpResponse> send(HttpRequest request);

    Future<HttpResponse> send(HttpRequest request, HttpResponseHandler handler);

}
