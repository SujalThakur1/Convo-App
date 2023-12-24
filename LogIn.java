import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LogIn {
    // Private member variables
    private JFrame frame;
    private JFrame frame3;
    private Edit editBtn;
    private JTextField textField,textField2,textField3,textField4,textField5;
    private MyGraph graph;
    private userProfile profile;

    /**
     * Constructor for the LogIn class.
     * Initializes the member variables and creates a new MyGraph object.
     */
    public LogIn() {
        graph = new MyGraph();
        textField = new JTextField();
        textField2 = new JTextField();
        textField3 = new JTextField();
        textField4 = new JTextField();
        textField5 = new JTextField();
        editBtn = new Edit(graph);
    }

    public static void main(String[] args)
    {
        LogIn panelsDemo = new LogIn();
        panelsDemo.run();
    }

    /**
     * Runs the application by creating a new frame and adding the main panel to it.
     * Also reads data from a file and sets the frame to be visible.
     */
    public void run(){
        File file;
        file = new File(graph);
        file.readFromAFile();
        frame = new JFrame("Convo");
        frame.setSize(600, 400);
        editBtn.WindowExit(frame);
        mainPanel();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /**
     * Creates the main panel for the application.
     * Includes a welcome message, text field for user ID, and sign up/sign in buttons.
     */
    public void mainPanel() {
        JPanel topPanel = new JPanel();

        // Create a welcome message and add it to the top panel
        JLabel label = new JLabel("Welcome To Convo");
        label.setFont(new Font("Serif", Font.PLAIN, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(label);

        // Create a text panel with a user ID text field and add it to the top panel
        topPanel.add(createTextPanel());

        // Create sign up and sign in buttons
        JButton btn1 = new JButton("Sign Up");
        JButton btn2 = new JButton("Sign In");
        editBtn.btnSmall(btn1,btn2);

        // Add action listeners to the buttons
        btn1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If the sign up button is clicked, clear the text fields and display the registration panel
                    if(e.getSource() == btn1){
                        textField2.setText(null);
                        textField3.setText(null);
                        textField4.setText(null);
                        textField5.setText(null);
                        firstButton();
                        frame.dispose();
                    }
                }
            });
        btn2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If the sign in button is clicked, check if the user ID exists and display the appropriate panel
                    if(e.getSource() == btn2) {
                        char check = 'c';
                        int ID = 0;
                        try {
                            ID = Integer.parseInt(textField.getText());
                            check = graph.specialCheck(ID);
                        } catch (NumberFormatException nfe) {
                            //
                        }
                        if (check == 'a') {
                            friendSuggestion friendSuggestion = new friendSuggestion(ID,graph);
                            friendSuggestion.suggestion();
                            frame.dispose();
                        } else if(check == 'b'){
                            profile = new userProfile(ID,graph);
                            profile.display(true);
                            frame.dispose();
                        }else{
                            // If the user ID doesn't exist, display an error message
                            editBtn.popUpRandom(new JLabel("No User Found"));
                        }
                    }
                }
            });

        JPanel btnPanel = new JPanel();
        btnPanel.add(btn1);
        btnPanel.add(btn2);
        btnPanel.setBackground(Color.lightGray);
        // Add the sign up and sign in buttons to the top panel
        topPanel.add(btnPanel);
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.PINK);
        topPanel.add(Box.createVerticalGlue());
        frame.add(topPanel, BorderLayout.CENTER);

    }

    /**
     * Creates a panel with a user ID text field.
     * @return The text panel with a user ID text field.
     */
    public JPanel createTextPanel() {
        JPanel textPanel = new JPanel();
        JLabel textLabel = new JLabel("User ID");
        textLabel.setFont(new Font("Serif", Font.PLAIN, 30));

        textField.setFont(new Font("Serif", Font.PLAIN, 30));
        textField.setPreferredSize(new Dimension(300, 50));

        textPanel.add(textLabel);
        textPanel.add(textField);
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER , 50, 50));
        textPanel.setBackground(Color.lightGray);
        return textPanel;
    }

    /**
     * Handles the action of the "First" button by creating a new frame, registering a user
     * and providing a way to go back to the previous frame.
     */
    public void firstButton(){
        JButton btn3 = new JButton("Back"); // Create a new button labeled "Back"
        JButton btn4 = new JButton("Register"); // Create a new button labeled "Register"
        frame3 = new JFrame("Convo"); // Create a new frame with title "Convo"
        editBtn.WindowExit(frame3); // Set the properties of the new frame

        // Register a user by taking user input and validating it
        editBtn.registration(textField2,textField3,textField4,textField5,frame3,btn3,btn4,"Registration",true);

        // Add an action listener to the "Back" button to dispose of the current frame and display the previous one
        btn3.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(e.getSource() == btn3){
                        frame3.dispose(); // Dispose of the current frame
                        textField.setText(""); // Clear a text field
                        frame.setVisible(true); // Display the previous frame
                    }
                }
            });

        // Add an action listener to the "Register" button to register the user if the input is valid
        btn4.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    if(e.getSource() == btn4){
                        char ch = editBtn.inputValidation(textField2,textField3,textField4,textField5,true); // Validate the user input
                        if (ch == 'a'){
                            int id = Integer.parseInt(textField2.getText()); // Get the user ID from the input field
                            String name = capital(textField3.getText()); // Get the user name from the input field and capitalize the first letter
                            String work = capital(textField4.getText()); // Get the user workplace from the input field and capitalize the first letter
                            String home = capital(textField5.getText()); // Get the user home from the input field and capitalize the first letter

                            // Add the user to the graph
                            graph.addUser(id,name,work,home,"","");

                            JButton btn = new JButton("Back"); // Create a new button labeled "Back"
                            JFrame ram = new JFrame("Registered"); // Create a new frame with title "Registered"
                            editBtn.popUp(btn,ram,new JLabel("Registration Successful"),300); // Set the properties of the new frame

                            // Add an action listener to the "Back" button to dispose of the current frame and display the previous one
                            btn.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        ram.dispose(); // Dispose of the current frame
                                        frame3.dispose(); // Dispose of the previous frame
                                        frame.setVisible(true); // Display the previous frame
                                    }
                                });
                            ram.setVisible(true); // Display the new frame
                        } else if (ch == 'b') {
                            editBtn.popUpRandom(new JLabel("Use Different ID")); // Display an error message if the user ID already exists
                        }else {
                            editBtn.popUpRandom(new JLabel("Incorrect Information")); // Display an error message if the user input is incorrect
                        }
                    }
                }
            });

        frame3.setVisible(true);
    }

    /**
     * Capitalizes the first letter of a string.
     * @param str the string to be capitalized
     * @return the capitalized string
     */
    public String capital(String str){
        String capitalizedStr = Character.toUpperCase(str.charAt(0)) + str.substring(1);
        return capitalizedStr;
    }

}
