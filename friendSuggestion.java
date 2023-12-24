import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A class for displaying friend suggestions to a user.
 */
public class friendSuggestion {
    private JFrame frame; // frame to hold the suggestion panel
    private Edit editBtn; // a reference to the Edit class for styling buttons and panels
    private boolean check; // boolean flag to check if a friend has been added
    private int id; // id of the current user
    private MyGraph graph; // a reference to the MyGraph class for retrieving user details

    /**
     * Constructor for FriendSuggestion class.
     * @param ID The id of the current user.
     * @param graph The MyGraph object used to retrieve user details.
     */
    public friendSuggestion(int ID,MyGraph graph){
        this.graph = graph;
        editBtn = new Edit();
        this.id = ID;
        check = false;
    }

    /**
     * Method to display the suggestion window.
     */
    public void suggestion(){
        frame = new JFrame("Convo");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        editBtn.WindowExit(frame);
        mainPanel();
        frame.setVisible(true);
    }

    /**
     * Method to create the main panel of the suggestion window.
     */
    public void mainPanel(){
        JPanel MainPanel = editBtn.panel("Suggestion");

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        ArrayList<UserDetails> details = graph.getUsersByPlace(id);
        if (details == null){
            // if user has no friends in the same place, show all users
            for (UserDetails detail: graph.getUsers()) {
                if(id != detail.getID()){
                    panel.add(Stackbtn(detail.getName() , detail.getID() , detail.getWork(), detail.getHome()));
                }
            }
        }else{
            // if user has friends in the same place, show only friends
            if(details != null) {
                for (UserDetails det : details) {
                    panel.add(GraphBtn(det.getName(), det.getID(), det.getWork(), det.getHome()));
                }
            }
        }

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JButton button = new JButton("OK");
        editBtn.btn(button);

        button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == button){
                        userProfile profile = new userProfile(id,graph);
                        if(check) {
                            // if user has selected at least one friend, display user profile
                            frame.dispose();
                            profile.display(true);
                        }else {
                            // if user has not selected any friend, show error message
                            JFrame frame1 = new JFrame("Convo");
                            JButton errorButton = new JButton("Try Again");
                            JLabel errorMsg = new JLabel("Please Add At Least One Friend To Proceed");
                            editBtn.popUp(errorButton,frame1,errorMsg,400);
                            errorButton.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if(e.getSource() == errorButton){
                                            frame1.dispose();
                                            frame.setVisible(true);
                                        }
                                    }
                                });
                            frame1.setVisible(true);
                        }
                    }
                }
            });

        MainPanel.add(scrollPanel);
        MainPanel.add(button);
        frame.add(MainPanel);
    }

    /**
     * Method to create a panel for each friend.
     * @param name The name of the friend.
     * @param ID The ID of the friend.
     * @param work The workplace of the friend.
     * @param home The homeplace of the friend.
     * @return A JPanel object representing the friend's panel.
     */
    public JPanel GraphBtn(String name , int ID, String work, String home){
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == addBtn) {
                        graph.Friend(id,ID,true);
                        addBtn.setEnabled(false);
                        addBtn.setText("Added");
                        check = true;
                    }
                }
            });
        return friends(name,work,home,addBtn);
    }

    /**
     *This method creates a JPanel for a friend with a given name, work, and home address,
     * along with an "Add" button that triggers an ActionListener.
     * @param name The name of the friend.
     * @param ID The ID of the friend.
     * @param work The work address of the friend.
     * @param home The home address of the friend.
     * @return A JPanel for the friend with a name, work address, home address, and an "Add" button.
     */
    public JPanel Stackbtn(String name , int ID, String work, String home){
        JButton addBtn = new JButton("Add");
        addBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(e.getSource() == addBtn) {
                        actionListener(ID,addBtn,true);
                    }
                }
            });
        return friends(name,work,home,addBtn);
    }

    /**
     *
     * along with an "Inspect" button that triggers an ActionListener.
     * @param name The name of the friend.
     * @param work The work address of the friend.
     * @param home The home address of the friend.
     * @param addBtn The "Add" button for the friend.
     * @return a JPanel containing friend's details and buttons
     */
    public JPanel friends(String name, String work, String home,JButton addBtn){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("Serif", Font.PLAIN, 30));

        JButton InspectBtn = new JButton("Inspect");
        editBtn.btnSmall(addBtn,InspectBtn);

        InspectBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == InspectBtn) {
                        inspect(name,work,home,frame);
                    }
                }
            });

        GridBagConstraints NameContainer = new GridBagConstraints();
        NameContainer.gridx = 0;
        NameContainer.gridy = 0;
        NameContainer.weightx = 1.0;
        NameContainer.fill = GridBagConstraints.HORIZONTAL;
        NameContainer.anchor = GridBagConstraints.WEST;
        NameContainer.insets = new Insets(10, 100, 10, 10);

        GridBagConstraints BtnContainer = new GridBagConstraints();
        BtnContainer.gridx = 1;
        BtnContainer.gridy = 0;
        BtnContainer.weightx = 0.0;
        BtnContainer.insets = new Insets(10, 10, 10, 50);

        GridBagConstraints Btn2Container = new GridBagConstraints();
        Btn2Container.gridx = 2;
        Btn2Container.gridy = 0;
        Btn2Container.weightx = 0.0;
        Btn2Container.insets = new Insets(10, 10, 10, 100);

        panel.add(nameLbl, NameContainer);
        panel.add(InspectBtn, BtnContainer);
        panel.add(addBtn, Btn2Container);
        panel.setBackground(Color.cyan);

        return panel;
    }

    /**
     * Displays friend's details in a popup window
     * @param name the name of the friend
     * @param work the workplace of the friend
     * @param home the home address of the friend
     * @param frame the JFrame to display the popup window
     */
    public void inspect(String name , String work, String home, JFrame frame){
        String Name = "User Name => " + name;
        String Work = "User WorkPlace => " + work;
        String Home = "User HomePlace => " + home;

        JFrame frame2 = new JFrame("Convo");
        JButton btn = new JButton("Back");
        JLabel label = new JLabel(Name);
        JLabel label2 = new JLabel(Work);
        JLabel label3 = new JLabel(Home);

        editBtn.PopUpFrame(frame2, 350);
        editBtn.btn(btn);
        editBtn.PopUpText(label);
        editBtn.PopUpText(label2);
        editBtn.PopUpText(label3);

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.add(Box.createVerticalGlue());
        panel1.add(label);
        panel1.add(label2);
        panel1.add(label3);
        panel1.add(Box.createVerticalStrut(10));
        panel1.add(btn);

        btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == btn) {
                        frame2.dispose();
                        frame.setVisible(true);
                    }
                }
            });

        panel1.add(Box.createVerticalGlue());
        panel1.setBackground(Color.cyan);

        frame2.add(panel1);
        frame2.setVisible(true);
    }

    /**
     * This method is an action listener which updates the user's friends list by adding or removing a friend.
     * @param ID the ID of the friend being added or removed
     * @param addBtn the button that triggered the action
     * @param bull a boolean that determines whether the friend is being added (true) or removed (false)
     * @return the updated size of the user's friend list
     */

    public int actionListener(int ID, JButton addBtn, boolean bull) {
        int size;
        if (bull) {
            // adds a friend to the user's friend list
            size = graph.Friend(id, ID, true);
        } else {
            // removes a friend from the user's friend list
            size = graph.Friend(id, ID, false);
        }

        if (bull) {
            // sets the text of the add button to "Added" if a friend is added
            addBtn.setText("Added");
        } else {
            // sets the text of the add button to "Removed" if a friend is removed
            addBtn.setText("Removed");
        }

        // sets check to true
        check = true;

        // sets the background color of the add button to red
        addBtn.setBackground(Color.red);

        // disables the add button
        addBtn.setEnabled(false);

        // returns the updated size of the user's friend list
        return size;
    }

}
