package se.iths.tt.javafxtt.Paint;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class PaintController {

    //chat
    public Button sendButton;
    public ListView<String> messagesListView;
    public TextArea messageField;
    public MenuItem disConnectFromNetworkLabel;
    @FXML
    //network
    private MenuItem connectToNetworkLabel;

    //paint
    public Canvas canvas;
    @FXML
    private Button undoButton;

    @FXML
    private Button redoButton;

    @FXML
    private ColorPicker colorpicker;
    @FXML
    private TextField penSize;

    //for manual drawing
    @FXML
    private CheckBox eraser;
    //for manual drawing

    //public TextField sizeField;  //***
    @FXML
    private Slider sizeSlider;

    //choicebox with objects square circle etc:
    @FXML
    private ChoiceBox <ShapeType> choiceBox;
    @FXML
    private CheckBox selectMode;

    @FXML
    public Stage stage;

    // Calendar
    @FXML
    private DatePicker picker;
    @FXML
    private TextArea notes;
    @FXML
    private Button saveButton;

    //Cal
    @FXML
    private Text output;
    @FXML
    private Button numButton;


    PaintModel model = new PaintModel();
    //ChatViewModel model = new ChatViewModel();

    ConnectToNetwork connectToNetwork = new ConnectToNetwork();
    //ChatViewModel model = new ChatViewModel();
    ObservableList<ShapeType> shapeTypesList=
            //fill it with shapetype values (the enum)
            FXCollections.observableArrayList(ShapeType.values());


    public void init(Stage stage){
        this.stage=stage;

    }

        public void initialize(){
        colorpicker.valueProperty().bindBidirectional(model.currentColorProperty());
        sizeSlider.valueProperty().bindBidirectional(model.doubleSizeProperty());
        //connectToNetwork.booleanProperty.bindBidirectional(model.booleanProperty());
        choiceBox.valueProperty().bindBidirectional(model.currentShapeTypeProperty());
        choiceBox.setItems(shapeTypesList);
        model.getShapes().addListener(this::listChanged);

        messageField.textProperty().bindBidirectional(connectToNetwork.messageProperty());
        messagesListView.setItems(connectToNetwork.getObservableListMessages());
        sendButton.disableProperty().bind(connectToNetwork.messageProperty().isEmpty());
        /*sendButton.textProperty().bind(Bindings.when(model.messageProperty().isEqualTo("secret"))
                    .then("Hemligt")
                    .otherwise("Send message"));*/
        disConnectFromNetworkLabel.setDisable(true);


        picker.valueProperty().addListener((o, oldDate, date) ->{    //TODO ändra till en bindbidirectional?
            //listen for when we select a new date and present info connected to it
        notes.setText(data.getOrDefault(date,""));
            //Returns a value if there is a key called date, if there is no such key then
            //an empty string will be returned
        });
        picker.setValue(LocalDate.now());
    }

    public void onConnectToNetworkLabelClicked(ActionEvent actionEvent) {
        connectToNetworkLabel.setDisable(true);
        disConnectFromNetworkLabel.setDisable(false);
        connectToNetworkLabel.disableProperty();
        connectToNetwork.connect();
        //connectToNetwork.createThreads(ListView<String> messagesListView);
        connectToNetwork.createThreads();
    }

    public void onDisConnectFromNetworkLabelClicked(ActionEvent actionEvent) {
        connectToNetworkLabel.setDisable(false);
    }

    public void onSendButtonClicked() {
        connectToNetwork.sendMessage();
    }


    public void enableSendButton(){
        String message = messageField.getText();
        boolean disableButtons = message.isEmpty() || message.trim().isEmpty();
        sendButton.setDisable(disableButtons);
    }

    public void onCanvasClicked(MouseEvent mouseEvent) {
        if (selectMode.isSelected()) {
            model.canvasClicked(mouseEvent.getX(), mouseEvent.getY());
        }else{
                model.createShape(mouseEvent.getX(), mouseEvent.getY());
            }
    }


//TODO move to model
    public void onCanvasDragged(MouseEvent mouseEvent){
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(e ->{
            double size = Double.parseDouble(penSize.getText());   //TODO ha den här eller i model?
            double x = e.getX() - size /2;
            double y = e.getY() - size /2;
            if(eraser.isSelected()){
                graphics.clearRect(x,y,size,size);
            }else{
                graphics.setFill(colorpicker.getValue());
                graphics.fillRect(x,y,size,size);
            }
        });
    }


    public void onUndoButtonClicked(ActionEvent actionEvent) {
        model.undo();
    }

public void onRedoButtonClicked(ActionEvent actionEvent){
        model.redo();

}
    //TODO ska det här vara i model?
    private void listChanged(Observable observable){
        var context = canvas.getGraphicsContext2D();
        context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        //när man tar bort så vill man sudda allt o rita om från början
        for (Shape s : model.getShapes()){
            s.draw(context);
            //Ritar ut det grafiska gränssnittet när en shape läggs till
        }
    }


    public void onExitButtonClicked(){
        Platform.exit();

    }


//TODO skapa tråd att spara i parallellt med att programmet körs + move below to model

   public void onSaveLabelClicked(ActionEvent actionEvent) {
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Save as");   //totle of filechooser
       fileChooser.setInitialDirectory(new File(System.getProperty("user.home"))); //hamnar i hemkatalog
       fileChooser.getExtensionFilters().clear(); //vi tar bort allt som finns i den
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
       //constructor som tar två parametrar
       File filePath = fileChooser.showSaveDialog(stage);
       //filechooserobjektet returns a file to save to
       //showsaveDialog tar ett window dvs det fönster vi körs ovanpå och ej kunna klicka
       if (filePath != null)
           //om anv trycker på cancel
           model.saveToFile(filePath.toPath());
    }
    
    
    //TODO move Calendar to model/seperate class
    private Map<LocalDate, String> data = new HashMap<>();
    //maps date to a note

    public void onUpdateNotesButtonClicked() {
        load();
        //load the data when program starts               //TODO fungerar ej
        data.put(picker.getValue(), notes.getText());
        //inserts values into map
    }

    public void onSaveNotesButtonClicked(ActionEvent actionEvent){
        //we save the data object (name of the list)
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get("notes.data")))){
            //create a stream of the file, call the file notes.data,
            stream.writeObject(data);      //TODO få savemetoden för calendar att funka
            //and write the dataobject to the file
        }catch (Exception e){
            System.out.println("Failed to save: " + e);
        }


    }

    private void load(){
        Path file = Paths.get("notes.data");
        //obtain a reference to the file and see if it exists so we can load

        if (Files.exists(file)){
            try (ObjectInputStream stream =
                         new ObjectInputStream(Files.newInputStream(file))){
                data = (Map<LocalDate, String>) stream.readObject();
                //read the object, its the same object we read back, we typecast it to map of localdate and string
                //because we know the type bc we saved the data to it
            }catch (Exception e){
                System.out.println("Failed to load: " + e);
            }
        }
    }
    //Calculator

    public void processNumPad(ActionEvent event){
        String value = numButton.getText();
        System.out.println(value);

    }


    public void processOperator(ActionEvent actionEvent) {
    }

    //notes

    public void onFullViewClicked(ActionEvent actionEvent) {
    }



}
