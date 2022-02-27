package panel;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImagePanel extends Thread {
    private JPanel panel;
    private static JLabel imageDisplay = new JLabel("", SwingConstants.CENTER);
    private static List<BufferedImage> images = new ArrayList<>();
    private static int currentImage = 0;
    private static boolean animated = false;

    public ImagePanel() {
        panel = new JPanel();
        panel.add(imageDisplay);
    }

    public JPanel getPanel() {
        return panel;
    }

    public static void addImages(List<BufferedImage> newImages) {
        images.clear();
        images.addAll(newImages);
        currentImage = 0;
        changeImage();
    }

    public static void nextImage() {
        if (images.size() == currentImage + 1) return;
        currentImage++;
        changeImage();
    }

    public static void previousImage() {
        if (currentImage == 0) return;
        currentImage--;
        changeImage();
    }

    public static void startAnimation() {
        animated = true;
    }

    public static void stopAnimation() {
        animated = false;
    }

    public void run() {
        while (true) {
            try {
                sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (animated) {
                if (images.size() == currentImage + 1) currentImage = -1;
                nextImage();
            }
        }
    }

    public static void changeImage() {
    	var bi = images.get(currentImage);
        ImageIcon icon = new ImageIcon(bi);
        imageDisplay.setIcon(icon);

        MainPanel.changeFrameSize(bi.getWidth() + 50, bi.getHeight() + 140);
    }
}
