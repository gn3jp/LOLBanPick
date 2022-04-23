package jp.gn3.lol.banpicklive;

public class PlayerInfo {

    public String summonerName = "";
    public String pick = null;
    public String spell1Id = null;
    public String spell2Id = null;
    public boolean completed = false;

    public void setSpell(String url1, String url2) {
        this.spell1Id = url1;
        this.spell2Id = url2;
    }

    public void setPick(String url, boolean completed) {
        this.pick = url;
        this.completed = completed;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }
}
