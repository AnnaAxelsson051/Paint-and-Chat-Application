package se.iths.tt.javafxtt.Paint;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PaintController {

    //chat
    public Button sendButton;
    public ListView<String> messagesListView;
    public TextArea messageField;
    public MenuItem disConnectFromNetworkLabel;

    //network
    public MenuItem connectToNetworkLabel;

    //paint
    public Canvas canvas;

    public Button undoButton;


    public Button redoButton;


    public ColorPicker colorpicker;

    public TextField penSize;

    //for manual drawing

    public CheckBox eraser;
    //for manual drawing

    //public TextField sizeField;  //***

    public Slider sizeSlider;

    //choicebox with objects square circle etc:

    public ChoiceBox <ShapeType> choiceBox;

    public CheckBox selectMode;


    public Stage stage;

    // Calendar

    public DatePicker picker;

    public TextArea notes;

    public Button saveButton;




    PaintModel model = new PaintModel();
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
        sendButton.disableProperty().bind(model.connectToNetwork.messageProperty().isEmpty());
        messageField.textProperty().bindBidirectional(model.connectToNetwork.messageProperty());
        messagesListView.setItems(model.connectToNetwork.getObservableListMessages());
        sendButton.disableProperty().bind(model.connectToNetwork.messageProperty().isEmpty());
        /*sendButton.textProperty().bind(Bindings.when(model.messageProperty().isEqualTo("secret"))
                    .then("Hemligt")
                    .otherwise("Send message"));*/
        disConnectFromNetworkLabel.setDisable(true);


       /* picker.valueProperty().addListener((o, oldDate, date) ->{    //TODO ändra till en bindbidirectional?
            //listen for when we select a new date and present info connected to it
        notes.setText(data.getOrDefault(date,""));
            //Returns a value if there is a key called date, if there is no such key then
            //an empty string will be returned
        });
        picker.setValue(LocalDate.now());*/
    }

    public void onConnectToNetworkLabelClicked(ActionEvent actionEvent) {
        connectToNetworkLabel.setDisable(true);
        disConnectFromNetworkLabel.setDisable(false);
        connectToNetworkLabel.disableProperty();
        model.connectToNetwork.connect();
        //connectToNetwork.createThreads(ListView<String> messagesListView);
        model.connectToNetwork.createThreads();
    }

    public void onDisConnectFromNetworkLabelClicked(ActionEvent actionEvent) {
        connectToNetworkLabel.setDisable(false);
    }

    public void onSendButtonClicked() {
        model.connectToNetwork.sendMessage(shape.toString().split(","));



    }


    public void onCanvasClicked(MouseEvent mouseEvent) {
        if (selectMode.isSelected()) {
            model.canvasClicked(mouseEvent.getX(), mouseEvent.getY());
        }else{
                model.createShape(mouseEvent.getX(), mouseEvent.getY());
            }
    }


//TODO move to model?
    public void onCanvasDragged(MouseEvent mouseEvent){
        GraphicsContext graphics = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(e ->{
            double size = Double.parseDouble(penSize.getText());
            double x = e.getX() - size /2;
            double y = e.getY() - size /2;
            if(eraser.isSelected()){
                graphics.clearRect(x,y,size,size);
            }else{
                graphics.setFill(colorpicker.getValue());
                model.createShape(x,y);
            }
        });
    }


    public void onUndoButtonClicked(ActionEvent actionEvent) {
        model.undo();
    }

public void onRedoButtonClicked(ActionEvent actionEvent){
        model.redo();

}
    //TODO move to model?
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


//TODO move to model?

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
    
    /*
    //TODO move Calendar to model/seperate class
    private Map<LocalDate, String> data = new HashMap<>();
    //maps date to a note

    public void onUpdateNotesButtonClicked() {
        load();
        //load the data when program starts               //TODO anteckningar ej sparade när man startar om
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
    }*/






}
