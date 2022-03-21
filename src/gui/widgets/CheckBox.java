package gui.widgets;

import gui.Settings;
import gui.util.FontManager;
import processing.core.PFont;
import processing.core.PGraphics;
import rendering.Renderable;
import rendering.Renderer;

public class CheckBox extends Renderable {
    private float x;
    private float y;
    private int size;

    private String text;
    private PFont font;

    public boolean underMouse = false;
    public boolean checked = false;

    public CheckBox(float x, float y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;

        this.layer = Renderer.GUI;
    }

    public CheckBox(float x, float y, int size, String text) {
        this.x = x;
        this.y = y;
        this.size = size;

        this.text = text;
        this.font = FontManager.getInstance().get("Consolas", size);
    }

    public boolean contains(float x, float y) {
        if ((x - this.x) * (x - this.x) + (y - this.y) * (y - this.y) < size * size / 4) {
            return true;
        }
        return false;
    }

    @Override
    public void render(PGraphics g) {
        if (underMouse) g.stroke(Settings.Colors.active[0], Settings.Colors.active[1], Settings.Colors.active[2], Settings.Colors.active[3]);
        else            g.stroke(Settings.Colors.borders[0], Settings.Colors.borders[1], Settings.Colors.borders[2], Settings.Colors.borders[3]);
        g.fill(Settings.Colors.back[0], Settings.Colors.back[1], Settings.Colors.back[2], Settings.Colors.back[3]);

        g.ellipse(x, y, size, size);

        if (checked) {
            g.noStroke();
            g.fill(Settings.Colors.accent[0], Settings.Colors.accent[1], Settings.Colors.accent[2], Settings.Colors.accent[3]);
            g.ellipse(x, y, size - 2, size - 2);
        }

        if (text != null && text != "") {
            g.textFont(font);
            g.textSize(size);
            g.fill(Settings.Colors.text[0], Settings.Colors.text[1], Settings.Colors.text[2], Settings.Colors.text[3]);
            g.text(text, x + size, y + size / 3);
        }
    }

    @Override
    public float getY() {
        return y;
    }
}
