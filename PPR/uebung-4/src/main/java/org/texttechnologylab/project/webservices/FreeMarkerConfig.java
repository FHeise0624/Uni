package org.texttechnologylab.project.webservices;

import freemarker.template.*;

/**
 * Class that defines settings for template-usage for webservices.
 * @author Felix Tams
 */
public class FreeMarkerConfig {
    public static Configuration configure(){
        Configuration config = new Configuration(new Version(2, 3, 26));
        config.setClassForTemplateLoading(FreeMarkerConfig.class, "/templates/");
        return config;
    }
}
