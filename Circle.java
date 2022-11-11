package se.iths.tt.javafxtt.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import static java.lang.Math.sqrt;

public class Circle extends Shape {

    //gets x and y from super (Shape):
    public Circle (double x, double y){
        super(x, y);
    }

    @Override
    public boolean isInside(double x, double y) {
        double px = x;
        double py = y;
        double cx = getX();
        double cy = getY();
        double radius = (getSize() /2);
        boolean hit = pointCircle(px,py, cx,cy, radius);
        return hit;
    }

    public boolean pointCircle(double px, double py, double cx, double cy, double r){
        double distX = px - cx;
        double distY = py - cy;
        double distance = sqrt( (distX*distX) + (distY*distY) );
        if(distance <= r) {
            return true;
        }
        return false;
    }

    //draws a circle:
    @Override
    public void draw(GraphicsContext context){
        context.setFill(getColor());
        context.fillOval(getX()-getSize()/2, getY()-getSize()/2, getSize(), getSize());
        //skapa fylld cirkel
        context.setStroke(Color.BLUE);
        context.strokeOval(getX()-getSize()/2, getY()-getSize()/2, getSize(), getSize());
    }    //skapa ytterkant av 




}


