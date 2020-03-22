package cc.techport.zetty.client;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class HttpResponseFuture implements Future<HttpResponse> {

    private final AtomicBoolean isDone;
    private final AtomicBoolean isCanceled;
    private final AtomicReference<HttpResponse> response;
    private final AtomicReference<Exception> exception;
    private final CountDownLatch doneLatch;


    public HttpResponseFuture(){
        this.isDone = new AtomicBoolean(false);
        this.isCanceled = new AtomicBoolean(false);
        this.response = new AtomicReference<>(null);
        this.exception = new AtomicReference<>(null);
        this.doneLatch = new CountDownLatch(1);
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCanceled.get();
    }

    @Override
    public boolean isDone() {
        return this.isDone.get();
    }

    public void setResponse(HttpResponse response) {
        this.response.compareAndSet(null, response);
        this.doneLatch.countDown();
    }

    public void setException(Exception exception) {
        this.exception.compareAndSet(null, exception);
        this.doneLatch.countDown();
    }

    @Override
    public HttpResponse get() throws InterruptedException, ExecutionException {
        this.doneLatch.await();
        if (exception.get() != null) {
            throw new ExecutionException(exception.get());
        }else {
            return response.get();
        }
    }

    @Override
    public HttpResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        boolean success =  this.doneLatch.await(timeout, unit);
        if (success){
            if (exception.get() != null) {
                throw new ExecutionException(exception.get());
            }else {
                return response.get();
            }
        }else {
            throw new TimeoutException("Request timeout");
        }
    }
}
