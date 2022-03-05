package panel;

import javax.imageio.ImageIO;
import javax.swing.*;

import carver.SeamCarver;
import imageWrapper.ImageWrapper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        if (images.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have not submitted an image");
            return;
        }

        if (images.size() == currentImage + 1) return;
        currentImage++;
        changeImage();
    }

    public static void previousImage() {
        if (images.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have not submitted an image");
            return;
        }

        if (currentImage == 0) return;
        currentImage--;
        changeImage();
    }

    public static void lastImage() {
        if (images.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have not submitted an image");
            return;
        }

        currentImage = images.size() - 1;
        changeImage();
    }

    public static void startAnimation() throws Exception {
        if (images.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have not submitted an image");
            throw new IllegalArgumentException("No image selected");
        }
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

        int newWidth = Math.max(bi.getWidth() + 70, 900);
        int newHeight = Math.max(bi.getHeight() + 140, 500);

        MainPanel.changeFrameSize(newWidth, newHeight);
    }

    public static void saveImage() throws IOException {
        if (images.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have not submitted an image");
            return;
        }
        BufferedImage bi = images.get(currentImage);

        File carvedImage = new File("./carvedImage.png");
        ImageIO.write(bi, "png", carvedImage);
    }

    public static void viewEnergy() {
        if (images.isEmpty()) {
            JOptionPane.showMessageDialog(null, "You have not submitted an image");
            return;
        }

        var bi = images.get(currentImage);
        
        ImageWrapper wrap = new ImageWrapper(bi);
        SeamCarver.calculateImageEnergy(wrap);
        BufferedImage energyBI = ImageWrapper.generateEnergyImage(wrap);
        ImageIcon icon = new ImageIcon(energyBI);
        imageDisplay.setIcon(icon);

        int newWidth = Math.max(bi.getWidth() + 70, 900);
        int newHeight = Math.max(bi.getHeight() + 140, 500);

        MainPanel.changeFrameSize(newWidth, newHeight);
    }
}
