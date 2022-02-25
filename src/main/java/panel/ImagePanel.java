package panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImagePanel {
    private JPanel panel;
    private static JLabel imageDisplay = new JLabel("", SwingConstants.CENTER);

    public ImagePanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));

        panel.add(imageDisplay);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void setImage(File img) throws IOException{
        BufferedImage bi = ImageIO.read(img);
        ImageIcon icon = new ImageIcon(bi);
        imageDisplay.setIcon(icon);

        MainPanel.changeFrameSize(bi.getWidth() + 50, bi.getHeight() + 110);
    }
}
