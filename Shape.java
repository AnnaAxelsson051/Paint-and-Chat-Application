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

    //ObservableList<String> shapes = FXCollections.observableArrayList();
//observable list så att det grafiska gränssnittet uppdateras automatiskt
    //när man tar bort och lägger till saker

    private final double x;

    private final double y;

    //Observable objejt property color:
    private ObjectProperty <Color> color = new SimpleObjectProperty<>();
//telling list that when we add a shape the list must bind to this field and registrera sig för
//notifieringar om det ändras, och d ska du trigga notifiering att listan eller ngt i listan har ändrats
// //Så alla fält man vill fånga händelser på ska vara observerbara
private ObjectProperty <Double> size = new SimpleObjectProperty<>();

//private ObjectProperty <Position> position = new SimpleObjectProperty<>();

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

    public Double getSize() {
        return size.get();
    }

    public void setSize(Double size) {
        this.size.set(size);
    }

    public ObjectProperty <Double> sizeProperty(){
        return size;
    }

    public abstract void draw(GraphicsContext context);

    //göra en statisk factory metod som returns en shape
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
