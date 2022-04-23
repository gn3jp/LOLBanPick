package jp.gn3.lol.banpicklive;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Collection;

public class SocketServer extends WebSocketServer {

    public SocketServer(int port) {
        super(new InetSocketAddress(port));
    }

    public void sendMessage(String msg) {
        Collection<WebSocket> websockets = getConnections();
        synchronized (websockets) {
            for (WebSocket websocket : websockets) {
                try {
                    websocket.send(msg);
                } catch (Exception e) {
                }
            }
        }
    }

    @Override
    public void onOpen(WebSocket connection, ClientHandshake handshake) {
        System.out.println(connection + " connected!");
    }

    @Override
    public void onClose(WebSocket connection, int code, String reason, boolean remote) {
    }

    @Override
    public void onMessage(WebSocket connection, String message) {
    }

    @Override
    public void onError(WebSocket connection, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onStart() {
    }
}
