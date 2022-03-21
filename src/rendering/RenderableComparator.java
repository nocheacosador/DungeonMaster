package rendering;

import java.util.Comparator;
class RenderableComparator implements Comparator<Renderable> {
    public int compare(Renderable r1, Renderable r2)
    {
        float y1 = r1.getY();
        float y2 = r2.getY();

        if (y1 > y2) return 1;
        if (y1 < y2) return -1;
        return 0;
    }
}