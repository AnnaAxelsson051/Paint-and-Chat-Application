package se.iths.tt.javafxtt.Paint;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.net.Socket;

public class ConnectToNetworkChat {

    StringProperty message = new SimpleStringProperty();

    ObservableList<String> observableList = FXCollections.observableArrayList();
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
                        String line = reader.readLine();
                        Platform.runLater(()-> observableList.add(line));
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


    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    public ObservableList<String> getObservableList() {
        return observableList;
    }

    public void setObservableList(ObservableList<String> observableList) {
        this.observableList = observableList;
    }

    public void sendMessage() {
        writer.println(getMessage());
        //getObservableList().add(getMessage());
        setMessage("");
    }
}