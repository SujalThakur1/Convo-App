import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    public void run(boolean boo) {
        // Create and configure the main JFrame
        frame = new JFrame("Convo");
        frame.setSize(900, 730);
        frame.setLocationRelativeTo(null);
        edit.WindowExit(frame);

        // Create the main panel with gradient background
        JPanel mainPanel = edit.panel("Friends");

        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(edit.backgroundColor);

        // Create header panel for title
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Friends");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 40));
        titleLabel.setForeground(edit.textColor);
        headerPanel.add(titleLabel);

        // Create search and filter panel
        JPanel controlPanel = new JPanel(new BorderLayout(10, 0));
        controlPanel.setOpaque(false);
        controlPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Create search field
        JTextField searchField = edit.createStyledTextField();
        searchField.setPreferredSize(new Dimension(300, 40));
        searchField.putClientProperty("JTextField.placeholderText", "Search friends...");

        // Create filter button
        JButton btnFilter = edit.createStyledButton("Filter");
        btnFilter.setPreferredSize(new Dimension(120, 40));

        // Add search and filter to control panel
        controlPanel.add(searchField, BorderLayout.CENTER);
        controlPanel.add(btnFilter, BorderLayout.EAST);

        // Combine header and control panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.add(headerPanel, BorderLayout.NORTH);
        topPanel.add(controlPanel, BorderLayout.CENTER);

        // Create center panel for friends list
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(main(boo), BorderLayout.CENTER);

        // Create bottom panel for navigation buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setOpaque(false);

        if(boo) {
            // Single back button for true case
            JButton backBtn = edit.createStyledButton("Back");
            bottomPanel.add(backBtn);
            back(backBtn);
        } else {
            // Back and Home buttons for false case
            JButton backBtn = edit.createStyledButton("Back");
            JButton homeBtn = edit.createStyledButton("Go Home");

            bottomPanel.add(backBtn);
            bottomPanel.add(homeBtn);

            backBtn.addActionListener(e -> {
                if(e.getSource() == backBtn) {
                    frame.dispose();
                    BackFrame.setVisible(true);
                }
            });

            homeBtn.addActionListener(e -> {
                if(e.getSource() == homeBtn) {
                    frame.dispose();
                    HomeFrame.setVisible(true);
                }
            });
        }

        // Add filter button action
        btnFilter.addActionListener(e -> filter(boo));

        // Add search functionality
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String capitalizedStr = "";
                if(!searchField.getText().equals("")) {
                    capitalizedStr = Character.toUpperCase(searchField.getText().charAt(0)) + searchField.getText().substring(1);
                }
                ArrayList<UserDetails> details = graph.getUsersByPlace(capitalizedStr, 's', id);
                for (UserDetails user : details) {
                    System.out.println(user.getName());
                }
                combinePart(details);
                frind.revalidate();
                suggestion.revalidate();
                frind.repaint();
                suggestion.repaint();
                frame.repaint();
            }
        });

        // Add all panels to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    /**
     * This method creates and returns a JPanel that displays a list of friends and friend suggestions
     * @param boo boolean flag that indicates if friend suggestions should be displayed
     * @return JPanel that displays a list of friends and friend suggestions
     */
    public JPanel main(boolean boo) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(edit.backgroundColor);
        mainPanel.setOpaque(false);

        if (!boo) {
            // Create a panel containing the user's friends
            combinePart2(FriendFriends);
            mainPanel.add(list("Your Friends", frind));
            return mainPanel;
        }

        // Create panels for friends and suggestions
        combinePart(graph.getUsers());
        mainPanel.add(list("Your Friends", frind));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(list("Suggestions", suggestion));
        return mainPanel;
    }

    public JScrollPane list(String name, JPanel pnl) {
        // Create header
        JLabel headingLabel = new JLabel(name);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setForeground(edit.textColor);
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(edit.backgroundColor);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.add(headingLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(pnl);

        // Create scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createLineBorder(edit.accentColor, 1));
        scrollPane.setPreferredSize(new Dimension(600, 300));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBackground(edit.backgroundColor);

        return scrollPane;
    }

    public void back(JButton btn) {
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(e.getSource() == btn) {
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
    public void filter(boolean boo) {
        JFrame popup = new JFrame("Filter Friends");
        popup.setUndecorated(true);

        // Main panel with dark background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, new Color(25, 25, 35), 0, getHeight(), new Color(45, 45, 60));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(123, 104, 238), 2),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));

        // Title label
        JLabel titleLabel = new JLabel("Filter Options");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Button panel with more vertical space
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // Grid layout with 20px gaps
        buttonPanel.setOpaque(false);

        ButtonGroup group = new ButtonGroup();

        JButton button2 = edit.createStyledButton("Work");
        JButton button3 = edit.createStyledButton("Home");

        if (!boo) {
            JButton button1 = edit.createStyledButton("Same Friends");
            button1.setPreferredSize(new Dimension(120, 50));
            group.add(button1);
            buttonPanel.add(button1);
            sameFriend(button1);
            popup.setSize(400, 250);
            button2.setPreferredSize(new Dimension(100, 50));
            button3.setPreferredSize(new Dimension(100, 50));
        }else{
            popup.setSize(400, 250);
            button2.setPreferredSize(new Dimension(140, 50));
            button3.setPreferredSize(new Dimension(140, 50));
        }


        JButton button4 = edit.createStyledButton("Apply Filter");

        button4.setPreferredSize(new Dimension(200, 50));

        work(button2, boo);
        home(button3, boo);

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == button4) {
                    if (boo) {
                        if (wark || homi) {
                            combine(boo, new JTextField());
                        }
                    } else {
                        if (wark || homi || samefriend) {
                            combine(boo, new JTextField());
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

        buttonPanel.add(button2);
        buttonPanel.add(button3);

        // Wrap buttonPanel in another panel to maintain centering
        JPanel buttonWrapperPanel = new JPanel();
        buttonWrapperPanel.setOpaque(false);
        buttonWrapperPanel.add(buttonPanel);
        mainPanel.add(buttonWrapperPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // Center the Apply Filter button
        JPanel applyButtonPanel = new JPanel();
        applyButtonPanel.setOpaque(false);
        applyButtonPanel.add(button4);
        mainPanel.add(applyButtonPanel);

        popup.add(mainPanel);
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

        // Create styled labels for no results
        JLabel label = new JLabel("No Friends Found");
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        label.setForeground(edit.textColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel label2 = new JLabel("No Suggestions Found");
        label2.setFont(new Font("Poppins", Font.BOLD, 20));
        label2.setForeground(edit.textColor);
        label2.setHorizontalAlignment(SwingConstants.CENTER);

        boolean zeroFriend = false;
        boolean zeroSuggestion = false;

        String[] frnd = friends.split(":");

        if(details!=null) {
            // Use GridBagLayout for better control
            frind.setLayout(new GridBagLayout());
            suggestion.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(2, 2, 2, 2);

            int friendRow = 0;
            int suggestionRow = 0;

            for (UserDetails details1 : details) {
                if(details1.getID() != id) {
                    boolean found = false;
                    for (int i = 0; i < frnd.length; i++) {
                        if(!frnd[0].equals("")) {
                            int frndId = Integer.parseInt(frnd[i]);
                            if (details1.getID() == frndId) {
                                JButton inspect = edit.createStyledButton("Inspect");
                                JButton Remove = edit.createStyledButton("Remove");

                                inspect.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        userProfile profile = new userProfile(details1.getID(),
                                                HomeFrame, frame, id, graph);
                                        frame.dispose();
                                        profile.display(false);
                                    }
                                });
                                removeAction(Remove, details1.getID());

                                JPanel friendPanel = createUserPanel(details1.getName(), inspect, Remove);
                                gbc.gridy = friendRow++;
                                frind.add(friendPanel, gbc);
                                zeroFriend = true;
                                found = true;
                            }
                        }
                    }
                    if (!found) {
                        JButton inspect = edit.createStyledButton("Inspect");
                        JButton add = edit.createStyledButton("Add");

                        inspect.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if (e.getSource() == inspect) {
                                    friendSuggestion.inspect(details1.getName(), details1.getWork(), details1.getHome(), frame);
                                }
                            }
                        });
                        addAction(add, details1.getID());

                        JPanel suggestionPanel = createUserPanel(details1.getName(), inspect, add);
                        gbc.gridy = suggestionRow++;
                        suggestion.add(suggestionPanel, gbc);
                        zeroSuggestion = true;
                    }
                }
            }

            // Add filler component to push everything to the top
            gbc.weighty = 1.0;
            gbc.gridy = Math.max(friendRow, 1);
            frind.add(Box.createVerticalGlue(), gbc);
            gbc.gridy = Math.max(suggestionRow, 1);
            suggestion.add(Box.createVerticalGlue(), gbc);

            if (!zeroFriend) {
                gbc.gridy = 0;
                gbc.weighty = 1.0;
                frind.add(label, gbc);
            }
            if (!zeroSuggestion) {
                gbc.gridy = 0;
                gbc.weighty = 1.0;
                suggestion.add(label2, gbc);
            }
        } else {
            frind.setLayout(new GridBagLayout());
            suggestion.setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            frind.add(label, gbc);
            suggestion.add(label2, gbc);
        }
        frind.setBackground(edit.backgroundColor);
        suggestion.setBackground(edit.backgroundColor);
        frind.revalidate();
        frind.repaint();
        suggestion.revalidate();
        suggestion.repaint();
    }

    private JPanel createUserPanel(String name, JButton... buttons) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(edit.backgroundColor);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, edit.accentColor.darker()));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        // Smaller bottom inset for name label
        gbc.insets = new Insets(5, 10, 0, 10);

        // Name label
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Poppins", Font.PLAIN, 18));
        nameLabel.setForeground(edit.textColor);
        panel.add(nameLabel, gbc);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 2));
        buttonPanel.setOpaque(false);

        for(JButton button : buttons) {
            button.setPreferredSize(new Dimension(100, 30));
            buttonPanel.add(button);
        }

        gbc.gridx = 1;
        gbc.weightx = 0;
        // Different insets for button panel to keep some bottom spacing
        gbc.insets = new Insets(5, 10, 2, 10);
        panel.add(buttonPanel, gbc);

        return panel;
    }

    public void combinePart2(ArrayList<UserDetails> detail) {
        frind.removeAll();

        JLabel label = new JLabel("No Friends Found");
        label.setFont(new Font("Poppins", Font.BOLD, 20));
        label.setForeground(edit.textColor);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        String[] buddy = UserFriend.split(":");

        if(detail!=null) {
            frind.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(2, 2, 2, 2);

            int row = 0;

            for (UserDetails details : detail) {
                if (details.getID() != userID) {
                    boolean foundFriend = false;
                    JButton inspect = edit.createStyledButton("Inspect");

                    for (int j = 0; j < buddy.length; j++) {
                        int buddyID = Integer.parseInt(buddy[j]);
                        if (buddyID == details.getID()) {
                            JButton Remove = edit.createStyledButton("Remove");
                            removeAction(Remove, details.getID());
                            gbc.gridy = row++;
                            frind.add(createUserPanel(details.getName(), inspect, Remove), gbc);
                            foundFriend = true;
                        }
                    }

                    if (!foundFriend) {
                        JButton Add = edit.createStyledButton("Add");
                        addAction(Add, details.getID());
                        gbc.gridy = row++;
                        frind.add(createUserPanel(details.getName(), inspect, Add), gbc);
                    }

                    inspect.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            userProfile profile = new userProfile(details.getID(), HomeFrame, frame, userID, graph);
                            frame.dispose();
                            profile.display(false);
                        }
                    });

                } else {
                    JButton youBtn = edit.createStyledButton("You");
                    gbc.gridy = row++;
                    frind.add(createUserPanel(details.getName(), youBtn), gbc);
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
            }

            // Add filler to push everything to top
            gbc.weighty = 1.0;
            gbc.gridy = Math.max(row, 1);
            frind.add(Box.createVerticalGlue(), gbc);

        } else {
            frind.setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.weightx = 1;
            gbc.weighty = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            frind.add(label, gbc);
        }
        frind.setBackground(edit.backgroundColor);
        frind.revalidate();
        frind.repaint();
    }
}