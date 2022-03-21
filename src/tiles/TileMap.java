package tiles;

import rendering.ComplexRenderable;
import rendering.Renderable;
import rendering.Renderer;
import rendering.Vertex;

import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import processing.core.PConstants;
import processing.core.PGraphics;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class TileMap implements ComplexRenderable {
    public static final int TILE_SIZE_ORIGINAL = 16;
    public static final int TILE_SCALE = 4;
    public static final int TILE_SIZE = TILE_SCALE * TILE_SIZE_ORIGINAL;

    public class Slot extends Renderable {
        private Tile back;
        private Tile mid;
        private Tile front;

        public boolean walkable = false;
        public boolean spawn = false;

        public int spawnID = -1;

        public int row;
        public int column;

        private Vertex[] vertices;

        public boolean outline = false;
        public float[] outlineColor = new float[] { 100, 100, 100 };

        public boolean fill = false;
        public float[] fillColor = new float[] { 255, 255, 255, 100 };

        public Slot() {
            this(0, 0);
        }

        public Slot(int row, int col) {
            setPosition(row, col);

            this.layer = Renderer.GUI;
        }

        public void setPosition(int row, int col) {
            this.row = row;
            this.column = col;
    
            generateVertices();
        }

        private void generateVertices() {
            float width = TileMap.TILE_SIZE;
            float height = TileMap.TILE_SIZE;
            float xOffset = TileMap.TILE_SIZE * row;
            float yOffset = TileMap.TILE_SIZE * column;
    
            vertices = new Vertex[] {
                new Vertex(xOffset,         yOffset),
                new Vertex(xOffset,         yOffset - height),
                new Vertex(xOffset + width, yOffset - height),
                new Vertex(xOffset + width, yOffset)
            };
        }

        public boolean contains(float x, float y) {
            float width = TileMap.TILE_SIZE;
            float height = TileMap.TILE_SIZE;
            float xOffset = TileMap.TILE_SIZE * row;
            float yOffset = TileMap.TILE_SIZE * column;
            
            if ((xOffset < x && x < xOffset + width) && (yOffset > y && y > yOffset - height)) {
                return true;
            }
            return false;
        }

        public float[] getBounds() {
            float width = TileMap.TILE_SIZE;
            float height = TileMap.TILE_SIZE;
            float xOffset = TileMap.TILE_SIZE * row;
            float yOffset = TileMap.TILE_SIZE * column;

            return new float[] { xOffset, yOffset - height, xOffset + width, yOffset };
        }

        public Tile getBack() {
            return back;
        }

        public void setBack(Tile tile) {
            if (tile != null) {
                tile.setLayer(Renderer.BACK);
                tile.setPosition(row, column);
            }
            this.back = tile;
        }

        public Tile getMid() {
            return mid;
        }

        public void setMid(Tile tile) {
            if (tile != null) {
                tile.setLayer(Renderer.MID);
                tile.setPosition(row, column);
            }
            this.mid = tile;
        }

        public Tile getFront() {
            return front;
        }

        public void setFront(Tile tile) {
            if (tile != null) {
                tile.setLayer(Renderer.FRONT);
                tile.setPosition(row, column);
            }
            this.front = tile;
        }

        @Override
        public void render(PGraphics g) {
            if (vertices == null) return;

            if (outline) g.stroke(outlineColor[0], outlineColor[1], outlineColor[2]);
            else         g.noStroke();

            if (fill) g.fill(fillColor[0], fillColor[1], fillColor[2], fillColor[3]);
            else      g.noFill();

            g.beginShape(PConstants.QUAD);

            for (Vertex v : vertices) {
                g.vertex(v.x, v.y);
            }

            g.endShape();
        }

        @Override
        public float getY() {
            return TileMap.TILE_SIZE * column;
        }
    }

    public class TileGrid {
        public List<List<Slot>> grid;
        
        private int minX;
        private int maxX;
        private int minY;
        private int maxY;

        public boolean showGridLines = false;

        public TileGrid(int width, int height) {
            this.minX = -width / 2;
            this.maxX = minX + width - 1;
            this.minY = -height / 2;
            this.maxY = minY + height - 1;

            this.grid = new ArrayList<List<Slot>>(width);
            for (int x = minX; x <= maxX; x++) {
                this.grid.add(new ArrayList<Slot>(height));
                for (int y = minY; y <= maxY; y++) {
                    Slot slot = new Slot(x, y);
                    slot.outline = showGridLines;
                    this.grid.get(x - minX).add(slot);
                }
            }
        }

        public TileGrid(int width, int height, int minX, int maxX, int minY, int maxY) {
            this.minX = minX;
            this.maxX = maxX;
            this.minY = minY;
            this.maxY = maxY;

            this.grid = new ArrayList<List<Slot>>(width);
            for (int x = minX; x <= maxX; x++) {
                this.grid.add(new ArrayList<Slot>(height));
                for (int y = minY; y <= maxY; y++) {
                    Slot slot = new Slot(x, y);
                    slot.outline = showGridLines;
                    this.grid.get(x - minX).add(slot);
                }
            }
        }

        public Slot getSlot(int x, int y) {
            if (x < minX || x > maxX || y < minY || y > maxY) {
                return null;
            }
            return grid.get(x - minX).get(y - minY);
        }

        public int getMinX() {
            return minX;
        }

        public int getMaxX() {
            return maxX;
        }

        public int getMinY() {
            return minY;
        }

        public int getMaxY() {
            return maxY;
        }

        public int getWidth() {
            return maxX - minX + 1;
        }

        public int getHeight() {
            return maxY - minY + 1;
        }

        public void pushTop() {
            minY--;
            for (int x = minX; x <= maxX; x++) {
                Slot slot = new Slot(x, minY);
                slot.outline = showGridLines;
                tiles.grid.get(x - minX).add(0, slot);
            }
        }

        public void popTop() {
            if (getHeight() < 2) return;

            minY++;
            for (int x = minX; x <= maxX; x++) {
                tiles.grid.get(x - minX).remove(0);
            }
        }

        public void pushBottom() {
            maxY++;
            for (int x = minX; x <= maxX; x++) {
                Slot slot = new Slot(x, maxY);
                slot.outline = showGridLines;
                tiles.grid.get(x - minX).add(slot);
            }
        }

        public void popBottom() {
            if (getHeight() < 2) return;
            
            maxY--;
            for (int x = minX; x <= maxX; x++) {
                List<Slot> col = tiles.grid.get(x - minX);
                int index = col.size() - 1;
                col.remove(index);
            }
        }

        public void pushLeft() {
            minX--;

            List<Slot> row = new ArrayList<Slot>();
            for (int y = minY; y <= maxY; y++) {
                Slot slot = new Slot(minX, y);
                slot.outline = showGridLines;
                row.add(slot);
            }

            tiles.grid.add(0, row);
        }

        public void popLeft() {
            if (getWidth() < 2) return;

            minX++;
            tiles.grid.remove(0);
        }

        public void pushRight() {
            maxX++;

            List<Slot> row = new ArrayList<Slot>();
            for (int y = minY; y <= maxY; y++) {
                Slot slot = new Slot(maxX, y);
                slot.outline = showGridLines;
                row.add(slot);
            }

            tiles.grid.add(row);
        }

        public void popRight() {
            if (getWidth() < 2) return;

            maxX--;
            int index = tiles.grid.size() - 1;
            tiles.grid.remove(index);
        }
    }

    public TileGrid tiles;
    public List<Slot> spawns = new LinkedList<Slot>();
    private File file;

    public void showGridLines(boolean show) {
        tiles.showGridLines = show;

        for (List<Slot> col : tiles.grid) {
            for (Slot s : col) {
                s.outline = show;
            }
        }
    }

    public TileMap() {
        initEmpty(1, 1);
    }

    public TileMap(String filepath) {
        this(new File(filepath));
    }

    public TileMap(File file) {
        load(file);
    }

    public File getFile() {
        return file;
    }

    private void initEmpty(int width, int height) {
        tiles = new TileGrid(width, height);
    }

    public boolean load(File file) {
        try {
            DocumentBuilderFactory documentFactory  = DocumentBuilderFactory.newInstance();
            documentFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder documentBuilder  = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            Element map = document.getDocumentElement();
            if (map.getNodeName() != "map") {
                throw new IOException("File not in MAP format or corrupted");
            }
            String version = map.getAttribute("version");

            if (version != "1.0") {
                throw new IOException("MAP this map file version is not supported. Version was: " + version);
            }

            System.out.println("Parsing MAP file. Version: " + version);

            int width = Integer.parseInt(map.getAttribute("width"));
            int height = Integer.parseInt(map.getAttribute("heigth"));
            int minX = Integer.parseInt(map.getAttribute("minX"));
            int maxX = Integer.parseInt(map.getAttribute("maxX"));
            int minY = Integer.parseInt(map.getAttribute("minY"));
            int maxY = Integer.parseInt(map.getAttribute("maxY"));

            tiles = new TileGrid(width, height, minX, maxX, minY, maxY);

            NodeList list = map.getElementsByTagName("slot");

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    int row = Integer.parseInt(element.getAttribute("x"));
                    int column = Integer.parseInt(element.getAttribute("y"));
                    boolean walkable = Boolean.parseBoolean(element.getAttribute("walkable"));
                    boolean spawn = Boolean.parseBoolean(element.getAttribute("spawn"));
                    int spawnID = Integer.parseInt(element.getAttribute("spawnID"));

                    Slot slot = tiles.getSlot(row, column);
                    slot.walkable = walkable;
                    slot.spawn = spawn;
                    slot.spawnID = spawnID;
                    
                    if (slot.spawn) spawns.add(slot);

                    parseSlot(element, slot);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            initEmpty(1, 1);
            this.file = null;
            return false;
        }

        this.file = file;
        return true;
    }

    public void reload() {
        load(file);
    }

    private static void parseSlot(Element e, Slot slot) {
        NodeList list = e.getElementsByTagName("tile");

        for (int temp = 0; temp < list.getLength(); temp++) {
            Node node = list.item(temp);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;

                int id = Integer.parseInt(element.getAttribute("id"));
                int layer = Integer.parseInt(element.getAttribute("layer"));

                Tile tile = TileFactory.getInstance().createTile(id);

                switch (layer) {
                case Renderer.BACK:
                    slot.setBack(tile);
                    break;
                case Renderer.MID:
                    slot.setMid(tile);
                    break;
                case Renderer.FRONT:
                    slot.setFront(tile);
                    break;
                }
            }
        }
    } 

    public boolean save() {
        if (file == null) {
            return false;
        }
        
        try {
            DocumentBuilderFactory documentFactory  = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder  = documentFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            Element root = document.createElement("map");

            root.setAttribute("version", "1.0");
            root.setAttribute("name", "map");
            root.setAttribute("width", String.valueOf(getWidth()));
            root.setAttribute("heigth", String.valueOf(getHeight()));
            root.setAttribute("minX", String.valueOf(tiles.getMinX()));
            root.setAttribute("maxX", String.valueOf(tiles.getMaxX()));
            root.setAttribute("minY", String.valueOf(tiles.getMinY()));
            root.setAttribute("maxY", String.valueOf(tiles.getMaxY()));
            document.appendChild(root);

            for (List<Slot> col : tiles.grid) {
                for (Slot s : col) {
                    Element slot = document.createElement("slot");
                    slot.setAttribute("x", String.valueOf(s.row));
                    slot.setAttribute("y", String.valueOf(s.column));
                    slot.setAttribute("walkable", String.valueOf(s.walkable));
                    slot.setAttribute("spawn", String.valueOf(s.spawn));
                    slot.setAttribute("spawnID", String.valueOf(s.spawnID));

                    if (s.back != null) {
                        Tile t = s.back;
                        Element tile = document.createElement("tile");
                        tile.setAttribute("id", String.valueOf(t.id));
                        tile.setAttribute("layer", String.valueOf(t.getLayer()));
                        slot.appendChild(tile);
                    }
                    if (s.mid != null) {
                        Tile t = s.mid;
                        Element tile = document.createElement("tile");
                        tile.setAttribute("id", String.valueOf(t.id));
                        tile.setAttribute("layer", String.valueOf(t.getLayer()));
                        slot.appendChild(tile);
                    }
                    if (s.front != null) {
                        Tile t = s.front;
                        Element tile = document.createElement("tile");
                        tile.setAttribute("id", String.valueOf(t.id));
                        tile.setAttribute("layer", String.valueOf(t.getLayer()));
                        slot.appendChild(tile);
                    }
                    root.appendChild(slot);
                }
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            
            transformer.transform(domSource, streamResult);

        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }

    @Override
    public void complexSubmit(Renderer renderer) {
        for (int y = tiles.getMinY(); y <= tiles.getMaxY(); y++) {
            for (int x = tiles.getMinX(); x <= tiles.getMaxX(); x++) {
                Slot slot = tiles.getSlot(x, y);
                renderer.submit(slot);
                if (slot.back  != null) renderer.submit(slot.back);
                if (slot.mid   != null) renderer.submit(slot.mid);
                if (slot.front != null) renderer.submit(slot.front);
            }
        }
    }

    public void update(int millis) {
        for (int y = tiles.getMinY(); y <= tiles.getMaxY(); y++) {
            for (int x = tiles.getMinX(); x <= tiles.getMaxX(); x++) {
                Slot slot = tiles.getSlot(x, y);
                if (slot.back != null && slot.back instanceof AnimatedTile) {
                    ((AnimatedTile)(slot.back)).update(millis);
                }
                if (slot.mid != null && slot.mid instanceof AnimatedTile) {
                    ((AnimatedTile)(slot.mid)).update(millis);
                }
                if (slot.front != null && slot.front instanceof AnimatedTile) {
                    ((AnimatedTile)(slot.front)).update(millis);
                }
            }
        }
    }

    public int getWidth() {
        return tiles.getWidth();
    }

    public int getHeight() {
        return tiles.getHeight();
    }

    public void setFile(File file) {
        this.file = file;
    }

    private Slot getSlot(float x, float y) {
        int slotX = (int)Math.floor(x / TILE_SIZE);
        int slotY = (int)Math.ceil(y / TILE_SIZE);

        Slot res = tiles.getSlot(slotX, slotY);
        
        return res;
    }

    public boolean checkIfWalkable(float x, float y) {
        Slot slot = getSlot(x, y);

        if (slot == null)   return false;
        else                return slot.walkable;
    }

    public Slot getSpawn(int id) {
        for (Slot s : spawns) {
            if (s.spawn && s.spawnID == id) return s;
        }
        return null;
    }
}