package panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActionPanel {
    private JPanel panel;

    public ActionPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));

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

        JButton animate = new JButton("Animate");

        panel.add(previous);
        panel.add(next);
        panel.add(animate);
    }

    public JPanel getPanel() {
        return panel;
    }
}
