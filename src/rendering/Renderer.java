package rendering;

import processing.core.PApplet;
import processing.core.PGraphics;

public class Renderer {
    public static final int BACK = 0;
    public static final int MID = 1;
    public static final int FRONT = 2;
    public static final int GUI = 3;

    private PGraphics graphics;
    
    public RenderLayer[] layers = { 
        new RenderLayer("Back"), new RenderLayer("Mid"), new RenderLayer("Front"), new RenderLayer("GUI")
    };

    public float[] backgroundColor = { 30, 30, 30 };

    private float translateX = 0;
    private float translateY = 0;
    private float scale = 1;

    public Renderer(PApplet papplet) {
        this.graphics = papplet.g;
    }

    public Renderer(PGraphics graphics) {
        this.graphics = graphics;
    }

    public void setGlobalTransform(float x, float y, float s) {
        this.translateX = x;
        this.translateY = y;
        this.scale = s;
    }

    public void submit(Renderable renderable) {
        int layer = renderable.getLayer();
        if (layer >= 0 && layer < layers.length) {
            layers[layer].add(renderable);
        }
        else {
            System.out.println("Error: renderable layer id was not found.");
        }
    }

    public void submit(Renderable[] renderables) {
        for (int i = 0; i < renderables.length; i++) {
            submit(renderables[i]);
        }
    }

    public void submit(ComplexRenderable complexRenderable) {
        complexRenderable.complexSubmit(this);
    }

    public void render() {
        if (graphics != null) {
            graphics.beginDraw();
            graphics.background(backgroundColor[0], backgroundColor[1], backgroundColor[2]);

            // global transform
            graphics.pushMatrix();
            graphics.translate(translateX, translateY);
            graphics.scale(scale);

            for (RenderLayer layer : layers) {
                layer.render(graphics);
            }
            
            graphics.popMatrix();
            
            graphics.endDraw();
        }
    }
}