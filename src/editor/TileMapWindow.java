package editor;

import java.util.List;

import gui.Window;
import gui.widgets.Label;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.event.MouseEvent;
import rendering.Renderer;
import tiles.Tile;
import tiles.TileFactory;
import tiles.TileMap;
import tiles.TileMap.Slot;

public class TileMapWindow extends Window {
    private TileMap tileMap;
    private TileEditor tileEditor;

    public enum Mode {
        Inspect("Inspect"), 
        EditTiles("Edit tiles"), 
        EditWalkable("Edit walkable"),
        EditSpawn("Edit spawns");

        private final String name;

        private Mode(String s) {
            this.name = s;
        }

        public String toString() {
            return this.name;
        }
    }

    // for panning and zooming
    private float scale = 1.f;
    private float translateX = 0.f;
    private float translateY = 0.f;
    private float xOffset = 0.f; 
    private float yOffset = 0.f; 
    private boolean locked = false;

    private TileMap.Slot slotUnderMouse;

    private Mode mode = Mode.Inspect;

    private boolean shouldEditSlot = false;
    private boolean controlPressed = false;

    public TileMapWindow(TileEditor parent) {
        super(parent);

        this.tileEditor = parent;

        this.width = parent.width - TileMap.TILE_SIZE * 4 - BORDER * 5;
        this.height = parent.height - BORDER * 2;
        this.x = BORDER;
        this.y = BORDER;
        
        init();
        this.graphics.setSize(width, height);
        
        this.tileMap = new TileMap();
        this.tileMap.showGridLines(true);

        this.renderer.backgroundColor = new float[] {40, 40, 40};

        this.translateX = this.width / 2;
        this.translateY = this.height / 2;
    }

    @Override
    public void render() {
        int millis = parent.millis();
        float mouseX = (parent.mouseX - x - translateX) / scale;
        float mouseY = (parent.mouseY - y - translateY) / scale;

        tileMap.update(millis);

        slotUnderMouse = null;
        
        if (mode != Mode.Inspect) {
            cursor(PConstants.CROSS);

            for (List<TileMap.Slot> i : tileMap.tiles.grid) {
                for (TileMap.Slot s : i) {
                    if (s.contains(mouseX, mouseY)) {
                        s.fill = true;
                        if (mode == Mode.EditWalkable) {
                            if (s.walkable) s.fillColor = new float[] { 100, 255, 100, 150 };
                            else            s.fillColor = new float[] { 255, 100, 100, 150 };
                        }
                        else if (mode == Mode.EditSpawn && s.spawn) {
                            s.fillColor = new float[] { 255, 100, 255, 150 };
                            Label label = new Label( String.valueOf(s.spawnID), 
                                                        s.row * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2, 
                                                        s.column * TileMap.TILE_SIZE - TileMap.TILE_SIZE / 2, 
                                                        TileMap.TILE_SIZE / 2);
                            label.align = PConstants.CENTER;
                            renderer.submit(label);
                        }
                        else {
                            s.fillColor = new float[] { 255, 255, 255, 50 };
                        }
                        slotUnderMouse = s;
                    }
                    else if (mode == Mode.EditWalkable) {
                        s.fill = true;
                        if (s.walkable) s.fillColor = new float[] { 100, 255, 100, 100 };
                        else            s.fillColor = new float[] { 255, 100, 100, 100 };
                    }
                    else if (mode == Mode.EditSpawn) {
                        s.fill = s.spawn;
                        if (s.spawn) {
                            s.fillColor = new float[] { 255, 100, 255, 100 };
                            Label label = new Label( String.valueOf(s.spawnID), 
                                                     s.row * TileMap.TILE_SIZE + TileMap.TILE_SIZE / 2, 
                                                     s.column * TileMap.TILE_SIZE - TileMap.TILE_SIZE / 2, 
                                                     TileMap.TILE_SIZE / 2);
                            label.align = PConstants.CENTER;
                            renderer.submit(label);
                        }
                    }
                    else {
                        s.fill = false;
                    }
                    
                    Tile back = s.getBack();
                    Tile mid = s.getMid();
                    Tile front = s.getFront();

                    if (tileEditor.controlWindow.opagueInactiveCheckBox.checked) {
                        if (back != null)  back.tint = !tileEditor.controlWindow.backLayerCheckBox.checked;
                        if (mid != null)   mid.tint = !tileEditor.controlWindow.midLayerCheckBox.checked;
                        if (front != null) front.tint = !tileEditor.controlWindow.frontLayerCheckBox.checked;
                    }
                    else {
                        if (back != null)  back.tint = false;
                        if (mid != null)   mid.tint = false;
                        if (front != null) front.tint = false;
                    }
                }
            }

            if (shouldEditSlot) {
                editSlot();
            }
        }
        
        renderer.setGlobalTransform(translateX, translateY, scale);

        renderer.submit(tileMap);
        renderer.render();
    }

    private void editSlot() {
        if (slotUnderMouse == null) return;
    
        if (mode == Mode.EditTiles) {
            int layer = tileEditor.controlWindow.getActiveLayer();
            if (layer == -1) return;
            
            if (controlPressed) {
                switch (layer) {
                case Renderer.BACK:
                    slotUnderMouse.setBack(null);
                    return;
                case Renderer.MID:
                    slotUnderMouse.setMid(null);
                    return;
                case Renderer.FRONT:
                    slotUnderMouse.setFront(null);
                    return;
                }
            }

            Tile selected = tileEditor.tileSelectorWindow.getSelectedTile();
            if (selected == null) return;

            Tile newTile = TileFactory.getInstance().createTile(selected.id);

            newTile.setLayer(layer);
            newTile.setPosition(slotUnderMouse.row, slotUnderMouse.column);

            switch (layer) {
            case Renderer.BACK:
                slotUnderMouse.setBack(newTile);
                return;
            case Renderer.MID:
                slotUnderMouse.setMid(newTile);
                return;
            case Renderer.FRONT:
                slotUnderMouse.setFront(newTile);
                return;
            }
        }
        else if (mode == Mode.EditWalkable) {
            slotUnderMouse.walkable = !controlPressed;
        }
        else if (mode == Mode.EditSpawn) {
            //slotUnderMouse.spawn = !controlPressed;
            if (!slotUnderMouse.spawn && !controlPressed) { // new spawn
                slotUnderMouse.spawn = true;
                slotUnderMouse.spawnID = tileMap.spawns.size();
                tileMap.spawns.add(slotUnderMouse);
            }
            else if (slotUnderMouse.spawn && controlPressed) {
                slotUnderMouse.spawn = false;
                slotUnderMouse.spawnID = -1;
                tileMap.spawns.remove(slotUnderMouse);

                int i = 0;
                for (Slot s : tileMap.spawns) {
                    s.spawnID = i;
                    i++;
                }
            }
        }
    }

    @Override
    public void draw() {
        if (contains(parent.mouseX, parent.mouseY)) parent.cursor(cursorKind);
        
        parent.image(graphics, x, y);
        parent.stroke(80, 120, 120);
        parent.noFill();
        parent.rect(x, y, width, height);
    }

    public void onMousePressed(MouseEvent event) {
        int mouseX = event.getX() - x;
        int mouseY = event.getY() - y;

        if (event.getButton() == PApplet.CENTER) {
            locked = true;
            xOffset = mouseX - translateX; 
            yOffset = mouseY - translateY;
            cursor(PApplet.MOVE);
        }
        else if (event.getButton() == PApplet.LEFT) {
            shouldEditSlot = true;
            controlPressed = (event.getModifiers() & MouseEvent.CTRL) != 0;
        }
    }

    public void onMouseDragged(MouseEvent event) {
        int mouseX = event.getX() - x;
        int mouseY = event.getY() - y;

        if (locked) {
            translateX = mouseX - xOffset; 
            translateY = mouseY - yOffset;
        }
    }

    public void onMouseReleased(MouseEvent event) {
        if (event.getButton() == PApplet.CENTER) {
            locked = false;
            cursor(PApplet.ARROW);
        }
        else if (event.getButton() == PApplet.LEFT) {
            shouldEditSlot = false;
        }
    }

    public void onMouseWheel(MouseEvent event) {
        int mouseX = event.getX() - x;
        int mouseY = event.getY() - y;
        
        if (locked) return;

        float delta = event.getCount() > 0 ? 1.05f : event.getCount() < 0 ? 1.0f/1.05f : 1.0f;
        if (scale * delta > 10.f) return;
        if (scale * delta < 0.01f) return;
        
        scale *= delta;
        translateX -= mouseX;
        translateY -= mouseY;
        translateX *= delta;
        translateY *= delta;
        translateX += mouseX;
        translateY += mouseY;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        cursor(PConstants.ARROW);

        if (mode == Mode.Inspect) {
            for (List<TileMap.Slot> i : tileMap.tiles.grid) {
                for (TileMap.Slot s : i) {
                    s.fill = false;
                }
            }
        }
    }

    public Mode getMode() {
        return mode;
    }
}
