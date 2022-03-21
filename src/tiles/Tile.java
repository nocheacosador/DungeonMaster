package tiles;

import processing.core.PConstants;
import processing.core.PGraphics;
import rendering.Renderable;
import rendering.Texture;
import rendering.Vertex;

public class Tile extends Renderable {
    public final int id;
    public final String name;

    public int row;
    public int column;

    private Texture texture;
    private Vertex[] vertices;

    public boolean outline = false;
    public float[] outlineColor = new float[] {0, 255, 0};
    public boolean tint = false;
    public float[] tintColor = {255, 255, 255, 60};

    public Tile(int id, String name, Texture texture) {
        this.id = id;
        this.name = name;

        this.texture = texture;
        
        setPosition(0, 0);
    }
    
    public int getID() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setPosition(int row, int col) {
        this.row = row;
        this.column = col;

        generateVertices();
    }

    private void generateVertices() {
        if (texture == null) {
            vertices = null;
            return;
        }

        float width = texture.getWidth() * TileMap.TILE_SCALE;
        float height = texture.getHeight() * TileMap.TILE_SCALE;
        float xOffset = TileMap.TILE_SIZE * row;
        float yOffset = TileMap.TILE_SIZE * column;

        vertices = new Vertex[] {
            new Vertex(xOffset,         yOffset,          texture.left,  texture.bottom),
            new Vertex(xOffset,         yOffset - height, texture.left,  texture.top),
            new Vertex(xOffset + width, yOffset - height, texture.right, texture.top),
            new Vertex(xOffset + width, yOffset,          texture.right, texture.bottom)
        };
    }

    protected void setTexture(Texture texture) {
        this.texture = texture;
        generateVertices();
    }

    public int getWidth() {
        if (texture == null) return TileMap.TILE_SIZE;
        return texture.getWidth() * TileMap.TILE_SCALE;
    }

    public int getHeight() {
        if (texture == null) return TileMap.TILE_SIZE;
        return texture.getHeight() * TileMap.TILE_SCALE;
    }

    public boolean contains(int x, int y) {
        float width = getWidth();
        float height = getHeight();
        float xOffset = TileMap.TILE_SIZE * row;
        float yOffset = TileMap.TILE_SIZE * column;
        
        if ((xOffset < x && x < xOffset + width) && (yOffset > y && y > yOffset - height)) {
            return true;
        }
        return false;
    }

    @Override
    public void render(PGraphics g) {
        if (vertices == null) return;

        if (outline) g.stroke(outlineColor[0], outlineColor[1], outlineColor[2]);
        else         g.noStroke();

        if (tint) g.tint(tintColor[0], tintColor[1], tintColor[2], tintColor[3]);
        else      g.noTint();

        g.textureMode(PConstants.IMAGE);
        g.beginShape(PConstants.QUAD);
                
        if (texture != null) g.texture(texture.texture);

        for (Vertex v : vertices) {
            if (texture != null) g.vertex(v.x, v.y, v.textureX, v.textureY);
        }

        g.endShape();
    }

    @Override
    public float getY() {
        return TileMap.TILE_SIZE * column;
    }
}