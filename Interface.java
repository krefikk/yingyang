import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Interface extends JFrame {

    protected static int frameHeight = 637;
    protected static int frameWidth = 637;
    private int tileSize = 30;

    public Interface() {

        setSize(frameWidth, frameHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Bouncing Ball");
        add(new Core(frameHeight / tileSize, frameWidth / tileSize, tileSize));
        setVisible(true);

    }

    public static void main(String[] args) {
        new Interface();
    }

}

class ColoredShape {
    
    private Shape shape;
    private Color color;

    public ColoredShape(Shape shape, Color color) {
        
        this.shape = shape;
        this.color = color;

    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}

class Ball {
    
    private Ellipse2D.Double shape;
    private Color color;
    private int vx;
    private int vy;

    public Ball(int x, int y, int diameter, Color color, int vx, int vy) {
        
        this.shape = new Ellipse2D.Double(x, y, diameter, diameter);
        this.color = color;
        this.vx = vx;
        this.vy = vy;

    }

    public Ellipse2D.Double getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public int getX() {
        return (int) shape.x;
    }

    public void setX(int x) {
        shape.x = x;
    }

    public int getY() {
        return (int) shape.y;
    }

    public void setY(int y) {
        shape.y = y;
    }

}