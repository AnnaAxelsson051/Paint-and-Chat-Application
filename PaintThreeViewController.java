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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class PaintThreeViewController {


    @FXML
    public Canvas canvas;
    public Button undo;
    public Button redo;
    @FXML
    private ColorPicker colorpicker;
    @FXML
    private TextField penSize;
    @FXML
    private CheckBox eraser;
    @FXML
    private Button button;

    public ChoiceBox <ShapeType> choiceBox;
    //choicebox with shapetype objects

    Model model = new Model();
    ObservableList<ShapeType> shapeTypesList=
    //fill it with shapetype values (the enum)
            FXCollections.observableArrayList(ShapeType.values());

    //ÅNGRA
    Deque <Command> undoStack = new ArrayDeque<>();
    //Deque implements Command interface with execute method
    Deque <Command> redoStack = new ArrayDeque<>();



   //factory method creating shapes:
    public void canvasClicked(MouseEvent mouseEvent){
       if(mouseEvent.isControlDown()) {
           //if control is pressed last drawn circle turns red
           model.getShapes().stream().reduce((first, second) -> second).ifPresent(shape -> shape.setColor(Color.RED));
           return;
       }
         Shape shape = Shape.createShape(model.getCurrentShapeType(), mouseEvent.getX(), mouseEvent.getY());
        //skapar en ny shape där man klickar men vill vi ha tex rectangle kan vi göra det
        //utan att koden som skapar den (factorymetoden) i shape behöver ändras
        shape.setColor(model.getCurrentColor());

    model.addShape(shape);
    //adding shapes to a list in model w addShape method in model
    //the above triggers listChanged method/lyssnaren below som triggar utritning
        }

        public void init(Stage stage){

        }
    private void listChanged(Observable observable){
        var context = canvas.getGraphicsContext2D();
        for (Shape s : model.getShapes()){
            s.draw(context);
    //Ritar ut det grafiska gränssnittet när listan uppdateras dvs när man ritar en shape
    //triggas av add
        }
    }


    public void initialize(){
        //sizeField.textProperty().bindBidirectional(model.sizeProperty());
        //om man vill ha ett sizefild, + lägga till i modellklass osv m getters o setters mm
        colorpicker.valueProperty().bindBidirectional(model.currentColorProperty());
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
            //get brushsize
            double x = e.getX() - size /2;
            double y = e.getY() - size /2;

            if(eraser.isSelected()){
                graphics.clearRect(x,y,size,size);
                //if eraser is selected erase
            }else{
                graphics.setFill(colorpicker.getValue());
                //otherwise draw with selected color
                graphics.fillRect(x,y,size,size);
            }
        });
    }

    //man skulle kunna välja från en drop down meny vad man vill skapa, eller med olika knappar
    // sä säter den ett fält i modellen som säger vad det är för type vi vill rita

    //ÅNGRA
//Återställer senaste ändring med klick på ångra knapp
    public void undo(ActionEvent actionEvent) {
        Command firstUndoToExecute = undoStack.pop();
        firstUndoToExecute.execute();
        //behöver kopplas till speciella skapanden av former
    }
    
    public void redo(ActionEvent actionEvent){

        /*Command firstRedoToExecute = redoStack.push();
        firstRedoToExecute.execute();*/
        //behöver kopplas till speciella skapanden av former
        
    }

    public void changeSize(double newSize){
     /*Shape oldSize = shape.getSize();
     //spar nuv storlek i oldsize
     shape.setSize(newSize);
     //sets new size
     Command undo = ()-> shape.setSize(oldSize);
     //create undo
     undoStack.push(undo);*/

    }

    public void changeColor(){

    }



    /*public void onActionSave(){

    }

    public void onActionSelectShapes(){
    }
    */



    /*public void onCanvasSquare(MouseEvent mouseEvent){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRect(mouseEvent.getX(), mouseEvent.getY(), 100, 100);

    }*/

    /*public void onCanvasSquare(MouseEvent mouseEvent){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRect(mouseEvent.getX(), mouseEvent.getY(), 100, 100);

    }*/


    public void onActionExit(){
        Platform.exit();

    }

    public void onCanvasSmallCircle(ActionEvent actionEvent) {
    }

    public void onCanvasMediumCircle(ActionEvent actionEvent) {
    }

    public void onCanvasLargeCircle(ActionEvent actionEvent) {
    }

    public void onCanvasSmallTringle(ActionEvent actionEvent) {
    }

    public void onCanvasMediumTriangle(ActionEvent actionEvent) {
    }

    public void onCanvasLargeTriangle(ActionEvent actionEvent) {
    }

    public void onCanvasSmallSquare(ActionEvent actionEvent) {
    }

    public void onCanvasMediumSquare(ActionEvent actionEvent) {
    }

    public void onCanvasLargeSquare(ActionEvent actionEvent) {
    }

    public void onCanvasSmallRectangle(ActionEvent actionEvent) {
    }

    public void onCanvasMediumRectangle(ActionEvent actionEvent) {
    }

    public void onCanvasLargeRectangle(ActionEvent actionEvent) {
    }



    //spara till fil

    /*public void onSaveAction(ActionEvent actionEvent) {
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
            PaintThreeModel.saveToFile(filePath.toPath());
    }
    //ska va en listview av product*/
}
