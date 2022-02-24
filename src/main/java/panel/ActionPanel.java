package panel;

import javax.swing.*;
import java.awt.*;

public class ActionPanel {
    private JPanel panel;

    public ActionPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));

        JButton previous = new JButton("Prev");
        JButton next = new JButton("Next");
        JButton animate = new JButton("Animate");

        panel.add(previous);
        panel.add(next);
        panel.add(animate);
    }

    public JPanel getPanel() {
        return panel;
    }
}
