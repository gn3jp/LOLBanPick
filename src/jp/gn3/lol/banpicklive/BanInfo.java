package jp.gn3.lol.banpicklive;

public class BanInfo {

    public String pick = null;
    public boolean completed = false;

    public void setPick(String url, boolean completed) {
        this.pick = url;
        this.completed = completed;
    }
}
