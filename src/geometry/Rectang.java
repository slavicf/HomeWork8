package geometry;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Rectang {

    public Rectangle rectangle = new Rectangle();
    public boolean xDn = true;
    public boolean yRt = true;

    public Rectang(double x,double  y,double  width,double  height,Paint color) {
        rectangle.setX(x);
        rectangle.setY(y);
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setStroke(color);
        rectangle.setFill(color);
        rectangle.setStrokeWidth(2);
    }
}
