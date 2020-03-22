package cc.techport.zetty.client.pool;

import java.util.Objects;

public class Route {

    private String host;

    private int port;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return port == route.port &&
                host.equals(route.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
