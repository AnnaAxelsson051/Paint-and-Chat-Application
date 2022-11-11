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

public class Model {
    //Models can be used to feed multiple View objects

    List<Node> selectionModel = new ArrayList<>();


    public ObservableList<? extends Shape> getShapes() {
        return shapes;
    }
    //som parameter till observerbaralistan skickar vi ett lambda som implementerar inerfacet extractor with a
    //method observable som tar objektet som vi stoppat in, tala om vilka observerbara fält ska vi få
    //notifieringar från
    ObservableList<Shape> shapes = FXCollections.observableArrayList(param -> new Observable[]{
            param.colorProperty(),
            param.sizeProperty(),
            param.heightProperty(),
            param.widthProperty()
    });

    //connect to network


    ObservableList<Shape> observableListShapes = FXCollections.observableArrayList();
    ObservableList<String> observableListMessages = FXCollections.observableArrayList();

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;


    public void connectToNetwork() {

        try {
            socket = new Socket("localhost", 8000);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createThreadShapes();

    }
    public void createThreadShapes() {
        var threadShapes = new Thread(() -> {
            try {
                while (true) {
                    String string = reader.readLine();
                    if (string.contains("Shape")) {
                        System.out.println("Shape");
                        //skickar som kommaseparerad lista o anv string.split för att dela
                        //Platform.runLater(() -> observableListShapes.add((Shape) shape));
                    }else
                        Platform.runLater(() -> observableListMessages.add(string));
                    System.out.println("Message");

                }
            } catch (IOException e) {
                throw new RuntimeException(e);       //TODO deleta catch?
            }
        });
        threadShapes.setDaemon(true);
        threadShapes.start();
    }

//connect to network
    public BooleanProperty connectToNetwork = new SimpleBooleanProperty();

    public BooleanProperty connectToNetworkProperty(){
        return connectToNetwork;
    }

    public boolean getConnectToNetwork() {
        return connectToNetwork.get();
    }

    public void setConnectToNetwork(boolean connectToNetwork) {
        this.connectToNetwork.set(connectToNetwork);
    }


    //shapes

    ObjectProperty shape = new SimpleObjectProperty();

    public Object getShape() {
        return shape.get();
    }

    public ObjectProperty shapeProperty() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape.set(shape);
    }

    public ObservableList<Shape> getObservableListShapes() {
        return observableListShapes;
    }

    public void setObservableListShapes (ObservableList<Shape> observableListShapes) {
        this.observableListShapes = observableListShapes;
    }

    public void sendShape() {
        writer.println(getShape());
        //getObservableList().add(getMessage());
        setShape(null);
    }

    //messages

    public StringProperty message = new SimpleStringProperty();

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public ObservableList<String> getObservableListMessages() {
        return observableListMessages;
    }

    public void setObservableListMessages(ObservableList<String> observableListMessages) {
        this.observableListMessages = observableListMessages;
    }

    public void sendMessage() {
        writer.println(getMessage());
        //getObservableList().add(getMessage());
        setMessage("");
    }




    //shapetype
    public ObjectProperty<ShapeType> currentShapeType = new SimpleObjectProperty<>(ShapeType.CIRCLE);
    public ObjectProperty<ShapeType> currentShapeTypeProperty() {return currentShapeType;}
    public ShapeType getCurrentShapeType() {
        return currentShapeType.get();
    }
    public void setCurrentShapeType(ShapeType currentShapeType) {
        this.currentShapeType.set(currentShapeType);
    }


    //color
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

  //height
  public DoubleProperty doubleHeight = new SimpleDoubleProperty();     //varför inget <> ?
    public DoubleProperty doubleHeightProperty() {return doubleHeight;}
    public double getDoubleHeight() {
        return doubleHeight.get();
    }
    public void setDoubleHeight(double doubleHeight) {
        this.doubleHeight.set(doubleHeight);
    }

    //width
    public DoubleProperty doubleWidth = new SimpleDoubleProperty();
    public DoubleProperty doubleWidthProperty() {return doubleWidth;}
    public double getDoubleWidth() {
        return doubleWidth.get();
    }
    public void setDoubleWidth(double doubleWidth) {
        this.doubleWidth.set(doubleWidth);
    }


    //create shapes with mouse coordinates:   //TODO make create/add seperate class
    public void createShape(double x, double y) {
        Shape shape = Shape.createShape(getCurrentShapeType(), x, y);
        shape.setColor(getCurrentColor());
        shape.setSize(getDoubleSize());
        addShape(shape);
        //adding shapes to a list in model w addShape method in model
        //the above triggers listChanged method/lyssnaren below som triggar utritning
    }


    public Shape addShape (Shape shape){
        shapes.add(shape);
        CommandPair commandpair = new CommandPair();
        commandpair.undo = () -> shapes.remove(shape);
        commandpair.redo = () -> shapes.add(shape);
        //Skapa ett command o spar det i undo/redostack för undoanvändning
        undoStack.push(commandpair);
        return shape;
    }


//TODO make undo redo seperate class
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





//TODO make save to file seperate class
    //TODO add function for saving as an image?

    //Save shapes to excell
    public void saveToFile(Path file) {
        StringBuffer outPut = new StringBuffer();
        for (Shape p : shapes) {
            if (p instanceof Circle) {
                outPut.append("<circle cx=");
                //<circle cx="25" cy="75" r="20"/>      //TODO Create seperate tread for this?
                outPut.append(p.getX());
                outPut.append(" cy=");
                outPut.append(p.getY());
                outPut.append(" r=");
                outPut.append(p.getSize());
                outPut.append("/>");
            }
            //<rect x="10" y="10" width="30" height="30"/>
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
        Model model = (Model) o;
        return Objects.equals(selectionModel, model.selectionModel) && Objects.equals(shapes, model.shapes) && Objects.equals(currentShapeType, model.currentShapeType) && Objects.equals(currentColor, model.currentColor) && Objects.equals(doubleSize, model.doubleSize) && Objects.equals(doubleHeight, model.doubleHeight) && Objects.equals(doubleWidth, model.doubleWidth) && Objects.equals(undoStack, model.undoStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(selectionModel, shapes, currentShapeType, currentColor, doubleSize, doubleHeight, doubleWidth, undoStack);
    }



}


