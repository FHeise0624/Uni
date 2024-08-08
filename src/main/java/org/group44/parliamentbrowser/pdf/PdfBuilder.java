package org.group44.parliamentbrowser.pdf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.group44.parliamentbrowser.Configuration;
import org.group44.parliamentbrowser.database.MongoDBConnectionHandler;
import org.group44.parliamentbrowser.types.Rede;
import org.group44.parliamentbrowser.types.Sitzung;
import org.group44.parliamentbrowser.types.Tagesordnung;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Klasse die aus einer Sitzung auf basis einer Latex Vorlage ein PDF erzeugen kann
 * 
 * @author Dominik, Cora
 */
public class PdfBuilder {

	private static Logger LOGGER = LoggerFactory.getLogger(PdfBuilder.class);

    private MongoDBConnectionHandler mongoDb;

    private String vorlagePath = null;
    
    /**
     * Erzeugt den PdfBuilder
     */
    public PdfBuilder() {
    	this.mongoDb = MongoDBConnectionHandler.getInstance();
    	this.vorlagePath = Configuration.getInstance().getLatexVorlagenFolder() + Configuration.getInstance().getLatexVorlageFile();
    }
    
    /**
     * Aktualisiert die Latex-Vorlage zur Erzeug der Sitzung-PDF Dokumente im Dateisystem
     * 
     * @param content - Inhalt der neuen Latex Vorlage 
     */
    public void updateSitzungVorlage(String content) {
    	try (FileWriter writer = new FileWriter(new File(this.vorlagePath))) {
    		LOGGER.info("Update Vorlage in {}", vorlagePath);
    		writer.write(content);
    	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * erstellt aus sitzung ein pdf als byte array
     * @throws InterruptedException
     * @throws IOException
     * @author dominik, cora
     */
    public byte[] sitzungToPdf(Integer wahlperiode, Integer sitzungsNummer) throws IOException, InterruptedException {
        // String inhalt des eingelesen latex dokuments
        String dokument = readVorlage(this.vorlagePath);

        //liste aller sitzungen
        Sitzung sitzung = mongoDb.readSitzung(wahlperiode, sitzungsNummer);
        if (sitzung != null) {
	        List<Tagesordnung> tagesordnungen = mongoDb.readTagesordnungen(wahlperiode, sitzungsNummer);
	        if (tagesordnungen != null && !tagesordnungen.isEmpty()) {
		        StringBuilder inhalt = new StringBuilder();
		        String sitzunTex = sitzung.toTex();
		        inhalt.append(sitzunTex).append("\n ");
		        inhalt.append(sitzunTex).append("\n ");
		
		        for (Tagesordnung tagesordnung : tagesordnungen){
		            String tagesOrdnungTex = tagesordnung.toTex();
		            inhalt.append(tagesOrdnungTex).append("\n ");
		
		            List<Rede> reden = getReden(tagesordnung);
		            for (Rede rede : reden){
		                String redeTex = rede.toTex();
		                // Ersetze "&" durch "\&" in der Rede
		                redeTex = cleanUpText(redeTex);
		                inhalt.append(redeTex).append("\n ");
		            }
		        }
		        // ersetze die gewünschte zeile es eingelesenen dokunments mit gewünschten parametern
		        dokument = dokument.replace("**** Hier kommen die Tagesordnungen rein ****", inhalt.toString());
		
		        return buildPDF(dokument);
	        }
        }
        return null;
    }
    
    /**
     * erstellt pdf datei
     * @throws InterruptedException
     * @throws IOException
     * @author dominik
     */
    public byte[] buildPDF(String dokument) throws InterruptedException, IOException {
    	String latexFile = Configuration.getInstance().getLatexOutputFolder() +  "sitzung.tex";
    	String pdfFile = Configuration.getInstance().getLatexOutputFolder() +  "sitzung.pdf";
    	writeToFile(latexFile, dokument);
    	ProcessBuilder pb  = new ProcessBuilder(Configuration.getInstance().getLatexCompiler(), "-output-directory=" + Configuration.getInstance().getLatexOutputFolder(), latexFile);
        // starte den process
        pb.redirectErrorStream(true);
        Process process = pb.start();

        // lese ein was im hintergrund passiert
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        // laufe jeden hintergrund Process ab um so aufhängen zu vermeiden
        String zeile;
        while ((zeile = reader.readLine()) != null){
        	LOGGER.info(zeile);
        }

        // prüfe ob pdf datei erstellt wurde
        int exitValue = process.waitFor();
        if (exitValue == 0) {
        	LOGGER.info("PDF-Erzeugung erfolgreich.");
            return Files.readAllBytes(new File(pdfFile).toPath());
        } else {
        	String message = "Fehler beim Erzeugen des PDFs. Exit-Code: " + exitValue;
        	LOGGER.error(message);
            return message.getBytes();
        }
    }

    /**
     * läuft durch vorlage und liest diese ein
     * @param pfad
     * @return inhalt
     * @throws IOException
     * @author dominik
     */
    private String readVorlage(String pfad) throws IOException{

        // String variable in der ich meine latex vorlage speicher
        StringBuilder inhalt = new StringBuilder();

        // insanz welche meine latex vorlage einlesen soll
        BufferedReader reader = new BufferedReader(new FileReader(pfad));

        // laufe durch das dokument und tue jede zeile in inhalt
        String zeile;
        while ((zeile = reader.readLine()) != null) {
            inhalt.append(zeile).append("\n");
        }

        reader.close();
        return inhalt.toString();
    }

    /**
     * schreibt ein neues latex dokument an einen gewünschten ort
     * @param pfad
     * @param inhalt
     * @throws IOException
     * @author dominik
     */
    private static void writeToFile(String pfad, String inhalt) throws IOException {

        // erstelle einen writer mit ziel pfad
        BufferedWriter writer = new BufferedWriter(new FileWriter(pfad));

        // lasse ihn den übergebenen inhalt überschreiben
        writer.write(inhalt);

        // schließe ihn wieder
        writer.close();
    }

    /**
     * gibt alle reden der tagesordnungen wieder
     * @param tagesordnung
     * @return reden
     * @author dominik
     */
    private List<Rede> getReden(Tagesordnung tagesordnung){

        // gehe durch alle tagesordnungen und holle ihre reden
        return redenAusTagesordnung(tagesordnung.getRedenIds());

    }

    /**
     * hollt alle reden mittel ihrer id´s
     * @param redeIDs
     * @return redenAusTg
     * @author dominik
     */
    private List<Rede> redenAusTagesordnung(List<String> redeIDs){
        // speichere alle reden aus der tagesordnung hier
        List<Rede> redenAusTg = new ArrayList<>();

        // gehe durch alle id und hole die jeweilige rede
        for (String id : redeIDs){
        	Rede rede = mongoDb.readRede(id);
        	if (rede == null) {
        		LOGGER.warn("Rede mit ID {} existiert nicht!", id);
        	} else {
        		redenAusTg.add(rede);
        	}
        }
        return  redenAusTg;
    }

    /**
     * ersetzt ungültige im text vorkommende zeichen für latex
     * @param inhalt
     * @return Der bereinigte Text
     * @author dominik
     */
    private String cleanUpText(String inhalt) {
        // ersetze schrägstriche die nicht von buchstaben oder zahlen umgeben sind
        inhalt = inhalt.replaceAll("(?<![a-zA-Z0-9])/(?![a-zA-Z0-9])", "//")
                .replaceAll("&", " und ")  // & mit und
                .replaceAll("\\u2003", ""); // das unicode leerzeichen entfernen

        return inhalt;
    }
}
