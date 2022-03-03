package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ActionPanel {
    private JPanel panel;

    public ActionPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 6));

        JButton previous = new JButton("Prev");
        previous.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImagePanel.previousImage();
            }
        });

        JButton next = new JButton("Next");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImagePanel.nextImage();
            }
        });

        JButton last = new JButton("Last");
        last.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImagePanel.lastImage();
            }
        });

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ImagePanel.saveImage();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Could not save the image onto your computer");
                    ex.printStackTrace();
                }
            }
        });

        JButton viewEnergy = new JButton("View Image Energy");
        viewEnergy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImagePanel.viewEnergy();
            }
        });

        JButton animate = new JButton("Animate");
        animate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animate.getText().equalsIgnoreCase("animate")) {
                    try {
                        ImagePanel.startAnimation();
                        animate.setText("Stop Animate");
                    } catch (Exception ex) {
                    }
                } else {
                    ImagePanel.stopAnimation();
                    animate.setText("Animate");
                }
            }
        });

        panel.add(previous);
        panel.add(next);
        panel.add(last);
        panel.add(save);
        panel.add(viewEnergy);
        panel.add(animate);
    }

    public JPanel getPanel() {
        return panel;
    }
}
