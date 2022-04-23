package jp.gn3.lol.banpicklive;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;
import com.stirante.lolclient.ClientApi;
import com.stirante.lolclient.ClientConnectionListener;
import com.stirante.lolclient.ClientWebSocket;
import generated.LolChampSelectChampSelectPlayerSelection;
import generated.LolChampSelectChampSelectSession;
import jp.gn3.lol.datadragon.DataDragon;
import jp.gn3.lol.datadragon.ImageDownloader;
import jp.gn3.lol.banpicklive.httpserver.HttpServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class BanPickLive {

    public static void main(String[] args) throws IOException {
        if (args.length != 0 && args[0].equalsIgnoreCase("-d")) {
            try {
                if (args.length >= 2) {
                    new ImageDownloader(Integer.parseInt(args[1]));
                } else {
                    new ImageDownloader(0);
                }
                System.exit(-1);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
        new BanPickLive();
    }

    public DataDragon dataDragon;
    private final SettingFrame settingFrame;
    public String localip = "localhost";

    public GameInfo gameInfo = null;

    public Gson gson;

    private ClientWebSocket socket;
    private HttpServer httpServer = null;
    public SocketServer socketServer = null;

    public ClientApi api = null;

    public BanPickLive() {
        gson = new GsonBuilder().create();
        dataDragon = new DataDragon();
        gameInfo = new GameInfo();
        try {
            localip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        start();
        settingFrame = new SettingFrame(this);
    }

    String imgUrl = "http://localhost:8334";

    public void startImageServer(String ip, int httpServerPort, int webSocketPort) {
        imgUrl = "http://" + ip + ":" + httpServerPort;
        httpServer = new HttpServer(this, httpServerPort, ip + ":" + webSocketPort);
        httpServer.start();
        socketServer = new SocketServer(webSocketPort);
        socketServer.start();
    }

    public void start() {
        if (api != null) return;
        api = new ClientApi();
        api.addClientConnectionListener(new ClientConnectionListener() {
            @Override
            public void onClientConnected() {
//                System.out.println("ClientAPI Connected");
                while (true) {
                    try {
                        socket = api.openWebSocket();
                        break;
                    } catch (Exception e) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
                try {
                    socket.setSocketListener(new ClientWebSocket.SocketListener() {
                        @Override
                        public void onEvent(ClientWebSocket.Event event) {
//                            System.out.println(event.getUri());
                            if (socketServer != null) {
//                                if(1 == 2){
                                if (event.getUri().equalsIgnoreCase("/lol-champ-select/v1/session")) {
                                    LolChampSelectChampSelectSession champSelect = (LolChampSelectChampSelectSession) event.getData();
                                    List<Object> actions = champSelect.actions;
                                    for (int size = 0; size <= actions.size() - 1; size++) {
                                        com.stirante.lolclient.libs.com.google.gson.internal.LinkedTreeMap<String, Object> selectData = ((ArrayList<com.stirante.lolclient.libs.com.google.gson.internal.LinkedTreeMap<String, Object>>) actions.get(size)).get(0);
                                        int championId = (int) (double) selectData.get("championId");
                                        int id = (int) (double) selectData.get("id");
                                        boolean isInProgress = (boolean) selectData.get("isInProgress");
                                        boolean completed = (boolean) selectData.get("completed");
                                        String type = (String) selectData.get("type");
                                        boolean ban = type.equalsIgnoreCase("ban");
                                        String url = null;
                                        if (ban) {
                                            if (championId != 0) {
                                                String name = dataDragon.getChampionData(championId).id;
                                                url = imgUrl + "/img/icon/" + name + ".png";
                                            }
                                            gameInfo.setPick(id, url, completed, true);
                                        } else {
                                            if (championId != 0) {
                                                String name = dataDragon.getChampionData(championId).id;
                                                url = imgUrl + "/img/splash/" + name + ".jpg";
                                            }
                                            gameInfo.setPick(id, url, completed, false);
                                        }
                                        if (isInProgress) {
                                            gameInfo.setProgress(id, ban);
                                        }
                                    }

                                    List<LolChampSelectChampSelectPlayerSelection> myTeam = champSelect.myTeam;
                                    for (int size = 0; size <= myTeam.size() - 1; size++) {
                                        LolChampSelectChampSelectPlayerSelection selectData = myTeam.get(size);
                                        int spell1Id = Math.toIntExact(selectData.spell1Id);
                                        try {
                                            String id1 = dataDragon.getSummonerSpellData(spell1Id).id;
                                            String url1 = imgUrl + "/img/spell/" + id1 + ".png";
                                            int spell2Id = Math.toIntExact(selectData.spell2Id);
                                            String id2 = dataDragon.getSummonerSpellData(spell2Id).id;
                                            String url2 = imgUrl + "/img/spell/" + id2 + ".png";
                                            int team = selectData.team;
                                            gameInfo.setSpell(size, team, url1, url2);
                                        } catch (Exception e) {
                                        }
                                    }
                                    List<LolChampSelectChampSelectPlayerSelection> theirTeam = champSelect.theirTeam;
                                    for (int size = 0; size <= theirTeam.size() - 1; size++) {
                                        try {
                                            LolChampSelectChampSelectPlayerSelection selectData = theirTeam.get(size);
                                            int spell1Id = Math.toIntExact(selectData.spell1Id);
                                            String id1 = dataDragon.getSummonerSpellData(spell1Id).id;
                                            String url1 = imgUrl + "/img/spell/" + id1 + ".png";
                                            int spell2Id = Math.toIntExact(selectData.spell2Id);
                                            String id2 = dataDragon.getSummonerSpellData(spell2Id).id;
                                            String url2 = imgUrl + "/img/spell/" + id2 + ".png";
                                            int team = selectData.team;
                                            gameInfo.setSpell(size, team, url1, url2);
                                        } catch (Exception e) {
                                        }
                                    }
                                    gameInfo.phase = champSelect.timer.phase;
                                }
                                socketServer.sendMessage(gson.toJson(gameInfo));
                            }
//                        }
                        }

                        @Override
                        public void onClose(int code, String reason) {
                            System.out.println("Socket closed, reason: " + reason);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onClientDisconnected() {
                socket.close();
            }
        });
    }
//
//    public void test(){
//        JsonReader jsr = null;
//        try {
//            InputStreamReader isr = new InputStreamReader(new FileInputStream("E:\\Project\\Works-Sample\\LOLBanPickStream\\2.json"));
//            jsr = new JsonReader(isr);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        ArrayList<LinkedTreeMap> l = new Gson().newBuilder().create().fromJson(jsr, ArrayList.class);
//        long startTime = -1;
//        List<LinkedTreeMap> delete = new ArrayList<>();
//        while(true){
//            if (startTime == -1) {
//                startTime = System.currentTimeMillis();
//            } else {
//                long ct = (System.currentTimeMillis()) - startTime+90000;
////                long ct = (System.currentTimeMillis()) - startTime+100000;
//                for (LinkedTreeMap cd : l) {
//                    if (((double)cd.get("time")) <= ct) {
//                        if (((String)cd.get("url")).equalsIgnoreCase("/lol-champ-select/v1/session")) {
//                            ArrayList<ArrayList<LinkedTreeMap<String,Object>>> data = (ArrayList<ArrayList<LinkedTreeMap<String,Object>>>) ((LinkedTreeMap) cd.get("data")).get("actions");
//                            for (int size = 0; size <= data.size()-1; size++) {
//                                LinkedTreeMap<String, Object> selectData = data.get(size).get(0);
//                                int championId = (int) (double) selectData.get("championId");
//                                int id = (int) (double) selectData.get("id");
//                                boolean isInProgress = (boolean) selectData.get("isInProgress");
//                                boolean completed = (boolean) selectData.get("completed");
//                                String type = (String) selectData.get("type");
//                                boolean ban = type.equalsIgnoreCase("ban");
//                                String url = null;
//                                if(ban){
//                                    if(championId != 0){
//                                        String name = dataDragon.getChampionData(championId).id;
//                                        url = imgUrl+"/img/icon/"+name+".png";
//                                    }
//                                    gameInfo.setPick(id,url,completed,true);
//                                } else {
//                                    if(championId != 0){
//                                        String name = dataDragon.getChampionData(championId).id;
//                                        url = imgUrl+"/img/splash/"+name+".jpg";
//                                    }
//                                    gameInfo.setPick(id,url,completed,false);
//                                }
//                                if (isInProgress) {
//                                    gameInfo.setProgress(id,ban);
//                                }
//                            }
//
//                            ArrayList<LinkedTreeMap<String,Object>> myTeam = (ArrayList<LinkedTreeMap<String,Object>>) ((LinkedTreeMap) cd.get("data")).get("myTeam");
//                            for (int size = 0; size <= myTeam.size()-1; size++) {
//                                LinkedTreeMap<String, Object> selectData = myTeam.get(size);
//                                int spell1Id = (int) (double) selectData.get("spell1Id");
//                                String id1 = dataDragon.getSummonerSpellData(spell1Id).id;
//                                String url1 = imgUrl+"/img/spell/"+id1+".png";
//                                int spell2Id = (int) (double) selectData.get("spell2Id");
//                                String id2 = dataDragon.getSummonerSpellData(spell2Id).id;
//                                String url2 = imgUrl+"/img/spell/"+id2+".png";
//                                int team = (int) (double) selectData.get("team");
//                                gameInfo.setSpell(size, team, url1, url2);
//                            }
//                            ArrayList<LinkedTreeMap<String,Object>> theirTeam = (ArrayList<LinkedTreeMap<String,Object>>) ((LinkedTreeMap) cd.get("data")).get("theirTeam");
//                            for (int size = 0; size <= theirTeam.size()-1; size++) {
//                                LinkedTreeMap<String, Object> selectData = theirTeam.get(size);
//                                int spell1Id = (int) (double) selectData.get("spell1Id");
//                                String id1 = dataDragon.getSummonerSpellData(spell1Id).id;
//                                String url1 = imgUrl+"/img/spell/"+id1+".png";
//                                int spell2Id = (int) (double) selectData.get("spell2Id");
//                                String id2 = dataDragon.getSummonerSpellData(spell2Id).id;
//                                String url2 = imgUrl+"/img/spell/"+id2+".png";
//                                int team = (int) (double) selectData.get("team");
//                                gameInfo.setSpell(size, team, url1, url2);
//                            }
//                            LinkedTreeMap<String,Object> timer = (LinkedTreeMap<String,Object>) ((LinkedTreeMap) cd.get("data")).get("timer");
//                            gameInfo.phase = (String) timer.get("phase");
//                        }
//                        delete.add(cd);
//                    }
//                }
//                for (LinkedTreeMap d : delete) {
//                    l.remove(d);
//                }
//                socketServer.sendMessage(gson.toJson(gameInfo));
//            }
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}