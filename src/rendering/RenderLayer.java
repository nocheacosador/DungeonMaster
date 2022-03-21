package rendering;

import java.util.PriorityQueue;

import processing.core.PGraphics;

public class RenderLayer {
    private PriorityQueue<Renderable> queue;
    public final String name;

    public boolean render = true;

    // layer transform 
    private float translateX = 0;
    private float translateY = 0;
    private float scale = 1;

    public RenderLayer(String name) {
        this.name = name;
        queue = new PriorityQueue<Renderable>(new RenderableComparator());
    }

    public void add(Renderable renderable) {
        if (!queue.add(renderable)) {
            System.out.println("Error: failed to add element to a render queue of layer '" + name + "'.");
        }
    }

    public void render(PGraphics graphics) {
        if (!render) return;
        
        graphics.pushMatrix();
        graphics.translate(translateX, translateY);
        graphics.scale(scale);

        while (!queue.isEmpty()) {
            Renderable renderable = queue.poll();

            graphics.push();
            renderable.render(graphics);
            graphics.pop();
        }
    
        graphics.popMatrix();
    }

    public void setTransform(float x, float y, float s) {
        this.translateX = x;
        this.translateY = y;
        this.scale = s;
    }
}