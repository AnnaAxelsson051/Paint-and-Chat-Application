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

    //2 tester
    //Hur vi kan implementera tester i labben tex
    //Man ha en modell som lagrar shapes o man kan modifiera shapes om man tar
    // bort shapes med en undo metod ska de tas bort den sneatse ifrån händelserna
    //Om man lägger till en ska den finnas

    @Test
     void createNewPaintModelAtMouseCoordinatesPositionWhenMouseIsClicked() {
        paint.createShape(12.0, 4.2);
        var expected = new Position((int) 12.0, (int) 4.2);
        var actual = paint.getShapes().get(0).getX();
        Assertions.assertEquals(expected, actual);
        //assertEquals(new Position(12,4), paint.currentShapeType.get());
    }

    @Test
    void removeLastShapeInListWhenUndoIsSelected(){
        paint.canvasClicked(13.2, 3.7);
        paint.canvasClicked(4.0, 10.4);
        paint.undo();
        paint.getShapes().stream().reduce((first, second) -> second);
        var actual = paint.getShapes().get(0);
        var expected = paint.currentShapeType.get();
        Assertions.assertEquals(expected, actual);


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



}