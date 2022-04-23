package jp.gn3.lol.datadragon;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class ImageDownloader {

    private final DataDragon dataDragon;
    private final String patch;
    private String icon;
    private final String splash;
    private final String spell;

    public ImageDownloader(int i) throws IOException {
        dataDragon = new DataDragon("en_US");
        String file = new File(".").getAbsoluteFile().getParent();
        patch = dataDragon.getVersions().get(i);
        icon = file + "\\img";
        if (!new File(icon).exists()) {
            new File(icon).mkdir();
        }
        icon = file + "\\img\\icon";
        if (!new File(icon).exists()) {
            new File(icon).mkdir();
        }
        splash = file + "\\img\\splash";
        if (!new File(splash).exists()) {
            new File(splash).mkdir();
        }
        spell = file + "\\img\\spell";
        if (!new File(spell).exists()) {
            new File(spell).mkdir();
        }
        System.out.println("start");

        try {
            getImage(this.patch);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String banIcon = "https://ddragon.leagueoflegends.com/cdn/%s/img/champion/%s.png";
    String spellIcon = "https://ddragon.leagueoflegends.com/cdn/%s/img/spell/%s.png";

    public void getImage(String latestPatch) throws IOException {

        int size = dataDragon.getChampions().size() * 2 + dataDragon.getSummonerSpells().size();
        int count = 0;
        for (Map.Entry<Integer, ChampionData> champs : dataDragon.getChampions().entrySet()) {

            String s = splash + "\\" + champs.getValue().id + ".jpg";
            String i = icon + "\\" + champs.getValue().id + ".png";
            if (!new File(s).exists()) {
                downloadImage(getChampionCenterUrl(champs.getValue().name.replace(" ", "_")), s, "jpg");
            }
            count++;
            System.out.println("画像をダウンロードしています...(" + count + "/" + size + ")");
            if (!new File(i).exists()) {
                downloadImage(String.format(banIcon, latestPatch, champs.getValue().id), i, "png");
            }
            count++;
            System.out.println("画像をダウンロードしています...(" + count + "/" + size + ")");
        }
        for (Map.Entry<Integer, SummonerSpellData> spells : dataDragon.getSummonerSpells().entrySet()) {
            String s = spell + "\\" + spells.getValue().id + ".png";
            if (!new File(s).exists()) {
                downloadImage(String.format(spellIcon, latestPatch, spells.getValue().id), spell + "\\" + spells.getValue().id + ".png", "png");
            }
            count++;
            System.out.println("画像をダウンロードしています...(" + count + "/" + size + ")");
        }
        System.out.println("画像のダウンロードが終わりました (" + count + "/" + size + ")");
    }

    public void downloadImage(String url, String fileName, String format) throws IOException {
        URLConnection cn = new URL(url).openConnection();
        cn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36");
        BufferedImage read = ImageIO.read(cn.getInputStream());
        ImageIO.write(read, format, new File(fileName));
        System.out.println("download: \"" + fileName + "\"");
    }

    public String getChampionCenterUrl(String championName) throws IOException {
        String url = "https://leagueoflegends.fandom.com/wiki/" + championName + "/LoL";
        Document doc = null;
        try {
            Connection connect = Jsoup.connect(url);
            connect.timeout(10 * 1000);
            doc = connect.get();
        } catch (Exception e) {
            Connection connect = Jsoup.connect(url);
            connect.timeout(10 * 1000);
            doc = connect.get();
        }
        if (doc == null) {
            throw new Error("Error: " + url);
        }
        return doc.select(".FullWidthImage.fxaa .image").attr("href");
    }

//    public BufferedImage invert(BufferedImage img) {
//        int y, x_s, x_e;
//        int width, height;
//        int color_s, color_e;
//        width = img.getWidth();
//        height= img.getHeight();
//
//        for (y = 0; y < height; y++) {
//            x_s = 0;
//            x_e = width - 1;
//            while ( x_s < x_e ) {
//                color_s = img.getRGB( x_s, y);
//                color_e = img.getRGB( x_e, y);
//                img.setRGB( x_s, y, color_e );
//                img.setRGB( x_e, y, color_s );
//                x_s++;
//                x_e--;
//            }
//        }
//        return img;
//    }
}
