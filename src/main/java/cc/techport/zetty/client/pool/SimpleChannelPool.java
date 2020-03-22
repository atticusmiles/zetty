package cc.techport.zetty.client.pool;

import io.netty.channel.Channel;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleChannelPool implements ChannelPool {

    private int maxRouteChannels;

    private ConcurrentHashMap<Route, LinkedBlockingDeque<Channel>> free;
    private ConcurrentHashMap<Route, LinkedBlockingDeque<Channel>> borrowed;
    private ConcurrentHashMap<Route, AtomicInteger> total;

    public SimpleChannelPool(int maxRouteChannels) {
        this.maxRouteChannels = maxRouteChannels;
        this.free = new ConcurrentHashMap<>();
        this.borrowed = new ConcurrentHashMap<>();
        this.total = new ConcurrentHashMap<>();
    }

    @Override
    public Channel borrow(Route route) {
        return null;
    }

    @Override
    public Channel borrow(Route route, Callable<Channel> generator) {
        if (!total.containsKey(route)) {
            initRoute(route);
        }

        AtomicInteger routeTotal = total.get(route);
        return null;
    }

    private synchronized void initRoute(Route route){
        free.putIfAbsent(route, new LinkedBlockingDeque<>());
        borrowed.putIfAbsent(route, new LinkedBlockingDeque<>());
        total.putIfAbsent(route, new AtomicInteger(0));
    }

    @Override
    public void release(Channel channel, boolean usable) {

    }
}
