import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HelloWorldSwing implements ActionListener {
    private static JFrame frame;
    private static JLabel label;
    private int clicks = 0;

    public HelloWorldSwing() {
        frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(); 
        panel.setBorder(BorderFactory.createEmptyBorder(600, 600, 600, 600));
        panel.setLayout(new GridLayout(0,1));


        label = new JLabel("Clicks: " + clicks);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        panel.add(label);

        JButton btn = new JButton("Test button");
        btn.addActionListener(this);

        panel.add(btn);

        frame.add(panel, BorderLayout.CENTER);
        frame.setTitle("Test application");
        frame.pack();
        frame.setVisible(true);
    }

    @Override 
    public void actionPerformed(ActionEvent e) {
        clicks++;
        label.setText("Clicks: " + clicks);
    } 

    public static void main(String[] args) {
        new HelloWorldSwing(); 
    }
}
