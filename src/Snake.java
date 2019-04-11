import java.awt.EventQueue;
import javax.swing.JFrame;

public class Snake extends JFrame {


    public Snake() {

        add(new Board());
        setTitle("Snake");

        setTitle("Snake");
        setLocationRelativeTo(null);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pack();
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new Snake();
            frame.setVisible(true);
        });
    }
}