package se.iths.tt.javafxtt.Paint;

import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Model {

    public ObjectProperty<Color> currentColor = new SimpleObjectProperty<>(Color.PINK);
    public ObjectProperty<ShapeType> currentShapeType = new SimpleObjectProperty<>(ShapeType.CIRCLE);


    ObservableList<ObsShape> shapes = FXCollections.observableArrayList(param -> new Observable[]{
            param.colorProperty(),
            param.sizeProperty()
    });

    //som parameter till observerbaralistan skickar vi ett lambda som implementerar inerfacet extractor with a
    //method som tar objektet som vi stoppat in odå får man tala om vilka observerbara fält ska vi få
    //notifieringar från, skapar en array av observables o säger att colorproperty på vår shape ska
    //va registrerad på, så har man många prop man vill lyssna på, man skapar en array av dem
    //lägga till fler då även lägga till i shape property getters o setters mm
    public ShapeType getCurrentShapeType() {
        return currentShapeType.get();
    }

    public ObjectProperty<ShapeType> currentShapeTypeProperty() {
        return currentShapeType;
    }

    public void setCurrentShapeType(ShapeType currentShapeType) {
        this.currentShapeType.set(currentShapeType);
    }

    public Color getCurrentColor() {
        return currentColor.get();
    }

    public ObjectProperty<Color> currentColorProperty() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor.set(currentColor);
    }

    public ObservableList<? extends Shape> getShapes() {
        return shapes;
    }

    //public ObjectProperty <String> size= new SimpleObjectProperty<>();
    //om man vill ha sizefield
    //public StringProperty size = new SimpleStringProperty("10x10");
    //den undre är bättre +

    //public StringProperty sizeProperty(){
    //return size;
//}

    //add shapes to a list
    //triggers listener
    public Shape addShape(Shape shape) {
        var oShape= new ObsShape(shape);
        shapes.add(shape);
        return oShape;
    }
}

class ObsShape extends Shape {
    Shape shape;
    ObjectProperty<Color> color = new SimpleObjectProperty<>();

    public ObsShape(Shape shape){
        super(shape.getX(), shape.getY());
        this.shape = shape;

    }

    @Override
    public void draw(GraphicsContext context) {

    }
}
