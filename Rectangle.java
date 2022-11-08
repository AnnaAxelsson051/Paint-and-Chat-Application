package se.iths.tt.javafxtt.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {

    public Rectangle(double x, double y) {
        super(x, y);
    }

    @Override
    public boolean isInside(double x, double y){
        double px = (float) x;
        double py = (float) y;
        double sx = (float) getX();
        double sy = (float) getY();
        boolean hit = pointRect(px,py, sx,sy, getWidth(), getHeight());
        if (hit) {
            setColor(getColor());
            setSize(getSize());
        }
        return false;
    }

        public boolean pointRect(double px, double py, double rx, double ry, double rw, double rh) {
        // is the point inside the rectangle's bounds?
            if (px >= rx &&        // right of the left edge AND
                    px <= rx + rw &&   // left of the right edge AND
                    py >= ry &&        // below the top AND
                    py <= ry + rh) {   // above the bottom
                return true;
            }
            return false;
        }
    @Override
    public void draw(GraphicsContext context){
        context.setFill(getColor());
        context.fillRect(getX()-getSize()/2, getY()-getSize()/2, getSize(), getSize());
        //skapa fylld cirkel
        context.setStroke(Color.BLUE);
        context.strokeRect(getX()-getSize()/2, getY()-getSize()/2, getSize(), getSize());
    }    //skapa ytterkant av

}