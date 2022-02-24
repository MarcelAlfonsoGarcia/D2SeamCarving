package panel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.text.ParseException;

public class SettingsPanel {
    private JPanel panel;
    private File imageFile;
    private int numberCarves;

    public SettingsPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));

        JButton selectImage = new JButton("Select Image");

        JFileChooser filePicker = new JFileChooser();
        filePicker.addChoosableFileFilter(new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes()));

        selectImage.addActionListener( new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int returnVal = filePicker.showOpenDialog(panel);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    imageFile = filePicker.getSelectedFile();
                }
            }
        });


        JTextField carvesField = new JTextField("");
        carvesField.setColumns(4);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numberCarvesField = carvesField.getText();
                try {
                    numberCarves = Integer.parseInt(numberCarvesField);
                } catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Input must be a numeric value");
                }

                if (imageFile == null) {
                    throw new IllegalArgumentException("Please select a file to carve");
                }
            }
        });

        panel.add(selectImage);
        panel.add(carvesField);
        panel.add(submitButton);
    }

    public JPanel getPanel() {
        return panel;
    }

    public int getNumberCarves() {
        return numberCarves;
    }

    public File getImageFile() {
        return imageFile;
    }
}
