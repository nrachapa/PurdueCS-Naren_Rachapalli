import java.util.*;
import java.io.*;
import java.net.*;

/**
 * Server class for CS180 Project 5
 * 
 * Spawns threads for each client connection and writes data on accounts and profiles to a .csv
 */

public class Server extends Thread {
    /**
     * Variables used for running the Server
     *
     * accounts - HashMap that stores all the Accounts centrally
     * socket - Socket for Client/Thread to connect to
     * serverSocket - ServerSocket for Server to run on
     */
    private static HashMap<String, Account> accounts = new HashMap<>();
    private Socket socket;
    private static ServerSocket serverSocket;


    /**
     * Constants used
     *
     * PORT_NUMBER - Port number that Clients will connect to
     * CSV_STORAGE - File to read from/write to
     * SYNCHRO - Used for synchronization of variables across threads
     */
    private static final int PORT_NUMBER = 4242;
    private static final String  CSV_STORAGE = "accounts.csv";
    private static final Object SYNCHRO = new Object();

    /**
     * Variables for tracking changes
     *
     * numThreads - keeps track of how many Clients are connected
     * changes - holds all the Accounts that need to be sent to Clients
     * threadsChanged - tracks how many threads have made the change
     * sent - Account/change most recently sent to that Client
     */
    private static int numThreads = 0;
    private static ArrayList<Account> changes = new ArrayList<>();
    private static int threadsChanged = 0;
    private Account sent;


    /**
     * Server Constructor
     * creates a Server object to allow creation of a new thread for each Client
     *
     * @param socket Client connection for the thread
     */
    public Server(Socket socket) {
        this.socket = socket;
        sent = null;
    }

    /**
     * main method, creates a ServerSocket and then runs an infinite loop to create new threads for
     * each new client that connects
     */
    public static void main(String[] args) {
        // Start up the server
        try {
            serverSocket = new ServerSocket(4242);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            return;
        }

        // Populate accounts with csv data
        readFromFile(CSV_STORAGE);

        while (true) {
            // Start a new Thread
            try {
                new Server(serverSocket.accept()).start();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            // Check to remove changes that have been universally pushed
            // Checks value of threadsChanged vs the total number of connected Clients
            // If the values are equal, then all Clients have been sent that change, and the first change can be removed
            synchronized (SYNCHRO) {
                if (numThreads != 0 && numThreads == threadsChanged) {
                    changes.remove(0);
                    threadsChanged = 0;
                }
            }
        }

    }

    /**
     * run method, overrides default Thread run method. Connects to the Client, and then runs until the Client
     * disconnects. Reads Account objects from the Client and calls editAccounts in order to process them.
     */
    @Override
    public void run() {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(this.socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(this.socket.getInputStream())) {

            System.out.println(socket.toString() + " connected!");
            clientConnected();

            // Send Client the HashMap of Accounts for setup
            outputStream.writeObject(accounts);
            outputStream.flush();
            outputStream.reset();

            while (socket.isConnected()) {
                Object inputObject = inputStream.readObject();
                if (inputObject.getClass().equals(Account.class)) {
                    editAccounts((Account) inputObject);    // Edit the accounts list

                    synchronized (SYNCHRO) {
                        changes.add((Account) inputObject); // Add the inputObject to list of changes
                    }

                } else if (inputObject.getClass().equals(String.class)) {
                    if (((String) inputObject).equals("close")) {   // Client is closing
                        clientDisconnected();
                        break;
                    }
                }

                // Checks if there is an update to list of Accounts and sends new list to Client
                if (changes.get(0) != null && !changes.get(0).equals(sent)) {
                    // Send the changed Account
                    outputStream.writeObject(changes.get(0));
                    outputStream.flush();
                    outputStream.reset();

                    synchronized (SYNCHRO) {    // Update threadsChanged
                        threadsChanged++;
                    }

                    sent = changes.get(0);  // Store the change to check next time
                }
            }

            System.out.println(socket.toString() + " disconnected");
            outputStream.close();
            inputStream.close();
            socket.close();

        } catch (EOFException eofException) {
            System.out.println(socket.toString() + " disconnected");
            clientDisconnected();
        } catch (SocketException socketException) {
            System.out.println(socket.toString() + " disconnected without completing");
            clientDisconnected();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Writes all Accounts in the accounts HashMap an their associated data into a .csv
     *
     * @param filename filepath of the file to write data to
     */
    public void writeToFile(String filename) {
        // Variables
        File file = new File(filename);
        StringBuilder line;

        if (!filename.endsWith(".csv")) {
            System.out.println("Invalid filename!");
            return;
        }

        // Create the file
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        // write the data
        // first line is the account, followed by each profile on its own line
        // Accounts are denoted by "ACCOUNT" preceding data, Profiles are denoted with "PROFILE" preceding data
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            for (Account account : accounts.values()) {
                // write account data
                line = new StringBuilder("ACCOUNT," + account.toCSV());
                writer.write(line.toString());
                writer.println();

                // write profile data
                for (Profile profile : account.getProfileList()) {
                    line = new StringBuilder("PROFILE," + profile.toCSV());

                    writer.write(line.toString());
                    writer.println();
                    writer.flush();
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Reads account and profile data from a .csv
     *
     * @param filename filepath to read data from
     */
    public static void readFromFile(String filename) {
        // Variables
        File file = new File(filename);
        String line;
        String[] data;
        Account account = null;
        Profile profile;
        accounts = new HashMap<>();

        if (!filename.endsWith(".csv")) {
            System.out.println("Invalid filename!");
            return;
        }

        // Check File
        if (!file.exists()) {
            System.out.println("No read from file found");
            return;
        }

        // Read data
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            line = reader.readLine();
            while (line != null) {
                data = line.split(",");

                if (data[0].equals("ACCOUNT")) {
                    // Place the account from the last iteration in the HashMap
                    if (account != null) accounts.put(account.getUserName(), account);

                    // Create new Account
                    account = new Account(data[1], data[2]);

                } else if (data[0].equals("PROFILE")) {
                    // Create new Profile based on line from csv
                    profile = new Profile(data[1], data[2], data[3], data[4], data[5]); // Profile constructor
                    profile.setFriendRequest(Boolean.parseBoolean(data[6]));    // add data that is not in constructor

                    // Reset line and data to parse friends
                    line = line.substring(line.indexOf('[') + 1, line.indexOf("]"));
                    if (line.length() > 0) {    // Check if there are friends to be added
                        data = line.split(",");

                        // Add friends to the Profile
                        for (String friend : data) {
                            profile.addFriend(friend);
                        }
                    }

                    // Add profile to account
                    if (account != null) account.addNewProfile(profile);

                }

                // advance reader
                line = reader.readLine();
            }

            // Adds last Account
            if (account != null) accounts.put(account.getUserName(), account);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Processes an account into the accounts HashMap
     * if account does not exist, adds it; if account exists but has differences, then edits it; if account exists but
     * has no differences, deletes it
     * synchronized to avoid race conditions
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

        writeToFile(CSV_STORAGE);
    }

    /**
     * increments numThreads and eliminates race conditions
     */
    private synchronized void clientConnected() {
        numThreads++;
    }

    /**
     * decrements numThreads and eliminates race conditions
     */
    private synchronized void clientDisconnected() {
        numThreads--;
    }
}
