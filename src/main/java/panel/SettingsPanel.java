package panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SettingsPanel {
    private JPanel panel;
    private File imageFile;

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
                    imageFile = filePicker.getSelectedFile();
                    imageName.setText(imageFile.getName());
                    ImagePanel.addImages(Collections.singletonList(imageFile));
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
                if (imageFile == null) {
                    throw new IllegalArgumentException("Please select a file to carve");
                }

                int numberCarves = 0;
                String seamDirection;
                try {
                    String numberCarvesField = carvesField.getText();
                    if (numberCarvesField.isEmpty()) System.out.println("whelp");
                    numberCarves = Integer.parseInt(numberCarvesField);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Input must be a numeric value");
                }

                if (verticalButton.isSelected()) seamDirection = "vertical";
                if (horizontalButton.isSelected()) seamDirection = "horizontal";

                // here is where we will call the carving functions. Expected result is a List<File>
                // something like: carve(imageFile, numberCarves, seamDirection)
                List<File> fileList = new ArrayList<>();
                fileList.add(new File("C:\\Users\\marce\\Documents\\Marcel School\\Data Structures 2\\SeamCarving\\test1.jpg"));
                fileList.add(new File("C:\\Users\\marce\\Documents\\Marcel School\\Data Structures 2\\SeamCarving\\test2.jpg"));
                ImagePanel.addImages(fileList);
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
}
