package se.iths.tt.javafxtt.Paint;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;

class ConnectToNetworkShapes {

    ObjectProperty shape = new SimpleObjectProperty();

    ObservableList<Shape> observableList = FXCollections.observableArrayList();
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;


    public static void connectToNetwork() {

        try {
            socket = new Socket("localhost", 8000);
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));

            var thread = new Thread(() -> {
                try {
                    while (true) {
                        Shape shape = reader.readLine();
                        Platform.runLater(() -> observableList.add(shape));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.setDaemon(true);
            thread.start();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Shape getShape() {
        return shape.get();
    }

    public ObjectProperty shapeProperty() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape.set(shape);
    }

    public ObservableList<Shape> getObservableList() {
        return observableList;
    }

    public void setObservableList(ObservableList<Shape> observableList) {
        this.observableList = observableList;
    }

    public void sendShape() {
        writer.println(getShape());
        //getObservableList().add(getMessage());
        setShape("");
    }
}