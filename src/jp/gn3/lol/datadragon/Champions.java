package jp.gn3.lol.datadragon;

import com.google.gson.internal.LinkedTreeMap;

public class Champions {
    public String type;
    public String format;
    public String version;
    public LinkedTreeMap<String, ChampionData> data;
}
