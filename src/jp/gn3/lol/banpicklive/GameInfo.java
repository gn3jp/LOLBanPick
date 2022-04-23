package jp.gn3.lol.banpicklive;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class GameInfo {

    public GameInfo() {
    }

    public String blueTeamName = "";
    public String blueTeamCoachName = "";
    public String redTeamName = "";
    public String redTeamCoachName = "";

    public int progress = -1;
    public boolean banProgress = true;

    public String phase;

    public List<PlayerInfo> players = new ArrayList<>() {{
        add(new PlayerInfo());//0
        add(new PlayerInfo());//1
        add(new PlayerInfo());//2
        add(new PlayerInfo());//3
        add(new PlayerInfo());//4
        add(new PlayerInfo());//5
        add(new PlayerInfo());//6
        add(new PlayerInfo());//7
        add(new PlayerInfo());//8
        add(new PlayerInfo());//9
    }};//01234blue 56789red

    public List<BanInfo> bans = new ArrayList<>() {{
        add(new BanInfo());//0
        add(new BanInfo());//1
        add(new BanInfo());//2
        add(new BanInfo());//3
        add(new BanInfo());//4
        add(new BanInfo());//5
        add(new BanInfo());//6
        add(new BanInfo());//7
        add(new BanInfo());//8
        add(new BanInfo());//9
    }};

    public String getJson() {
        return new Gson().newBuilder().create().toJson(this);
    }

    public void setProgress(int id, boolean ban) {
        progress = idToListId(id);
        banProgress = ban;
    }

    public int idToListId(int id) {
        int[] listIds = {1, 6, 2, 7, 3, 8, 1, 6, 7, 2, 3, 8, 9, 4, 10, 5, 9, 4, 5, 10};
        return listIds[id - 1];
    }

    public void setSpell(int i, int team, String url1, String url2) {
        int listId = team == 1 ? i : 5 + i;
        players.get(listId).setSpell(url1, url2);
    }

    public void setSummonerName(int id, String name) {
        players.get(id).setSummonerName(name);
    }

    public void setPick(int id, String url, boolean completed, boolean ban) {
        int listId = idToListId(id);
        if (ban) {
            bans.get(listId - 1).setPick(url, completed);
        } else {
            players.get(listId - 1).setPick(url, completed);
        }
    }
}
