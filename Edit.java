import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.Border;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;

/**
 * A class to provide methods to edit GUI components
 */
public class  Edit {
    public MyGraph graph;
    public Color primaryColor = new Color(72, 61, 139); // Dark slate blue
    public Color accentColor = new Color(123, 104, 238); // Medium slate blue
    public Color backgroundColor = new Color(25, 25, 35); // Dark background
    public Color textColor = new Color(147, 112, 219); // Medium purple

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
    public void btn(JButton btn1, JButton btn2) {
        styleButtons(btn1, btn2);
        btn1.setPreferredSize(new Dimension(300, 50));
        btn2.setPreferredSize(new Dimension(300, 50));
    }



    private void styleButtons(JButton btn1, JButton btn2) {
        styleButton(btn1);
        styleButton(btn2);
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setForeground(Color.WHITE);
        btn.setBackground(primaryColor);
        btn.setBorder(createStyledBorder());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(accentColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(primaryColor);
            }
        });
    }

    private Border createStyledBorder() {
        return new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(accentColor);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(x + 1, y + 1, width - 3, height - 3, 20, 20);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 10, 5, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        };
    }

    /**
     * This method sets the font, background color and preferred size of two JButtons and adds them to the same button group.
     * @param btn1 The first JButton whose properties are being set.
     * @param btn2 The second JButton whose properties are being set.
     */
    public void btnSmall(JButton btn1, JButton btn2) {
        styleButtons(btn1, btn2);
        btn1.setPreferredSize(new Dimension(150, 50));
        btn2.setPreferredSize(new Dimension(150, 50));
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
    public JPanel registration(JTextField textField2, JTextField textField3, JTextField textField4,
                               JTextField textField5, JFrame frame3, JButton btn3, JButton btn4, String name, boolean b) {
        frame3.setSize(600, 500);
        frame3.getRootPane().setBorder(BorderFactory.createLineBorder(accentColor, 2));

        JPanel panel = new JPanel();
        panel.removeAll();
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel label = new JLabel(name);
        styleLabel(label);
        label.setFont(new Font("Poppins", Font.BOLD, 40));

        btnSmall(btn3, btn4);

        JPanel button = new JPanel();
        button.setBackground(backgroundColor);
        button.add(btn3);
        button.add(Box.createHorizontalStrut(20));
        button.add(btn4);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(label);
        panel.add(Box.createVerticalStrut(30));
        panel.add(Registration(textField2, textField3, textField4, textField5, b));
        panel.add(Box.createVerticalStrut(20));
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
    public JPanel Registration(JTextField textField2, JTextField textField3,
                               JTextField textField4, JTextField textField5, boolean b) {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(backgroundColor);
        main.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        if(b) {
            addFormField(main, "User ID", textField2);
        }

        addFormField(main, "User Name", textField3);
        addFormField(main, "WorkPlace", textField4);
        addFormField(main, "Home Town", textField5);

        return main;
    }

    private void addFormField(JPanel panel, String labelText, JTextField field) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        fieldPanel.setBackground(backgroundColor);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Poppins", Font.PLAIN, 16));
        label.setForeground(textColor);
        label.setPreferredSize(new Dimension(120, 30));

        field.setPreferredSize(new Dimension(300, 40));
        field.setFont(new Font("Poppins", Font.PLAIN, 14));
        field.setForeground(Color.WHITE);
        field.setBackground(new Color(45, 45, 60));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        fieldPanel.add(label);
        fieldPanel.add(field);
        panel.add(fieldPanel);
        panel.add(Box.createVerticalStrut(10));
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
    public void WindowExit(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JButton yesButton = createStyledButton("Yes");
                JButton noButton = createStyledButton("No");

                JDialog confirmationDialog = new JDialog(frame, "Confirmation", true);
                confirmationDialog.setSize(400, 135);
                confirmationDialog.setLocationRelativeTo(frame);
                confirmationDialog.setUndecorated(true);

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
                mainPanel.setBorder(BorderFactory.createLineBorder(new Color(147, 112, 219), 2));

                JLabel label = new JLabel("Are you sure you want to exit?");
                label.setAlignmentX(Component.CENTER_ALIGNMENT);
                label.setForeground(Color.WHITE);
                label.setFont(new Font("Arial", Font.BOLD, 16));
                label.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

                yesButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(e.getSource() == yesButton){
                            File file = new File(graph);
                            file.addToFile();
                            System.exit(0);
                        }
                    }
                });

                noButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        confirmationDialog.dispose();
                    }
                });

                JPanel buttonPanel = new JPanel();
                buttonPanel.setOpaque(false);
                buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
                buttonPanel.add(yesButton);
                buttonPanel.add(noButton);

                mainPanel.add(label);
                mainPanel.add(buttonPanel);

                confirmationDialog.add(mainPanel);
                confirmationDialog.setVisible(true);
            }
        });
    }

    public JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(72, 61, 139));
        button.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(123, 104, 238));
                g2d.setStroke(new BasicStroke(2));
                int arcWidth = 20;
                int arcHeight = 20;
                int adjustedX = x + 1;  // Adjust x to leave space for stroke
                int adjustedY = y + 1;  // Adjust y to leave space for stroke
                int adjustedWidth = width - 3; // Adjust width to account for stroke
                int adjustedHeight = height - 3; // Adjust height to account for stroke

                g2d.drawRoundRect(adjustedX, adjustedY, adjustedWidth, adjustedHeight, arcWidth, arcHeight);
            }


            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 10, 5, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 40));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(123, 104, 238));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(72, 61, 139));
            }
        });

        return button;
    }

    public JTextField createStyledTextField() {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;


                // Paint rounded background
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);

                super.paintComponent(g);
            }
        };
        return createStyledField(field);
    }

    public JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField() {
            private boolean showPassword = false;
            private final int iconSize = 20;
            private final int padding = 10;
            private ImageIcon openEyeIcon, closedEyeIcon;

            {
                try {
                    // Load and scale icons
                    BufferedImage openEyeImg = ImageIO.read(new URL("https://raw.githubusercontent.com/google/material-design-icons/master/png/action/visibility/materialicons/24dp/1x/baseline_visibility_black_24dp.png"));
                    BufferedImage closedEyeImg = ImageIO.read(new URL("https://raw.githubusercontent.com/google/material-design-icons/master/png/action/visibility_off/materialicons/24dp/1x/baseline_visibility_off_black_24dp.png"));

                    openEyeIcon = new ImageIcon(openEyeImg.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                    closedEyeIcon = new ImageIcon(closedEyeImg.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        int x = e.getX();
                        if (x >= getWidth() - iconSize - padding && x <= getWidth() - padding) {
                            showPassword = !showPassword;
                            setEchoChar(showPassword ? (char) 0 : '•');
                            repaint();
                        }
                    }
                });

                addMouseMotionListener(new MouseMotionAdapter() {
                    @Override
                    public void mouseMoved(MouseEvent e) {
                        int x = e.getX();
                        if (x >= getWidth() - iconSize - padding && x <= getWidth() - padding) {
                            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        } else {
                            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                        }
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);

                super.paintComponent(g);
                //Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw the eye icon
                int iconX = getWidth() - iconSize - padding;
                int iconY = (getHeight() - iconSize) / 2;

                if (showPassword && openEyeIcon != null) {
                    openEyeIcon.paintIcon(this, g2d, iconX, iconY);
                } else if (closedEyeIcon != null) {
                    closedEyeIcon.paintIcon(this, g2d, iconX, iconY);
                }
            }
        };
        field.setEchoChar('•');
        final int arcSize = 20;  // Roundness control

        field.setFont(new Font("Montserrat", Font.PLAIN, 14));
        field.setForeground(Color.BLACK);
        field.setOpaque(false);
        field.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(primaryColor);
                g2d.drawRoundRect(x, y, width-1, height-1, arcSize, arcSize);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 10, 5, 33);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });
        field.setBackground(Color.white);
        return field;
    }

    // Common styling method for both text and password fields
    private <T extends JTextField> T createStyledField(T field) {
        final int arcSize = 20;  // Roundness control

        field.setFont(new Font("Montserrat", Font.PLAIN, 14));
        field.setForeground(Color.BLACK);
        field.setOpaque(false);

        field.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(primaryColor);
                g2d.drawRoundRect(x, y, width-1, height-1, arcSize, arcSize);
            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 10, 5, 10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });
        field.setBackground(Color.white);
        return field;
    }

    public JLabel createErrorLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Montserrat", Font.ITALIC, 12));
        label.setForeground(Color.RED);
        label.setVisible(false);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    public void showError(JLabel error, String message){
        error.setText("<html><font color='red'>" + message + "</font></html>");
        error.setVisible(true);

        // Add shake animation for error
        Timer shakeTimer = new Timer(50, new ActionListener() {
            int count = 0;
            int direction = 1;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (count < 6) {
                    error.setLocation(error.getX() + (5 * direction), error.getY());
                    direction *= -1;
                    count++;
                } else {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        shakeTimer.start();
    }

    public void styleLabel(JLabel label) {
        label.setFont(new Font("Montserrat", Font.BOLD, 14));
        label.setForeground(textColor);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    /**
     * This method creates a JPanel with a label containing the specified name and sets its properties.
     * @param name the name to be displayed in the label of the JPanel
     * @return a JPanel with a label containing the specified name
     */
    public JPanel panel(String name) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                        0, 0, backgroundColor,
                        getWidth(), getHeight(), primaryColor
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(accentColor, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setLayout(new BorderLayout(10, 10)); // Use BorderLayout with gaps

        // Create the label
        JLabel label = new JLabel(name, SwingConstants.CENTER); // Center-align text horizontally
        label.setFont(new Font("Poppins", Font.BOLD, 40));
        label.setForeground(textColor);

        // Add the label to the top of the panel
        panel.add(label, BorderLayout.NORTH);

        // Ensure transparency is maintained
        panel.setOpaque(false);

        return panel;
    }


}
