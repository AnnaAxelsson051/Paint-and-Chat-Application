package se.iths.tt.javafxtt.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Square extends Shape {
    public Square(double x, double y) {
        super(x, y);
    }

    @Override
    public void draw(GraphicsContext context){
        context.setFill(getColor());
        context.fill(getX()-10, getY()-10, 20, 20);
        //skapa fylld cirkel
        context.setStroke(Color.BLUE);
        context.strokeOval(getX()-10, getY()-10, 20, 20);
    }    //skapa ytterkant av
}
