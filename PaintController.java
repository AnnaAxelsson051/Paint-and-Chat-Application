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

    public Slider sizeSlider;

    //choicebox

    public ChoiceBox <ShapeType> choiceBox;

    public CheckBox selectMode;


    public Stage stage;

    PaintModel model = new PaintModel();


    ObservableList<ShapeType> shapeTypesList=
            //fill it with shapetype values (the enum)
            FXCollections.observableArrayList(ShapeType.values());


    public void init(Stage stage){
        this.stage=stage;

    }

        public void initialize(){
        colorpicker.valueProperty().bindBidirectional(model.currentColorProperty());
        sizeSlider.valueProperty().bindBidirectional(model.doubleSizeProperty());
        choiceBox.valueProperty().bindBidirectional(model.currentShapeTypeProperty());
        choiceBox.setItems(shapeTypesList);
        model.getShapes().addListener(this::listChanged);
        sendButton.disableProperty().bind(model.connectToNetwork.messageProperty().isEmpty());
        messageField.textProperty().bindBidirectional(model.connectToNetwork.messageProperty());
        messagesListView.setItems(model.connectToNetwork.getObservableListMessages());
        sendButton.disableProperty().bind(model.connectToNetwork.messageProperty().isEmpty());
        disConnectFromNetworkLabel.setDisable(true);
    }

    public void onConnectToNetworkLabelClicked(ActionEvent actionEvent) {
        connectToNetworkLabel.setDisable(true);
        disConnectFromNetworkLabel.setDisable(false);
        connectToNetworkLabel.disableProperty();
        model.connectToNetwork.connect();
        model.connectToNetwork.createThreads();
    }

    public void onDisConnectFromNetworkLabelClicked(ActionEvent actionEvent) {
        connectToNetworkLabel.setDisable(false);
    }

    public void onSendButtonClicked() {
        //model.connectToNetwork.sendMessage(shape.toString());
        model.connectToNetwork.sendMessage(messageField.getText());

    }


    public void onCanvasClicked(MouseEvent mouseEvent) {
        if (selectMode.isSelected()) {
            model.canvasClicked(mouseEvent.getX(), mouseEvent.getY());
        }else{
                model.createShape(mouseEvent.getX(), mouseEvent.getY());
            }
    }

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

    private void listChanged(Observable observable){
        var context = canvas.getGraphicsContext2D();
        context.clearRect(0,0, canvas.getWidth(), canvas.getHeight());
        for (Shape s : model.getShapes()){
            s.draw(context);
        }
    }


    public void onExitButtonClicked(){
        Platform.exit();

    }


   public void onSaveLabelClicked(ActionEvent actionEvent) {
       FileChooser fileChooser = new FileChooser();
       fileChooser.setTitle("Save as");
       fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
       fileChooser.getExtensionFilters().clear();
       fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
       File filePath = fileChooser.showSaveDialog(stage);
       //filechooserobjektet returns a file to save to
       //showsaveDialog takes a window vi körs ovanpå och ej kan klicka på bakgrunden
       if (filePath != null)
           //om anv trycker cancel
           model.saveToFile(filePath.toPath());
    }

}
