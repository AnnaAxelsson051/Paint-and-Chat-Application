package se.iths.tt.javafxtt.Paint;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Size;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class Model {

    public DoubleProperty doubleSize = new SimpleDoubleProperty();     //varför inget <> ?
    //till slider
    public ObjectProperty<Color> currentColor = new SimpleObjectProperty<>(Color.PINK);
    public ObjectProperty<ShapeType> currentShapeType = new SimpleObjectProperty<>(ShapeType.CIRCLE);


    //observable list
    public ObservableList<? extends Shape> getShapes() {
        return shapes;
    }

    //what observable fields we wanna listen to:
    ObservableList<ObsShape> shapes = FXCollections.observableArrayList(param -> new Observable[]{
            param.colorProperty(),
            param.sizeProperty()
    });
    //som parameter till observerbaralistan skickar vi ett lambda som implementerar inerfacet extractor with a
    //method som tar objektet som vi stoppat in, tala om vilka observerbara fält ska vi få
    //notifieringar från


    //shapetype
    public ShapeType getCurrentShapeType() {
        return currentShapeType.get();
    }

    public ObjectProperty<ShapeType> currentShapeTypeProperty() {
        return currentShapeType;
    }

    public void setCurrentShapeType(ShapeType currentShapeType) {
        this.currentShapeType.set(currentShapeType);
    }


    //color
    public Color getCurrentColor() {
        return currentColor.get();
    }

    public ObjectProperty<Color> currentColorProperty() {
        return currentColor;
    }

    public void setCurrentColor(Color currentColor) {
        this.currentColor.set(currentColor);
    }

    //size slider
    public double getDoubleSize() {
        return doubleSize.get();
    }

    public DoubleProperty doubleSizeProperty() {
        return doubleSize;
    }

    public void setDoubleSize(double doubleSize) {
        this.doubleSize.set(doubleSize);
    }


    //create shapes with mouse coordinates:
    public void createShape(double x, double y) {
        Shape shape = Shape.createShape(getCurrentShapeType(), x, y);
        shape.setColor(getCurrentColor());
        //shape.setSize(getDoubleSize());
        addShape(shape);
        //adding shapes to a list in model w addShape method in model
        //the above triggers listChanged method/lyssnaren below som triggar utritning
    }


    //Sizefield
    public StringProperty size = new SimpleStringProperty("10x10");       //***

    //left value in textfield
    public double getWidth() {
        String left = size.get().split("[Xx*,]")[0];
        return Double.parseDouble(left);
    }

    //right value
    public double getHeight() {
        String right = size.get().split("[Xx*,]")[0];
        return Double.parseDouble(right);

    }


    //add shapes to a list
    //triggers listener
    public Shape addShape(Shape shape) {
        var oShape = new ObsShape(shape);
        shapes.add(oShape);
        return oShape;
    }




    public StringProperty sizeProperty() {
        return size;
    }

    /*for auto conversion
    public ObjectProperty<Point> widthHeightProperty(){
     }
}

//for auto converting betw string n double in sixefield
record Point(double w, double h){}*/


class ObsShape extends Shape {
    Shape shape;
    ObjectProperty<Color> color = new SimpleObjectProperty<>();

    public ObsShape(Shape shape){
        super(shape.getX(), shape.getY());
        this.shape = shape;
        color.set(shape.getColor());
    }

    public ObjectProperty <Color> colorProperty(){
    return color;
    }
    @Override
    public Color getColor(){
        return color.get();
    }

    @Override
    public void setColor(Color color){
        shape.setColor(color);
        this.color.set(color);
    }
    @Override
    public void draw(GraphicsContext context) {

    }

}


}
