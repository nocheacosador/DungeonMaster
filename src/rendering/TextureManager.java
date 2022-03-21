package rendering;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Map;
import java.util.TreeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TextureManager {
    private class TextureImageInfo {
        String imagePath;
        String texturelistPath;

        TextureImageInfo(String imagePath, String texturelistPath) {
            this.imagePath = imagePath;
            this.texturelistPath = texturelistPath;
        }
    }

    private final TextureImageInfo[] TEXTURES = { 
        new TextureImageInfo("assets/tileset/dungeon_tileset.png", "assets/tileset/texturelist.txt"),
        new TextureImageInfo("assets/tileset/gothicvania patreon collection/demon-Files/PNG/demon-idle.png", 
                             "assets/tileset/gothicvania patreon collection/demon-Files/PNG/demon-idle-subtextures.txt")
    };

    static private TextureManager instance = null;

    private Map<String, Texture> textureMap;

    private TextureManager() {}

    public void load(PApplet applet) {
        textureMap = new TreeMap<String, Texture>();

        for (TextureImageInfo textureImageInfo : TEXTURES) {
            System.out.println("Loading texture file '" + textureImageInfo.imagePath + "'...");
            PImage texture = applet.loadImage(textureImageInfo.imagePath);

            try {
                Scanner textureList = new Scanner(new File(textureImageInfo.texturelistPath));
                while (textureList.hasNextLine()) {
                    String line = textureList.nextLine();
                    String[] tokens = line.split("\\s+");

                    int left, top, right, bottom;

                    if (tokens.length == 5) {
                        try {
                            left = Integer.parseInt(tokens[1]);
                            top = Integer.parseInt(tokens[2]);
                            right = Integer.parseInt(tokens[3]) + left;
                            bottom = Integer.parseInt(tokens[4]) + top;
                        }
                        catch (NumberFormatException ex) {
                            System.out.println("Error: failed to parse texture list line.");
                            continue;
                        }
                        
                        if (textureMap.containsKey(tokens[0])) {
                            System.out.println("Error: failed to add texture. Theres already texture with key: '" + tokens[0] + "'.");
                            continue;
                        }
                        textureMap.put(tokens[0], new Texture(texture, left, top, right, bottom));
                        System.out.println("  Texture '" + tokens[0] + "' added.");
                    }
                    if (tokens.length == 6) {
                        try {
                            int frameCount = Integer.parseInt(tokens[5]);
                            
                            for (int frame = 0; frame < frameCount; frame++) {
                                int width = Integer.parseInt(tokens[3]);
                                left = Integer.parseInt(tokens[1]) + width * frame;
                                top = Integer.parseInt(tokens[2]);
                                right = Integer.parseInt(tokens[3]) + left;
                                bottom = Integer.parseInt(tokens[4]) + top;
                                
                                String key = tokens[0] + "_f" + frame;
                                
                                if (textureMap.containsKey(key)) {
                                    System.out.println("Error: failed to add texture. Theres already texture with key: '" + tokens[0] + "'.");
                                    continue;
                                }

                                textureMap.put(key, new Texture(texture, left, top, right, bottom));
                                System.out.println("  Texture '" + key + "' added.");
                            }
                        }
                        catch (NumberFormatException ex) {
                            System.out.println("Error: failed to parse texture list line.");
                        }
                    }
                }
                textureList.close();
            } catch (FileNotFoundException e) {
                System.out.println("Error: Couldn't find texture list file '" + textureImageInfo.texturelistPath + "'");
            }
        }
    }

    public Texture getTexture(String key) {
        Texture t = textureMap.get(key);
        if (t == null) {
            System.out.println("Error: key '" + key + "' retured null.");
        }
        return textureMap.get(key);
    }

    public static TextureManager getInstance() {
        if (instance == null) {
            instance = new TextureManager();
        }
        return instance;
    }
}