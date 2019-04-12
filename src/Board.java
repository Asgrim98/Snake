import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Board extends JPanel implements ActionListener, Cloneable {

    private final int X_SIZE = 500;
    private final int Y_SIZE = 500;
    private final int DELAY = 140;
    private final int SIZE = 10;

    private boolean up = false;
    private boolean down = false;
    private boolean right = true;
    private boolean left = false;

    private Integer points = 0;

    private boolean gameOver = false;

    private Boolean isPointSpawned = false;

    private Position pointPoz = new Position();

    private ArrayList<Position> bodyPoz = new ArrayList<>();

    private Timer timer;

    private Image point;
    private Image body;

    public Board() {


        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(X_SIZE, Y_SIZE));
        addKeyListener(new Poruszanie());

        timer = new Timer(DELAY, this);
        timer.start();

        loadImage();

        bodyPoz.add(new Position(20 * SIZE, 20 * SIZE));
        bodyPoz.add(new Position(21 * SIZE, 20 * SIZE));


    }


    private void loadImage() {

        ImageIcon body = new ImageIcon("src/images/body.jpg");
        this.body = body.getImage();


        ImageIcon point = new ImageIcon("src/images/point.jpg");
        this.point = point.getImage();
    }

    private void spawnPoint() {

        int r = (int) (Math.random() * 49);
        pointPoz.setX((r * SIZE));

        r = (int) (Math.random() * 49);
        pointPoz.setY((r * SIZE));

        isPointSpawned = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if (isPointSpawned == false) spawnPoint();
        checkColision();
        snakeMove();
        checkIfPointIsTaken();
        repaint();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);

        if( gameOver ) gameOver( g );

        else {


            g.drawImage(point, pointPoz.getX(), pointPoz.getY(), this);

            for (Position p : bodyPoz) {

                g.drawImage(body, p.getX(), p.getY(), this);
            }

            Toolkit.getDefaultToolkit().sync();

        }
    }

    public void gameOver( Graphics g ){

        String msg = "Game over";

        Font font = new Font( "Arial", Font.BOLD, 40);

        g.setColor( Color.WHITE );
        g.setFont( font );
        g.drawString( msg , 150,200);

        g.drawString( points.toString( ), 230, 250 );
    }

    public void checkIfPointIsTaken() {

        if( bodyPoz.get(0).getX() == pointPoz.getX()
            && bodyPoz.get(0).getY() == pointPoz.getY() ){

                isPointSpawned = false;
                bodyPoz.add( new Position( 700, 700) );
                points ++;
        }
    }

    public void snakeMove() {

        for (int i = bodyPoz.size() - 1; i > 0; i--) {

            bodyPoz.get(i).setX(bodyPoz.get(i - 1).getX());
            bodyPoz.get(i).setY(bodyPoz.get(i - 1).getY());
        }

        if (left) bodyPoz.get(0).subX(SIZE);


        if (right) bodyPoz.get(0).addX(SIZE);


        if (up) bodyPoz.get(0).subY(SIZE);


        if (down) bodyPoz.get(0).addY(SIZE);
    }

    public void checkColision() {

        if( bodyPoz.get( 0 ).getX() >= X_SIZE
            || bodyPoz.get( 0 ).getX() <= 0
            || bodyPoz.get( 0 ).getY() >= Y_SIZE
            || bodyPoz.get( 0 ).getY() <= 0 ) {

            gameOver = true;
        }

        int headX = bodyPoz.get( 0 ).getX();
        int headY = bodyPoz.get( 0 ).getY();

        for( int i = 1 ; i < bodyPoz.size() ; i++ ){

            if( bodyPoz.get( i ).getY() == headY && bodyPoz.get( i ).getX() == headX ) gameOver = true;
        }
    }


    private class Poruszanie extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_A && (right != true)) {
                left = true;
                up = false;
                down = false;
                right = false;
            }

            if (key == KeyEvent.VK_D && (left != true)) {
                right = true;
                up = false;
                down = false;
                left = false;
            }

            if (key == KeyEvent.VK_W && (down != true)) {
                up = true;
                right = false;
                left = false;
                down = false;
            }

            if (key == KeyEvent.VK_S && (up != true)) {
                down = true;
                right = false;
                left = false;
                up = false;
            }
        }
    }
}