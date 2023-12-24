import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Friends class represents a JFrame that displays a list of user's friends and friend suggestions.
 * It contains methods to filter the friends list, edit friend details, and navigate to the user's home page or go back to a previous page.
 */
public class Friends {
    private JFrame frame,HomeFrame,BackFrame;
    private Edit edit;
    private JButton CountFriends;
    private int id,userID,Size;
    private String friends,UserFriend,home,work;
    private friendSuggestion friendSuggestion;
    private GridBagConstraints gbc;
    private MyGraph graph;
    private JPanel frind,suggestion;
    private ArrayList<UserDetails> FriendFriends;
    private boolean wark,homi,samefriend;

    /**
     * Constructs of Friend class.
     * @param id ID of the user whose friends are to be displayed
     * @param HomeFrame the JFrame of the user's home page
     * @param CountFriends the button that displays the number of user's friends
     * @param BackFrame the JFrame of the previous page
     * @param userID ID of the user
     * @param graph the MyGraph object containing the user data
     */
    public Friends(int id,JFrame HomeFrame,JButton CountFriends
    ,JFrame BackFrame,int userID,MyGraph graph){
        friendSuggestion = new friendSuggestion(userID,graph);
        frame = new JFrame("Convo");
        edit = new Edit();
        this.graph = graph;
        this.id = id;
        this.userID = userID;
        this.CountFriends = CountFriends;
        FriendFriends = graph.getUserFriends(id);
        for (UserDetails details : graph.getUsers()){
            if(id == details.getID()){
                friends = graph.getUserEdges(details);
                String []a = friends.split(":");
                Size = a.length;
            }
            if(userID == details.getID()){
                UserFriend = graph.getUserEdges(details);
                work = details.getWork();
                home = details.getHome();
            }
        }
        this.HomeFrame = HomeFrame;
        this.BackFrame = BackFrame;
        frind = new JPanel();
        suggestion = new JPanel();
        wark = false;
        homi = false;
        samefriend = false;
    }

    /**
     * Creates a new instance of the Friends class with the specified parameters.
     *
     * @param id the ID of the user for whom friends are being displayed
     * @param HomeFrame the JFrame that contains the "Home" button for navigation
     * @param CountFriends the JButton that displays the number of friends
     * @param graph the graph containing user data
     */
    public Friends(int id, JFrame HomeFrame, JButton CountFriends, MyGraph graph) {
        // initialize panels and other objects
        frind = new JPanel();
        suggestion = new JPanel();
        this.graph = graph;
        friendSuggestion = new friendSuggestion(id, graph);
        frame = new JFrame("Convo");
        edit = new Edit();
        this.id = id;
        this.CountFriends = CountFriends;

        // iterate over all user details in the graph
        for (UserDetails details : graph.getUsers()) {
            // if the current user ID matches the specified ID
            if (id == details.getID()) {
                // get the user's friends and the number of friends
                friends = graph.getUserEdges(details);
                String[] a = friends.split(":");
                Size = a.length;

                // get the user's home and work information
                home = details.getHome();
                work = details.getWork();
            }
        }

        // set the HomeFrame parameter
        this.HomeFrame = HomeFrame;

        // initialize other boolean variables
        wark = false;
        homi = false;
    }

    /**
     * This method creates and displays a JFrame that shows a list of user's friends and
     * allows the user to filter the list by various criteria.
     * @param boo boolean that determines whether to display the list of user's friends with Back button or Go Home button.
     *            true - display the list with Back button to go back to the previous frame
     *            false - display the list with Go Home button to go back to the home frame
     */
    public void run(boolean boo){
        // Create and configure the main JFrame
        frame = new JFrame("Convo");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        edit.WindowExit(frame);

        // Create the main JPanel that will hold all the components
        JPanel panel = edit.panel("Friends");

        // Create another JPanel that will hold the list of friends and filter button
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        // Create and add a Filter button to the top of the panel2
        JButton btnFilter = new JButton("Filter");
        panel2.add(button(btnFilter), BorderLayout.NORTH);

        // Create a Back button to go back to the previous frame
        JButton BackBtn = new JButton("Back");

        // If boo is true, display the list with Back button and configure the panel2 accordingly
        if(boo) {
            panel2.add(main(true), BorderLayout.CENTER);
            panel2.add(button(BackBtn), BorderLayout.SOUTH);
            back(BackBtn);
        }
        // If boo is false, display the list with Go Home button and configure the panel2 accordingly
        else{
            panel2.add(main(false), BorderLayout.CENTER);

            // Create a Go Home button to go back to the home frame
            JButton HomeBtn = new JButton("Go Home");

            // Configure the Back and Go Home buttons using the edit utility class
            edit.btn(BackBtn,HomeBtn);

            // Create another JPanel to hold the Back and Go Home buttons, set its background color, and add the buttons
            JPanel panel1 = new JPanel();
            panel1.setBackground(Color.GREEN);
            panel1.add(BackBtn);
            panel1.add(HomeBtn);

            // Add the panel1 to panel2 at the bottom
            panel2.add(panel1,BorderLayout.SOUTH);

            // Add ActionListeners to the Back and Go Home buttons to go back to the previous frame or home frame
            BackBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getSource() == BackBtn) {
                            frame.dispose();
                            BackFrame.setVisible(true);
                        }
                    }
                });

            HomeBtn.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getSource() == HomeBtn) {
                            frame.dispose();
                            HomeFrame.setVisible(true);
                        }
                    }
                });

        }

        // Add an ActionListener to the Filter button to filter the list of friends
        btnFilter.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(boo) {
                        filter(true);
                    }else {
                        filter(false);
                    }
                }
            });

        // Add panel2 to the main panel and add the main panel to the JFrame
        panel.add(panel2 );
        frame.add(panel);

        // Display the JFrame
        frame.setVisible(true);
    }

    /**
     * This method creates and returns a JPanel that displays a list of friends and friend suggestions
     * @param boo boolean flag that indicates if friend suggestions should be displayed
     * @return JPanel that displays a list of friends and friend suggestions
     */
    public JPanel main(boolean boo){
        JPanel check = new JPanel();
        check.setLayout(new BoxLayout(check,BoxLayout.Y_AXIS));
        if (!boo) {
            // Create a panel containing the user's friends
            combinePart2(FriendFriends);
            check.add(list("Your Friends", frind));
            return check;
        }
        // Create a panel containing the user's friends and friend suggestions
        combinePart(graph.getUsers());
        check.add(list("Your Friends" , frind));
        check.add(list("Suggestions" , suggestion));
        return check;
    }

    /**
     * Creates a JPanel containing a single JButton with the specified label, and returns it.
     *
     * @param filterButton The JButton to add to the JPanel.
     * @return A JPanel containing the specified JButton.
     */
    public JPanel button(JButton filterButton){
        edit.btn(filterButton); // Set properties of the JButton using an Edit object.
        JPanel btnpanel = new JPanel();
        btnpanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Set the layout of the JPanel to FlowLayout with center alignment.
        btnpanel.add(filterButton); // Add the specified JButton to the JPanel.
        btnpanel.setBackground(Color.green); // Set the background color of the JPanel to green.
        return btnpanel; // Return the JPanel containing the specified JButton.
    }

    /**
     * Creates a JScrollPane with a JPanel that contains a title and another JPanel.
     * @param name the title of the JPanel
     * @param pnl the JPanel to be displayed within the JScrollPane
     * @return the created JScrollPane
     */
    public JScrollPane list(String name, JPanel pnl) {

        // Create a JLabel for the title of the JPanel
        JLabel headingLabel = new JLabel(name);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 16));
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create a new JPanel to hold the title and the passed-in JPanel
        JPanel Panel = new JPanel();
        Panel.setBackground(Color.cyan);
        Panel.setLayout(new BoxLayout(Panel, BoxLayout.Y_AXIS));
        Panel.add(headingLabel);
        Panel.add(pnl);

        // Create a new JScrollPane to hold the new JPanel
        JScrollPane scrollPane = new JScrollPane(Panel);

        // Always show the vertical scrollbar
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // Set the preferred size of the scrollPane
        scrollPane.setPreferredSize(new Dimension(800, 500));

        // Create a border around the scrollPane
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        scrollPane.setBorder(border);

        // Return the created JScrollPane
        return scrollPane;
    }

    /**
     * Creates a JPanel for editing a friend's information.
     * @param name The name of the friend.
     * @param inspect The JButton for inspecting the friend's information.
     * @param btn2 The JButton for performing another action on the friend.
     * @return The JPanel for editing the friend's information.
     */
    public JPanel edit(String name ,JButton inspect , JButton btn2){
        JPanel friendPanel = editCombination(name);

        JPanel buttonPanel = editCombo();

        edit.btnSmall(inspect,btn2);

        buttonPanel.add(inspect);
        buttonPanel.add(btn2);

        friendPanel.add(buttonPanel, gbc);
        friendPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        return friendPanel;
    }

    /**
     * Creates a JPanel for editing a friend's information
     * @param name The friend's name
     * @param button The button to edit the friend's information
     * @return The JPanel for editing the friend's information
     */
    public JPanel edit(String name, JButton button) {
        JPanel friendPanel = editCombination(name);
        edit.btn3(button);
        JPanel buttonPanel = editCombo();
        buttonPanel.add(button);
        friendPanel.add(buttonPanel, gbc);
        friendPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
        return friendPanel;
    }

    public JPanel editCombo(){
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.cyan);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        return buttonPanel;
    }

    /**
     * Creates and returns a JPanel containing a labeled panel.
     *
     * @param name the name of the label to be displayed
     * @return the JPanel containing the labeled panel
     */
    public JPanel editCombination(String name){
        // Create the main panel with a grid bag layout
        JPanel friendPanel = new JPanel(new GridBagLayout());
        friendPanel.setBackground(Color.cyan);

        // Set up the grid bag constraints for the label
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Create and add the label to the panel
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Serif", Font.PLAIN, 30));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        friendPanel.add(nameLabel, gbc);

        // Set up the grid bag constraints for the next component
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.weightx = 0.0;

        return friendPanel;
    }

    /**
     * @param btn the JButton to attach the ActionListener to
     */
    public void back(JButton btn){
        btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == btn){
                        frame.dispose();
                        CountFriends.setText(Size + " Friends");
                        HomeFrame.setVisible(true);
                    }
                }
            });
    }

    /**
     * Displays a filter popup window to filter friends by either workplace, home address, or same friends.
     *
     * @param boo a boolean value indicating whether to display the search bar or not
     *            - true to display the search bar
     *            - false to display the "Same Friends" button instead of the search bar
     *
     * The user can also filter friends by clicking the "Work" or "Home" buttons. Once the user has selected a filter,
     * they can click the "Go" button to apply the filter and close the popup window.
     */
    public void filter(boolean boo){
        JFrame popup = new JFrame("Convo");
        edit.PopUpFrame(popup, 400);
        ButtonGroup group = new ButtonGroup();
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel1.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panel1.setBackground(Color.PINK);

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setBackground(Color.CYAN);
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField search = new JTextField();
        if(boo) {
            search.setFont(new Font("Serif", Font.PLAIN, 30));
            search.setPreferredSize(new Dimension(300, 50));
            panel2.add(search);
        }else {
            JButton button1 = new JButton("Same Friends");
            group.add(button1);
            edit.btn2(button1);
            panel1.add(button1);
            sameFriend(button1);
        }

        JButton button2 = new JButton("Work");
        JButton button3 = new JButton("Home");
        JButton button4 = new JButton("Go");

        work(button2,boo);
        home(button3,boo);

        button4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == button4) {
                        if(boo) {
                            if (wark || homi || search.getText() != null || search.getText().equals("")) {
                                combine(boo, search);
                            }
                        }else{
                            if(wark||homi||samefriend){
                                combine(boo,search);
                            }
                        }
                        wark = false;
                        homi = false;
                        samefriend = false;
                        popup.dispose();
                        frame.setVisible(true);
                    }
                }
            });

        group.add(button2);
        group.add(button3);
        group.add(button4);

        edit.btn2(button2);
        edit.btn2(button3);
        edit.btn2(button4);

        panel1.add(button2);
        panel1.add(button3);


        panel2.add(Box.createRigidArea(new Dimension(0, 10)));
        panel2.add(panel1);
        panel2.add(Box.createRigidArea(new Dimension(0, 10)));
        panel2.add(button4);

        popup.add(panel2);
        popup.pack();
        popup.setLocationRelativeTo(null);
        popup.setVisible(true);
    }

    public void addAction(JButton add, int idd){
        add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == add) {
                        Size = friendSuggestion.actionListener(idd, add,true);
                        frame.revalidate();
                        frame.repaint();
                    }
                }
            });
    }

    public void removeAction(JButton Remove,int iddd){
        Remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Size = friendSuggestion.actionListener(iddd,Remove,false);
                }
            });
    }

    public void sameFriend(JButton btn){
        btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btn){
                        btn.setEnabled(false);
                        samefriend = true;
                    }
                }
            });
    }

    public void work(JButton btn,boolean boo){
        btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btn){
                        btn.setEnabled(false);
                        wark = true;
                    }
                }
            });
    }

    public void home(JButton btn, boolean boo){
        btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btn){
                        btn.setEnabled(false);
                        homi = true;
                    }
                }
            });
    }

    /**
     * Combines and displays user details based on search parameters.
     *
     * @param boo true if combining user details, false if only displaying friend details
     * @param search the search field containing the query string
     * @return void
     */
    public void combine(boolean boo, JTextField search) {
        // Capitalize the first letter of the query string
        String capitalizedStr = "";
        if(!search.getText().equals("")) {
            capitalizedStr = Character.toUpperCase(search.getText().charAt(0)) + search.getText().substring(1);
        }

        if(wark && homi &&  !search.getText().equals("")) {
            // Combine user details by work and home location
            ArrayList<UserDetails> details = graph.getUsersByPlace(work, home, capitalizedStr, id);
            combinePart(details);
        } else if(wark &&  !search.getText().equals("")) {
            // Combine user details by work location
            ArrayList<UserDetails> details = graph.getUsersByPlace(work, capitalizedStr, id, 'w', 's');
            combinePart(details);
        } else if(homi && !search.getText().equals("")) {
            // Combine user details by home location
            ArrayList<UserDetails> details = graph.getUsersByPlace(home, capitalizedStr, id, 'h', 's');
            combinePart(details);
        } else if(wark && homi) {
            // Combine user details by work and home location (all users)
            ArrayList<UserDetails> details = graph.getUsersByPlace(work, home, id, 'w', 'h');
            combinePart(details);
        } else if(wark) {
            // Combine user details by work location (all users)
            ArrayList<UserDetails> details = graph.getUsersByPlace(work, 'w', id);
            combinePart(details);
        } else if(homi) {
            // Combine user details by home location (all users)
            ArrayList<UserDetails> details = graph.getUsersByPlace(home, 'h', id);
            combinePart(details);
        } else if(!search.getText().equals("")) {
            // Combine user details by query string only
            ArrayList<UserDetails> details = graph.getUsersByPlace(capitalizedStr, 's', id);
            combinePart(details);
        }

        frind.repaint();
        suggestion.repaint();

        if(!boo) {
            frind.removeAll();
            if(wark && homi && samefriend) {
                // Combine friend details by work and home location
                ArrayList<UserDetails> details = graph.getUserFriendsByPlace(FriendFriends, work, home, UserFriend);
                combinePart2(details);
            } else if(wark && homi) {
                // Combine friend details by work and home location (all friends)
                ArrayList<UserDetails> details = graph.getUserFriendsByPlace(FriendFriends, work, home, 'w', 'h');
                combinePart2(details);
            } else if(wark && samefriend) {
                // Combine friend details by work location and same friend
                ArrayList<UserDetails> details = graph.getUserFriendsByPlace(FriendFriends, work, UserFriend, 'w', 's');
                combinePart2(details);
            } else if(homi && samefriend) {
                // Combine friend details by home location and same friend
                ArrayList<UserDetails> details = graph.getUserFriendsByPlace(FriendFriends, home, UserFriend, 'h', 's');
                combinePart2(details);
            } else if(wark) {
                // Combine friend details by userwork location
                ArrayList<UserDetails> details = graph.getUserFriendsByPlace(FriendFriends, work, 'w');
                combinePart2(details);
            } else if (homi) {
                // Combine friend details by user home location
                ArrayList<UserDetails> details = graph.getUserFriendsByPlace(FriendFriends,home,'h');
                combinePart2(details);
            }else if (samefriend){
                // Combine friend details by user sameFriend
                ArrayList<UserDetails> details = graph.getUserFriendsByPlace(FriendFriends,UserFriend,'s');

                combinePart2(details);
            }
            frind.repaint();
        }
    }

    /**
     * Combines the friend list and suggestion list for the given user details.
     *
     * @param details an ArrayList of UserDetails objects representing the user details
     */

    public void combinePart(ArrayList<UserDetails> details) {
        // Clear the friend and suggestion lists
        frind.removeAll();
        suggestion.removeAll();

        // Create labels to display if no friends or matches are found
        JLabel label = new JLabel("No Friend Found");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Serif", Font.PLAIN, 30));
        JLabel label2 = new JLabel("No Matches found");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setFont(new Font("Serif", Font.PLAIN, 30));

        // Initialize variables to track the number of suggestions and friends
        boolean zeroFriend = false;
        boolean zeroSuggestion = false;
        int countSuggestion = 0;
        int countFriends = 0;

        // Split the list of friends for this user by ":" delimiter
        String[] frnd = friends.split(":");

        // Loop through each user in the user details list
        if(details!=null) {
            for (UserDetails details1 : details) {
                // Ignore the current user (by ID)
                if(details1.getID() != id) {
                    boolean found = false;
                    // Check if the current user is already a friend
                    for (int i = 0; i < frnd.length; i++) {
                        if(!frnd[0].equals("")) {
                            int frndId = Integer.parseInt(frnd[i]);
                            if (details1.getID() == frndId) {
                                // Create inspect and remove buttons for the friend
                                JButton inspect = new JButton("Inspect");
                                JButton Remove = new JButton("Remove");
                                // Add action listeners for the inspect and remove buttons
                                inspect.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            // Create a user profile for the selected friend
                                            userProfile profile = new userProfile(details1.getID(),
                                                    HomeFrame, frame, id, graph);
                                            // Close the current window and display the profile
                                            frame.dispose();
                                            profile.display(false);
                                        }
                                    });
                                removeAction(Remove, details1.getID());
                                // Add the friend to the friend list
                                frind.add(edit(details1.getName(), inspect, Remove));
                                zeroFriend = true;
                                found = true;
                                countFriends++;
                            }
                        }
                    }
                    // If the current user is not already a friend, add to suggestion list
                    if (!found) {
                        // Create inspect and add buttons for the suggestion
                        JButton inspect = new JButton("Inspect");
                        JButton add = new JButton("Add");
                        // Add action listener for the inspect button
                        inspect.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    if (e.getSource() == inspect) {
                                        // Display a friend suggestion dialog for the selected user
                                        friendSuggestion.inspect(details1.getName(), details1.getWork(), details1.getHome(), frame);
                                    }
                                }
                            });
                        addAction(add, details1.getID());
                        // Add the suggestion to the suggestion list
                        suggestion.add(edit(details1.getName(), inspect, add));
                        countSuggestion++;
                        zeroSuggestion = true;
                    }
                }
            }
            // Set the background and layout for the suggestion and friend lists
            suggestion.setBackground(Color.cyan);
            suggestion.setLayout(new GridLayout(countSuggestion, 1, 10, 10));
            frind.setBackground(Color.cyan);
            frind.setLayout(new GridLayout(countFriends,10,10,10));
            if (!zeroFriend) {
                frind.add(label);
                frind.setLayout(new BoxLayout(frind, BoxLayout.Y_AXIS));
            }
            if (!zeroSuggestion) {
                suggestion.add(label2);
                suggestion.setLayout(new BoxLayout(suggestion, BoxLayout.Y_AXIS));
            }
        }else{
            frind.add(label);
            frind.setLayout(new BoxLayout(frind, BoxLayout.Y_AXIS));
            suggestion.add(label2);
            suggestion.setLayout(new BoxLayout(suggestion, BoxLayout.Y_AXIS));
        }

    }

    /**
     * Updates the friend list with the given list of user details.
     * If the user is not already a friend, displays the "Add" button,
     * otherwise displays the "Remove" button. If the user is the current user,
     * displays "You" button instead.
     *
     * @param detail an ArrayList of UserDetails objects representing users to be displayed in the friend list.
     *               Can be null if there are no friends.
     */
    public void combinePart2(ArrayList<UserDetails> detail){
        // Clear the friend list
        frind.removeAll();

        // Create a label to display if there are no friends
        JLabel label = new JLabel("No Friend Found");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setFont(new Font("Serif", Font.PLAIN, 30));

        // Get the list of user IDs of the current user's friends
        String[] buddy = UserFriend.split(":");

        // Counter for the number of friends displayed
        int countFriends = 0;

        if(detail!=null) {
            for (UserDetails details : detail) {
                // If the user is not the current user
                if (details.getID() != userID) {
                    boolean foundFriend = false;
                    JButton inspect = new JButton("Inspect");

                    // Check if the user is already a friend
                    for (int j = 0; j < buddy.length; j++) {
                        int buddyID = Integer.parseInt(buddy[j]);
                        if (buddyID == details.getID()) {
                            JButton Add = new JButton("Remove");
                            removeAction(Add, details.getID());
                            frind.add(edit(details.getName(), inspect, Add));
                            foundFriend = true;
                        }
                    }

                    // If the user is not already a friend, display "Add" button
                    if (!foundFriend) {
                        JButton Add = new JButton("Add");
                        addAction(Add, details.getID());
                        frind.add(edit(details.getName(), inspect, Add));
                    }

                    // Add an action listener to the "Inspect" button to display the user's profile
                    inspect.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                userProfile profile = new userProfile(details.getID(), HomeFrame, frame, userID, graph);
                                frame.dispose();
                                profile.display(false);
                            }
                        });

                } else {
                    // If the user is the current user, display "You" button
                    JButton youBtn = new JButton("You");
                    frind.add(edit(details.getName(), youBtn));
                    youBtn.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == youBtn) {
                                    frame.dispose();
                                    HomeFrame.setVisible(true);
                                }
                            }
                        });
                }

                countFriends++;
            }

            // Set the background color and layout for the friend list
            frind.setBackground(Color.cyan);
            frind.setLayout(new GridLayout(countFriends,10,10,10));
        } else {
            // If there are no friends, display the label
            frind.add(label);
            frind.setLayout(new BoxLayout(frind, BoxLayout.Y_AXIS));
        }
    }
}

