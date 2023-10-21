/**
 *
 * @author limdan8vq
 */
import java.io.*;
import java.util.*;
import java.awt.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
public class Main extends JFrame{
    public static int WIDTH = 1275;
    public static int HEIGHT = 750;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException{
        // TODO code application logic here
        Main main = new Main();
    }
    
    public Main() throws FileNotFoundException, UnsupportedAudioFileException, IOException, LineUnavailableException {
        super("Vulture");
        setSize(WIDTH, HEIGHT);
        setBackground(Color.BLACK);
        
        //new PointLines( #of points, redraw bckgrnd, color, changing gradient, bouncy, changing size, fill in area)
        Vulture game = new Vulture();
        ((Component) game).setFocusable(true);
        getContentPane().add(game);
        
        //setExtendedState(JFrame.MAXIMIZED_BOTH); 
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setVisible(true);
    }
}
