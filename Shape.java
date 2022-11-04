package se.iths.tt.javafxtt.Paint;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Size;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import se.iths.tt.javafxtt.model.Position;

public abstract class Shape {


    //coordinates for mouseclick passed to each individual class:
    private final double x;
    private final double y;

    //Observable fields = those we wanna collect events on:
    //when we add a shape the list must get notifications of change in list
    private ObjectProperty <Color> color = new SimpleObjectProperty<>();
    private ObjectProperty <Double> size = new SimpleObjectProperty<>();

    private ObjectProperty <Double> height = new SimpleObjectProperty<>();
    private ObjectProperty <Double> width = new SimpleObjectProperty<>();


    //constructor for shape:
    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
        color.set(Color.BLACK);
    }

    //for color:
    public Color getColor(){
        return color.get();
    }
    public void setColor(Color color){
        this.color.set(color);
    }
    public ObjectProperty <Color> colorProperty(){
        return color;
    }

    //for cordinates:
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }


    //for size:
    public ObjectProperty <Double> sizeProperty(){
        return size;
    }
    public Double getSize() {return size.get();}
    public void setSize(Double size) {
        this.size.set(size);
    }

    //height
    public ObjectProperty <Double> heightProperty(){
        return height;
    }
    public Double getHeight(){return height.get();}
    public void setHeight(Double height) { this.height.set(height);}

    //width
    public ObjectProperty <Double> widthProperty(){
        return width;
    }
    public Double getWidth(){return width.get();}
    public void setWidth(Double height) {this.height.set(height);}


    //draw method overridden in individual classes:
    public abstract void draw(GraphicsContext context);

    //statisk factory metod som returns en shape:
    public static Shape createShape(ShapeType type, double x, double y){
        return switch (type) {
            case CIRCLE -> new Circle(x, y);
            case RECTANGLE -> new Rectangle(x, y);
            case TRIANGLE -> new Triangle(x, y);
            case SQUARE -> new Square(x, y);
            case ARC -> new Arc(x, y);
            default -> new Arc(x, y);
            //case ROUND_RECTANGLE -> new RoundRectangle(x, y);
            //case TRIANGLE -> new Triangle(x,y);
        };

    }
}
