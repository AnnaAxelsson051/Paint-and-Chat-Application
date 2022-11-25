package se.iths.tt.javafxtt.Paint;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.paint.Color;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class PaintModel {

    ConnectToNetwork connectToNetwork = new ConnectToNetwork();

    List<Node> selectionModel = new ArrayList<>();

    public ObservableList<? extends Shape> getShapes() {
        return shapes;
    }
    ObservableList<Shape> shapes = FXCollections.observableArrayList(param -> new Observable[]{
            param.colorProperty(),
            param.sizeProperty(),
            param.heightProperty(),
            param.widthProperty()
    });


    public ObjectProperty<ShapeType> currentShapeType = new SimpleObjectProperty<>(ShapeType.CIRCLE);
    public ObjectProperty<ShapeType> currentShapeTypeProperty() {return currentShapeType;}
    public ShapeType getCurrentShapeType() {
        return currentShapeType.get();
    }
    public void setCurrentShapeType(ShapeType currentShapeType) {
        this.currentShapeType.set(currentShapeType);
    }


    public ObjectProperty<Color> currentColor = new SimpleObjectProperty<>(Color.BLACK);
    public ObjectProperty<Color> currentColorProperty() {
        return currentColor;
    }
    public Color getCurrentColor() {
        return currentColor.get();
    }
    public void setCurrentColor(Color currentColor) {
        this.currentColor.set(currentColor);
    }

    //size slider
    public DoubleProperty doubleSize = new SimpleDoubleProperty();
    public DoubleProperty doubleSizeProperty() {
        return doubleSize;
    }
    public double getDoubleSize() {
        return doubleSize.get();
    }
    public void setDoubleSize(double doubleSize) {
        this.doubleSize.set(doubleSize);
    }


  public DoubleProperty doubleHeight = new SimpleDoubleProperty();     //varför inget <> ?
    public DoubleProperty doubleHeightProperty() {return doubleHeight;}
    public double getDoubleHeight() {
        return doubleHeight.get();
    }
    public void setDoubleHeight(double doubleHeight) {
        this.doubleHeight.set(doubleHeight);
    }


    public DoubleProperty doubleWidth = new SimpleDoubleProperty();
    public DoubleProperty doubleWidthProperty() {return doubleWidth;}
    public double getDoubleWidth() {
        return doubleWidth.get();
    }
    public void setDoubleWidth(double doubleWidth) {
        this.doubleWidth.set(doubleWidth);
    }

    public void canvasClicked(double x, double y) {
        getShapes()
                    .stream()
                    .filter((shape) -> shape.isInside(x, y))
                    .reduce((first, second) -> second)
                    .ifPresent(s -> {
                        s.setColor(getCurrentColor());
                        s.setSize(getDoubleSize());   //TODO bryta ut till metod
                    });
    }

    public void createShape(double x, double y) {
        Shape shape = Shape.createShape(getCurrentShapeType(), x, y);
        shape.setColor(getCurrentColor());
        shape.setSize(getDoubleSize());
        addShape(shape);
    }


    private Shape addShape (Shape shape){
        if(connectToNetwork.connected) {
            String shapeAsString = shape.toString();
            String shapeAsStringClean = shapeAsString.substring
                    (shapeAsString.indexOf(' ') + 1);
            connectToNetwork.sendMessage(shapeAsStringClean);
            shapes.add(shape);
            //spar det som kommer från toString i en string
            //tar bort första (id) som skickas av server
        }
            shapes.add(shape);
            CommandPair commandpair = new CommandPair();
            commandpair.undo = () -> shapes.remove(shape);
            commandpair.redo = () -> shapes.add(shape);
            //Skapa ett command o spar det i undo/redostack för återanvändning
            undoStack.push(commandpair);
            return shape;
    }


    Deque<CommandPair> undoStack = new ArrayDeque<>();
    Deque<CommandPair> redoStack = new ArrayDeque<>();

    public void undo(){
        System.out.println("test");
        var commandPair= undoStack.pop();
        commandPair.undo.execute();
        redoStack.push(commandPair);
    }

    public void redo(){
        var commandPair= redoStack.pop();
        commandPair.redo.execute();
        undoStack.push(commandPair);
    }



    interface Command{
        void execute();
    }
    public class CommandPair{

        Command undo;
        Command redo;
    }


    public void saveToFile(Path file) {
        StringBuffer outPut = new StringBuffer();
        for (Shape p : shapes) {
            if (p instanceof Circle) {
                outPut.append("<circle cx=");
                outPut.append(p.getX());
                outPut.append(" cy=");
                outPut.append(p.getY());
                outPut.append(" r=");
                outPut.append(p.getSize());
                outPut.append("/>");
            }
            if (p instanceof Rectangle) {
                outPut.append("<rectangle x=");
                outPut.append(p.getX());
                outPut.append(" y=");
                outPut.append(p.getY());
                outPut.append(" width=");
                outPut.append(p.getWidth());
                outPut.append(" height=");
                outPut.append(p.getHeight());
                outPut.append("/>");
            }
            try {
                Files.writeString(file, outPut.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaintModel model = (PaintModel) o;
        return Objects.equals(selectionModel, model.selectionModel) && Objects.equals(shapes, model.shapes) && Objects.equals(currentShapeType, model.currentShapeType) && Objects.equals(currentColor, model.currentColor) && Objects.equals(doubleSize, model.doubleSize) && Objects.equals(doubleHeight, model.doubleHeight) && Objects.equals(doubleWidth, model.doubleWidth) && Objects.equals(undoStack, model.undoStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectionModel, shapes, currentShapeType, currentColor, doubleSize, doubleHeight, doubleWidth, undoStack);
    }



}


