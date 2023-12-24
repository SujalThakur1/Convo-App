import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ConcurrentModificationException;

/**
 * A class to provide methods to edit GUI components
 */
public class Edit {
    private MyGraph graph;

    /**
     *  Constructs an instance of Edit with a MyGraph object.
     *  @param graph the MyGraph object to work with.
     */
    public Edit(MyGraph graph){
        this.graph = graph;
    }

    /**
     * Constructs an instance of Edit with no arguments.
     */
    public Edit(){

    }

    /**
     * Changes the font, background color and alignment of a button to a specified format,
     * and sets its preferred size.
     * @param btn1 the first button to modify.
     * @param btn2 the second button to modify.
     */
    public void btn(JButton btn1,JButton btn2){
        btnColorFont(btn1 , btn2);
        btn1.setPreferredSize(new Dimension(300,50));
        btn2.setPreferredSize(new Dimension(300, 50));
    }

    /**
     * Changes the font, background color and alignment of a button to a specified format,
     * and sets its preferred size to (300,50).
     * @param btn1 the button to modify.
     */
    public void btn(JButton btn1){
        btn2(btn1);
        btn1.setPreferredSize(new Dimension(300,50));
        btn1.setAlignmentX(Component.CENTER_ALIGNMENT);

    }

    /**
     * This method sets the font, background color and horizontal alignment of a JButton.
     * @param btn1 The JButton whose properties are being set.
     */
    public void btn2(JButton btn1){
        btn1.setFont(new Font("Arial Black", Font.PLAIN, 20));
        btn1.setBackground(Color.yellow);
        btn1.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     *This method sets the font, background color, horizontal alignment and preferred size of a JButton by calling the btn2 method.
     * @param btn1 The first JButton whose properties are being set.
     */
    public void btn3(JButton btn1){
        btn2(btn1);
        btn1.setPreferredSize(new Dimension(150,50));
    }

    /**
     * This method sets the font, background color and preferred size of two JButtons and adds them to the same button group.
     * @param btn1 The first JButton whose properties are being set.
     * @param btn2 The second JButton whose properties are being set.
     */
    public void btnSmall(JButton btn1,JButton btn2){
        btnColorFont(btn1 , btn2);
        btn1.setPreferredSize(new Dimension(150,50));
        btn2.setPreferredSize(new Dimension(150, 50));
    }

    /**
     * This method sets the font, background color and adds two JButtons to the same button group.
     * @param btn1 The first JButton whose properties are being set.
     * @param btn2 The second JButton whose properties are being set.
     */
    public void btnColorFont(JButton btn1, JButton btn2){
        ButtonGroup group = new ButtonGroup();
        group.add(btn1);
        group.add(btn2);
        btn1.setFont(new Font("Arial Black", Font.PLAIN, 20));
        btn2.setFont(new Font("Arial Black", Font.PLAIN, 20));
        btn1.setBackground(Color.yellow);
        btn2.setBackground(Color.yellow);
    }

    /**
     * This method creates a pop-up window with a "Try Again" button, a JFrame and a JLabel.
     *
     * @param label The label in the pop-up window.
     */
    public void popUpRandom(JLabel label){
        JButton btn = new JButton("Try Again");
        JFrame frame1 = new JFrame("Error");
        popUp(btn,frame1,label,300);

        btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame1.dispose();
                }
            });

        frame1.setVisible(true);
    }

    /**
     * Display a pop-up with a given JButton, JFrame, JLabel, and width
     *
     * @param btn1 - the JButton to display on the pop-up
     *
     * @param frame2 - the JFrame to display the pop-up on
     *
     * @param text - the JLabel to display on the pop-up
     *
     * @param width - the width of the pop-up frame
     */
    public void popUp(JButton btn1, JFrame frame2 , JLabel text, int width){
        PopUpFrame(frame2,width);
        PopUpText(text);
        btn(btn1);

        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(text);
        panel.add(Box.createVerticalStrut(10));
        panel.add(btn1);

        panel.add(Box.createVerticalGlue());
        panel.setBackground(Color.cyan);

        frame2.add(panel);
    }

    /**
     * Set the size and location of a given JFrame for a pop-up
     * @param frame - the JFrame to set the size and location of frame
     * @param width - the desired width of the JFrame
     */
    public void PopUpFrame(JFrame frame ,int width){
        frame.setSize(width, 200);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Set the font and alignment of a given JLabel for a pop-up
     * @param text - the JLabel to set the font and alignment of
     */
    public void PopUpText(JLabel text){
        text.setFont(new Font("Serif", Font.PLAIN, 20));
        text.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * Create a registration panel with a given set of JTextFields, JButtons, JFrame, and other settings
     * @param textField2 - the first JTextField to display
     * @param textField3 - the second JTextField to display
     * @param textField4 - the third JTextField to display
     * @param textField5 - the fourth JTextField to display
     * @param frame3 - the JFrame to display the registration panel on
     * @param btn3 - the first JButton to display on the registration panel
     * @param btn4 - the second JButton to display on the registration paneL
     * @param name - the text to display on the registration panel as a label
     * @param b - a boolean indicating whether or not to display a user ID JTextField
     * @return the created registration panel as a JPanel
     */
    public JPanel registration(JTextField textField2,JTextField textField3,JTextField textField4,
    JTextField textField5,JFrame frame3,JButton btn3,JButton btn4,String name,boolean b){
        frame3.setSize(600, 500);

        JPanel panel = new JPanel();
        panel.removeAll();

        JLabel label = new JLabel(name);
        label.setFont(new Font("Serif", Font.PLAIN, 40));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSmall(btn3,btn4);

        JPanel button = new JPanel();
        button.setBackground(Color.PINK);
        button.add(btn3);
        button.add(btn4);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.PINK);
        panel.add(label);
        panel.add(Registration(textField2,textField3,textField4,textField5,b));
        panel.add(button);
        frame3.add(panel);
        frame3.setLocationRelativeTo(null);
        return panel;
    }

    /**
     * Generates a JPanel that contains text fields for registration details.
     * @param textField2 The user ID text field.
     * @param textField3 The user name text field.
     * @param textField4 The workplace text field.
     * @param textField5 The hometown text field.
     * @param b A boolean that specifies if the user ID field should be shown.
     * @return A JPanel that contains the registration details text fields
     */
    public JPanel Registration(JTextField textField2,JTextField textField3,
    JTextField textField4,JTextField textField5,boolean b){
        JPanel main = new JPanel();

        if(b) {
            JPanel textPanel = new JPanel();
            JLabel textLabel = new JLabel("User ID");
            textLabel.setFont(new Font("Serif", Font.PLAIN, 30));
            textField2.setFont(new Font("Serif", Font.PLAIN, 30));
            textField2.setPreferredSize(new Dimension(300, 50));
            textPanel.add(textLabel);
            textPanel.add(textField2);
            textPanel.setLayout(new FlowLayout(FlowLayout.CENTER , 95, 10));
            main.add(textPanel);
            textPanel.setBackground(Color.lightGray);
        }

        JPanel textPanel2 = new JPanel();
        JPanel textPanel3 = new JPanel();
        JPanel textPanel4 = new JPanel();

        JLabel textLabel2 = new JLabel("User Name");
        JLabel textLabel3 = new JLabel("WorkPlace");
        JLabel textLabel4 = new JLabel("Home Town");

        textLabel2.setFont(new Font("Serif", Font.PLAIN, 30));
        textLabel3.setFont(new Font("Serif", Font.PLAIN, 30));
        textLabel4.setFont(new Font("Serif", Font.PLAIN, 30));

        textField3.setFont(new Font("Serif", Font.PLAIN, 30));
        textField4.setFont(new Font("Serif", Font.PLAIN, 30));
        textField5.setFont(new Font("Serif", Font.PLAIN, 30));

        textField3.setPreferredSize(new Dimension(300, 50));
        textField4.setPreferredSize(new Dimension(300, 50));
        textField5.setPreferredSize(new Dimension(300, 50));

        textPanel2.add(textLabel2);
        textPanel2.add(textField3);
        textPanel2.setLayout(new FlowLayout(FlowLayout.CENTER , 50, 10));

        textPanel3.add(textLabel3);
        textPanel3.add(textField4);
        textPanel3.setLayout(new FlowLayout(FlowLayout.CENTER , 53, 10));

        textPanel4.add(textLabel4);
        textPanel4.add(textField5);
        textPanel4.setLayout(new FlowLayout(FlowLayout.CENTER , 38, 10));

        main.add(textPanel2);
        main.add(textPanel3);
        main.add(textPanel4);

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        textPanel2.setBackground(Color.lightGray);
        textPanel3.setBackground(Color.lightGray);
        textPanel4.setBackground(Color.lightGray);
        return main;
    }

    /**
     * Validates the user registration inputs.
     * @param textField1 The user ID text field.
     * @param textField2 The user name text field.
     * @param textField3 The workplace text field.
     * @param textField4 The hometown text field.
     * @param b A boolean that specifies if the user ID field should be shown.
     * @return A character that indicates the validation
     * result: 'a' if the ID is valid and does not exist in flie, 'b' if the inputs are valid, or 'c' otherwise.
     */
    public char inputValidation(JTextField textField1 ,JTextField textField2,
    JTextField textField3,JTextField textField4,boolean b){
        boolean valid = false;
        int ID = 0;
        String name;
        String work;
        String home;
        try {
            if(b) {
                ID = Integer.parseInt(textField1.getText());
            }
            name = textField2.getText();
            work = textField3.getText();
            home = textField4.getText();
            if(name.matches("[a-zA-Z]+") &&
            work.matches("[a-zA-Z]+") &&
            home.matches("[a-zA-Z]+")){
                valid = true;
            }
        } catch (NumberFormatException nfe) {
            //
        }
        boolean check = false;
        if(b) {
            check = graph.check(ID);
        }

        if (valid && !check){
            return 'a';
        }else if(valid){
            return 'b';
        }else{
            return 'c';
        }
    }

    /**
     * This method updates the number of likes for the image with the given name and returns the updated value as a String.
     * @param id int value representing the user ID.
     * @param name String value representing the image name.
     * @param n int value representing the number of likes to be added.
     * @return String value representing the updated number of likes.
     */
    public String likes(int id , String name,int n) {
        UserDetails details = graph.getUser(id);
        String[] imG = details.getImage().split(":");
        String[] lik = details.getLikes().split(":");
        int likee = 0;
        for (int i = 0; i < imG.length; i++) {
            if (imG[i].equals(name)) {
                likee = Integer.parseInt(lik[i]) + n;
                if (likee < 0) {
                    likee = 1;
                }
                lik[i] = likee + "";
            }
        }
        String reversed = String.join(":", lik) + ":";
        details.setLikes(reversed);
        return likee + "";
    }

    /**
     * This method sets the default close operation for the given JFrame to
     * DO_NOTHING_ON_CLOSE and displays a confirmation dialog when the window is closed.
     * @param frame JFrame to be exited.
     */
    public void WindowExit(JFrame frame){
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    JButton yes = new JButton("Yeah");
                    JButton no = new JButton("Noi");
                    JFrame fm = new JFrame("Confirmation");
                    yes.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(e.getSource() == yes){
                                    File file = new File(graph);
                                    file.addToFile();
                                    System.exit(0);
                                }
                            }
                        });
                    no.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(e.getSource() == no){
                                    fm.dispose();
                                }
                            }
                        });
                    JLabel label = new JLabel("Seriously Dude :-(");
                    label.setFont(new Font("Serif", Font.PLAIN, 30));
                    label.setAlignmentX(Component.CENTER_ALIGNMENT);
                    fm.setSize(400, 170);
                    fm.setLocationRelativeTo(null);
                    btnSmall(yes,no);
                    JPanel panel = new JPanel();
                    panel.setBackground(Color.CYAN);
                    panel.add(label);
                    JPanel panel1 = new JPanel();
                    panel1.setBackground(Color.cyan);
                    panel1.add(yes);
                    panel1.add(no);
                    panel.add(panel1);
                    fm.add(panel);
                    fm.setVisible(true);
                }
            });
    }

    /**
     * This method creates a JPanel with a label containing the specified name and sets its properties.
     * @param name the name to be displayed in the label of the JPanel
     * @return a JPanel with a label containing the specified name
     */
    public JPanel panel(String name){
        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));

        JLabel label = new JLabel(name);
        label.setFont(new Font("Serif", Font.PLAIN, 50));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.setBackground(Color.PINK);
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));

        return panel;
    }

}
