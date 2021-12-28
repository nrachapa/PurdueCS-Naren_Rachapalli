package HW11;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Project 4: CalculatorClient.java
 *
 * brief description of the program
 *
 *
 * @author Naren Rachapalli, lab sec 008
 * @version November 8, 2020
 *
 */
public class CalculatorClient {

    public static boolean checkEquationFormatting(String equation) {
        if (equation.isBlank()) {
            return false;
        }
        String[] operandsStrings = { "+", "-", "*", "/", "%" };
        String[] equationStrings = equation.split(" ");
        String operand = "";
        if (equationStrings.length != 3) {
            JOptionPane.showMessageDialog(null, "Error Occurred: Enter a equation", "Calculator",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        boolean isOperand = false;
        for (int i = 0; i < operandsStrings.length; i++) {
            if (operandsStrings[i].equals(equationStrings[1])) {
                isOperand = true;
                operand = equationStrings[1];
            }
        }
        if (!isOperand) {
            JOptionPane.showMessageDialog(null, "Error Occurred: Enter a equation", "Calculator",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        try {
            Double.parseDouble(equationStrings[0]);
            Double.parseDouble(equationStrings[2]);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Error Occurred: Enter a valid equation", "Calculator",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        double num2 = Double.parseDouble(equationStrings[2]);
        if (operand.equals("/") && num2 == 0) {
            JOptionPane.showMessageDialog(null, "Error Occurred: Cannot Divide by Zero!", "Calculator",
                    JOptionPane.PLAIN_MESSAGE);
            return false;
        }
        return true;
    }

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        String hostname = "";
        do {
            hostname = JOptionPane.showInputDialog(null, "Enter a host name", "Calculator",
                    JOptionPane.QUESTION_MESSAGE);
            if (!hostname.equals("localhost")) {
                JOptionPane.showMessageDialog(null, "Error Occurred: Enter a valid host name!", "Calculator",
                        JOptionPane.ERROR_MESSAGE);
                continue;
            }
            break;
        } while (true);

        int portNumber;
        do {
            try {
                portNumber = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter a port number", "Calculator",
                        JOptionPane.QUESTION_MESSAGE));
                if (portNumber != 62736) {
                    JOptionPane.showMessageDialog(null, "Error Occurred: Enter a valid port number!", "Calculator",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }
                break;
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Error Occurred: Enter a valid port number!", "Calculator",
                        JOptionPane.ERROR_MESSAGE);
            }
        } while (true);

        Socket socket = new Socket(hostname, portNumber);

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.flush();

        JOptionPane.showMessageDialog(null, "Connection Established!", "Calculator", JOptionPane.INFORMATION_MESSAGE);
        while (true) {
            String input = "";
            do {
                input = JOptionPane.showInputDialog(null, "Enter your Equation!", "Calculator",
                        JOptionPane.QUESTION_MESSAGE);

            } while (!checkEquationFormatting(input));

            writer.write(input);
            writer.println();
            writer.flush();

            String solution = reader.readLine();
            JOptionPane.showMessageDialog(null, input + " = " + solution, "Calculator", JOptionPane.PLAIN_MESSAGE);

            int choice = JOptionPane.showConfirmDialog(null, "Do you want to input another equation?", "Calculator",
                    JOptionPane.YES_NO_OPTION);
            if (choice == 0) {
                continue;
            } else if (choice == 1) {
                JOptionPane.showMessageDialog(null, "Thanks for using the calculator!", "Calculator",
                        JOptionPane.PLAIN_MESSAGE);
                break;
            }
        }
        writer.close();
        reader.close();
    }
}
