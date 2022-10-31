package se.iths.tt.javafxtt.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import se.iths.tt.javafxtt.model.PaintModel;
import se.iths.tt.javafxtt.model.ProductModel;

import java.net.URL;
import java.util.ResourceBundle;

public class PaintViewController {

    PaintModel paintModel = new PaintModel();

    public ColorPicker colorPicker;
    public TextField brushSize;
    public Button brushButton;

    public void onAddAction(ActionEvent actionEvent) {
    }

    /*private ColorPicker colorpicker;

    @FXML
    private TextField bsize;

    @FXML
    private Canvas canvas;

    boolean toolSelected = false;
    //onAction for the brush button, when we click on teh button its true

    GraphicsContext brushTool;
    //class used for drawing on canvas

    public void initialize() {
        brushTool = canvas.getGraphicsContext2D();
        canvas.setOnMouseDragged(e -> {
            double size = Double.parseDouble(bsize.getText());
            //get the textsize tha user enters and store it in the double variable
            double x = e.getX() - size / 2;
            //coordinates for mouse
            double y = e.getY() - size / 2;
            if (toolSelected && !bsize.getText().isEmpty()) {
                //if the tool is selected and text in bsize is empty
                brushTool.setFill(colorpicker.getValue());
                brushTool.fillRoundRect(x, y, size, size, size, size);

            }
        });

    }

    @FXML
    public void setToolSelected(ActionEvent e) {
        toolSelected = true;
        //when someone press the button we set tool selected to true

    }*/

}





