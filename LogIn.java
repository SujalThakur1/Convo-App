import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

public class LogIn {
    // Private member variables
    private JFrame frame;
    private Edit editBtn;
    private JTextField signInEmailField, emailField, nameField, surnameField, workplaceField, townField;
    private JPasswordField signInPasswordField, passwordField, confirmPasswordField;
    private MyGraph graph;
    private userProfile profile;
    private JLabel errorLabel, nameErrorLabel, signInEmailError, signInPasswordError, emailErrorLabel, passwordErrorLabel, workplaceErrorLabel, townErrorLabel;
    private JPanel signUpPanel1, signUpPanel2; // Panels for sign up steps
    private boolean isSignUpMode = false;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        LogIn panelsDemo = new LogIn();
        panelsDemo.run();
    }

    public void run(){
        File file;
        file = new File(graph);
        file.readFromAFile();
        frame = new JFrame("Convo");
        frame.setSize(900, 730);
        frame.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(147, 112, 219), 2));
        editBtn.WindowExit(frame);
        mainPanel();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JButton createStyledButton(String text) {
        return editBtn.createStyledButton(text);
    }

    public void mainPanel() {
        // Create main panel with sleek dark theme background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                // Modern gradient with deep purple to dark gray
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(25, 25, 35), // Dark background at top
                        0, getHeight(), new Color(45, 45, 60) // Slightly lighter at bottom
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Minimalist header panel
        JPanel headerPanel = new JPanel(new BorderLayout(0, 15));
        headerPanel.setOpaque(false);

        // Modern logo design
        JLabel logoLabel = new JLabel("CONVO");
        logoLabel.setFont(new Font("Poppins", Font.BOLD, 72)); // Changed to Poppins font
        logoLabel.setForeground(new Color(147, 112, 219)); // Modern purple
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Subtle glow effect
        logoLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 0, 5, 0),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));

        // Smooth fade-in animation
        Timer fadeTimer = new Timer(30, new ActionListener() {
            float alpha = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                alpha += 0.03;
                if (alpha > 1) {
                    alpha = 1;
                    ((Timer)e.getSource()).stop();
                }
                logoLabel.setForeground(new Color(147, 112, 219, (int)(alpha * 255)));
                logoLabel.repaint();
            }
        });
        fadeTimer.start();

        // Minimalist tagline
        JLabel subtitleLabel = new JLabel("Find Friends • Create Connections • Explore Profiles");
        subtitleLabel.setFont(new Font("Poppins", Font.PLAIN, 24));
        subtitleLabel.setForeground(new Color(180, 180, 200)); // Soft light color
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(logoLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Card panel setup
        JPanel cardPanel = new JPanel(new CardLayout());
        cardPanel.setOpaque(false);

        // Initialize panels
        JPanel loginPanel = createLoginPanel();
        signUpPanel1 = createSignUpPanel1();
        signUpPanel2 = createSignUpPanel2();

        cardPanel.add(loginPanel, "login");
        cardPanel.add(signUpPanel1, "signup1");
        cardPanel.add(signUpPanel2, "signup2");

        // Modern button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 20));
        buttonPanel.setOpaque(false);

        // Sleek button styling
        JButton signUpBtn = createStyledButton("Sign Up");
        JButton signInBtn = createStyledButton("Sign In");
        JButton nextBtn = createStyledButton("Next");

        signUpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                if (!isSignUpMode) {
                    cl.show(cardPanel, "signup1");
                    signUpBtn.setText("Back");
                    signInBtn.setVisible(false);
                    nextBtn.setVisible(true);
                    nextBtn.setText("Next");
                    isSignUpMode = true;
                } else {
                    cl.show(cardPanel, "login");
                    signUpBtn.setText("Sign Up");
                    signInBtn.setVisible(true);
                    nextBtn.setVisible(false);
                    isSignUpMode = false;
                    clearFields();
                }
            }
        });

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nextBtn.getText().equals("Next")) {
                    if (validateSignUpPage1()) {
                        CardLayout cl = (CardLayout) cardPanel.getLayout();
                        cl.show(cardPanel, "signup2");
                        nextBtn.setText("Register");
                        signUpBtn.setText("Back");
                    }
                } else if (nextBtn.getText().equals("Register")) {
                    int ID = validateSignUpPage2();
                    if (ID != -1) {
                        friendSuggestion friendSuggestion = new friendSuggestion(ID,graph);
                        friendSuggestion.suggestion();
                    }
                }
            }
        });

        signInBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignIn();
            }
        });

        buttonPanel.add(signUpBtn);
        buttonPanel.add(signInBtn);
        buttonPanel.add(nextBtn);
        nextBtn.setVisible(false);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(cardPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void handleSignIn() {
        String email = signInEmailField.getText().trim();
        String password = new String(signInPasswordField.getPassword());

        // Reset all error labels
        signInEmailError.setVisible(false);
        signInPasswordError.setVisible(false);
        errorLabel.setVisible(false);

        // Validate empty fields
        boolean hasError = false;
        if (email.isEmpty()) {
            editBtn.showError(signInEmailError, "Please enter your email");
            hasError = true;
        }
        if (password.isEmpty()) {
            editBtn.showError(signInPasswordError, "Please enter your password");
            hasError = true;
        }
        if (hasError) {
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Check.txt"));
            String line;
            boolean credentialsFound = false;
            int ID = 0;

            while ((line = reader.readLine()) != null) {
                // Find first two commas to split email and ID
                int firstComma = line.indexOf(',');
                int secondComma = line.indexOf(',', firstComma + 1);

                if (firstComma != -1 && secondComma != -1) {
                    String fileEmail = line.substring(0, firstComma);
                    String fileID = line.substring(firstComma + 1, secondComma);
                    // Get remaining string as password (may contain commas)
                    String filePassword = line.substring(secondComma + 1);

                    if (email.equals(fileEmail) && password.equals(filePassword)) {
                        ID = Integer.parseInt(fileID);
                        credentialsFound = true;
                        break;
                    }
                }
            }
            reader.close();

            if (!credentialsFound) {
                signInPasswordError.setVisible(true);
                editBtn.showError(signInPasswordError, "Invalid email or password");
                return;
            }

            // Check user exists and credentials
            char check = graph.specialCheck(ID);
            if (check == 'c') {
                signInPasswordError.setVisible(true);
                editBtn.showError(signInPasswordError, "No user found with this ID");
                return;
            }

            // Successful login
            if (check == 'a') {
                friendSuggestion friendSuggestion = new friendSuggestion(ID, graph);
                friendSuggestion.suggestion();
                frame.dispose();
            } else if (check == 'b') {
                profile = new userProfile(ID, graph);
                profile.display(true);
                frame.dispose();
            }

        } catch (IOException e) {
            signInPasswordError.setVisible(true);
            editBtn.showError(signInPasswordError, "Error reading credentials file");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            signInPasswordError.setVisible(true);
            editBtn.showError(signInPasswordError, "Invalid ID format in credentials file");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        signInEmailField.setText("");
        signInPasswordField.setText("");
        emailField.setText("");
        nameField.setText("");
        surnameField.setText("");
        workplaceField.setText("");
        townField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        errorLabel.setVisible(false);
        workplaceErrorLabel.setVisible(false);
        townErrorLabel.setVisible(false);
        nameErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
        signInPasswordError.setVisible(false);
        signInEmailError.setVisible(false);
    }

    /**
     * Constructor for the LogIn class.
     * Initializes the member variables and creates a new MyGraph object.
     */
    public LogIn() {
        // Initialize text fields with custom styling
        graph = new MyGraph();
        editBtn = new Edit(graph);


        signInEmailField = editBtn.createStyledTextField();
        signInPasswordField = editBtn.createStyledPasswordField();
        emailField = editBtn.createStyledTextField();
        nameField = editBtn.createStyledTextField();
        surnameField = editBtn.createStyledTextField();
        workplaceField = editBtn.createStyledTextField();
        townField = editBtn.createStyledTextField();
        passwordField = editBtn.createStyledPasswordField();
        confirmPasswordField = editBtn.createStyledPasswordField();

        // Initialize error labels with red text
        errorLabel = editBtn.createErrorLabel();

        nameErrorLabel = editBtn.createErrorLabel();

        emailErrorLabel = editBtn.createErrorLabel();

        signInEmailError = editBtn.createErrorLabel();

        signInPasswordError = editBtn.createErrorLabel();

        passwordErrorLabel = editBtn.createErrorLabel();

        workplaceErrorLabel = editBtn.createErrorLabel();
        townErrorLabel = editBtn.createErrorLabel();
    }

    private void styleLabel(JLabel label) {
        editBtn.styleLabel(label);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));

        // Email Field with modern styling
        JLabel emailLabel = new JLabel("Enter Email");
        styleLabel(emailLabel);
        signInEmailField.setMaximumSize(new Dimension(400, 50));

        // Password Field with enhanced styling
        JLabel passwordLabel = new JLabel("Enter Password");
        styleLabel(passwordLabel);
        signInPasswordField.setMaximumSize(new Dimension(400, 50));
        signInPasswordField.setEchoChar('•');

        // Error panels with improved visibility
        JPanel emailErrorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailErrorPanel.setOpaque(false);
        emailErrorPanel.setMaximumSize(new Dimension(400, 30));
        emailErrorPanel.add(signInEmailError);

        JPanel passwordErrorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordErrorPanel.setOpaque(false);
        passwordErrorPanel.setMaximumSize(new Dimension(400, 30));
        passwordErrorPanel.add(signInPasswordError);



        // Add components with improved spacing
        panel.add(emailLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(signInEmailField);
        panel.add(Box.createVerticalStrut(0));
        panel.add(emailErrorPanel);
        panel.add(Box.createVerticalStrut(15));

        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(8));
        panel.add(signInPasswordField);
        panel.add(Box.createVerticalStrut(0));
        panel.add(passwordErrorPanel);

        return panel;
    }


    private JPanel createSignUpPanel1() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Name and Surname in horizontal layout
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
        namePanel.setOpaque(false);
        namePanel.setMaximumSize(new Dimension(400, 70));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name");
        styleLabel(nameLabel);
        nameField.setMaximumSize(new Dimension(195, 60));

        JLabel surnameLabel = new JLabel("Surname");
        styleLabel(surnameLabel);
        surnameField.setMaximumSize(new Dimension(195, 50));

        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(nameField);

        rightPanel.add(surnameLabel);
        rightPanel.add(Box.createVerticalStrut(5));
        rightPanel.add(surnameField);

        namePanel.add(leftPanel);
        namePanel.add(Box.createHorizontalStrut(10));
        namePanel.add(rightPanel);
        panel.add(namePanel);

        // Create error panels with FlowLayout
        JPanel nameErrorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nameErrorPanel.setOpaque(false);
        nameErrorPanel.setMaximumSize(new Dimension(350, 20));
        nameErrorPanel.add(nameErrorLabel);
        panel.add(nameErrorPanel);

        // Add workplace and town fields with error labels
        JLabel workplaceLabel = new JLabel("Workplace");
        styleLabel(workplaceLabel);
        workplaceField.setMaximumSize(new Dimension(400, 50));

        JLabel townLabel = new JLabel("Town");
        styleLabel(townLabel);
        townField.setMaximumSize(new Dimension(400, 50));

        panel.add(Box.createVerticalStrut(10));
        panel.add(workplaceLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(workplaceField);

        JPanel workplaceErrorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        workplaceErrorPanel.setOpaque(false);
        workplaceErrorPanel.setMaximumSize(new Dimension(350, 20));
        workplaceErrorPanel.add(workplaceErrorLabel);
        panel.add(workplaceErrorPanel);

        panel.add(Box.createVerticalStrut(10));
        panel.add(townLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(townField);

        JPanel townErrorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        townErrorPanel.setOpaque(false);
        townErrorPanel.setMaximumSize(new Dimension(350, 20));
        townErrorPanel.add(townErrorLabel);
        panel.add(townErrorPanel);

        return panel;
    }

    private JPanel createSignUpPanel2() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // Email field
        JLabel emailLabel = new JLabel("Email");
        styleLabel(emailLabel);
        emailField.setMaximumSize(new Dimension(400, 50));

        // Password fields
        JLabel passwordLabel = new JLabel("New Password");
        styleLabel(passwordLabel);
        passwordField = editBtn.createStyledPasswordField();
        passwordField.setMaximumSize(new Dimension(400, 50));
        passwordField.setEchoChar('•');

        JLabel confirmLabel = new JLabel("Confirm Password");
        styleLabel(confirmLabel);
        confirmPasswordField = editBtn.createStyledPasswordField();
        confirmPasswordField.setMaximumSize(new Dimension(400, 50));
        confirmPasswordField.setEchoChar('•');

        // Error panels
        JPanel emailErrorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        emailErrorPanel.setOpaque(false);
        emailErrorPanel.setMaximumSize(new Dimension(350, 20));
        emailErrorPanel.add(emailErrorLabel);

        JPanel passwordErrorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordErrorPanel.setOpaque(false);
        passwordErrorPanel.setMaximumSize(new Dimension(350, 20));
        passwordErrorPanel.add(passwordErrorLabel);

        // Add components to main panel
        panel.add(emailLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(emailField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(emailErrorPanel);
        panel.add(Box.createVerticalStrut(5));

        panel.add(passwordLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(30));

        panel.add(confirmLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(confirmPasswordField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(passwordErrorPanel);

        return panel;
    }

    private boolean validateSignUpPage1() {
        boolean isValid = true;
        // Validate name and surname
        if (nameField.getText().trim().isEmpty() || surnameField.getText().trim().isEmpty()) {
            editBtn.showError(nameErrorLabel, "Name and surname are required");
            isValid = false;
        } else {
            nameErrorLabel.setVisible(false);
        }

        // Validate workplace
        if (workplaceField.getText().trim().isEmpty()) {
            editBtn.showError(workplaceErrorLabel, "Workplace is required");
            isValid = false;
        } else {
            workplaceErrorLabel.setVisible(false);
        }

        // Validate town
        if (townField.getText().trim().isEmpty()) {
            editBtn.showError(townErrorLabel, "Town is required");
            isValid = false;
        } else {
            townErrorLabel.setVisible(false);
        }

        return isValid;
    }

    private int validateSignUpPage2() {
        boolean isValid = true;

        // Email validation
        String email = emailField.getText().trim();
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        if (email.isEmpty()) {
            editBtn.showError(emailErrorLabel, "Email address is required");
            isValid = false;
        } else if (!email.matches(emailRegex)) {
            editBtn.showError(emailErrorLabel, "Please enter a valid email address");
            isValid = false;
        } else {
            emailErrorLabel.setVisible(false);
        }

        // Password validation
        String password = new String(passwordField.getPassword());
        String confirmPass = new String(confirmPasswordField.getPassword());

        if (password.isEmpty()) {
            editBtn.showError(passwordErrorLabel, "Password is required");
            isValid = false;
        } else if (password.length() < 8) {
            editBtn.showError(passwordErrorLabel, "Password must be at least 8 characters long");
            isValid = false;
        } else if (!password.matches(".*[A-Z].*")) {
            editBtn.showError(passwordErrorLabel, "Password must contain at least one uppercase letter");
            isValid = false;
        } else if (!password.matches(".*[a-z].*")) {
            editBtn.showError(passwordErrorLabel, "Password must contain at least one lowercase letter");
            isValid = false;
        } else if (!password.matches(".*\\d.*")) {
            editBtn.showError(passwordErrorLabel, "Password must contain at least one number");
            isValid = false;
        } else if (!password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
            editBtn.showError(passwordErrorLabel, "Password must contain at least one special character");
            isValid = false;
        } else if (!password.equals(confirmPass)) {
            editBtn.showError(passwordErrorLabel, "Passwords do not match");
            isValid = false;
        } else {
            passwordErrorLabel.setVisible(false);
        }

        if(isValid){
            try {
                // Check if email already exists
                BufferedReader reader = new BufferedReader(new FileReader("Check.txt"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(email)) {
                        passwordErrorLabel.setVisible(true);
                        editBtn.showError(passwordErrorLabel, "Email already exists");
                        reader.close();
                        return -1;
                    }
                }
                reader.close();

                // Find largest user ID from graph
                int maxId = 0;
                for (UserDetails user : graph.getUsers()) {
                    int userId = user.getID();  // Assuming getId() is a method in UserDetails that returns the user ID
                    if (userId > maxId) {
                        maxId = userId;
                    }
                }
                int id = maxId + 1;

                // Write new user to Check.txt
                BufferedWriter writer = new BufferedWriter(new FileWriter("Check.txt", true));
                writer.newLine();
                writer.write(email + "," + id + "," + password);
                writer.close();

                // Add user to graph
                String fullName = nameField.getText() + " " + surnameField.getText();
                graph.addUser(id, fullName, workplaceField.getText(), townField.getText(), "", "");

                return id;

            } catch (IOException e) {
                e.printStackTrace();
                return -1;
            }
        }else
            return -1;
    }
}
