package se.iths.tt.javafxtt.Paint;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Circle extends Shape {

    public Circle (double x, double y){
        super(x, y);
        //Kunna rita cirklar   2.33.20
    }

    @Override
    public void draw(GraphicsContext context){
        context.setFill(getColor());
        context.fillOval(getX()-getSize()/2, getY()-getSize()/2, getSize(), getSize());
        //skapa fylld cirkel
        context.setStroke(Color.BLUE);
        context.strokeOval(getX()-getSize()/2, getY()-getSize()/2, getSize(), getSize());
    }    //skapa ytterkant av 

    //Om man har en metod som ska ta x y som är cenyrum fr
    // cirkeln och radius  så kan man ist för x och y skicka med ett
    // point objekt som innehåller x och y

    /*private final double x;
    private final double y;
    //private final double radius;

    public Circle(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }



    /*public void build(){
        return new Circle().setX(x).setY(y).setRadius(radius).setCategory(category).createProduct();
    }*/


}


