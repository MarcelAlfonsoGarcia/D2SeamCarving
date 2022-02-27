package panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import imageWrapper.ImageWrapper;

public class SettingsPanel {
    private JPanel panel;
    private BufferedImage image;

    public SettingsPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));

        JPanel filePanel = new JPanel();
        BoxLayout fileLayout = new BoxLayout(filePanel, BoxLayout.Y_AXIS);
        filePanel.setLayout(fileLayout);
        filePanel.add(Box.createVerticalGlue());
        JButton selectImage = new JButton("Select Image");
        JLabel imageName = new JLabel("None Selected", SwingConstants.CENTER);

        JFileChooser filePicker = new JFileChooser();
        filePicker.addChoosableFileFilter(new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes()));
        filePicker.setCurrentDirectory(new File("./"));
        selectImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int returnVal = filePicker.showOpenDialog(panel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    var imageFile = filePicker.getSelectedFile();
                    imageName.setText(imageFile.getName());
                    
                    try {
                    	image = ImageIO.read(imageFile);
                        ImagePanel.addImages(Collections.singletonList(image));
                    } catch (IOException ex) {
                    	System.out.println("Could not load file: " + imageFile.getName());
                    	image = null;
                    }
                }
            }
        });
        filePanel.add(selectImage);
        filePanel.add(imageName);

        JPanel carvesPanel = new JPanel();
        JLabel carvesLabel = new JLabel("Carves", JLabel.CENTER);
        JTextField carvesField = new JTextField("0");
        carvesField.setColumns(2);
        carvesPanel.add(carvesLabel);
        carvesPanel.add(carvesField);

        JPanel directionPanel = new JPanel();
        BoxLayout directionLayout = new BoxLayout(directionPanel, BoxLayout.Y_AXIS);
        directionPanel.setLayout(directionLayout);
        directionPanel.add(Box.createVerticalGlue());
        ButtonGroup bg = new ButtonGroup();
        JRadioButton verticalButton = new JRadioButton("Vertical", true);
        JRadioButton horizontalButton = new JRadioButton("Horizontal");
        bg.add(verticalButton);
        bg.add(horizontalButton);
        directionPanel.add(verticalButton);
        directionPanel.add(horizontalButton);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (image == null) {
                    throw new IllegalArgumentException("Please select a file to carve");
                }

                int numberCarves = 0;
                boolean bHorizontalSeam = false;
                try {
                    String numberCarvesField = carvesField.getText();
                    if (numberCarvesField.isEmpty()) System.out.println("whelp");
                    numberCarves = Integer.parseInt(numberCarvesField);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Input must be a numeric value");
                }

                if (verticalButton.isSelected()) bHorizontalSeam = false;
                if (horizontalButton.isSelected()) bHorizontalSeam = true;
                

                var imageWrap = new ImageWrapper(image);
                
                if (bHorizontalSeam && numberCarves >= imageWrap.getHeight()) {
                	throw new IllegalArgumentException("Input must be a numeric value less than image height.");
                } else if (numberCarves >= imageWrap.getWidth()) {
                	throw new IllegalArgumentException("Input must be a numeric value less than image width.");
                }
                
                // here is where we will call the carving functions. Expected result is a List<BufferedImage>
                var carvedUpImages = reallyDumbSeamCarvingAlgorithmToReplaceWithRealOne(bHorizontalSeam, numberCarves, imageWrap);
                ImagePanel.addImages(carvedUpImages);
            }
        });

        panel.add(filePanel);
        panel.add(carvesPanel);
        panel.add(directionPanel);
        panel.add(submitButton);
    }

    public JPanel getPanel() {
        return panel;
    }
    
    // super dumb seam carving algorithm below.
    // for a given image, select a seam that is approximately near the middle
    // and remove it N times.
    static private List<BufferedImage> reallyDumbSeamCarvingAlgorithmToReplaceWithRealOne(boolean bHorizontalSeam, int nCarves, ImageWrapper imageWrap) {
    	ArrayList<BufferedImage> result = new ArrayList<>();
    	// Add the first one so we can see where we started
    	result.add(imageWrap.currentContentsAsImage());
    	
    	if (bHorizontalSeam) {
    		for (int i = 0; i < nCarves; ++i) {
    			final int nRowToRemove = imageWrap.getHeight() / 2;
    			
    			var seam = new int[imageWrap.getWidth()];
    			for (int j = 0; j < seam.length; ++j)
    				seam[j] = nRowToRemove;
    			
    			imageWrap.removeHorizontalSeam(seam);
    			result.add(imageWrap.currentContentsAsImage());
    		}
    	} else {
    		for (int i = 0; i < nCarves; ++i) {
    			final int nColToRemove = imageWrap.getWidth() / 2;
    			
    			var seam = new int[imageWrap.getHeight()];
    			for (int j = 0; j < seam.length; ++j)
    				seam[j] = nColToRemove;
    			
    			imageWrap.removeVerticalSeam(seam);
    			result.add(imageWrap.currentContentsAsImage());
    		}
    	}
    	
    	return result;
    }
}
