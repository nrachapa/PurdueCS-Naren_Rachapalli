package HW11;

import java.net.*;
import javax.swing.*;
import java.io.*;

/**
 * Project 4: CalculatorServer.java
 *
 * brief description of the program
 *
 *
 * @author Naren Rachapalli, lab sec 008
 * @version November 8, 2020
 *
 */
public class CalculatorServer {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
        JOptionPane.showMessageDialog(null, "Welcome User!", "Calculator", JOptionPane.PLAIN_MESSAGE);
        ServerSocket serverSocket = new ServerSocket(62736);

        Socket socket = serverSocket.accept();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());
        writer.flush();

        while (true) {
            String equation = reader.readLine();

            String[] equationStrings = equation.split(" ");
            double num1 = Double.parseDouble(equationStrings[0]);
            double num2 = Double.parseDouble(equationStrings[2]);
            String operand = equationStrings[1];
            double result = 0;

            if (operand.equals("+")) {
                result = num1 + num2;

            } else if (operand.equals("-")) {
                result = num1 - num2;

            } else if (operand.equals("*")) {
                result = num1 * num2;

            } else if (operand.equals("/")) {
                result = num1 / num2;
            }
            writer.write(Double.toString(result));
            writer.println();
            writer.flush();
        }
    }
}