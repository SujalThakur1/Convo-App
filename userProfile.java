import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import java.util.Stack;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
    public void display(boolean boo) {
        // Create main frame
        frame = new JFrame("Convo");
        frame.setSize(900, 730);
        frame.setLocationRelativeTo(null);
        editBtn.WindowExit(frame);

        // Create main panel with gradient background
        JPanel mainPanel = editBtn.panel("");
        mainPanel.setLayout(new BorderLayout());

        // Create navigation bar panel - now transparent
        JPanel navBar = new JPanel();
        navBar.setLayout(new GridLayout(1, 3));
        navBar.setOpaque(false);  // Make nav bar transparent
        navBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, editBtn.accentColor));

        // Create and style buttons
        but1 = new JButton(name1);
        JButton btn2 = new JButton(name2);
        editBtn.btn(but1, btn2);

        // Apply modern button styling
        for (JButton btn : new JButton[]{but1, btn2}) {
            btn.setFont(new Font("Poppins", Font.BOLD, 16));
            btn.setForeground(editBtn.textColor);
            btn.setOpaque(false);
            btn.setContentAreaFilled(false);
            btn.setBorderPainted(false);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Add hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setForeground(editBtn.accentColor);
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setForeground(editBtn.textColor);
                }
            });
        }

        // Add button action listeners
        but1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == but1) {
                    frame2 = new JFrame("Convo");
                    frame2.setSize(900, 730);
                    frame2.setLocationRelativeTo(null);

                    JPanel panel = editBtn.panel("Profile");
                    panel.add(btn1(boo));
                    panel.setOpaque(false);

                    frame2.add(panel);
                    frame2.setVisible(false);

                    // Use invokeLater to handle frame switching
                    SwingUtilities.invokeLater(() -> {
                        frame2.setVisible(true);
                        frame.dispose();
                    });
                }
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn2) {
                    if (boo) {
                        Friends friend = new Friends(id, frame, btn2, graph);
                        friend.run(true);
                    } else {
                        Friends friend = new Friends(id, homePage, btn2, frame, userID, graph);
                        friend.run(false);
                    }
                    frame.dispose();
                }
            }
        });

        // Left section - name1 button
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);  // Make panel transparent
        leftPanel.add(but1);

        // Center section - title
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);  // Make panel transparent
        JLabel titleLabel = new JLabel(boo ? "Home" : "Friends Page");
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        if (boo)
            titleLabel.setPreferredSize(new Dimension(75, 50));
        else
            titleLabel.setPreferredSize(new Dimension(175, 50));

        titleLabel.setForeground(editBtn.textColor);
        centerPanel.add(titleLabel);

        // Right section - name2 button
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);  // Make panel transparent
        rightPanel.add(btn2);

        // Add panels to nav bar
        navBar.add(leftPanel);
        navBar.add(centerPanel);
        navBar.add(rightPanel);

        // Create content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Create and add image panel
        imagee = image(new JPanel(), boo);
        imagee.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(imagee);

        if (!boo) {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);

            JButton backButton = editBtn.createStyledButton("Back");
            JButton homeButton = editBtn.createStyledButton("Home");

            backButton.addActionListener(e -> {
                if(e.getSource() == backButton) {
                    frame.dispose();
                    backFrame.setVisible(true);
                }
            });

            homeButton.addActionListener(e -> {
                if(e.getSource() == homeButton) {
                    frame.dispose();
                    homePage.setVisible(true);
                }
            });

            buttonPanel.add(backButton);
            buttonPanel.add(Box.createHorizontalStrut(20));
            buttonPanel.add(homeButton);

            mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        }

        // Add components to main panel
        mainPanel.add(navBar, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);
    }
    /**
     * Creates a JPanel which displays user details including name, ID, work place and hometown. If the boolean argument 'boo'
     * is true, then an "Edit" button and "Back" button are added to the panel. When the button is clicked, a registration form pops up which allows the user
     * to edit their information, and changes are saved when the "Ok" button is clicked. If the boolean argument 'boo' is false, then
     * the "Back" button is added to the panel, which takes the user back to the previous page when clicked.
     * @param boo a boolean value which determines whether or not an "Edit" button is displayed on the panel.
     * @return a JPanel which displays user details and a "Back" button or an "Edit" button.
     */
    public JPanel btn1(boolean boo) {
        JPanel main = new JPanel();
        main.setBackground(new Color(0, 0, 0, 0));
        main.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        // Create user details panel
        JPanel detailsPanel = createUserDetailsPanel();
        main.add(detailsPanel);
        main.add(Box.createVerticalStrut(20));

        // Create buttons panel
        JPanel buttonsPanel = createButtonsPanel(boo);

        main.add(buttonsPanel);
        main.setOpaque(false);

        return main;
    }

    private JPanel createUserDetailsPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(editBtn.backgroundColor);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(editBtn.accentColor, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));

        // Add user details with consistent styling
        addDetailField(panel, "User ID", ID);
        addDetailField(panel, "User Name", name1);
        addDetailField(panel, "WorkPlace", workPlace);
        addDetailField(panel, "Home Town", homeTown);

        return panel;
    }

    private void addDetailField(JPanel panel, String labelText, String value) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 180, 20));
        fieldPanel.setBackground(editBtn.backgroundColor);

        JLabel label = new JLabel(labelText + ":");
        label.setFont(new Font("Poppins", Font.BOLD, 16));
        label.setForeground(editBtn.textColor);
        label.setPreferredSize(new Dimension(120, 30));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Poppins", Font.PLAIN, 16));
        valueLabel.setForeground(Color.WHITE);

        fieldPanel.add(label);
        fieldPanel.add(valueLabel);
        panel.add(fieldPanel);
    }

    private JPanel createButtonsPanel(boolean boo) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        // Use FlowLayout, with centered alignment and reasonable gaps between buttons
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 10));  // Adjust the gap as needed

        // Create Back button
        JButton backButton = editBtn.createStyledButton("Back");
        backButton.setPreferredSize(new Dimension(150, 50));
        backButton.addActionListener(e -> {
            if (e.getSource() == backButton) {
                backButton.setOpaque(false);
                frame2.dispose();
                frame.setVisible(true);
            }
        });
        buttonsPanel.add(backButton);

        // Optionally add Edit button if 'boo' is true
        if (boo) {
            JButton editButton = editBtn.createStyledButton("Edit");
            editButton.setPreferredSize(new Dimension(150, 50));
            editButton.addActionListener(e -> {
                if (e.getSource() == editButton) {
                    showEditDialog();
                }
            });
            buttonsPanel.add(editButton);
        }

        return buttonsPanel;
    }



    private void showEditDialog() {
        JFrame editFrame = new JFrame("Edit Profile");
        JTextField nameField = editBtn.createStyledTextField();
        nameField.setText(name1);
        JTextField workField = editBtn.createStyledTextField();
        workField.setText(workPlace);
        JTextField homeField = editBtn.createStyledTextField();
        homeField.setText(homeTown);

        JButton saveButton = editBtn.createStyledButton("Save");
        JButton cancelButton = editBtn.createStyledButton("Cancel");

        saveButton.addActionListener(e -> {
            UserDetails details = graph.getUser(id);
            name1 = nameField.getText();
            workPlace = workField.getText();
            homeTown = homeField.getText();

            details.setName(name1);
            details.setWork(workPlace);
            details.setHome(homeTown);

            but1.setText(name1 + " Profile");

            frame2.getContentPane().removeAll();
            JPanel newPanel = editBtn.panel("Profile");
            newPanel.add(btn1(true));  // This creates fresh panel with new data
            newPanel.setOpaque(false);
            frame2.add(newPanel);

            frame2.revalidate();
            frame2.repaint();
            frame2.setVisible(true);
            editFrame.dispose();
        });

        cancelButton.addActionListener(e -> editFrame.dispose());

        JPanel panel = editBtn.registration(null, nameField, workField, homeField,
                editFrame, cancelButton, saveButton, "Edit Profile", false);

        editFrame.add(panel);
        editFrame.setUndecorated(true);
        editFrame.setLocationRelativeTo(null);
        editFrame.setVisible(true);
    }

    /**
     * This method generates a JPanel with images and controls for navigating and adding more images.
     * @param image The JPanel to which the images and controls are added.
     * @param boo A boolean flag indicating whether the JPanel should display the option to add more images.
     * @return A JPanel with images and controls.
     */
    public JPanel image(JPanel image, boolean boo) {
        JButton addImageButton;

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(editBtn.backgroundColor);

        if(!ImG.equals("")) {
            // If there are images, create a panel and add them to it
            image.setLayout(new BoxLayout(image, BoxLayout.Y_AXIS));
            String[] list = ImG.split(":");
            String[] love = LIKE.split(":");

            JPanel imagesPanel = new JPanel();
            imagesPanel.setLayout(new BoxLayout(imagesPanel, BoxLayout.Y_AXIS));
            imagesPanel.setBackground(editBtn.backgroundColor);
            imagesPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            for (int i = 0; i < list.length; i++) {
                String im = list[i];
                String l = love[i];
                imagesPanel.add(likeAndImage(im, l));
                if (i < list.length - 1) {
                    imagesPanel.add(Box.createVerticalStrut(20));
                }
            }

            if(boo) {
                addImageButton = editBtn.createStyledButton("Add More Images");
                addImageButton.setPreferredSize(new Dimension(150, 50));
                JPanel buttonPanel = new JPanel();
                buttonPanel.setBackground(editBtn.backgroundColor);
                buttonPanel.add(addImage(addImageButton));
                imagesPanel.add(Box.createVerticalStrut(20));
                imagesPanel.add(buttonPanel);
            }

            image.add(imagesPanel);
        } else {
            // If there are no images, create a panel with a message
            image = new JPanel();
            image.setLayout(new BoxLayout(image, BoxLayout.Y_AXIS));
            image.setBackground(editBtn.backgroundColor);
            image.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

            if (boo) {
                // If requested, add a button to add an image
                addImageButton = editBtn.createStyledButton("Add Image");
                addImageButton.setPreferredSize(new Dimension(150, 50));
                JPanel buttonPanel = new JPanel();
                buttonPanel.setBackground(editBtn.backgroundColor);
                buttonPanel.add(addImage(addImageButton));
                image.add(buttonPanel);
            } else {
                // Otherwise, display a message saying there are no posts yet
                JLabel lab = new JLabel("No Posts Yet");
                lab.setFont(new Font("Montserrat", Font.BOLD, 24));
                lab.setForeground(editBtn.textColor);
                lab.setAlignmentX(Component.CENTER_ALIGNMENT);
                image.add(lab);
            }
        }

        JScrollPane scrollPanel = new JScrollPane(image);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPanel.setBorder(BorderFactory.createEmptyBorder());
        scrollPanel.getViewport().setBackground(editBtn.backgroundColor);

        mainPanel.add(scrollPanel, BorderLayout.CENTER);

        mainPanel.setBorder(BorderFactory.createLineBorder(editBtn.accentColor, 2));

        return mainPanel;
    }

    public JPanel likeAndImage(String name, String heart) {
        Photo image = new Photo(name);
        image.setBackground(editBtn.backgroundColor);
        image.setPreferredSize(new Dimension(700, 420));

        JPanel imagePanel = new JPanel();
        imagePanel.add(image);
        imagePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imagePanel.setBackground(editBtn.backgroundColor);

        try {
            ImageIcon icon = new ImageIcon("./images/like.png");
            Image img = icon.getImage().getScaledInstance(24, 20, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);

            ImageIcon icon1 = new ImageIcon("./images/unlike.png");
            Image img1 = icon1.getImage().getScaledInstance(30, 36, Image.SCALE_SMOOTH);
            ImageIcon heartIcon = new ImageIcon(img1);

            JButton likeButton = editBtn.createStyledButton(heart);

            UserDetails details = graph.getUser(id);
            String[] imG = details.getImage().split(":");
            String[] lik = details.getLikes().split(":");
            int likee = 0;
            for (int i = 0; i < imG.length; i++) {
                if (imG[i].equals(name)) {
                    likee = Integer.parseInt(lik[i]);
                }
            }

            if(!bull || likee == 0) {
                likeButton.setIcon(heartIcon);
            }else{
                likeButton.setIcon(scaledIcon);
            }

            likeButton.setFont(new Font("Montserrat", Font.BOLD, 18));
            likeButton.setIconTextGap(10);
            likeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

            likeButton.addActionListener(e -> {
                if(bull) {
                    likeButton.setText(editBtn.likes(id, name, 1));
                    likeButton.setIcon(scaledIcon);
                    bull = false;
                } else {
                    likeButton.setText(editBtn.likes(id, name, -1));
                    likeButton.setIcon(heartIcon);
                    bull = true;
                }
            });

            JPanel containerPanel = new JPanel();
            containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));
            containerPanel.setBackground(editBtn.backgroundColor);
            containerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            containerPanel.add(imagePanel);
            containerPanel.add(Box.createVerticalStrut(10));
            containerPanel.add(likeButton);

            return containerPanel;

        } catch (Exception e) {
            e.printStackTrace();
            return imagePanel;
        }
    }

    public void actionListener(JButton btn) {
        btn.addActionListener(e -> {
            if(e.getSource() == btn) {
                UserDetails details = graph.getUser(id);
                ImG = details.getImage() + btn.getText() + ".jpg" + ":";
                LIKE = details.getLikes() + 0 + ":";
                details.setImage(ImG);
                details.setLikes(LIKE);
                btn.setBackground(editBtn.accentColor);
                btn.setEnabled(false);

                imagee.removeAll();
                imagee.add(image(new JPanel(), true));
                imagee.revalidate();
                imagee.repaint();
            }
        });
    }

    public JPanel addImage(JButton addButton) {
        // Pre-load and cache all images
        Map<String, ImageIcon> imageCache = new HashMap<>();
        String[] imgName = {"Boat","Book","Bike","Game","Joke","Meme","Bake","Ball","Bead","Calm","Camp","Cook",
                "Dog","Draw","Drum","Felt","Film","Fish","Fizz","Food","Golf","Gym","Hike","Hula","Dart",
                "Kite","Knot","LARP","Love","Lyft","Moon","Mosh","Park","Puzz","Read","Ride","Rink","Run","Dive",
                "Sail","Shop","Sing","Ski","Skip","Sled","Surf","Swim","Toss","Tree","Tuba","Vlog","Walk","Wine","Yoga"};

        // Pre-load images in background thread
        new Thread(() -> {
            for (String s : imgName) {
                try {
                    ImageIcon icon = new ImageIcon("./images/" + s + ".jpg");
                    Image img = icon.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
                    imageCache.put(s, new ImageIcon(img));
                } catch (Exception ex) {
                    System.out.println("Could not load image: " + s + ".jpg");
                }
            }
        }).start();

        JPanel panel = new JPanel();
        panel.setBackground(editBtn.backgroundColor);
        panel.add(addButton);

        addButton.addActionListener(e -> {
            if(e.getSource() == addButton) {
                JFrame imageFrame = new JFrame("Choose Image");
                imageFrame.setUndecorated(true);
                imageFrame.getRootPane().setBorder(BorderFactory.createLineBorder(editBtn.accentColor, 2));

                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(new BorderLayout());
                mainPanel.setBackground(editBtn.backgroundColor);

                JPanel gridPanel = new JPanel(new GridLayout(0, 1, 54, 10));
                gridPanel.setBackground(editBtn.backgroundColor);
                gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                ButtonGroup group = new ButtonGroup();
                for (String s : imgName) {
                    JButton imageButton = new JButton();
                    imageButton.setText(s);
                    ImageIcon cachedIcon = imageCache.get(s);
                    if (cachedIcon != null) {
                        imageButton.setIcon(cachedIcon);
                    } else {
                        try {
                            ImageIcon icon = new ImageIcon("./images/" + s + ".jpg");
                            Image img = icon.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
                            imageButton.setIcon(new ImageIcon(img));
                        } catch (Exception ex) {
                            System.out.println("Could not load image: " + s + ".jpg");
                            continue;
                        }
                    }
                    imageButton.setName(s);
                    imageButton.setPreferredSize(new Dimension(400, 260));
                    imageButton.setBorderPainted(false);
                    imageButton.setContentAreaFilled(false);
                    imageButton.setFocusPainted(false);
                    group.add(imageButton);
                    gridPanel.add(imageButton);
                    actionListener(imageButton);
                }

                JScrollPane scrollPane = new JScrollPane(gridPanel);
                scrollPane.setBorder(null);
                scrollPane.getVerticalScrollBar().setUnitIncrement(16);

                JButton backButton = editBtn.createStyledButton("Back");
                backButton.addActionListener(event -> {
                    imageFrame.dispose();
                    frame.setVisible(true);
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.setBackground(editBtn.backgroundColor);
                buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                buttonPanel.add(backButton);

                mainPanel.add(scrollPane, BorderLayout.CENTER);
                mainPanel.add(buttonPanel, BorderLayout.SOUTH);

                imageFrame.add(mainPanel);
                imageFrame.setSize(500, 600);
                imageFrame.setLocationRelativeTo(null);
                imageFrame.setVisible(true);
            }
        });

        return panel;
    }

}
