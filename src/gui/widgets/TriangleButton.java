package gui.widgets;

import gui.Settings;
import gui.util.FontManager;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;
import rendering.Renderable;
import rendering.Renderer;

public class TriangleButton extends Renderable {
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private float x3;
    private float y3;
    private String text;
    private int textSize;
    private float textX;
    private float textY;
    private PFont font;

    public boolean underMouse = false;
    public boolean pressed = false;

    public TriangleButton(float x1, float y1, float x2, float y2, float x3, float y3, String text, int textSize) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;

        this.textX = (x1 + x2 + x3) / 3.f;
        this.textY = (y1 + y2 + y3) / 3.f;
        this.textSize = textSize;
        this.text = text;

        this.font = FontManager.getInstance().get("Consolas", textSize);

        this.layer = Renderer.GUI;
    }

    private static float sign(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        return (x1 - x3) * (y2 - y3) - (x2 - x3) * (y1 - y3);
    }

    public boolean contains(float x, float y) {
        float d1 = sign(x, y, x1, y1, x2, y2);
        float d2 = sign(x, y, x2, y2, x3, y3);
        float d3 = sign(x, y, x3, y3, x1, y1);

        boolean hasNeg = (d1 < 0) || (d2 < 0) || (d3 < 0);
        boolean hasPos = (d1 > 0) || (d2 > 0) || (d3 > 0);

        return !(hasNeg && hasPos);
    }


    @Override
    public void render(PGraphics g) {
        if (underMouse) g.stroke(Settings.Colors.active[0], Settings.Colors.active[1], Settings.Colors.active[2], Settings.Colors.active[3]);
        else            g.stroke(Settings.Colors.borders[0], Settings.Colors.borders[1], Settings.Colors.borders[2], Settings.Colors.borders[3]);
        if (pressed) g.fill(Settings.Colors.active[0], Settings.Colors.active[1], Settings.Colors.active[2], Settings.Colors.active[3]);
        else         g.fill(Settings.Colors.accent[0], Settings.Colors.accent[1], Settings.Colors.accent[2], Settings.Colors.accent[3]);

        g.beginShape(PConstants.TRIANGLE);

        g.vertex(x1, y1);
        g.vertex(x2, y2);
        g.vertex(x3, y3);

        g.endShape();

        if (text != null && text != "") {
            g.textFont(font);
            g.textSize(textSize);
            g.textAlign(PConstants.CENTER);
            g.fill(Settings.Colors.text[0], Settings.Colors.text[1], Settings.Colors.text[2], Settings.Colors.text[3]);
            g.text(text, textX, textY + textSize / 3);
        }
    }

    @Override
    public float getY() {
        return textY;
    }

    
}
