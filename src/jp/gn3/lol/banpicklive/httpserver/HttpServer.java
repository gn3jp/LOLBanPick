package jp.gn3.lol.banpicklive.httpserver;

import jp.gn3.lol.banpicklive.BanPickLive;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer extends Thread {

    private final ExecutorService service = Executors.newCachedThreadPool();

    private final BanPickLive main;
    private final int port;
    private final String websocketAddress;

    public HttpServer(BanPickLive main, int port, String websocketAddress) {
        this.main = main;
        this.port = port;
        this.websocketAddress = websocketAddress;
    }

    @Override
    public void run() {
        try (ServerSocket server = new ServerSocket(port)) {
            while (true) {
                this.serverProcess(server);
            }
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

    private void serverProcess(ServerSocket server) throws IOException {
        Socket socket = server.accept();

        this.service.execute(() -> {
            try (
                    InputStream in = socket.getInputStream();
                    OutputStream out = socket.getOutputStream()
            ) {

                HttpRequest request = new HttpRequest(in);

                HttpHeader header = request.getHeader();

                if (header.isGetMethod()) {
                    File file = null;
                    if (header.getPath().equalsIgnoreCase("/now")) {
                        try {
                            HttpResponse response = new HttpResponse(Status.OK);
                            response.addHeader("Access-Control-Allow-Origin", "*");
                            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
                            response.setBody(main.gameInfo.getJson());
                            response.writeTo(out);
                        } catch (Exception e) {
                            return;
                        }
                    } else {
                        try {
                            if (header.getPath().equalsIgnoreCase("/favicon.ico")) {
                                file = new File("\\img\\spell\\SummonerFlash.png");
                            } else {
                                if (header.getPath().startsWith("/img")) {
                                    file = new File(".", header.getPath());
                                } else {
                                    file = new File(".", "layout\\" + header.getPath());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                        if (file.exists() && file.isFile()) {
                            this.respondLocalFile(file, out);
                        } else {
                            this.respondNotFoundError(out);
                        }
                    }
                } else {
                    this.respondOk(out);
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void respondNotFoundError(OutputStream out) throws IOException {
        HttpResponse response = new HttpResponse(Status.NOT_FOUND);
        response.addHeader("Content-Type", ContentType.TEXT_PLAIN);
        response.setBody("404 Not Found");
        response.writeTo(out);
    }

    private void respondLocalFile(File file, OutputStream out) throws IOException {
        HttpResponse response = new HttpResponse(Status.OK);
        if (file.getName().equalsIgnoreCase("index.html")) {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String body = "";
            String text;
            while ((text = br.readLine()) != null) {
                body += text.replace("$WEBSOCKETADDRESS", websocketAddress) + "\r\n";
            }
            br.close();

            response.setBody(body);
        } else {
            response.setBody(file);
        }


        response.writeTo(out);
    }

    private void respondOk(OutputStream out) throws IOException {
        HttpResponse response = new HttpResponse(Status.OK);
        response.writeTo(out);
    }
}
