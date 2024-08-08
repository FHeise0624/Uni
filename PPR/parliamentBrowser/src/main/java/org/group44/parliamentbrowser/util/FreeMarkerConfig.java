package org.group44.parliamentbrowser.util;

import freemarker.template.Configuration;
import freemarker.template.Version;

/**
 * Diese Klasse liefert die Konfiguration zur nutzung von FreeMarker templates.
 *
 * @author Felix Tams
 */
public class FreeMarkerConfig {

    /**
     * Erstellen der Konfiguration, bestehend aus der FreeMarker Version und dem Path unter dem die .ftl-Dateine
     * abgelegt sind.
     * @return configuration
     * @author Felix
     */
    public static Configuration configure() {
        Configuration configuration = new Configuration(new Version("2.3.31"));
        configuration.setClassForTemplateLoading(FreeMarkerConfig.class, "/templates");
        configuration.setDefaultEncoding("UTF-8");
        return configuration;
    }
}

