import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Client for CS180 Project 5,
 * User interface for using the service
 * 
 * Connects to a server and can send and receive data
 */
public class Client extends JFrame implements Runnable {
    Socket socket;
    boolean connected;
 
    ObjectOutputStream outputStream;
    ObjectInputStream inputStream;
 
    JPanel panel;
    JPanel panel1;
    JButton login;
    JButton signup;
    JButton enterS;
    JButton enterL;
    JFrame frame;
    JTextField username;
    JTextField password;
    JLabel userName;
    JLabel pass;
    JLabel label;
    JLabel label1;
    JLabel label2;
    JLabel label3;
    JLabel userN;
    JLabel passW;
    JButton profile;
    JButton friend;
    JButton back;
    JButton requests;
    JTextField user;
    JTextField pas;
    JTextField interests;
    JTextField aboutMe;
    JLabel userL;
    JLabel passL;
    JLabel interestsL;
    JLabel aboutMeL;
    JLabel blank;
    JLabel friends;
    JTextField findAFriend;
    JLabel contactInfo;
    JTextField contact;
    JButton enter;
    JTextArea txtArea;
    JLabel profileMessage;
    JTextArea txtAreaF;
    JButton friendList;
    JButton profileList;
    JButton add;
    JButton enterFriend;
    
    String fri;
 
    private Account signedInAccount;
    private HashMap<String, Account> accounts;
 
    ActionListener actionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //sign up page
            if (e.getSource() == signup) {
                panel.revalidate();
                panel.repaint();
                frame.remove(panel1);
                panel.remove(label);
                panel.remove(label2);
                panel.remove(label1);
                panel.remove(label3);
                panel.add(userName);
                panel.add(username);
                panel.add(pass);
                panel.add(password);
                panel.add(enterS);
            }
            //login page
            if (e.getSource() == login) {
                panel.revalidate();
                panel.repaint();
                frame.remove(panel1);
                panel.remove(label);
                panel.remove(label2);
                panel.remove(label1);
                panel.remove(label3);
                panel.add(userN);
                panel.add(username);
                panel.add(passW);
                panel.add(password);
                panel.add(enterL);
            }
            //home page from sign up page
            if (e.getSource() == enterS) {
                if (username.getText().equals("") || password.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "You must fill out all of the fields", "Program",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    panel.revalidate();
                    panel.repaint();
                    panel.remove(username);
                    panel.remove(password);
                    panel.remove(userN);
                    panel.remove(passW);
                    panel.remove(userName);
                    panel.remove(pass);
                    panel.remove(enterS);
                    panel.add(profile);
                    panel.add(profileList);
                    panel.add(friend);
                    panel.add(friendList);
                    String usern = username.getText();
                    String pass = password.getText();
                    signedInAccount = new Account(usern, pass);
                    addAccount(signedInAccount);
                }
            }
            //home page from login
            if (e.getSource() == enterL) {
                String user = username.getText();
                String pas = password.getText();
                if (!accounts.containsKey(user) || !accounts.get(user).getPassword().equals(pas)) {
                    JOptionPane.showMessageDialog(null,
                            "The username and password you entered does not exist in this program", "Program",
                            JOptionPane.ERROR_MESSAGE);
                } else if (username.getText().equals("") || password.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "You must fill out all of the fields", "Program",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    signedInAccount = accounts.get(user);
                    panel.revalidate();
                    panel.repaint();
                    panel.remove(username);
                    panel.remove(password);
                    panel.remove(userN);
                    panel.remove(passW);
                    panel.remove(userName);
                    panel.remove(pass);
                    panel.remove(enterL);
                    panel.add(profile);
                    panel.add(profileList);
                    panel.add(friend);
                    panel.add(friendList);
                }
            }
            // profile list
            if (e.getSource() == profileList) {
                StringBuilder display = new StringBuilder();
                for (Profile profile : signedInAccount.getProfileList()) {
                    display.append(profile.getUserName()).append("\n");
                }
                txtArea.setText(display.toString());
                panel.revalidate();
                panel.repaint();
                panel.remove(friendList);
                panel.remove(friend);
                panel.remove(profile);
                panel.remove(profileList);
                panel.add(back);
                panel.add(txtArea);
            }
            //friendlist
            if (e.getSource() == friendList) {
                String user = username.getText();
                String pass = password.getText();
                String contactInfo = contact.getText();
                String interest = interests.getText();
                String aboutme = aboutMe.getText();
                Profile profile1 = new Profile(user, pass, contactInfo, interest, aboutme);
                ArrayList<String> friends = profile1.getFriendsList();
                for (int i = 0; i < friends.size(); i++) {
                    txtAreaF.setText(friends.get(i));
                }
                panel.revalidate();
                panel.repaint();
                panel.remove(friendList);
                panel.remove(friend);
                panel.remove(profile);
                panel.remove(profileList);
                panel.add(back);
                panel.add(txtAreaF);
            }
            //create a new profile page
            if (e.getSource() == profile) {
                panel.revalidate();
                panel.repaint();
                panel.remove(profile);
                panel.remove(friend);
                panel.remove(profileList);
                panel.remove(friendList);
                panel.add(back);
                panel.add(blank);
                panel.add(userL);
                panel.add(user);
                panel.add(passL);
                panel.add(pas);
                panel.add(interestsL);
                panel.add(interests);
                panel.add(aboutMeL);
                panel.add(aboutMe);
                panel.add(contactInfo);
                panel.add(contact);
                panel.add(enter);
            }
            if (e.getSource() == enter) {
                String username = user.getText();
                String password = pas.getText();
                String contactInfo = contact.getText();
                String interest = interests.getText();
                String aboutme = aboutMe.getText();
                if (username.equals("") || password.equals("") || contactInfo.equals("") ||
                        interest.equals("") || aboutme.equals("")) {
                    JOptionPane.showMessageDialog(null, "You must fill out all of the fields", "Program",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    Profile profile = new Profile(username, password, contactInfo, interest, aboutme);
                    signedInAccount.addNewProfile(profile);
                    panel.revalidate();
                    panel.repaint();
                    panel.add(profileMessage);
                    sendAccount(signedInAccount);
                }
            }
            //find a friend page
            if (e.getSource() == friend) {
                panel.revalidate();
                panel.repaint();
                panel.remove(profile);
                panel.remove(friend);
                panel.remove(profileList);
                panel.remove(friendList);
                panel.add(back);
                panel.add(requests);
                panel.add(friends);
                panel.add(findAFriend);
                panel.add(enterFriend);
            }
            //searching for a friend
            if (e.getSource() == enterFriend) {
                panel.revalidate();
                panel.repaint();
                String fri = findAFriend.getText();
                if (accounts.containsKey(fri)) {
                    JLabel friendPopUp = new JLabel("fri");
                    panel.add(friendPopUp);
                    panel.add(add);

                }
            }
            //adds a friend to list
            if (e.getSource() == add) {
                txtAreaF.setText(fri);
            }
            //look at who requested you
            if (e.getSource() == requests) {
                panel.revalidate();
                panel.repaint();
                panel.remove(requests);
            }
            //go back to the home page
            if (e.getSource() == back) {
                panel.revalidate();
                panel.repaint();
                panel.remove(back);
                panel.remove(userL);
                panel.remove(user);
                panel.remove(passL);
                panel.remove(pas);
                panel.remove(interestsL);
                panel.remove(interests);
                panel.remove(aboutMeL);
                panel.remove(aboutMe);
                panel.remove(enter);
                panel.remove(findAFriend);
                panel.remove(requests);
                panel.remove(contactInfo);
                panel.remove(contact);
                panel.remove(enterFriend);
                panel.remove(add);
                panel.remove(profileMessage);
                panel.remove(txtArea);
                panel.remove(txtAreaF);
                panel.add(profile);
                panel.add(profileList);
                panel.add(friend);
                panel.add(friendList);
            }
        }
    };

    public Client() {
        JFrame frame = new JFrame("Enter host name and port number");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Host Name and Port Number");
        JTextField hn = new JTextField("Host Name", 20);
        JTextField pn = new JTextField("Port Number", 7);
        JButton enter = new JButton("Enter");
        panel.add(label);
        panel.add(hn);
        panel.add(pn);
        panel.add(enter);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.setVisible(true);
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String host = hn.getText();
                String portNumber = pn.getText();
                int portNum = 0;
                try {
                    portNum = Integer.parseInt(portNumber);
                    socket = null;
                    connected = false;
                    connectToServer(host, portNum);
                    if (connected) {
                        frame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(null, "Error: Connection failed",
                                "Network Connection", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Error Occurred: Invalid Network Inputs!",
                            "Network Connection", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public Client(String hostName, int port) {
        socket = null;
        connected = false;
        connectToServer(hostName, port);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Client("localhost", 4242));
    }

    /**
     * Connects a client to a specified server.
     *
     * @param hostName The hostname of the server
     * @param hostName the port of the server
     */
    public void connectToServer(String hostName, int port) {
        try {
            socket = new Socket(hostName, port);
            connected = true;
            outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            inputStream = new ObjectInputStream(this.socket.getInputStream());
            updateAccounts();
        } catch (Exception e) {
            System.out.println("Connection failed");
        }
    }

    /**
     * Add or update an account in the accounts hashmap accordingly, then send it to the server
     *
     * @param account the account to be added or updated
     */
    public void addAccount(Account account) {
        accounts.put(account.getUserName(), account);
        sendAccount(account);
    }

    /**
     * Sends a serializable object to the server. Will not send if object is not serializable
     *
     * @param object the object to be sent to the server
     */
    public void sendObject(Object object) {
        try {
            outputStream.writeObject(object);
            outputStream.flush();
            outputStream.reset();
        } catch (IOException e) {
            System.out.println("Object failed to send!");
        }
    }

    /**
     * Sends an account to the server
     *
     * @param account the account to be sent to the server
     */
    public void sendAccount(Account account) {
        sendObject(account);
        receiveAccount();
    }

    /**
     * Gets a serializable object from the server
     */
    public Object receiveObject() throws IOException, ClassNotFoundException {
        if (connected) {
            Object inputObject = inputStream.readObject();
            return inputObject;
        } else {
            System.out.println("Not connected!");
            return null;
        }
    }

    /**
     * Receives the full accounts hashmap from the server, for initializing the client
     */
    @SuppressWarnings("unchecked")
    public void updateAccounts() {
        if (connected) {
            try {
                Object object = receiveObject();
                System.out.println(object);
                accounts = (HashMap<String, Account>) object;
                System.out.println("Received accounts: " + accounts.values());
            } catch (Exception e) {
                System.out.println("Failed to update accounts");
            }
        } else {
            System.out.println("Not connected");
        }
    }

    /**
     * Receives a single account from the server. Will add or update it in accounts accordingly
     */
    public void receiveAccount() {
        if (connected) {
            try {
                Account newAccount = (Account) receiveObject();
                System.out.println("Received account: " + newAccount);
                accounts.put(newAccount.getUserName(), newAccount);
            } catch (Exception e) {
                System.out.println("Failed to receive account");
            }
        } else {
            System.out.println("Not connected");
        }
    }

    /**
     * Ends the connection safely
     */
    public void endConnection() {
        try {
            sendObject("close");
            socket.close();
            connected = false;
        } catch (IOException e) {
            System.out.println("Disconnect failed. May already be disconnected.");
        }
    }

    public void run() {
        //sets up the frame
        frame = new JFrame();
        frame.setTitle("Option 2");
        Container content = frame.getContentPane();
        content.setLayout(new BorderLayout());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        //creates the JLabels for the welcome page
        label = new JLabel("Welcome!");
        label.setBounds(50, 50, 100, 30);
        label.setFont(new Font("Purisa", Font.ITALIC, 40));
        label.setForeground(Color.white);

        label2 = new JLabel("~Create an Account");
        label2.setFont(new Font("Purisa", Font.ITALIC, 20));
        label2.setForeground(Color.white);

        label1 = new JLabel("~Find Friends");
        label1.setFont(new Font("Purisa", Font.ITALIC, 20));
        label1.setForeground(Color.white);

        label3 = new JLabel("~Have Fun");
        label3.setFont(new Font("Purisa", Font.ITALIC, 20));
        label3.setForeground(Color.white);
        //the center panel that currently shows the welcome screen
        panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));
        label.setHorizontalAlignment(JLabel.CENTER);
        label2.setHorizontalAlignment(JLabel.CENTER);
        label1.setHorizontalAlignment(JLabel.CENTER);
        label3.setHorizontalAlignment(JLabel.CENTER);
        panel.add(label);
        panel.add(label2);
        panel.add(label1);
        panel.add(label3);
        panel.setBackground(Color.blue);
        content.add(panel, BorderLayout.CENTER);
        //the login and sign up buttons
        login = new JButton("Log In");
        login.addActionListener(actionListener);
        signup = new JButton("Sign Up");
        signup.addActionListener(actionListener);
        //the south panel that contains the login and sign up buttons
        panel1 = new JPanel();
        panel1.add(login);
        panel1.add(signup);
        content.add(panel1, BorderLayout.SOUTH);
        //the textfields and buttons that are used for the sign up show
        username = new JTextField(10);
        password = new JTextField(10);
        userName = new JLabel("Select a username");
        userName.setFont(new Font("Purisa", Font.ITALIC, 20));
        userName.setForeground(Color.white);
        pass = new JLabel("Select a password");
        pass.setFont(new Font("Purisa", Font.ITALIC, 20));
        pass.setForeground(Color.white);
        enterS = new JButton("Enter");
        enterS.addActionListener(actionListener);
        //The labels for the login page (slightly different then the sign up page, which is why I needed to make them)
        userN = new JLabel("Username");
        userN.setFont(new Font("Purisa", Font.ITALIC, 20));
        userN.setForeground(Color.white);
        passW = new JLabel("Password");
        passW.setFont(new Font("Purisa", Font.ITALIC, 20));
        passW.setForeground(Color.white);
        enterL = new JButton("Enter");
        enterL.addActionListener(actionListener);
        //The attributes for the home page
        profile = new JButton("Create a new Profile");
        profile.addActionListener(actionListener);
        friend = new JButton("Find new Friends");
        friend.addActionListener(actionListener);
        friendList = new JButton("Your friend list");
        friendList.addActionListener(actionListener);
        profileList = new JButton("Your profiles");
        profileList.addActionListener(actionListener);
        txtArea = new JTextArea();
        txtAreaF = new JTextArea();
        //the attributes for searching for friends
        back = new JButton("Back");
        back.addActionListener(actionListener);
        requests = new JButton("Look at who requested you");
        requests.addActionListener(actionListener);
        friends = new JLabel("Search for friends");
        friends.setFont(new Font("Purisa", Font.ITALIC, 20));
        friends.setForeground(Color.white);
        findAFriend = new JTextField();
        add = new JButton("add");
        add.addActionListener(actionListener);
        enterFriend = new JButton("Enter");
        enterFriend.addActionListener(actionListener);
        //the attributes for creating a new profile
        userL = new JLabel("Username");
        userL.setFont(new Font("Purisa", Font.ITALIC, 20));
        userL.setForeground(Color.white);
        passL = new JLabel("Password");
        passL.setFont(new Font("Purisa", Font.ITALIC, 20));
        passL.setForeground(Color.white);
        interestsL = new JLabel("Interests");
        interestsL.setFont(new Font("Purisa", Font.ITALIC, 20));
        interestsL.setForeground(Color.white);
        aboutMeL = new JLabel("About Me");
        aboutMeL.setFont(new Font("Purisa", Font.ITALIC, 20));
        aboutMeL.setForeground(Color.white);
        blank = new JLabel();
        blank.setFont(new Font("Purisa", Font.ITALIC, 20));
        blank.setForeground(Color.white);
        contactInfo = new JLabel("Contact Information");
        contactInfo.setFont(new Font("Purisa", Font.ITALIC, 20));
        contactInfo.setForeground(Color.white);
        user = new JTextField(10);
        pas = new JTextField(10);
        interests = new JTextField(10);
        aboutMe = new JTextField(10);
        contact = new JTextField(10);
        enter = new JButton("Enter");
        enter.addActionListener(actionListener);
        //message for enter
        profileMessage = new JLabel("You have succesfully added a profile");
        profileMessage.setFont(new Font("Purisa", Font.ITALIC, 10));
        profileMessage.setForeground(Color.white);
    }
}
