package rendering;

import processing.core.PGraphics;

public abstract class Renderable {
    protected int layer = 0;

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public int getLayer() {
        return layer;
    }

    public abstract void render(PGraphics g);

    public abstract float getY();
}
