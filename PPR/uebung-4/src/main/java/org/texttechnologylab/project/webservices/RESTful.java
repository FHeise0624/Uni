package org.texttechnologylab.project.webservices;

import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.get;
import static spark.Spark.port;

/**
 * This Class defines the REST Webservice and the available Pages with their content.
 * @author Felix Tams
 */
public class RESTful {
    // define the FreeMarkerEngine for usage of Template-files.
    private static final FreeMarkerEngine fME = new FreeMarkerEngine(FreeMarkerConfig.configure());

    /**
     * Method  that creates the webservice and makes it available  via Browser.
     * @param args
     */
    public static void main(String[] args) {
        port(1808); // Set the port for your application

        //Define route using template for the welcome page.
        get("/welcome", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            return fME.render(new ModelAndView(model, "welcome.ftl"));
        });

        //Define route for the speaker page.
        get("/speakers", (req, res) ->{
           Map<String, Object> model = new HashMap<>();
           return fME.render(new ModelAndView(model, "speaker.ftl"));
        });

        //Define route for the speeches page.
        get("/speeches", (req, res) -> {
           Map<String, Object> model = new HashMap<>();
           return  fME.render(new ModelAndView(model, "speech.ftl"));
        });
    }
}