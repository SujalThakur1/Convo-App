
import java.awt.*;
import javax.swing.*;

/**
 * The Photo class extends JPanel and represents an image panel
 * that can display an image on it.
 */
public class Photo extends JPanel {
    private Image bimage;// The image to be displayed

    /**
     * Constructs a new Photo object with the given image file path.
     * @param image The file path of the image to be displayed.
     */
    public Photo(String image) {
        bimage =  new javax.swing.ImageIcon("../Convo app/images/"+image).getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D 	g2 = (Graphics2D) g;
        g2.drawImage(bimage, 0, 0,700,400, null);
    }

    /**
     * Loads a new image and repaints the panel with it.
     * @param i The new Image object to be displayed.
     */
    public void loadImage(Image i) {
        bimage = i;
        repaint();
    }

}
