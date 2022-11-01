package se.iths.tt.javafxtt.Paint;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Shape {

    //ObservableList<String> shapes = FXCollections.observableArrayList();
//observable list så att det grafiska gränssnittet uppdateras automatiskt
    //när man tar bort och lägger till saker

    private final double x;

    private final double y;

    public Shape(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    //göra en statisk factory metod som returns en shape
    public static Shape createShape(ShapeType type, double x, double y){
        return switch (type) {
            case CIRCLE -> new Circle(x, y);
            case RECTANGLE -> new Rectangle(x, y);
            //case TRIANGLE -> new Triangle(x,y);
        };

    }
}
