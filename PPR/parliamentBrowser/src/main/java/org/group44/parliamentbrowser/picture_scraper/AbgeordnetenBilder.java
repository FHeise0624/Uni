package org.group44.parliamentbrowser.picture_scraper;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.group44.parliamentbrowser.types.Abgeordneter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class AbgeordnetenBilder {
    private String suchbegriff;
    private String defaultBild = "https://www.bundestag.de/resource/blob/451032/13770bf5fd9369e9df924ef51f071a96/Image3x4.png";

    /**
     * konstruktor ruft webseite auf und hollt sich alle bilder der abgeordneten und tut sie in die datenbank
     * @throws IOException
     * @throws InterruptedException
     * @author dominik
     */
    public AbgeordnetenBilder(Abgeordneter abgeordneter) {

        // string des abgeordneten namens nac dem wir suchen wollen
        this.suchbegriff = URLEncoder.encode(("\"\"" + abgeordneter.getName() + ", " + abgeordneter.getVorname() + "\"\""), StandardCharsets.UTF_8);

        // url zur verbindung der bundestagswebseite ( biografien liste)
        String baseURL = "https://bilddatenbank.bundestag.de";
        String url  = baseURL + "/search/picture-result?query=" + suchbegriff;

        // document der bundestagswebseite (biografien)
        Document doc;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // alle listen elemente und deren a tag
        Elements listenElemente = doc.select("img");

        // gehe durch alle listen elemente und holle den link
        int counter = 0;
        int total = listenElemente.size();

        // wenn keine passenden bilder gefunden wurden, füge default bild hinzu
        if (total == 3) {
            abgeordneter.addBildURL(defaultBild);
        }

        // laufe durch alle bilder der seite
        for (Element e : listenElemente){
            // überspringe unnötige bilder
            if (counter < 3) {
                counter++;
                continue;
            }
            // bild url
            String bildURL = baseURL + e.attr("src");

            // gebe dem abgeordneten sien bild
            abgeordneter.addBildURL(bildURL);

            counter++;
        }
    }
}
