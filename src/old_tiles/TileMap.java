package old_tiles;
import processing.core.PApplet;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;

public class TileMap {
    private Tile[][] tiles;
    private PApplet parent;
    private String filepath;

    public TileMap(PApplet parent) {
        this.parent = parent;

        initEmpty(10, 10);
    }

    public TileMap(PApplet parent, String filepath) {
        this.parent = parent;

        load(filepath);
    }

    public String getFile() {
        return filepath;
    }

    private void initEmpty(int width, int height) {
        tiles = new Tile[height][width];

        TileFactory tileFactory = new TileFactory();

        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {
                tiles[y][x] = tileFactory.getTile(0, parent);
            }
        }
    }

    public void load(String filepath) {
        this.filepath = filepath;

        try {
            Scanner scanner = new Scanner(new File(filepath));
            if (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] cellTokens = line.split(",");
                if (cellTokens.length == 2) {
                    try {
                        int width = Integer.parseInt(cellTokens[0]);
                        int height = Integer.parseInt(cellTokens[1]);

                        initEmpty(width, height);
                    }
                    catch (NumberFormatException ex) {
                        System.out.println("Error: file corrupted.");
                        return;
                    }
                }
                else {
                    System.out.println("Error: file corrupted.");
                    return;
                }
            }
            
            TileFactory tileFactory = new TileFactory();

            for (int y = 0; y < tiles.length && scanner.hasNextLine(); y++) {
                String line = scanner.nextLine();
                String[] cellTokens = line.split(";");
                for (int x = 0; x < tiles[y].length && x < cellTokens.length; x++) {
                    String[] ids = cellTokens[x].split(",");
                    for (String idStr : ids) {
                        int id = 0;
                        try {
                            id = Integer.parseInt(idStr);
                        } catch (NumberFormatException ex) {
                            System.out.println("Error: file corrupted.");
                        }

                        if (tiles[y][x] == null || tiles[y][x].getID() == 0) {
                            tiles[y][x] = tileFactory.getTile(id, parent);
                        }
                        else if (tiles[y][x].isStackable()) {
                            tiles[y][x].stack(tileFactory.getTile(id, parent));
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Couldn't find texture list file '" + filepath + "'");
        }
    }

    public boolean save() {
        File file = new File(filepath);
        
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            
            FileWriter writer = new FileWriter(file);
            writer.write("" + tiles[0].length + "," + tiles.length + "\n");
            
            for (Tile[] tileLine : tiles) {
                String line = new String();
                for (Tile tile : tileLine) {
                    line += tile.getID();
                    Tile next = tile.getNext();
                    while (next != null) {
                        line += "," + next.getID();
                        next = tile.getNext();
                    }
                    line += ";";
                }
                writer.write(line);
            }
            
            writer.close();
        } 
        catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void draw() {
        draw(80);
    }

    public void draw(float size) {
        parent.push();
        for (int y = 0; y < tiles.length; y++) {
            parent.push();
            for (int x = 0; x < tiles[y].length; x++) {
                tiles[y][x].draw(size);
                parent.translate(size, 0);
            }
            parent.pop();
            parent.translate(0, size);
        }
        parent.pop();
    }
}