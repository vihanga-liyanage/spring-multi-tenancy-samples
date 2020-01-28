package com.alonsegal.multitenancy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to hold the language identifier per request.
 */
public class LanguageContext {

    private static Logger log = LoggerFactory.getLogger(LanguageContext.class.getName());

    private static ThreadLocal<String> currentLanguage = new ThreadLocal<>();

    public static void setCurrentLanguage(String language) {

        log.debug("Setting language to: " + language);
        currentLanguage.set(language);
    }

    public static String getCurrentLanguage() {

        return currentLanguage.get();
    }

    public static void clear() {

        currentLanguage.set(null);
    }
}
