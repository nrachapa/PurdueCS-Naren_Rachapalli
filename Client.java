import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Client extends JFrame implements Runnable{
 JPanel panel;
    JPanel panel1;
    JButton login;
    JButton signup;
    JButton enter;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater( new Client());
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
        panel.setLayout(new GridLayout(6,2));
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
        enter = new JButton("Enter");
        enter.addActionListener(actionListener);
        //The labels for the login page (slightly different then the sign up page, which is why I needed to make them)
        userN = new JLabel("Username");
        userN.setFont(new Font("Purisa", Font.ITALIC, 20));
        userN.setForeground(Color.white);
        passW = new JLabel("Password");
        passW.setFont(new Font("Purisa", Font.ITALIC, 20));
        passW.setForeground(Color.white);
        //The attributes for the home page
        profile = new JButton("Create a new Profile");
        profile.addActionListener(actionListener);
        friend = new JButton("Find new Friends");
        friend.addActionListener(actionListener);
        //the attributes for searching for friends
        back = new JButton("Back");
        back.addActionListener(actionListener);
        requests = new JButton("Look at who requested you");
        requests.addActionListener(actionListener);
        friends = new JLabel("Search for friends");
        friends.setFont(new Font("Purisa", Font.ITALIC, 20));
        friends.setForeground(Color.white);
        findAFriend = new JTextField();
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
        aboutMeL= new JLabel("About Me");
        aboutMeL.setFont(new Font("Purisa", Font.ITALIC, 20));
        aboutMeL.setForeground(Color.white);
        blank = new JLabel();
        blank.setFont(new Font("Purisa", Font.ITALIC, 20));
        blank.setForeground(Color.white);
        user = new JTextField(10);
        pas = new JTextField(10);
        interests = new JTextField(10);
        aboutMe = new JTextField(10);
    }


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
                panel.add(enter);
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
                panel.add(enter);
            }
            //home page
            if (e.getSource() == enter) {
                panel.revalidate();
                panel.repaint();
                panel.remove(username);
                panel.remove(password);
                panel.remove(userN);
                panel.remove(passW);
                panel.remove(userName);
                panel.remove(pass);
                panel.remove(enter);
                panel.add(profile);
                panel.add(friend);
            }
            //create a new profile page
            if (e.getSource() == profile) {
                panel.revalidate();
                panel.repaint();
                panel.remove(profile);
                panel.remove(friend);
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
                panel.add(enter);
            }
            //find a friend page
            if (e.getSource() == friend) {
                panel.revalidate();
                panel.repaint();
                panel.remove(profile);
                panel.remove(friend);
                panel.add(back);
                panel.add(requests);
                panel.add(friends);
                panel.add(findAFriend);
            }
            //look at who requested you
            if (e.getSource() == requests) {
                panel.revalidate();
                panel.repaint();
                panel.remove(requests);
            }
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
                panel.add(profile);
                panel.add(friend);
            }
        }
    };

    public void requests() {
        JButton deny = new JButton("Deny");
        JButton accept = new JButton("Accept");
    }
}
