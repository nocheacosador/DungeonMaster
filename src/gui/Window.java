package gui;

import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PGraphicsOpenGL;
import rendering.Renderer;

public abstract class Window {
    protected final int BORDER = 5;

    protected PApplet parent;
    protected PGraphics graphics;
    protected Renderer renderer;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected int cursorKind = PApplet.ARROW;

    public Window(PApplet parent) {
        this.parent = parent;
    }

    protected void init() {
        graphics = parent.createGraphics(width - BORDER * 2 + 1, height - BORDER * 2, PApplet.P2D);
        ((PGraphicsOpenGL)graphics).textureSampling(2);
        
        renderer = new Renderer(graphics);
    }

    public abstract void render();
    
    public void draw() {
        if (contains(parent.mouseX, parent.mouseY)) parent.cursor(cursorKind);

        parent.image(graphics, x + BORDER, y + BORDER);
        parent.stroke(80, 120, 120);
        parent.noFill();
        parent.rect(x, y, width, height);
    }

    public boolean contains(int x, int y) {
        if ((this.x < x && x < this.x + this.width) && (this.y < y && y < this.y + this.height)) {
            return true;
        }
        return false;
    }

    public PGraphics getGraphics() {
        return graphics;
    }

    public void cursor(int kind) {
        this.cursorKind = kind;
    }
}