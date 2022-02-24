package panel;

import javax.swing.*;
import java.awt.*;

public class MainPanel {

    private static JFrame frame;
    private static JPanel panel;
    private static Container contentPane;
    private static CardLayout cardLayout;

    public MainPanel() {
        frame = new JFrame();
        cardLayout = new CardLayout();
        contentPane = frame.getContentPane();
        contentPane.setLayout(cardLayout);
        panel = new JPanel();
        panel.setLayout(new GridLayout(3,1));

        SettingsPanel sp = new SettingsPanel();
        ImagePanel ip = new ImagePanel();
        ActionPanel ap = new ActionPanel();

        panel.add(sp.getPanel());
        panel.add(ip.getPanel());
        panel.add(ap.getPanel());

        contentPane.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setTitle("Carver");
        frame.setVisible(true);
    }
}
