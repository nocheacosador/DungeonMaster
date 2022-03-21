package tiles;

import rendering.Texture;
import rendering.TextureManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class TileFactory {
    private class TileInfo {
        public final int id;
        public final String name;
        public final Texture[] textures;
        
        public TileInfo(int id, String name, Texture[] textures) {
            this.id = id;
            this.name = name;
            this.textures = textures;
        }
    }
    
    private static TileFactory instance;

    private final String TILELIST_PATH = "assets/tileset/tilelist.txt";
    
    private List<TileInfo> tileInfoList;

    private TileFactory() {}
    
    public static TileFactory getInstance() {
        if (instance == null) {
            instance = new TileFactory();
        }
        return instance;
    }

    public void load() {
        int tileCount = 0;
        TextureManager textureManager = TextureManager.getInstance();
        
        tileInfoList = new ArrayList<TileInfo>();
        try {
            System.out.println("Loading tilelist file '" + TILELIST_PATH + "'...");
            Scanner tilelistScaner = new Scanner(new File(TILELIST_PATH));
            while (tilelistScaner.hasNextLine()) {
                String line = tilelistScaner.nextLine();

                if (line.length() == 0 || line.getBytes()[0] == '#') {        // if line starts with # its a comment
                    continue;
                }

                String[] tokens = line.split("\\s+");

                if (tokens.length > 1) {                
                    Texture[] textures = new Texture[tokens.length - 1];

                    for (int i = 0; i < textures.length; i++) {
                        textures[i] = textureManager.getTexture(tokens[i + 1]);
                    }

                    tileInfoList.add(new TileInfo(tileCount, tokens[0], textures));
                    System.out.println("  Tile '" + tokens[0] + "' created. ID = " + tileCount);
                    tileCount++;
                }
            }

        } catch(FileNotFoundException e) {
            System.out.println("Error: Couldn't find texture list file '" + TILELIST_PATH + "'");
        }

        System.out.println(tileCount + " tiles created.");
    }

    public Tile createTile(String name) {
        for (TileInfo ti : tileInfoList) {
            if (ti.name == name) {
                if (ti.textures.length == 1) {
                    return new Tile(ti.id, ti.name, ti.textures[0]);
                }
                else {
                    return new AnimatedTile(ti.id, ti.name, ti.textures);
                }
            }
        }
        
        return null;
    }

    public Tile createTile(int id) {
        if (id < 0 || id >= tileInfoList.size()) {
            return null;
        }
        
        TileInfo ti = tileInfoList.get(id);
        if (ti.textures.length == 1) {
            return new Tile(ti.id, ti.name, ti.textures[0]);
        }
        else {
            return new AnimatedTile(ti.id, ti.name, ti.textures);
        }
    }

    public int getTileCount() {
        return tileInfoList.size();
    }
}
