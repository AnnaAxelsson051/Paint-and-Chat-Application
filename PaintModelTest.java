package se.iths.tt.javafxtt.model;

import javafx.css.Size;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testng.reporters.jq.Model;
import se.iths.tt.javafxtt.Paint.PaintModel;
import se.iths.tt.javafxtt.model.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaintModelTest {
    PaintModel paint = new PaintModel();

    @Test
    void createNewPaintModelAtMouseCoordinatesPositionWhenMouseIsClicked() {  //coordinates
        paint.createShape(12.0, 4.2);
        Assertions.assertEquals(12.0, paint.getShapes().get(0).getX());
        Assertions.assertEquals(4.2, paint.getShapes().get(0).getY());
    }

    @Test
    void removeLastShapeInListWhenUndoIsSelected() {
        paint.createShape(13.2, 3.7);
        paint.createShape(4.0, 10.4);
        paint.undo();
        Assertions.assertEquals(1, paint.getShapes().size());
        Assertions.assertEquals(13.2, paint.getShapes().get(0).getX());
        Assertions.assertEquals(3.7, paint.getShapes().get(0).getY());


    }
}












    /*@Test
    void changeShapesizeToSelectedSizeWhenShapeIsSelected() {
        paint.setDoubleSize(16.0);
        var expected = new Size(16.0);
        var actual = paint.getDoubleSize();
        assertEquals(expected,actual);
    }

    @Test
    void createNewPaintModelWithSelectedColorWhenMouseIsClicked() {
        paint.doubleSize(16.0);
        var expected = new Size(16.0);
        var actual = paint.;
        Assertions.assertEquals(expected, actual);
    }
    @Test
    void createNewPaintModelAtMouseCoordinatesPositionWhenMouseIsClickeds() {
        paint.
        var expected = new Position(12, 4);
        var actual = paint. ;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void setPreselectableShapesLabelToCircleWhenPaintAppIsCreated() {
        paint.canvasClicked(12.0, 4.0);
        var expected = new Position(12.0, 4.0);
        var actual = paint.currentShapeType.get();
        Assertions.assertEquals(expected, actual);
    }*/



