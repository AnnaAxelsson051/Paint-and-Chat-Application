package se.iths.tt.javafxtt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;
import se.iths.tt.javafxtt.Paint.PaintThreeViewController;
import se.iths.tt.javafxtt.controller.ProductViewController;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("paint-three-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Paint App");
        stage.setScene(scene);
        //stage.setScene(new Scene(root));
        PaintThreeViewController controller = fxmlLoader.getController();
        //för att visa fildialog som döljer vanliga fönstret
        controller.init(stage);
        //creates controller
        stage.show();



    }

    public static void main(String[] args) {
        launch();
        //HelloApplication firstThread = new HelloApplication();
        //Thread.ofPlatform().start(firstThread);
        //firstThread.run();
    }

    /*@Override
    public void run() {

    }*/
}
