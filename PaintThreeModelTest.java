package se.iths.tt.javafxtt.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.iths.tt.javafxtt.Paint.Model;

import java.awt.*;

public class PaintThreeModelTest {
    Model model = new Model();

    //2 tester
    //Hur vi kan implementera tester i labben tex
    //Man ha en modell som lagrar shapes o man kan modifiera shapes om man tar bort shapes med en undo metod ska de tas bort den sneatse ifrån händelserna
    //Om man lägger till en ska den finnas
    @Test
    void createNewPaintThreeModelAtMouseCoordinatesPositionWhenMouseIsClicked() {
        Position expected = new Position(getX(), getY());
        Position actual = this.model.getPosition();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void createNewPaintThreeModelWithSelectedColorWhenColorIsSelectedAndMouseIsClickedOnCanvas() {
        Color expected = new Color(colorSelected);
        Color actual = this.model.getColor();
        Assertions.assertEquals(expected, actual);
    }
}
