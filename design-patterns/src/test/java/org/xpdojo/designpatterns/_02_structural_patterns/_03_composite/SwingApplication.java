package org.xpdojo.designpatterns._02_structural_patterns._03_composite;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class SwingApplication {

    private SwingApplication() {
    }

    public static void main(String[] args) {
        // JFrame == Composite
        // java.awt.Component == Component
        // JTextField == Leaf
        // JButton == Leaf
        JFrame frame = new JFrame();

        JTextField textField = new JTextField();
        textField.setBounds(200, 200, 200, 40);
        textField.setName("hi");
        frame.add(textField);

        JButton button = new JButton("click");
        button.setBounds(200, 100, 60, 40);
        button.setName("hello");
        button.addActionListener(e -> textField.setText("Hello Swing"));
        frame.add(button);

        frame.setSize(600, 400);
        frame.setLayout(null);
        frame.setVisible(true);
    }
}
