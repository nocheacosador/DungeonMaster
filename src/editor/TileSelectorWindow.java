package editor;

import gui.Window;
import processing.core.PApplet;
import processing.event.MouseEvent;
import rendering.Renderer;
import tiles.AnimatedTile;
import tiles.Tile;
import tiles.TileFactory;
import tiles.TileMap;

public class TileSelectorWindow extends Window {
    private Tile[] tileList;
    private Tile tileSelected;

    private int scroll = 0;
    private int maxScroll = 0;

    public TileSelectorWindow(PApplet parent) {
        super(parent);

        this.width = TileMap.TILE_SIZE * 4 + BORDER * 2;
        this.height = parent.height / 2 - BORDER;
        this.x = parent.width - this.width - BORDER;
        this.y = BORDER;

        init();

        TileFactory tileFactory = TileFactory.getInstance();
        tileList = new Tile[tileFactory.getTileCount()];

        for (int id = 0; id < tileList.length; id++) {
            tileList[id] = tileFactory.createTile(id);
        }

        positionNeatly();
    }

    private void positionNeatly() {
        // position neatly
        int x = 0;
        int y = 0;
        Tile[] line = new Tile[4];
        int i = 0;
        int totalW = 0;
        int maxH = 0;
        for (Tile t : tileList) {
            int w = (int)Math.ceil((float)t.getWidth() / TileMap.TILE_SIZE);
            int h = (int)Math.ceil((float)t.getHeight() / TileMap.TILE_SIZE);

            if (totalW + w > 4) {
                y += maxH;
                for (int q = 0; q < line.length && line[q] != null; q++) {
                    line[q].setPosition(x, y);
                    x += (int)Math.ceil((float)line[q].getWidth() / TileMap.TILE_SIZE);
                    line[q] = null;
                }
                x = 0;
                totalW = 0;
                maxH = 0;
                i = 0;
            }
            totalW += w;
            maxH = Math.max(maxH, h);
            line[i++] = t;
        }
        y += maxH;
        for (int q = 0; q < line.length && line[q] != null; q++) {
            line[q].setPosition(x, y);
            x += (int)Math.ceil((float)line[q].getWidth() / TileMap.TILE_SIZE);
            line[q] = null;
        }

        maxScroll = y - height / TileMap.TILE_SIZE;
    }

    @Override
    public void render() {
        int millis = parent.millis();
        int mouseX = parent.mouseX - x - BORDER;
        int mouseY = parent.mouseY - y - BORDER;

        boolean onTile = false;

        for (Tile t : tileList) {
            if (t instanceof AnimatedTile) {
                ((AnimatedTile)t).update(millis);
            }

            if (t.contains(mouseX, mouseY)) {
                t.tint = true;
                onTile = true;
            }
            else {
                t.tint = false;
            }
        }

        if (onTile) cursor(PApplet.HAND);
        else        cursor(PApplet.ARROW);

        renderer.submit(tileList);
        renderer.render();
    }

    public Tile getSelectedTile() {
        return tileSelected;
    }

    public void onMouseWheel(MouseEvent event) {
        int c = event.getCount();
        
        if (scroll + c > maxScroll) {
            c = maxScroll - scroll;
        } else if (scroll + c < 0) {
            c = scroll;
        }

        if (c != 0) {
            for (Tile t : tileList) {
                t.setPosition(t.row, t.column - c);
            }
            scroll += c;
        }
    }

    public void onMouseClicked(MouseEvent event) {
        int mouseX = event.getX() - x - BORDER;
        int mouseY = event.getY() - y - BORDER;

        if (event.getButton() == PApplet.LEFT) {
            if (tileSelected != null) {
                tileSelected.setLayer(Renderer.BACK);
                tileSelected.outline = false;
                tileSelected = null;
            }
            for (Tile t : tileList) {
                if (t.contains(mouseX, mouseY)) {
                    t.setLayer(Renderer.MID);
                    t.outline = true;
                    tileSelected = t;
                    break;
                }
            }
        }
    }
}
