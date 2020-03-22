package cc.techport.zetty;

import cc.techport.zetty.client.HttpRequest;
import cc.techport.zetty.client.HttpResponse;
import cc.techport.zetty.client.HttpResponseHandler;
import cc.techport.zetty.client.ZettyClient;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class ZettyClientTest {

    @Test
    public void testRequestSync() throws IOException, ExecutionException, InterruptedException {
        ZettyClient client = new ZettyClient();
        HttpRequest request = new HttpRequest(HttpMethod.GET, "http://www.baidu.com");
        Future<HttpResponse> future = client.send(request);
        HttpResponse response = future.get();
        System.out.println(response);
        client.close();
    }

    @Test
    public void testRequestAsync() throws ExecutionException, InterruptedException, IOException {
        ZettyClient client = new ZettyClient();
        HttpRequest request = new HttpRequest(HttpMethod.GET, "http://www.baidu.com");
        Future<HttpResponse> future = client.send(request, new HttpResponseHandler() {
            @Override
            public void handle(HttpResponse response) {
                System.out.println("callback invoked");
                System.out.println(response);
            }
        });
        Thread.sleep(2000);
        client.close();
    }
}