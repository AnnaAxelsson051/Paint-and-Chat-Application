package se.iths.tt.javafxtt.Paint;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class ConnectToNetwork {
    //ConnectToNetwork
boolean connected;

    ObservableList<Shape> observableListShapes = FXCollections.observableArrayList();
    ObservableList<String> observableListMessages = FXCollections.observableArrayList();

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public BooleanProperty connectToNetwork = new SimpleBooleanProperty();
    public StringProperty message = new SimpleStringProperty();


    public void connect() {
        try {
            socket = new Socket("localhost", 8000);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createThreads();
        connected = true;

    }
    public void createThreads() {
        var threadShapes = new Thread(() -> {
            try {
                while (true) {
                    String string = reader.readLine();  //TODO Skapa tråd och läsa meddelanden

                    if (string.contains("Shape")) {
                        String[] temp = string.split(",");
                        String shapeType = temp[0];
                        double x = Double.parseDouble(temp[1]);
                        double y = Double.parseDouble(temp[2]);
                        //göra om fr textsträng tillbaka till shape sätta varje värde till shape konstruktorn
                        Platform.runLater(() -> observableListShapes
                                .add(Shape.createShape(ShapeType.valueOf(shapeType.toUpperCase()), x, y)));
                        //Om du skriver Shapetype.valueOf(string goes here)
                    }else {
                        Platform.runLater(() -> observableListMessages.add(string));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        threadShapes.setDaemon(true);
        threadShapes.start();
    }

    //connect

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

    public void setShape(Shape shape) {   //TODO ta bort dessa med shapes för att alla är messages?
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


    public void sendMessage(String networkFormat) {
        writer.println(networkFormat);
    //send message får en string som heter networkformat som den printar med println
    }
}

