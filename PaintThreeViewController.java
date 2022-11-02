package se.iths.tt.javafxtt.Paint;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.util.ArrayDeque;
import java.util.Deque;

public class PaintThreeViewController {


    @FXML
    public Canvas canvas;
    public Button undo;
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

    public void canvasClicked(MouseEvent mouseEvent){
        Shape shape = Shape.createShape(choiceBox.getValue(), mouseEvent.getX(), mouseEvent.getY());
        //här skapar vi en circle där man klickar men vill vi ha tex rectangle kan vi det
        //utan att koden som skapar den (factorymetoden) i shape behöver ändras
    }

    //fill it with shapetype values (the enum)
    ObservableList<ShapeType> shapeTypesList=
            FXCollections.observableArrayList(ShapeType.values());

    //attach listener to the canvas with initialize method
    //will be called automatically by fxmlloader

    //ÅNGRA
    Deque <Command> undoStack = new ArrayDeque<>();
    //Deque implements Command interface with execute method


    public void initialize(){
        choiceBox.setItems(shapeTypesList);
        choiceBox.setValue(ShapeType.CIRCLE);  //sets initial value

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
