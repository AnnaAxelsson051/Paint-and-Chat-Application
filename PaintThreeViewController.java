package se.iths.tt.javafxtt.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PaintThreeViewController {
    @FXML
    public Canvas canvas;
    @FXML
    private ColorPicker colorpicker;
    @FXML
    private TextField penSize;
    @FXML
    private CheckBox eraser;
    @FXML
    private Button button;

    //attach listener to the canvas with initialize method
    //will be called automatically by fxmlloader

    public void initialize(){

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

    public void onCanvasSquare(MouseEvent mouseEvent){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLUE);
        gc.fillRect(mouseEvent.getX(), mouseEvent.getY(), 100, 100);

    }

    public void onActionExit(){
        Platform.exit();

    }
}
