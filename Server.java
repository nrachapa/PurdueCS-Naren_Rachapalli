import java.util.HashMap;
import java.io.*;
import java.net.*;

/**
 * Server class for CS180 Project 5
 * Spawns threads for each client connection and writes data on accounts and profiles to a .csv
 *
 * @author Tejas George, B06
 * @version November 22, 2020
 */

public class Server extends Thread {
    private static HashMap<String, Account> accounts = new HashMap<String, Account>();  // Store the Accounts
    private static final int PORT_NUMBER = 4242;    // Port number, not sure if there is a specific value for this to be
    private static boolean running = true;  // used to make the while loops stop yelling at me for not having an exit
    private Socket socket;  // Socket for thread to run on

    /**
     * Server Constructor
     * creates a server object to allow creation of a new thread for each Client
     *
     * @param socket Client connection for the thread
     */
    public Server(Socket socket) {
        this.socket = socket;
        readFromFile();
    }

    /**
     * main method, creates a ServerSocket and then runs an infinite loop to create new threads for
     * each new client that connects
     */
    public static void main(String[] args) {
        // TODO: Start server and run thread for each client connection
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(4242);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        while (running) {
                System.out.println("Waiting for client connection...");
            try {
                if (serverSocket != null)
                    new Server(serverSocket.accept()).start();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    /**
     * run method, overrides default Thread run method. Connects to the Client, and then runs until the Client
     * disconnects. Reads Account objects from the Client and calls editAccounts in order to process them.
     */
    @Override
    public void run() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(this.socket.getInputStream());

            System.out.println("Client connected!");

            while (socket.isConnected()) {
                Account inputAccount = (Account) inputStream.readObject();
                editAccounts(inputAccount);
                System.out.println("from client: " + inputAccount.toString());
            }

            System.out.println(accounts.toString());

            inputStream.close();
            outputStream.close();
        } catch (EOFException eofException) {
            System.out.println(socket.toString() + " disconnected");
            System.out.println(accounts.toString());
        } catch (SocketException socketException) {
            System.out.println(socket.toString() + " disconnected without completing");
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Writes all Accounts in the accounts HashMap an their associated data into a .csv
     */
    public void writeToFile() {
        // TODO: write accounts to a .csv
        for (Account a : accounts.values()) {
            
        }
    }

    /**
     * Reads account and profile data from a .csv
     */
    public void readFromFile() {
        //TODO: read accounts from a .csv
    }

    /**
     * Processes an account into the accounts HashMap
     * if account does not exist, adds it; if account exists but has differences, then edits it; if account exists but
     * has no differences, deletes it
     *
     * @param account - account to be added, edited, or deleted
     */
    public synchronized void editAccounts(Account account) {
        String key = account.getUserName();
        if (accounts.get(key) == null) {    // add new account
            accounts.put(key, account);
        } else {
            if (accounts.get(key).equals(account)) {    // delete account
                accounts.remove(key);
            } else {    // edit account
                accounts.put(key, account);
            }
        }
    }
}
