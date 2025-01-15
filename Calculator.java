import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator {//Swing based calculator

    public static void main(String[] args) {
        // Create the frame
        JFrame frame = new JFrame("Calculator");
        frame.setSize(400, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create text field for display
        JTextField display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        // Create panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 10, 10));

        // Define button labels
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "C", "0", "=", "+"
        };

        // Add buttons to panel
        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            buttonPanel.add(button);

            // Add action listener for button
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command = e.getActionCommand();
                    handleButtonClick(command, display);
                }
            });
        }

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Make frame visible
        frame.setVisible(true);
    }

    // Variables to store current operation
    private static String operator = "";
    private static double firstNumber = 0;
    private static boolean isOperatorPressed = false;

    // Handle button click
    private static void handleButtonClick(String command, JTextField display) {
        if (command.matches("[0-9]")) {
            if (isOperatorPressed) {
                display.setText("");
                isOperatorPressed = false;
            }
            display.setText(display.getText() + command);
        } else if (command.matches("[+\\-*/]")) {
            operator = command;
            firstNumber = Double.parseDouble(display.getText());
            isOperatorPressed = true;
        } else if (command.equals("=")) {
            double secondNumber = Double.parseDouble(display.getText());
            double result = calculate(firstNumber, secondNumber, operator);
            display.setText(String.valueOf(result));
            operator = "";
        } else if (command.equals("C")) {
            display.setText("");
            firstNumber = 0;
            operator = "";
            isOperatorPressed = false;
        }
    }

    // Perform calculation
    private static double calculate(double num1, double num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    JOptionPane.showMessageDialog(null, "Cannot divide by zero!", "Error", JOptionPane.ERROR_MESSAGE);
                    return 0;
                }
            default:
                return 0;
        }
    }
}
