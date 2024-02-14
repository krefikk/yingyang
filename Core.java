import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.lang.Math;

class Core extends JPanel {

    //Adjusting the starting points of balls and other variables
    protected int xw, xb;
    protected int yw, yb;
    private int vx = 10, vy = 3;
    private int bounds;
    private int rows, columns, tileSize;
    private ArrayList<ColoredShape> tiles;
    private ArrayList<Ball> balls;

    public Core(int rows, int columns, int tileSize) {
        
        this.rows = rows;
        this.columns = columns;
        this.tileSize = tileSize;
        this.bounds = tileSize / 2;
        this.tiles = new ArrayList<>();
        this.balls = new ArrayList<>();

        //Randomizing location and speed values
        xw = (int) Math.floor(Math.random() *(((Interface.frameWidth / 2) - tileSize) - tileSize + 1) + tileSize);
        xb = (int) Math.floor(Math.random() *((Interface.frameWidth - tileSize) - (Interface.frameWidth / 2) + tileSize + 1) + (Interface.frameWidth / 2) + tileSize);
        yw = (int) Math.floor(Math.random() *((Interface.frameHeight - tileSize) - tileSize + 1) + tileSize);
        yb = (int) Math.floor(Math.random() *((Interface.frameHeight - tileSize) - tileSize + 1) + tileSize);
        vx = (Math.random() < 0.5) ? vx : -1*vx;
        vy = (Math.random() < 0.5) ? vy : -1*vy;

        // Creating tiles
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                
                int x = j * tileSize;
                int y = i * tileSize;
                Shape rect = new Rectangle(x, y, tileSize, tileSize);
                Color color = (j < columns / 2) ? Color.BLACK : Color.WHITE;
                tiles.add(new ColoredShape(rect, color));

            }
        }

        // Creating balls
        balls.add(new Ball(xw, yw, bounds, Color.WHITE, vx, vy));
        balls.add(new Ball(xb, yb, bounds, Color.BLACK, vx, vy));

        // Animation part
        Timer timer = new Timer(10, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                moveBalls();
                checkCollision();
                repaint();
            }

        });

        timer.start();
    }

    //Moving balls
    private void moveBalls() {
        for (Ball ball : balls) {
            
            ball.setX(ball.getX() + ball.getVx());
            ball.setY(ball.getY() + ball.getVy());
    
            // Bouncing back from walls
            if (ball.getX() > getWidth() - ball.getShape().width || ball.getX() < 0) {
                ball.setVx(-ball.getVx());
            }
            if (ball.getY() > getHeight() - ball.getShape().height || ball.getY() < 0) {
                ball.setVy(-ball.getVy());
            }

        }
    }

    private void checkCollision() {
        
        for (Ball ball : balls) {
            
            for (ColoredShape tile : tiles) {
                
                if (ball.getShape().getBounds().intersects(tile.getShape().getBounds())) {
                    
                    if (ball.getColor().equals(tile.getColor())) {
                        
                        tile.setColor(ball.getColor().equals(Color.WHITE) ? Color.BLACK : Color.WHITE);
                        
                        // Calculating the angle of incidence
                        double tileCenterX = tile.getShape().getBounds2D().getCenterX();
                        double tileCenterY = tile.getShape().getBounds2D().getCenterY();
                        double ballCenterX = ball.getShape().getCenterX();
                        double ballCenterY = ball.getShape().getCenterY();

                        double angle = Math.atan2(ballCenterY - tileCenterY, ballCenterX - tileCenterX);

                        // Adjusting velocity based on angle of incidence
                        if (Math.abs(angle) < Math.PI / 4 || Math.abs(angle) > 3 * Math.PI / 4) {
                            // Adjusting x velocity
                            ball.setVx(-ball.getVx());
                        } else {
                            // Adjusting y velocity
                            ball.setVy(-ball.getVy());
                        }

                    }
                    
                }
            }
        }
    }

    public void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Drawing tiles
        for (ColoredShape tile : tiles) {
            g2d.setColor(tile.getColor());
            g2d.fill(tile.getShape());
        }

        // Drawing balls
        for (Ball ball : balls) {
            g2d.setColor(ball.getColor());
            g2d.fill(ball.getShape());
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(columns * tileSize, rows * tileSize);
    }

}