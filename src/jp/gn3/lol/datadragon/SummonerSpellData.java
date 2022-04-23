package jp.gn3.lol.datadragon;

import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class SummonerSpellData {

    public String id;
    public String name;
    public String description;
    public String tooltip;
    public int maxrank;
    public ArrayList<Integer> cooldown;
    public String cooldownBurn;
    public ArrayList<Integer> cost;
    public String costBurn;
    public LinkedTreeMap datavalues;
    public ArrayList effect;
    public ArrayList effectBurn;
    public ArrayList vars;
    public int key;
    public int summonerLevel;
    public ArrayList modes;
    public String costType;
    public String maxammo;
    public ArrayList range;
    public String rangeBurn;
    public Image image;
    public String resource;
}
