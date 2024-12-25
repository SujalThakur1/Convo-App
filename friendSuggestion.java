import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A class for displaying friend suggestions to a user.
 */
public class friendSuggestion {
    private JFrame frame;
    private Edit editBtn;
    private boolean check;
    private int id;
    private MyGraph graph;
    private final Color primaryColor = new Color(147, 112, 219); // Modern purple
    private final Color backgroundColor = new Color(25, 25, 35); // Dark background
    private final Color textColor = new Color(180, 180, 200); // Soft light color

    public friendSuggestion(int ID, MyGraph graph) {
        this.graph = graph;
        editBtn = new Edit();
        this.id = ID;
        check = false;
    }

    public void suggestion() {
        frame = new JFrame("Convo");
        frame.setSize(900, 730);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(primaryColor, 2));
        editBtn.WindowExit(frame);
        mainPanel();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void mainPanel() {
        // Create main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(25, 25, 35),
                        0, getHeight(), new Color(45, 45, 60)
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Header
        JLabel headerLabel = new JLabel("Friend Suggestions");
        headerLabel.setFont(new Font("Poppins", Font.BOLD, 36));
        headerLabel.setForeground(primaryColor);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        ArrayList<UserDetails> details = graph.getUsersByPlace(id);
        if (details == null) {
            for (UserDetails detail : graph.getUsers()) {
                if (id != detail.getID()) {
                    contentPanel.add(Stackbtn(detail.getName(), detail.getID(), detail.getWork(), detail.getHome()));
                    contentPanel.add(Box.createVerticalStrut(10));
                }
            }
        } else {
            if (details != null) {
                for (UserDetails det : details) {
                    contentPanel.add(GraphBtn(det.getName(), det.getID(), det.getWork(), det.getHome()));
                    contentPanel.add(Box.createVerticalStrut(10));
                }
            }
        }

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Footer with OK button
        JButton okButton = editBtn.createStyledButton("Continue");
        okButton.setFont(new Font("Arial", Font.BOLD, 18));
        okButton.setPreferredSize(new Dimension(200, 50));


        okButton.addActionListener(e -> {
            if (check) {
                userProfile profile = new userProfile(id, graph);
                frame.dispose();
                profile.display(true);
            } else {
                showErrorDialog();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(okButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        mainPanel.add(headerLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void showErrorDialog() {
        JDialog dialog = new JDialog(frame, "Error", true);
        dialog.setUndecorated(true);
        dialog.setSize(330, 150);
        dialog.setLocationRelativeTo(frame);

        // Create main panel with BoxLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around the panel

        // Create error message label
        JLabel message = new JLabel("Please add at least one friend to proceed");
        message.setForeground(textColor);
        message.setFont(new Font("Poppins", Font.PLAIN, 14));
        message.setHorizontalAlignment(SwingConstants.CENTER);
        message.setAlignmentX(Component.CENTER_ALIGNMENT); // Centers the label horizontally

        // Create Try Again button
        JButton tryAgainButton = editBtn.createStyledButton("Try Again");
        tryAgainButton.addActionListener(e -> dialog.dispose());
        tryAgainButton.setPreferredSize(new Dimension(150, 40)); // Set preferred size of the button
        tryAgainButton.setMaximumSize(new Dimension(150, 40));    // Set maximum size to prevent stretching
        tryAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Centers the button horizontally

        // Add components to the panel with additional space
        panel.add(Box.createVerticalGlue()); // Adds flexible space at the top
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Adds space between top and message
        panel.add(message); // Adds the message label
        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Adds space between message and button
        panel.add(tryAgainButton); // Adds the button
        panel.add(Box.createVerticalGlue()); // Adds flexible space at the bottom

        // Panel border and dialog settings
        panel.setBorder(BorderFactory.createLineBorder(new Color(147, 112, 219), 2));

        dialog.add(panel);
        dialog.setVisible(true);
    }

    public JPanel GraphBtn(String name, int ID, String work, String home) {
        JButton addBtn = createStyledButton("Add");
        addBtn.addActionListener(e -> {
            graph.Friend(id, ID, true);
            addBtn.setEnabled(false);
            addBtn.setText("Added");
            addBtn.setBackground(new Color(45, 45, 60));
            check = true;
        });
        return createFriendPanel(name, work, home, addBtn);
    }

    public JPanel Stackbtn(String name, int ID, String work, String home) {
        JButton addBtn = createStyledButton("Add");
        addBtn.addActionListener(e -> actionListener(ID, addBtn, true));
        return createFriendPanel(name, work, home, addBtn);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Poppins", Font.BOLD, 12));
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createFriendPanel(String name, String work, String home, JButton addBtn) {
        // Create main panel with subtle gradient
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Soft gradient background
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(104, 83, 195, 132), // A lighter purple
                        getWidth(), getHeight(), new Color(85, 65, 163, 98) // Another lighter purple
                );
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        panel.setOpaque(false);

        // Left side - Name with icon and modern typography
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        leftPanel.setOpaque(false);

        // Add user icon
        JLabel iconLabel = new JLabel("👤");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        iconLabel.setForeground(new Color(220, 220, 230));
        leftPanel.add(iconLabel);

        // Name label with modern typography
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        nameLabel.setForeground(new Color(220, 220, 230));
        leftPanel.add(nameLabel);

        // Right side - Buttons
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setOpaque(false);

        // Style the inspect button
        JButton inspectBtn = createStyledButton("Inspect");
        inspectBtn.addActionListener(e -> inspect(name, work, home, frame));

        rightPanel.add(inspectBtn);
        rightPanel.add(addBtn);

        // Add panels to main panel
        panel.add(leftPanel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        return panel;
    }

    public void inspect(String name, String work, String home, JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "User Details", true);
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setUndecorated(true);

        // Main panel with BoxLayout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(backgroundColor);

        // Add top gap
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Heading
        JLabel headingLabel = new JLabel("User Details");
        headingLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        headingLabel.setForeground(primaryColor);
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(headingLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Name Panel
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        namePanel.setOpaque(false);

        JLabel nameLabelLeft = new JLabel("Name");
        nameLabelLeft.setFont(new Font("Poppins", Font.PLAIN, 16));
        nameLabelLeft.setForeground(textColor);

        JLabel nameLabelRight = new JLabel(name);
        nameLabelRight.setFont(new Font("Poppins", Font.PLAIN, 16));
        nameLabelRight.setForeground(textColor);

        namePanel.add(nameLabelLeft, BorderLayout.WEST);
        namePanel.add(nameLabelRight, BorderLayout.EAST);
        mainPanel.add(namePanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Workplace Panel
        JPanel workPanel = new JPanel(new BorderLayout());
        workPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        workPanel.setOpaque(false);

        JLabel workLabelLeft = new JLabel("Workplace");
        workLabelLeft.setFont(new Font("Poppins", Font.PLAIN, 16));
        workLabelLeft.setForeground(textColor);

        JLabel workLabelRight = new JLabel(work);
        workLabelRight.setFont(new Font("Poppins", Font.PLAIN, 16));
        workLabelRight.setForeground(textColor);

        workPanel.add(workLabelLeft, BorderLayout.WEST);
        workPanel.add(workLabelRight, BorderLayout.EAST);
        mainPanel.add(workPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Home Panel
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));
        homePanel.setOpaque(false);

        JLabel homeLabelLeft = new JLabel("Home");
        homeLabelLeft.setFont(new Font("Poppins", Font.PLAIN, 16));
        homeLabelLeft.setForeground(textColor);

        JLabel homeLabelRight = new JLabel(home);
        homeLabelRight.setFont(new Font("Poppins", Font.PLAIN, 16));
        homeLabelRight.setForeground(textColor);

        homePanel.add(homeLabelLeft, BorderLayout.WEST);
        homePanel.add(homeLabelRight, BorderLayout.EAST);
        mainPanel.add(homePanel);

        mainPanel.add(Box.createVerticalGlue());

        // Back Button
        JButton backButton = editBtn.createStyledButton("Back");
        backButton.addActionListener(e -> dialog.dispose());
        backButton.setPreferredSize(new Dimension(150, 40));
        backButton.setMaximumSize(new Dimension(150, 40));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(backButton);

        // Add bottom gap
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Set border and finalize dialog
        mainPanel.setBorder(BorderFactory.createLineBorder(primaryColor, 2));
        dialog.add(mainPanel);
        dialog.setVisible(true);
    }

    public int actionListener(int ID, JButton addBtn, boolean bull) {
        int size = bull ? graph.Friend(id, ID, true) : graph.Friend(id, ID, false);
        addBtn.setText(bull ? "Added" : "Removed");
        addBtn.setEnabled(false);
        addBtn.setBackground(new Color(45, 45, 60));
        check = true;
        return size;
    }
}
