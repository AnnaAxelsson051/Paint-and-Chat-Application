package se.iths.tt.javafxtt.Paint;

public class Circle extends Shape {

    public Circle (double x, double y){
        super(x, y);
        //Kunna rita cirklar
    }

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


