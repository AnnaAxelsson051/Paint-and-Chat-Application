package se.iths.tt.javafxtt.Paint;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Shape {

    //ObservableList<String> shapes = FXCollections.observableArrayList();
//observable list så att det grafiska gränssnittet uppdateras automatiskt
    //när man tar bort och lägger till saker

    private final double x;

    private final double y;

    private ObjectProperty <Color> color = new SimpleObjectProperty<>();
//när vi stoppar in en shape ska man regga sig till notifieringar om dett aändras
//och då trigga notifikering att listan ändrats

//all fields we wanna collect events on must be observable;
    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
        color.set(Color.BLACK);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Color getColor(){
        return color.get();
    }

    public void setColor(Color color){
        this.color.set(color);
    }

    public ObjectProperty <Color> colorProperty(){
        return color;
    }

    public abstract void draw(GraphicsContext context);

    //göra en statisk factory metod som returns en shape
    public static Shape createShape(ShapeType type, double x, double y){
        return switch (type) {
            case CIRCLE -> new Circle(x, y);
            case RECTANGLE -> new Rectangle(x, y);
            case TRIANGLE -> new Triangle(x, y);
            case SQUARE -> new Square(x, y);
            case OVAL -> new Oval(x, y);
            case ARC -> new Arc(x, y);
            default -> new Arc(x, y);
            //case ROUND_RECTANGLE -> new RoundRectangle(x, y);
            //case TRIANGLE -> new Triangle(x,y);
        };

    }
}
