package editor;

import gui.Window;
import gui.widgets.CheckBox;
import gui.widgets.Label;
import gui.widgets.TriangleButton;
import rendering.RenderLayer;
import rendering.Renderer;
import tiles.TileMap;
import processing.core.PConstants;
import processing.event.MouseEvent;

public class ControlWindow extends Window {
    Renderer tileMapRenderer;
    TileMapWindow tileMapWindow;
    
    Label fileLabel;
    Label mapSizeLabel;
    Label mapResizeLabel;
    Label renderLayerLabel;
    Label activeLayerLabel;
    Label modeLabel;

    CheckBox renderAllCheckBox;
    CheckBox renderActiveCheckBox;
    CheckBox opagueInactiveCheckBox;
    CheckBox backLayerCheckBox;
    CheckBox midLayerCheckBox;
    CheckBox frontLayerCheckBox;
    CheckBox showGridLines;

    TriangleButton topPlusButton;
    TriangleButton topMinusButton;
    TriangleButton leftPlusButton;
    TriangleButton leftMinusButton;
    TriangleButton rightPlusButton;
    TriangleButton rightMinusButton;
    TriangleButton bottomPlusButton;
    TriangleButton bottomMinusButton;

    private int activeLayer = -1;

    public ControlWindow(TileEditor parent) {
        super(parent);

        this.width = TileMap.TILE_SIZE * 4 + BORDER * 2;
        this.height = parent.height / 2 - BORDER * 2;
        this.x = parent.width - this.width - BORDER;
        this.y = parent.height / 2 + BORDER;

        init();

        TileMap tileMap = parent.tileMapWindow.getTileMap();
        this.tileMapRenderer = parent.tileMapWindow.getRenderer();
        this.tileMapWindow = parent.tileMapWindow;

        this.fileLabel = new Label("File: " + (tileMap.getFile() == null ? "null" : tileMap.getFile().getName()), 5, 15, 16);
        this.mapSizeLabel = new Label("Map width:" + tileMap.getWidth() + " height: " + tileMap.getHeight(), 5, 35, 16);
        
        this.mapResizeLabel = new Label("Map resize:", 5, 55, 16);
        
        int hgw = (width - BORDER * 2) / 2;
        this.topPlusButton = new TriangleButton(hgw, 65 - 4, hgw + 30, 95 - 4, hgw - 30, 95 - 4, "+", 16);
        this.topMinusButton = new TriangleButton(hgw, 125 - 2, hgw + 30, 95 - 2, hgw - 30, 95 - 2, "-", 16);
        this.leftPlusButton = new TriangleButton(hgw - 60 - 4, 125, hgw - 30 - 4, 95, hgw - 30 - 4, 155, "+", 16);
        this.leftMinusButton = new TriangleButton(hgw - 2, 125, hgw - 30 - 2, 95, hgw - 30 - 2, 155, "-", 16);
        this.rightPlusButton = new TriangleButton(hgw + 60 + 4, 125, hgw + 30 + 4, 95, hgw + 30 + 4, 155, "+", 16);
        this.rightMinusButton = new TriangleButton(hgw + 2, 125, hgw + 30 + 2, 95, hgw + 30 + 2, 155, "-", 16);
        this.bottomPlusButton = new TriangleButton(hgw, 185 + 4, hgw + 30, 155 + 4, hgw - 30, 155 + 4, "+", 16);
        this.bottomMinusButton = new TriangleButton(hgw, 125 + 2, hgw + 30, 155 + 2, hgw - 30, 155 + 2, "-", 16);

        this.renderLayerLabel = new Label("Render:", 5, 200, 16);
        this.renderAllCheckBox = new CheckBox(10, 215, 14, "All layers");
        this.showGridLines = new CheckBox(hgw + 10, 215, 14, "Show grid");
        this.renderActiveCheckBox = new CheckBox(10, 235, 14, "Active layer");
        this.opagueInactiveCheckBox = new CheckBox(hgw + 10, 235, 14, "Opaque inactive");

        this.activeLayerLabel = new Label("Active layer:", 5, 265, 16);  
        this.backLayerCheckBox = new CheckBox(10, 280, 14, "Back");
        this.midLayerCheckBox = new CheckBox(10, 300, 14, "Mid");
        this.frontLayerCheckBox = new CheckBox(10, 320, 16, "Front");

        this.modeLabel = new Label("Mode: " + tileMapWindow.getMode().toString(), 5, 350, 16);

        this.renderAllCheckBox.checked = true;
        this.showGridLines.checked = true;

        tileMap.showGridLines(true);
    }

    @Override
    protected void init() {
        graphics = parent.createGraphics(width - BORDER * 2, height - BORDER * 2, PConstants.P2D);
        renderer = new Renderer(graphics);
    }

    @Override
    public void render() {
        //int millis = parent.millis();
        int mouseX = parent.mouseX - x - BORDER;
        int mouseY = parent.mouseY - y - BORDER;

        TileMap tileMap = tileMapWindow.getTileMap();

        fileLabel.text = "File: " + (tileMap.getFile() == null ? "null" : tileMap.getFile().getName());
        mapSizeLabel.text = "Map width: " + tileMap.getWidth() + " height: " + tileMap.getHeight();
        modeLabel.text = "Mode: " + tileMapWindow.getMode().toString();

        if (renderAllCheckBox.contains(mouseX, mouseY)) renderAllCheckBox.underMouse = true;
        else                                            renderAllCheckBox.underMouse = false;
        if (renderActiveCheckBox.contains(mouseX, mouseY)) renderActiveCheckBox.underMouse = true;
        else                                               renderActiveCheckBox.underMouse = false;
        if (backLayerCheckBox.contains(mouseX, mouseY)) backLayerCheckBox.underMouse = true;
        else                                            backLayerCheckBox.underMouse = false;
        if (opagueInactiveCheckBox.contains(mouseX, mouseY)) opagueInactiveCheckBox.underMouse = true;
        else                                                 opagueInactiveCheckBox.underMouse = false;
        if (midLayerCheckBox.contains(mouseX, mouseY)) midLayerCheckBox.underMouse = true;
        else                                           midLayerCheckBox.underMouse = false;
        if (frontLayerCheckBox.contains(mouseX, mouseY)) frontLayerCheckBox.underMouse = true;
        else                                        frontLayerCheckBox.underMouse = false;
        if (showGridLines.contains(mouseX, mouseX)) showGridLines.underMouse = true;
        else                                        showGridLines.underMouse = false;

        if (topPlusButton.contains(mouseX, mouseY)) topPlusButton.underMouse = true;
        else                                        topPlusButton.underMouse = false;
        if (topMinusButton.contains(mouseX, mouseY)) topMinusButton.underMouse = true;
        else                                        topMinusButton.underMouse = false;
        if (leftPlusButton.contains(mouseX, mouseY)) leftPlusButton.underMouse = true;
        else                                        leftPlusButton.underMouse = false;
        if (leftMinusButton.contains(mouseX, mouseY)) leftMinusButton.underMouse = true;
        else                                        leftMinusButton.underMouse = false;
        if (rightPlusButton.contains(mouseX, mouseY)) rightPlusButton.underMouse = true;
        else                                        rightPlusButton.underMouse = false;
        if (rightMinusButton.contains(mouseX, mouseY)) rightMinusButton.underMouse = true;
        else                                        rightMinusButton.underMouse = false;
        if (bottomPlusButton.contains(mouseX, mouseY)) bottomPlusButton.underMouse = true;
        else                                        bottomPlusButton.underMouse = false;
        if (bottomMinusButton.contains(mouseX, mouseY)) bottomMinusButton.underMouse = true;
        else                                        bottomMinusButton.underMouse = false;

        if (renderActiveCheckBox.checked) {
            tileMapRenderer.layers[Renderer.BACK].render  = backLayerCheckBox.checked;
            tileMapRenderer.layers[Renderer.MID].render   = midLayerCheckBox.checked;
            tileMapRenderer.layers[Renderer.FRONT].render = frontLayerCheckBox.checked;
        }
        else {
            for (RenderLayer layer : tileMapRenderer.layers) {
                layer.render = true;
            }
        }

        renderer.submit(topPlusButton);
        renderer.submit(topMinusButton);
        renderer.submit(leftPlusButton);
        renderer.submit(leftMinusButton);
        renderer.submit(rightPlusButton);
        renderer.submit(rightMinusButton);
        renderer.submit(bottomPlusButton);
        renderer.submit(bottomMinusButton);
        renderer.submit(fileLabel);
        renderer.submit(renderAllCheckBox);
        renderer.submit(renderActiveCheckBox);
        renderer.submit(opagueInactiveCheckBox);
        renderer.submit(backLayerCheckBox);
        renderer.submit(midLayerCheckBox);
        renderer.submit(frontLayerCheckBox);
        renderer.submit(renderLayerLabel);
        renderer.submit(activeLayerLabel);
        renderer.submit(mapSizeLabel);
        renderer.submit(mapResizeLabel);
        renderer.submit(showGridLines);
        renderer.submit(modeLabel);
        renderer.render();
    }

    public void onMouseClicked(MouseEvent event) {
        int mouseX = event.getX() - x - BORDER;
        int mouseY = event.getY() - y - BORDER;

        TileMap tileMap = tileMapWindow.getTileMap();

        if (event.getButton() == PConstants.LEFT) {
            if (renderAllCheckBox.contains(mouseX, mouseY)) {
                renderAllCheckBox.checked = true;
                renderActiveCheckBox.checked = false;
                opagueInactiveCheckBox.checked = false;
            }
            else if (renderActiveCheckBox.contains(mouseX, mouseY)) {
                renderActiveCheckBox.checked = true;
                renderAllCheckBox.checked = false;
                opagueInactiveCheckBox.checked = false;
            }
            else if (opagueInactiveCheckBox.contains(mouseX, mouseY)) {
                opagueInactiveCheckBox.checked = true;
                renderAllCheckBox.checked = false;
                renderActiveCheckBox.checked = false;
            }
            else if (backLayerCheckBox.contains(mouseX, mouseY)) {
                backLayerCheckBox.checked = !backLayerCheckBox.checked;
                midLayerCheckBox.checked = false;
                frontLayerCheckBox.checked = false;

                if (backLayerCheckBox.checked) activeLayer = Renderer.BACK;
                else                           activeLayer = -1;
            }
            else if (midLayerCheckBox.contains(mouseX, mouseY)) {
                midLayerCheckBox.checked = !midLayerCheckBox.checked;
                backLayerCheckBox.checked = false;
                frontLayerCheckBox.checked = false;

                if (midLayerCheckBox.checked) activeLayer = Renderer.MID;
                else                          activeLayer = -1;
            }
            else if (frontLayerCheckBox.contains(mouseX, mouseY)) {
                frontLayerCheckBox.checked = !frontLayerCheckBox.checked;
                backLayerCheckBox.checked = false;
                midLayerCheckBox.checked = false;

                if (frontLayerCheckBox.checked) activeLayer = Renderer.FRONT;
                else                       activeLayer = -1;
            }
            else if (showGridLines.contains(mouseX, mouseY)) {
                showGridLines.checked = !showGridLines.checked;
                tileMap.showGridLines(showGridLines.checked);
            }
            else if (topPlusButton.contains(mouseX, mouseY)) {
                tileMap.tiles.pushTop();
            }
            else if (topMinusButton.contains(mouseX, mouseY)) {
                tileMap.tiles.popTop();
            }
            else if (leftPlusButton.contains(mouseX, mouseY)) {
                tileMap.tiles.pushLeft();
            }
            else if (leftMinusButton.contains(mouseX, mouseY)) {
                tileMap.tiles.popLeft();
            }
            else if (rightPlusButton.contains(mouseX, mouseY)) {
                tileMap.tiles.pushRight();
            }
            else if (rightMinusButton.contains(mouseX, mouseY)) {
                tileMap.tiles.popRight();
            }
            else if (bottomPlusButton.contains(mouseX, mouseY)) {
                tileMap.tiles.pushBottom();
            }
            else if (bottomMinusButton.contains(mouseX, mouseY)) {
                tileMap.tiles.popBottom();
            }
        }
    }

    public int getActiveLayer() {
        return activeLayer;
    }
}
