package org.group44.parliamentbrowser.nlp_analyse;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import org.apache.commons.compress.compressors.CompressorException;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.apache.uima.util.InvalidXMLException;
import org.texttechnologylab.DockerUnifiedUIMAInterface.DUUIComposer;
import org.texttechnologylab.DockerUnifiedUIMAInterface.driver.DUUIRemoteDriver;
import org.texttechnologylab.DockerUnifiedUIMAInterface.lua.DUUILuaContext;
import org.texttechnologylab.DockerUnifiedUIMAInterface.pipeline_storage.sqlite.DUUISqliteStorageBackend;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Collection;

public class DUUI {
    private final DUUIComposer composer;

    /**
     * im konstruktor composer übergeben
     * @author dominik
     * @throws Exception
     */
    public DUUI() throws Exception {
        this.composer = createComposer();
    }

    /**
     * composer erstellen mit erforderlichen parametern
     * @return composer
     * @throws SQLException
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws CompressorException
     * @throws InvalidXMLException
     * @throws SAXException
     * @author dominik
     */
    private DUUIComposer createComposer() throws Exception {
        // Führe den Task mit n Prozessen (Instanzen) aus.
        int iWorkers = 1;

        DUUISqliteStorageBackend sqlite = new DUUISqliteStorageBackend("statistic.db")
                .withConnectionPoolSize(iWorkers);

        // Lua-Kontext für die Nutzung von Lua
        DUUILuaContext ctx = new DUUILuaContext().withJsonLibrary();

        // Instanziierung des Composers, mit einigen Parametern
        DUUIComposer composer = new DUUIComposer()
                .withSkipVerification(true)     // wir überspringen die Verifikation aller Componenten =)
                .withLuaContext(ctx)            // wir setzen den definierten Kontext
                .withStorageBackend(sqlite)     // SQLite-Backend (optional)
                .withWorkers(iWorkers);         // wir geben dem Composer eine Anzahl an Threads mit.

        DUUIRemoteDriver driver = new DUUIRemoteDriver();

        composer.addDriver(driver);

        // fügt componenten zum composer hinzu
        composer.add(new DUUIRemoteDriver.Component("http://127.0.0.1:1001")
                .withScale(iWorkers)
                .build());

        composer.add(new DUUIRemoteDriver.Component("http://127.0.0.1:1002")
                .withScale(iWorkers)
                .withParameter("selection", "text")
                .build());

        composer.add(new DUUIRemoteDriver.Component("http://127.0.0.1:1000")
                .withScale(iWorkers)
                .build());

        return composer;
    }

    /**
     * lässt remote server laufen und hollt alle sätze aus dem übergebenen text
     * @author dominik
     * @param jcas
     * @param id
     * @return Collection<Sentence>
     * @throws Exception
     */
    public Collection<Sentence> getSentences(JCas jcas, String id) throws Exception {

        System.out.println("composer run startet");
        composer.run(jcas, id);

        System.out.println("composer run endet");
        return JCasUtil.select(jcas, Sentence.class);
    }
}