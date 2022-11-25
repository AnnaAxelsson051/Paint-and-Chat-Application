package se.iths.tt.javafxtt.Paint;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

    private final double x;
    private final double y;

    //Observable fields to collect events on
    private ObjectProperty <Color> color = new SimpleObjectProperty<>();
    private ObjectProperty <Double> size = new SimpleObjectProperty<>();

    private ObjectProperty <Double> height = new SimpleObjectProperty<>();
    private ObjectProperty <Double> width = new SimpleObjectProperty<>();

    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
        color.set(Color.BLACK);
    }

    public abstract boolean isInside(double x, double y);


    public Color getColor(){
        return color.get();
    }
    public void setColor(Color color){
        this.color.set(color);
    }
    public ObjectProperty <Color> colorProperty(){
        return color;
    }


    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }


    public ObjectProperty <Double> sizeProperty(){
        return size;
    }
    public Double getSize() {return size.get();}
    public void setSize(Double size) {
        this.size.set(size);
    }


    public ObjectProperty <Double> heightProperty(){
        return height;
    }
    public double getHeight(){return height.get();}
    public void setHeight(Double height) { this.height.set(height);}


    public ObjectProperty <Double> widthProperty(){
        return width;
    }
    public double getWidth(){return width.get();}
    public void setWidth(Double height) {this.height.set(height);}

    public abstract void draw(GraphicsContext context);

    //statisk factory metod returns a shape
    public static Shape createShape(ShapeType type, double x, double y){
        return switch (type) {
            case CIRCLE -> new Circle(x, y);
            case RECTANGLE -> new Rectangle(x, y);
            default -> new Circle(x, y);

        };

    }



}
