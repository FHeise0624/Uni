package org.group44.parliamentbrowser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.group44.parliamentbrowser.exception.BundestagConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Die Klasse beinhalte alle Konfigurationen der Anwenudng (Latex, NLP, Datenbank)
 * 
 * @author Cora
 */
public class Configuration {

	private static Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	private static final String PROPERTY_FILE = "/parliamentbrowser.properties";

	private static Configuration configuration = null;

	private String remoteHost;
	private Integer remotePort;
	private String remoteDatabase;
	private String remoteUser;
	private String remotePassword;

	private String latexCompiler;
	private String latexVorlagenFolder;
	private String latexVorlageFile;
	private String latexOutputFolder;

	private boolean nlpActive = false;
	
	/**
	 * Zugriff auf die Konfiguration erfolgt über diese Methode, ist ein Singleton, da es nur eine Kopnfiguration geben kann
	 * 
	 * @return Configuration - aktuelle Konfiguration
	 */
	public static Configuration getInstance() {
		if (configuration == null) {
			configuration = new Configuration(PROPERTY_FILE);
		}
		return configuration;
	}

	private Configuration(String propertyFile) throws BundestagConfigurationException {
		Properties properties = new Properties();

		try (InputStream is = Configuration.class.getResourceAsStream(PROPERTY_FILE)) {
			properties.load(is);
			this.remoteHost = properties.getProperty("remote_host");
			this.remotePort = Integer.valueOf(properties.getProperty("remote_port"));
			this.remoteDatabase = properties.getProperty( "remote_database");
			this.remoteUser = properties.getProperty("remote_user");
			this.remotePassword = properties.getProperty("remote_password");
			this.latexCompiler = properties.getProperty("latex.compiler");
			this.latexVorlagenFolder = properties.getProperty("latex.vorlage.folder");
			this.latexVorlageFile = properties.getProperty("latex.vorlage.file");
			this.latexOutputFolder = properties.getProperty("latex.output.folder");
			this.nlpActive = Boolean.getBoolean(properties.getProperty("nlp.active"));
			LOGGER.info("Konfiguration aus Datei {} erfolgreich geladen.", PROPERTY_FILE);
		} catch (IOException e) {
			String message = String.format("Konfigurations-Datei '%s' nicht im Klassenpfad gefunden!", PROPERTY_FILE);
			throw new BundestagConfigurationException(message);
		}
    }

	/**
	 * Datenbank-Konfiguration (Mongo-DB): Host
	 * 
	 * @return Host auf dem die Datenbank zur verfügung steht
	 */
	public String getRemoteHost() {
		return remoteHost;
	}

	/**
	 * Datenbank-Konfiguration (Mongo-DB): Port
	 * 
	 * @return Port auf dem die Datenbank zur verfügung steht
	 */
	public Integer getRemotePort() {
		return remotePort;
	}

	/**
	 * Datenbank-Konfiguration (Mongo-DB): Database
	 * 
	 * @return Name der Datenbank 
	 */
	public String getRemoteDatabase() {
		return remoteDatabase;
	}

	/**
	 * Datenbank-Konfiguration (Mongo-DB): User
	 * 
	 * @return Datenbankbenutzer für den Zugriff auf die Datenbank 
	 */
	public String getRemoteUser() {
		return remoteUser;
	}

	/**
	 * Datenbank-Konfiguration (Mongo-DB): Password
	 * 
	 * @return Passwort des Benutzers für den Zugriff auf die Datenbank 
	 */
	public char[] getRemotePassword() {
		return remotePassword.toCharArray();
	}

	/**
	 * Pfad zum Latex-Compiler um aus dem Latex ein PDF zu erzeugen
	 * 
	 * @return Pfad zu Latex-Compiler
	 */
	public String getLatexCompiler() {
		return latexCompiler;
	}

	/**
	 * Ordner in dem die Latex-Vorlage gespeichert wird
	 * 
	 * @return Ordner mit Latex-Vorlage
	 */
	public String getLatexVorlagenFolder() {
		return latexVorlagenFolder;
	}

	/**
	 * Name der verwendeten Latex-Vorlage
	 * 
	 * @return Name der verwendeten Latex-Vorlage
	 */
	public String getLatexVorlageFile() {
		return latexVorlageFile;
	}

	/**
	 * Ordner in dem die temporär erzeugten Datei zur PDF Generierung gespeichert werden sollen.
	 * 
	 * @return Ordner für temporär erzeugte Dateien 
	 */
	public String getLatexOutputFolder() {
		return latexOutputFolder;
	}

	/**
	 * Schlater der angibt ob die NLP Analyse aktiviert ist.
	 * 
	 * @return true, wenn NLP Analyse erfolgen soll, sonst false
	 */
	public boolean isNlpActive() {
		return nlpActive;
	}
}
