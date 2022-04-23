package jp.gn3.lol.datadragon;

import com.google.gson.internal.LinkedTreeMap;

public class SummonerSpell {
    public String type;
    public String version;
    public LinkedTreeMap<String, SummonerSpellData> data;
}
