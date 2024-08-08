package org.group44.parliamentbrowser.nlp_analyse;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.uima.fit.factory.JCasFactory;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.JCas;
import org.group44.parliamentbrowser.types.Rede;
import org.hucompute.textimager.uima.type.Sentiment;
import org.hucompute.textimager.uima.type.category.CategoryCoveredTagged;

import de.tudarmstadt.ukp.dkpro.core.api.lexmorph.type.pos.POS;
import de.tudarmstadt.ukp.dkpro.core.api.ner.type.NamedEntity;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Sentence;
import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;
import de.tudarmstadt.ukp.dkpro.core.api.syntax.type.dependency.Dependency;

public class NLP {

    private JCas redeJcas;
    private DUUI duuiVerbindung;

    /**
     * startet nlp-analyse
     * @author dominik
     * @throws Exception
     */
    public List<Rede> doNlp(List<Rede> alleReden) throws Exception {

        // verbinden mit docker
        this.duuiVerbindung = new DUUI();

        // um fortschritt anzuzeigen
        int counter = 0;
        int total = alleReden.size();

        // ein Jcas object erstellen in dem später eine rede drinne ist
        this.redeJcas = JCasFactory.createText(" ");

        // durch alle reden laufen und Sätze raus filtern
        for (Rede rede : alleReden) {

            // prüfen ob dokument schon eingelsen wurde
            if (!rede.getTopics().isEmpty()) {
                counter++;
                System.out.println("Current progress: " + counter + " / " + total);
                continue;
            }

            // rufe funktion fürs verarbeiten der red auf
            verarbeiteReden(rede);

            // counter erhöhen
            counter ++;
            System.out.println("Current progress: " + counter + " / " + total);
        }
    	return alleReden;
    }
    
    /**
     * verarbeitert die rede und updated das dokument
     * @param rede
     * @throws Exception
     * @author dominik
     */
    private void verarbeiteReden(Rede rede) throws Exception {

        // den inhalt einer rede rausziehen
        String redeInhalt = rede.getText();

        // die id der rede bekommen für den composer in duui
        String redeID = rede.getRedeId();

        // aicher stellen, das jcas sauber und frei ist für nächste nutzung
        redeJcas.reset();

        // jcas objekt einer rede erzeugen um nlp analyse darauf durchzuführen
        redeJcas.setDocumentText(redeInhalt);

        // alle sätze einer rede
        Collection<Sentence> alleSaetze = duuiVerbindung.getSentences(redeJcas, redeID);

        // alle Titel einer rede
        Collection<CategoryCoveredTagged> alleTitel = JCasUtil.select(redeJcas, CategoryCoveredTagged.class);

        // erstellen von listen in denen ich die nlp elemente speichern kann

        List<String> sentences = alleSaetze.stream() // öffnen eines streams aller sätze
                .map(Sentence :: getCoveredText) // jeder satz zu seinem text umgewandelt
                .collect(Collectors.toList()); // packt ergebnisse des strams in liste

        List<String> tokens = alleSaetze.stream()
                .flatMap(satz -> JCasUtil.selectCovered(Token.class, satz).stream())
                .map(Token :: getCoveredText)
                .collect(Collectors.toList());

        List<String> pos = alleSaetze.stream()
                .flatMap(satz -> JCasUtil.selectCovered(POS.class, satz).stream())
                .map(POS :: getPosValue)
                .collect(Collectors.toList());

        List<Double> sentiments = alleSaetze.stream()
                .flatMap(satz -> JCasUtil.selectCovered(Sentiment.class, satz).stream())
                .map(Sentiment :: getSentiment)
                .collect(Collectors.toList());

        List<String> namedEntities = alleSaetze.stream()
                .flatMap(satz -> JCasUtil.selectCovered(NamedEntity.class, satz).stream())
                .map(NamedEntity ::getCoveredText)
                .collect(Collectors.toList());

        List<String> dependencies = alleSaetze.stream()
                .flatMap(satz -> JCasUtil.selectCovered(Dependency.class, satz).stream())
                .map(Dependency::getDependencyType)
                .collect(Collectors.toList());

        List<String> topics = alleTitel.stream()
                .map(CategoryCoveredTagged ::getValue)
                .collect(Collectors.toList());

        // die nlp attribute in das rede dokument packen
        rede.setSentences(sentences);
        rede.setDependencies(dependencies);
        rede.setPos(pos);
        rede.setNamedEntities(namedEntities);
        rede.setTokens(tokens);
        rede.setTopics(topics);
        rede.setSentiments(sentiments);
    }
}
