package gui.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import processing.core.PApplet;
import processing.core.PFont;

public class FontManager {
    private static final String FONT_DIRECTORY = "assets/fonts/";
    private static FontManager instance;

    private Map<String, PFont> fonts;
    private PApplet papplet;

    private FontManager() {
        fonts = new TreeMap<String, PFont>();
    }

    public static FontManager getInstance() {
        if (instance == null) {
            instance = new FontManager();
        }
        return instance;
    }

    public PFont defaultFont;

    private List<String> systemFonts;

    public void load(PApplet papplet) {
        this.papplet = papplet;
        defaultFont = papplet.createFont("assets/fonts/consola.ttf", 48, true);
        systemFonts = Arrays.asList(PFont.list());
    }

    public PFont get(String name, int size) {
        String key = name + "$" + size;
        
        PFont font = fonts.get(key);

        if (font != null) {
            return font;
        }

        font = createFont(name, size, FONT_DIRECTORY);

        if (systemFonts != null && systemFonts.contains(name)) {
            font = createFont(name, size);
            
            if (font != null) {
                return font;
            }
        }

        return defaultFont;
    }

    private PFont createFont(String name, int size) {
        PFont font = papplet.createFont(name, size, true);
        
        if (font != null) {
            String internalName = name + "$" + size;
            fonts.put(internalName, font);

            System.out.println("Font '" + name + "' of size " + size + " created. Internal name: " + internalName);
        }
        return font;
    }

    private PFont createFont(String name, int size, String directory) {
        PFont font = papplet.createFont(directory + name + ".ttf", size, true);
        
        if (font != null) {
            String internalName = name + "$" + size;
            fonts.put(internalName, font);

            System.out.println("Font '" + name + "' of size " + size + " created. Internal name: " + internalName);
        }
        return font;
    }
}
