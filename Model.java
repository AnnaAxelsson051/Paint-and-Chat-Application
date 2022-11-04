package se.iths.tt.javafxtt.Paint;

import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.Size;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    ObservableList<Shape> shapes = FXCollections.observableArrayList(param -> new Observable[]{
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
        shape.setSize(getDoubleSize());
        addShape(shape);
        //adding shapes to a list in model w addShape method in model
        //the above triggers listChanged method/lyssnaren below som triggar utritning
    }

    //add save method

    public void saveToFile(Path file) {
        StringBuffer outPut = new StringBuffer();
        for (Shape p : shapes) {
            if (p instanceof Circle) {
                outPut.append("<circle cx= ");
                //<circle cx="25" cy="75" r="20"/>
                outPut.append(p.getX());
                outPut.append(" cy= ");
                outPut.append(p.getY());
                outPut.append(" r= ");
                outPut.append(p.getSize());
                outPut.append("/>");
            }
            //<rect x="10" y="10" width="30" height="30"/>
            if (p instanceof Rectangle) {
                outPut.append("<rectangle x= ");
                //<circle cx="25" cy="75" r="20"/>
                outPut.append(p.getX());
                outPut.append(" y= ");
                outPut.append(p.getY());
                outPut.append(" y= ");
                //outPut.append(p.getWidth());
                outPut.append(" y= ");
                //outPut.append(p.getHeight());
                outPut.append("/>");
            }
            try {
                Files.writeString(file, outPut.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



        public Shape addShape (Shape shape){
            shapes.add(shape);
            return shape;
        }
    }


