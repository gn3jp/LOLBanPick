package jp.gn3.lol.datadragon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataDragon {

    private final Gson gson;
    private String latestPatch;
    private String lang = "ja_JP";

    private final LinkedTreeMap<Integer, ChampionData> champs = new LinkedTreeMap<>();
    private final LinkedTreeMap<String, Integer> idToChampKey = new LinkedTreeMap<>();
    private final LinkedTreeMap<String, Integer> nameToChampKey = new LinkedTreeMap<>();

    private final LinkedTreeMap<Integer, SummonerSpellData> spells = new LinkedTreeMap<>();
    private final LinkedTreeMap<String, Integer> idToSpellKey = new LinkedTreeMap<>();
    private final LinkedTreeMap<String, Integer> nameToSpellKey = new LinkedTreeMap<>();

    public DataDragon() {
        gson = new GsonBuilder().create();
        try {
            updateLatestPatch();
            updateChampionData();
            updateSummonerSpellData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public DataDragon(String lang) {
        this.lang = lang;
        gson = new GsonBuilder().create();
        try {
            updateLatestPatch();
            updateChampionData();
            updateSummonerSpellData();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ChampionData getChampionData(Object key) {
        if (key instanceof Integer) {
            return champs.get(key);
        } else if (key instanceof String) {
            if (nameToChampKey.containsKey(key)) {
                return champs.get(nameToChampKey.get(key));
            } else if (idToChampKey.containsKey(key)) {
                return champs.get(idToChampKey.get(key));
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public SummonerSpellData getSummonerSpellData(Object key) {
        if (key instanceof Integer) {
            return spells.get(key);
        } else if (key instanceof String) {
            if (nameToSpellKey.containsKey(key)) {
                return spells.get(nameToSpellKey.get(key));
            } else if (idToSpellKey.containsKey(key)) {
                return spells.get(idToSpellKey.get(key));
            } else {
                for (Map.Entry<String, Integer> ids : idToSpellKey.entrySet()) {
                    String lKey = ((String) key).toLowerCase();
                    if (ids.getKey().toLowerCase().contains(lKey)) {
                        return spells.get(ids.getValue());
                    }
                }
            }
        }
        return null;
    }

    public LinkedTreeMap<Integer, ChampionData> getChampions() {
        return champs;
    }

    public LinkedTreeMap<Integer, SummonerSpellData> getSummonerSpells() {
        return spells;
    }

    public void update() {
        try {
            if (updateLatestPatch()) {
                updateChampionData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLatestPatch() throws IOException {
        updateLatestPatch();
        return latestPatch;
    }

    private boolean updateLatestPatch() throws IOException {
        ArrayList<String> versions = request("https://ddragon.leagueoflegends.com/api/versions.json", ArrayList.class);
        boolean updated = latestPatch == versions.get(0);
        latestPatch = versions.get(0);
        return !updated;
    }

    public ArrayList<String> getVersions() throws IOException {
        return request("https://ddragon.leagueoflegends.com/api/versions.json", ArrayList.class);
    }

    private void updateSummonerSpellData() throws IOException {
        SummonerSpell summonerSpells = request("http://ddragon.leagueoflegends.com/cdn/" + latestPatch + "/data/" + this.lang + "/summoner.json", SummonerSpell.class);
        spells.clear();
        idToSpellKey.clear();
        nameToSpellKey.clear();
        for (Map.Entry<String, SummonerSpellData> spell : summonerSpells.data.entrySet()) {
            spells.put(spell.getValue().key, spell.getValue());
            idToSpellKey.put(spell.getValue().id, spell.getValue().key);
            nameToSpellKey.put(spell.getValue().name, spell.getValue().key);
        }
    }

    private void updateChampionData() throws IOException {
        Champions champions = request("http://ddragon.leagueoflegends.com/cdn/" + latestPatch + "/data/" + this.lang + "/champion.json", Champions.class);
        champs.clear();
        idToChampKey.clear();
        nameToChampKey.clear();
        List<String> tags = new ArrayList<>();
        for (Map.Entry<String, ChampionData> champData : champions.data.entrySet()) {
            champs.put(champData.getValue().key, champData.getValue());
            idToChampKey.put(champData.getValue().id, champData.getValue().key);
            nameToChampKey.put(champData.getValue().name, champData.getValue().key);
        }
    }

    private <T> T request(String requestUrl, Class<T> classOfT) throws IOException {
        InputStreamReader isr =
                new InputStreamReader(new URL(requestUrl).openConnection().getInputStream());
        JsonReader jsr = new JsonReader(isr);
        T t = gson.fromJson(jsr, classOfT);
        return t;
    }
}