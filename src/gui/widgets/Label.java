package gui.widgets;

import gui.Settings;
import gui.util.FontManager;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;
import rendering.Renderable;
import rendering.Renderer;

public class Label extends Renderable {
    public String text;
    
    public float x;
    public float y;
    public float[] color = Settings.Colors.text;
    
    public int align = PConstants.LEFT;
    
    private int size;
    private PFont font;

    private float width = 0;
    private float height = 0;

    public Label(String text, float x, float y, int size) {
        this.layer = Renderer.GUI;

        this.text = text;
        this.x = x;
        this.y = y;
        this.size = size;
        this.font = FontManager.getInstance().get("Consolas", size);
    }

    public Label(String text, float x, float y, int size, String font) {
        this.layer = Renderer.GUI;

        this.text = text;
        this.x = x;
        this.y = y;
        this.size = size;
        this.font = FontManager.getInstance().get(font, size);
    }

    public boolean contains(float x, float y) {
        float xOffset = this.x;
        float yOffset = this.y;
        
        switch (align) {
            case PConstants.CENTER:
                xOffset -= width / 2;
                break;
            case PConstants.RIGHT:
                xOffset -= width;
                break;
        }

        if ((xOffset < x && x < xOffset + width) && (yOffset > y && y > yOffset - height)) {
            return true;
        }
        return false;
    }

    @Override
    public void render(PGraphics g) {
        if (text != null) {
            g.fill(color[0], color[1], color[2], color[3]);
            g.textAlign(align);
            g.textFont(font);
            g.textSize(size);
            width = g.textWidth(text);
            height = size;
            g.text(text, x, y);

            // debug bound box and origin
            /*g.stroke(255, 0, 0);
            g.strokeWeight(1);
            g.fill(0, 0, 0, 0);
            float xOffset = x;
            switch (align) {
                case PConstants.CENTER:
                    xOffset -= width / 2;
                    break;
                case PConstants.RIGHT:
                    xOffset -= width;
                    break;
            }
            g.rect(xOffset, y, width, -height);

            g.stroke(0, 255, 0);
            g.strokeWeight(3);
            g.point(x, y);*/
        }
    }

    @Override
    public float getY() {
        return y;
    }
}
