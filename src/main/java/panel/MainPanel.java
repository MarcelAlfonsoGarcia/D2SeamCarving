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

        SettingsPanel sp = new SettingsPanel();
        ImagePanel ip = new ImagePanel();
        ip.start();
        ActionPanel ap = new ActionPanel();

        panel.add(sp.getPanel());
        panel.add(ip.getPanel());
        panel.add(ap.getPanel());

        contentPane.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setTitle("Carver");
        frame.setVisible(true);
    }

    public static void changeFrameSize(int width, int height) {
        frame.setSize(width, height);
    }
}
