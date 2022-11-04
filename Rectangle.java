package se.iths.tt.javafxtt.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Rectangle extends Shape {

    public Rectangle(double x, double y) {
        super(x, y);
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