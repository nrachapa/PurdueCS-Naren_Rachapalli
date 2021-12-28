package HW11;

import javax.swing.JOptionPane;

public class OrderFormGUI {
        public static void main(String[] args) {
                /** DO NOT CHANGE VALUES BELOW **/
                boolean hoodieInStock = true;
                boolean tshirtInStock = false;
                boolean longsleeveInStock = true;
                String item = "";
                int quantity = 0;
                String name = "";
                /** DO NOT CHANGE VALUES ABOVE **/

                while (true) {
                        while (true) {
                                String[] options = { "Hoodie", "T-shirt", "Long sleeve" };
                                item = (String) JOptionPane.showInputDialog(null, "Select item style ", "Order Form",
                                                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                                if (item.equals("Hoodie")) {
                                        if (hoodieInStock) {
                                                break;
                                        }
                                        JOptionPane.showMessageDialog(null, "Item is out of stock! Pick another item.",
                                                        "Order Form", JOptionPane.ERROR_MESSAGE);
                                } else if (item.equals("T-Shirt")) {
                                        if (tshirtInStock) {
                                               break;
                                        }
                                        JOptionPane.showMessageDialog(null, "Item is out of stock! Pick another item.",
                                                        "Order Form", JOptionPane.ERROR_MESSAGE);
                                } else if (item.equals("Long sleeve")) {
                                        if (longsleeveInStock) {
                                                break;
                                        }
                                }
                                JOptionPane.showMessageDialog(null,
                                "Item is out of stock! Pick another item.",
                                "Order Form", JOptionPane.ERROR_MESSAGE);
                        }

                        do {
                                try {
                                        quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter quantity",
                                                        "Order Form", JOptionPane.QUESTION_MESSAGE));
                                        if (quantity < 1) {
                                                JOptionPane.showMessageDialog(null, "Error Occurred! Enter a valid quantity!",
                                                                "Order Form", JOptionPane.ERROR_MESSAGE);
                                                continue;
                                        }
                                        break;
                                } catch (NumberFormatException nfe) {
                                        JOptionPane.showMessageDialog(null, "Error Occurred! Enter a valid quantity!",
                                                        "Order Form", JOptionPane.ERROR_MESSAGE);
                                }
                        } while (true);

                        while (true) {
                                name = JOptionPane.showInputDialog(null, "Enter your Name", "Order Form",
                                                JOptionPane.QUESTION_MESSAGE);
                                if (!name.contains(" ")) {
                                        JOptionPane.showMessageDialog(null, "Error Occurred! Enter a valid name!",
                                                        "Order Form", JOptionPane.ERROR_MESSAGE);
                                        continue;
                                }
                                break;
                        }

                        /** Order Confirmation Message **/
                        String resultMessage = "Name: " + name + "\nItem: " + item + "\nQuantity: " + quantity;
                        JOptionPane.showMessageDialog(null, resultMessage, "Order Confirmation",
                                        JOptionPane.INFORMATION_MESSAGE);

                        int input = JOptionPane.showConfirmDialog(null, "Would you like to place another order?", "Order Form",
                                        JOptionPane.YES_NO_OPTION);
                        if (input == 1) {
                                break;
                        }
                }
        }
}

