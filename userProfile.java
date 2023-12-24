import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.Stack;

/**
 * The userProfile class represents the user profile and contains the user details, such as name, ID, workplace, and hometown.
 * It provides methods to display the user profile, add buttons, and set button actions.
 * It has two constructors, one with a JFrame, and the other without a JFrame.
 * It is used to display the user profile and allows editing and adding friends.
 */
public class userProfile {
    // Class variables
    private JFrame frame, frame2, homePage, backFrame;
    private Edit editBtn;
    private JPanel imagee;
    private JButton but1;
    private boolean bull;
    private MyGraph graph;

    // User Details
    private int id, userID;
    private String name1, name2, ID, homeTown, friends, workPlace, ImG, LIKE;

    /**
     * Constructor for the userProfile class with JFrame.
     * @param id the id of the user profile to display
     * @param homePage the home page JFrame
     * @param backFrame the back frame JFrame
     * @param userID the user ID
     * @param graph the graph to display
     */
    public userProfile(int id,JFrame homePage,JFrame backFrame,int userID,MyGraph graph){
        this.graph = graph;
        this.id = id;
        editBtn = new Edit(graph);
        imagee = new JPanel();
        bull = true;
        this.homePage = homePage;
        this.backFrame = backFrame;
        UserDetails details = graph.getUser(id);
        this.userID = userID;
        if (details != null) {
            ID = id + "";
            name1 = details.getName();
            workPlace = details.getWork();
            homeTown = details.getHome();
            friends = graph.getUserEdges(details);
            String[] list = friends.split(":");
            name2 = list.length + " Friends";
            ImG = details.getImage();
            LIKE = details.getLikes();
        }else {
            System.out.println("error2");
        }
    }

    /**
     * Constructor for the userProfile class without JFrame.
     * @param id the id of the user profile to display
     * @param graph the graph to display
     */
    public userProfile(int id,MyGraph graph) {
        this.id = id;
        this.graph = graph;
        editBtn = new Edit(graph);
        imagee = new JPanel();
        bull = true;

        UserDetails details = graph.getUser(id);
        ID = id + "";
        name1 = details.getName();
        workPlace = details.getWork();
        homeTown = details.getHome();
        friends = graph.getUserEdges(details);
        String[] list = friends.split(":");
        name2 = list.length + " Friends";
        ImG = details.getImage();
        LIKE = details.getLikes();
    }

    /**
     * This method creates a GUI window and displays it with given panel, buttons and image based on the boolean value of "boo".
     * @param boo boolean that determines whether to display the user's home page or friend's page
     */
    public void display(boolean boo){
        // Create a new JFrame window with a title "Convo" and set its size to 800x600
        frame = new JFrame("Convo");
        frame.setSize(800, 600);
        // Center the JFrame window on the screen
        frame.setLocationRelativeTo(null);
        // Exit the window when the user clicks the "x" button
        editBtn.WindowExit(frame);

        // Create a new JPanel called "panel"
        JPanel panel;
        // If "boo" is true, set "panel" to display the "Home" panel created by the editBtn object.
        if(boo) {
            panel = editBtn.panel("Home");
        }else{
            // Otherwise, set "panel" to display the "Friends Page" panel created by the editBtn object.
            panel = editBtn.panel("Friends Page");
        }

        JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());

        // Add buttons to the top of "panel2" based on the boolean value of "boo".
        panel2.add(buttons(boo), BorderLayout.NORTH);

        // Create a new JPanel called "imagee" and add an image to it based on the boolean value of "boo".
        imagee = image(new JPanel(),boo);

        panel2.add(imagee, BorderLayout.CENTER);

        panel.add(panel2);

        // Add "panel" to the JFrame window.
        frame.add(panel);

        // Make the JFrame window visible.
        frame.setVisible(true);
    }

    /**
     * Creates a panel with two buttons and adds action listeners to them.
     * @param boo a boolean value indicating whether the user is on the "Home" page or "Friends Page".
     * @return a JPanel object containing two buttons and an action listener for each button.
     */
    public JPanel buttons(boolean boo){
        // Create two JButtons with respective names and apply styling
        but1 = new JButton(name1);
        JButton btn2 = new JButton(name2);
        editBtn.btn(but1,btn2);

        // Add action listener to first button
        but1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == but1) {
                        // Create new JFrame for profile page and set size and location
                        frame2 = new JFrame("Convo");
                        frame2.setSize(800, 600);
                        frame2.setLocationRelativeTo(null);

                        // Create panel for profile page and add necessary components
                        JPanel panel = new JPanel();
                        panel = editBtn.panel("Profile");
                        panel.add(btn1(boo));

                        // Add panel to JFrame and dispose of previous frame
                        frame2.add(panel);
                        frame.dispose();
                        frame2.setVisible(true);
                    }
                }
            });

        // Add action listener to second button
        btn2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == btn2){
                        if(boo) {
                            Friends friend = new Friends(id, frame, btn2,graph);
                            friend.run(true);
                        }else {
                            Friends friend = new Friends(id, homePage, btn2,frame,userID,graph);
                            friend.run(false);
                        }

                        frame.dispose();
                    }
                }
            });
        ButtonGroup group = new ButtonGroup();
        group.add(but1);
        group.add(btn2);

        JPanel btnpanel = new JPanel();
        btnpanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnpanel.add(but1);
        btnpanel.add(btn2);

        btnpanel.setBackground(Color.green);
        return btnpanel;
    }

    /**
     * Creates a JPanel which displays user details including name, ID, work place and hometown. If the boolean argument 'boo'
     * is true, then an "Edit" button and "Back" button are added to the panel. When the button is clicked, a registration form pops up which allows the user
     * to edit their information, and changes are saved when the "Ok" button is clicked. If the boolean argument 'boo' is false, then
     * the "Back" button is added to the panel, which takes the user back to the previous page when clicked.
     * @param boo a boolean value which determines whether or not an "Edit" button is displayed on the panel.
     * @return a JPanel which displays user details and a "Back" button or an "Edit" button.
     */
    public JPanel btn1(boolean boo){
        JPanel main = new JPanel();

        //Creating panels to hold text and labels
        JPanel textPanel = new JPanel();
        JPanel textPanel2 = new JPanel();
        JPanel textPanel3 = new JPanel();
        JPanel textPanel4 = new JPanel();
        JPanel textPanel5 = new JPanel();

        //Creating labels to hold user information
        JLabel textLabel = new JLabel("User ID => ");
        JLabel textLabel2 = new JLabel("User Name => ");
        JLabel textLabel3 = new JLabel("WorkPlace => ");
        JLabel textLabel4 = new JLabel("Home Town => ");

        JLabel textLabel5 = new JLabel(ID);
        JLabel textLabel6 = new JLabel(name1);
        JLabel textLabel7 = new JLabel(workPlace);
        JLabel textLabel8 = new JLabel(homeTown);

        //Setting font size for labels
        textLabel.setFont(new Font("Times New Roman", Font.PLAIN,30));
        textLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        textLabel3.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        textLabel4.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        textLabel5.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        textLabel6.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        textLabel7.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        textLabel8.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        //Adding labels to text panels
        textPanel.add(textLabel);
        textPanel.add(textLabel5);
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER , 90, 10));

        textPanel2.add(textLabel2);
        textPanel2.add(textLabel6);
        textPanel2.setLayout(new FlowLayout(FlowLayout.CENTER , 51, 10));

        textPanel3.add(textLabel3);
        textPanel3.add(textLabel7);
        textPanel3.setLayout(new FlowLayout(FlowLayout.CENTER , 52, 10));

        textPanel4.add(textLabel4);
        textPanel4.add(textLabel8);
        textPanel4.setLayout(new FlowLayout(FlowLayout.CENTER , 39, 10));

        //Creating "Back" button
        JButton btn1 = new JButton("Back");
        btn1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == btn1){
                        frame2.dispose();
                        frame.setVisible(true);
                    }
                }
            });

        //If boo is true, create "Edit" button and add it to panel
        if (boo){
            JButton btn2 = new JButton("Edit");
            editBtn.btn(btn1,btn2);

            //Action listener for "Edit" button
            btn2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getSource() == btn2){
                            JFrame frame1 = new JFrame("Convo");
                            JPanel panel = new JPanel();
                            JButton btn1 = new JButton("Back");
                            JButton btn2 = new JButton("Ok");
                            JTextField field2 = new JTextField();
                            field2.setText(name1);
                            JTextField field3 = new JTextField();
                            field3.setText(workPlace);
                            JTextField field4 = new JTextField();
                            field4.setText(homeTown);
                            panel = editBtn.registration(null,field2,field3,field4,frame1,btn1,btn2,"Edit",false);

                            btn1.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if(e.getSource() == btn1){
                                            frame1.dispose();
                                            frame2.setVisible(true);
                                        }
                                    }
                                });

                            btn2.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if(e.getSource()==btn2){

                                            char ch = editBtn.inputValidation(null,field2,field3,field4,false);
                                            JFrame frame3 = new JFrame("Error");
                                            JButton buttonn = new JButton("Try Again");
                                            JLabel text;

                                            buttonn.addActionListener(new ActionListener() {
                                                    @Override
                                                    public void actionPerformed(ActionEvent e) {
                                                        if(e.getSource() == buttonn){
                                                            frame3.dispose();
                                                            frame1.setVisible(true);
                                                        }
                                                    }
                                                });

                                            if(ch == 'a') {
                                                UserDetails details = graph.getUser(id);
                                                name1 = field2.getText();
                                                workPlace = field3.getText();
                                                homeTown = field4.getText();
                                                details.setName(name1);
                                                details.setWork(workPlace);
                                                details.setHome(homeTown);
                                                but1.setText(name1 + " Profile");
                                                textLabel6.setText(field2.getText());
                                                textLabel7.setText(field3.getText());
                                                textLabel8.setText(field4.getText());
                                                frame3.dispose();
                                                frame1.dispose();
                                            }else if (ch == 'b') {
                                                text = new JLabel("Use Different ID");
                                                editBtn.popUp(buttonn,frame3,text,250);
                                                frame3.setVisible(true);
                                            }else{
                                                text = new JLabel("Incorrect Information");
                                                editBtn.popUp(buttonn,frame3,text,250);
                                                frame3.setVisible(true);
                                            }
                                        }
                                    }
                                });

                            frame1.add(panel);
                            frame1.setLocationRelativeTo(null);
                            frame1.setVisible(true);
                        }
                    }
                });
            textPanel5.add(btn1);
            textPanel5.add(btn2);
        }else {
            editBtn.btn(btn1);
        }
        if (!boo){
            textPanel5.add(btn1);
        }

        textPanel5.setLayout(new FlowLayout(FlowLayout.CENTER , 39, 10));

        main.add(textPanel);
        main.add(textPanel2);
        main.add(textPanel3);
        main.add(textPanel4);
        main.add(textPanel5);

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        textPanel.setBackground(Color.CYAN);
        textPanel2.setBackground(Color.CYAN);
        textPanel3.setBackground(Color.CYAN);
        textPanel4.setBackground(Color.CYAN);
        textPanel5.setBackground(Color.GREEN);

        return main;
    }

    /**
     * This method generates a JPanel with images and controls for navigating and adding more images.
     * @param image The JPanel to which the images and controls are added.
     * @param boo A boolean flag indicating whether the JPanel should display the option to add more images.
     * @return A JPanel with images and controls.
     */
    public JPanel image(JPanel image,boolean boo){

        JButton Nothing;

        if(!ImG.equals("")){
            // If there are images, create a panel and add them to it
            image.setLayout(new BoxLayout(image , BoxLayout.Y_AXIS));
            String [] list = ImG.split(":");
            String [] love = LIKE.split(":");
            JPanel iii = new JPanel();
            iii.setLayout(new BoxLayout(iii,BoxLayout.Y_AXIS));
            iii.setBackground(Color.CYAN);
            iii.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            for (int i = 0; i < list.length ; i++) {
                String im = list[i];
                String l = love[i];
                iii.add(likeAndImage(im , l));
            }
            if(boo) {
                Nothing = new JButton("Add More Image");
                iii.add(addImage(Nothing));
            }
            image.add(iii);
        }else{
            // If there are no images, create a panel with a message
            image = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            if (boo) {
                // If requested, add a button to add an image
                Nothing = new JButton("Add Image");
                image.add(addImage(Nothing), gbc);
            } else {
                // Otherwise, display a message saying there are no posts yet
                JLabel lab = new JLabel("No Posts Yet");
                lab.setHorizontalAlignment(SwingConstants.CENTER);
                lab.setVerticalAlignment(SwingConstants.CENTER);
                lab.setFont(new Font("Serif", Font.PLAIN, 30));
                image.add(lab, gbc);
            }
        }
        image.setBackground(Color.CYAN);
        JScrollPane scrollPanel = new JScrollPane(image);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(scrollPanel,BorderLayout.CENTER);

        if (!boo) {
            // If buttons are not requested, add a back button and a home button
            JButton backButton = new JButton("Back");
            JButton Home = new JButton("Go Home");
            editBtn.btn(backButton,Home);
            JPanel p = new JPanel();

            backButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // When the back button is pressed, close the current frame and open the previous one
                        if(e.getSource() == backButton) {
                            frame.dispose();
                            backFrame.setVisible(true);
                        }
                    }
                });

            Home.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // When the home button is pressed, close the current frame and open the home page
                        if(e.getSource() == Home) {
                            frame.dispose();
                            homePage.setVisible(true);
                        }
                    }
                });
            p.setBackground(Color.PINK);

            p.add(backButton);
            p.add(Home);

            panel.add(p, BorderLayout.SOUTH);
        }
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        scrollPanel.setBorder(border);
        panel.setBorder(border);

        return panel;
    }

    /**
     * This method creates a JPanel containing an image and a like button.
     * @param name The name of the image.
     * @param heart The initial number of likes for the image.
     * @return A JPanel containing the image and like button.
     */
    public JPanel likeAndImage(String name , String heart){
        // Creating a new Photo object with the given name.
        Photo image = new Photo(name);
        image.setBackground(Color.CYAN);
        image.setPreferredSize(new Dimension(700, 420));

        // Creating a new JPanel and adding the image to it.
        JPanel imagePanel  = new JPanel();
        imagePanel.add(image);
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePanel.setBackground(Color.cyan);

        // Creating a new ImageIcon for the like button.
        ImageIcon icon = new ImageIcon("../Convo app/images/like.png");
        Image img = icon.getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(img);
        JButton like = new JButton(heart,scaledIcon);
        like.setFont(new Font(heart, Font.PLAIN, 24));
        like.setBackground(Color.RED);
        like.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Adding an ActionListener to the like button to update the like count when clicked.
        like.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == like) {
                        if(bull) {
                            //if user like first time then add 1
                            like.setText(editBtn.likes(id, name, 1));
                            bull = false;
                        }else {
                            //if user like second time then subtract 1
                            like.setText(editBtn.likes(id, name, -1));
                            bull = true;
                        }
                    }
                }
            });
        // Creating a new JPanel to hold the image and like button, and returning it.
        JPanel images = new JPanel();
        images.setLayout(new BoxLayout(images,BoxLayout.Y_AXIS));
        images.add(imagePanel);
        images.add(like);
        images.setBackground(Color.CYAN);
        images.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return images;
    }

    /**
     * This method adds an ActionListener to the given JButton. When the button is clicked,
     * it performs the following actions:
     * Gets the UserDetails of the given id from graph.
     * Updates the image and likes fields of the UserDetails object.
     * Changes the background color of the button to orange and disables it.
     * Removes all components from the imagee JPanel and adds a new image JPanel to it.
     * @param btn the JButton to which an ActionListener is to be added.
     */
    public void actionListener(JButton btn){
        btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == btn) {
                        UserDetails details = graph.getUser(id);
                        ImG = details.getImage() + btn.getText() + ".jpg" + ":";
                        LIKE = details.getLikes() + 0 + ":";
                        details.setImage(ImG);
                        details.setLikes(LIKE);
                        btn.setBackground(Color.orange);

                        btn.setEnabled(false);

                        imagee.removeAll();

                        imagee.add(image(new JPanel(), true));
                    }
                }
            });

    }

    /**
     * This method creates a JPanel with a given JButton and returns it.
     * When the JButton is clicked, it opens a new JFrame and displays a list of items.
     * @param Nothing the JButton to be added to the JPanel
     * @return a JPanel with a given JButton
     */
    public JPanel addImage(JButton Nothing){
        JPanel image = new JPanel();
        editBtn.btn(Nothing);
        image.add(Nothing);
        image.setBackground(Color.cyan);

        Nothing.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == Nothing) {
                        String [] imgName = {"You","Joke","Meme","Bake","Ball","Bead","Bike","Boat","Book","Calm","Camp","Cook",
                                "Dog","Draw","Drum","Felt","Film","Fish","Fizz","Food","Game","Golf","Gym","Hike","Hula","Dart",
                                "Kite","Knot","LARP","Love","Lyft","Moon","Mosh","Park","Puzz","Read","Ride","Rink","Run","Dive",
                                "Sail","Shop","Sing","Ski","Skip","Sled","Surf","Swim","Toss","Tree","Tuba","Vlog","Walk","Wine","Yoga"};

                        JFrame frame1 = new JFrame("Convo");
                        JButton button = new JButton("Back");
                        JPanel panel = new JPanel();
                        JPanel panel2 = new JPanel();

                        frame1.setSize(500, 400);
                        frame1.setLocationRelativeTo(null);

                        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                        panel2.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
                        panel2.setPreferredSize(new Dimension(450, 850));
                        panel2.setBackground(Color.PINK);

                        editBtn.btn(button);

                        ButtonGroup group = new ButtonGroup();
                        JButton button1;
                        for (int i = 0; i < imgName.length; i++) {
                            button1 = new JButton(imgName[i]);
                            editBtn.btn3(button1);
                            button1.setPreferredSize(new Dimension(100, 50));
                            group.add(button1);
                            panel2.add(button1);
                            actionListener(button1);
                        }

                        JScrollPane scrollPanel1 = new JScrollPane(panel2);
                        scrollPanel1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                        scrollPanel1.setPreferredSize(new Dimension(400, 300));
                        panel.add(scrollPanel1);
                        panel.add(Box.createVerticalStrut(10));
                        panel.add(button);
                        panel.add(Box.createVerticalStrut(20));
                        panel.setBackground(Color.cyan);

                        button.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    frame1.dispose();
                                    frame.setVisible(true);
                                }
                            });

                        frame1.add(panel);
                        frame1.setVisible(true);
                    }
                }
            });
        return image;
    }

}
