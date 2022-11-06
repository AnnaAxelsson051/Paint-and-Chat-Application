package se.iths.tt.javafxtt.Paint;

import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
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

public class PaintThreeViewController {


    @FXML
    private Canvas canvas;
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
    private Stage stage;

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


    Model model = new Model();
    ObservableList<ShapeType> shapeTypesList=
            //fill it with shapetype values (the enum)
            FXCollections.observableArrayList(ShapeType.values());


    //for select mode
    /*private List<Shape> selectedShapes = new ArrayList<>();
    @FXML
    public void onShapeSelected(MouseEvent e) {
        Shape shape = (Shape) e.getSource();

        if (!selectedShapes.contains(shape)) {
            selectedShapes.add(shape);
        }
    }*/




    public void init(Stage stage){
        this.stage=stage;

    }

    public void delete(ActionEvent e) {
        // delelte all the shapes from selected shapes list
    }


    public void canvasClicked(MouseEvent mouseEvent){
        if(mouseEvent.isControlDown()) {
            //if control is pressed last drawn circle turns red
            model.getShapes().stream().reduce((first, second) -> second).ifPresent(shape -> shape.setColor(Color.RED));
            return;
        }
        /*if(selectMode.isSelected()) {         //if selectmode is on select shapes....
            model.getShapes().stream()
        }*/

        //creating shapes where canvas is cklicked
        model.createShape(mouseEvent.getX(),mouseEvent.getY());
    }






    private void listChanged(Observable observable){
        var context = canvas.getGraphicsContext2D();              //---
        for (Shape s : model.getShapes()){
            s.draw(context);
    //Ritar ut det grafiska gränssnittet när listan uppdateras dvs när man ritar en shape
    //triggas av add
        }
    }


    public void initialize(){
        colorpicker.valueProperty().bindBidirectional(model.currentColorProperty());
        sizeSlider.valueProperty().bindBidirectional(model.doubleSizeProperty());
        choiceBox.setItems(shapeTypesList);
        choiceBox.valueProperty().bindBidirectional(model.currentShapeTypeProperty());
        model.getShapes().addListener(this::listChanged);
        //bc the list is an observable list we can add a listener to it that connects to method
        // listChangedMethod above för utritning i det grafiska gränssnittet


        //for manual drawing:
        GraphicsContext graphics = canvas.getGraphicsContext2D();

        //get a graphicscontext to draw on
        canvas.setOnMouseDragged(e ->{
            double size = Double.parseDouble(penSize.getText());

            //get pensize
            double x = e.getX() - size /2;
            double y = e.getY() - size /2;                         //bryta ut till metod?

            //erase:
            if(eraser.isSelected()){
                graphics.clearRect(x,y,size,size);
            }else{
            //draw:
                graphics.setFill(colorpicker.getValue());
                //otherwise draw with selected color
                graphics.fillRect(x,y,size,size);
            }
        });

        //for notes
        picker.valueProperty().addListener((o, oldDate, date) ->{
            //add listener for value property so when we change the value of the datepicker
            //to a new date then the textarea notes is gon be updated to contain the note for that date
            //o = observablevalue
            notes.setText(data.getOrDefault(date,""));
            //notes.setText refers to textarea
            //Returns a value if there is a key called date, if there is no such key then
            //an empty string will be returned
        });
        picker.setValue(LocalDate.now());
        //picker is set to todays date

    }



//undo function

    public void undoButtonClicked(ActionEvent actionEvent) {
        model.undo();
    }

public void redoButtonClicked(ActionEvent actionEvent){
        model.redo();

}



    public void onActionExit(){
        Platform.exit();

    }




   public void onActionSave(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as");
        //ska stå överst i filväljar fönstret
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        //vad för filändelse ska man ha, till att den ska vara på vår hemkatalog
        fileChooser.getExtensionFilters().clear();
        //vi tar bort allt som finns i den
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
//constructor som tar två parametrar

        File filePath = fileChooser.showSaveDialog(stage);
        //så vill vi köra filechooserobjektet o den returnerar en file det är den filen vi vlt att spara till
        //showsaveDialog vill ha ett window för man måste tala om för den vilket fönster ska vi köras ovanpå
        //medan vi är inne i dialogen och ska välja vilken fil vi ska spara som ska vi inte kunna klicka på
        //de knappar o grejer som finns i fönstret bakom  så den kräver en stage
        //stagen har vi i helloapplication så vi beh få över den så att controllen vet om stagen
        if (filePath != null)
            //om anv trycker på cancel
            model.saveToFile(filePath.toPath());
    }
    
    
    
    //Notes

    private Map<LocalDate, String> data = new HashMap<>();
    //map that maps the date to a note

    public void uppdateNotes() {
        load();
        //load the data when program starts

        data.put(picker.getValue(), notes.getText());
        //inserts values into map
    }

    public void saveNotes(ActionEvent actionEvent){
        //we save the data object (name of the list)
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get("notes.data")))){
            //create a stream of the file, call the file notes.data,
            stream.writeObject(data);
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
    //Cal

    public void processNumPad(ActionEvent event){
        String value = numButton.getText();
        System.out.println(value);

    }


    public void processOperator(ActionEvent actionEvent) {
    }
}
