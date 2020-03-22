package cc.techport.zetty.client.pool;

import io.netty.channel.Channel;

import java.util.concurrent.Callable;

public interface ChannelPool {

    public Channel borrow(Route route);

    public Channel borrow(Route route, Callable<Channel> generator);

    public void release(Channel channel, boolean usable);

}
