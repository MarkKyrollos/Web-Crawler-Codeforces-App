import java.sql.ResultSet;

public class SQLClientApp extends JFrame{
    private JButton connectBtn, clearQuery, executeBtn, clearWindow;
    private ResultSetTable tableModel;
    private JLabel queryLabel, userLabel, passwordLabel, statusLabel, windowLabel, dbInfoLabel, jdbcLabel;
    private JTextArea queryArea;
    private JTextField userField;
    private JLabel blankLabel;
    private JPasswordField passwordField;
    private JComboBox<String> propertiesCombo, urlCombo, actionsCombo;
    private Connection connection;
    private TableModel empty;
    private JTable resultTable;

    // List of actions to perform based on the user's selection
    private final String[] actions = {
        "Login and View Attempts/Competitions",
        "Competitions as Writer",
        "Problem Sets by Tag",
        "Top 5 Languages by Efficiency",
        "Top 10 Users by Activity",
        "Top 10 Users by Scores",
        "Top 5 Organizations by Rating (Div1 & Div2)",
        "Top 5 Users by Participation Frequency",
        "Top 10 AUC Users by Rating (Div1 & Div2)",
        "Top 5 Attempted Problems by Egyptian Users"
    };

    // GUI Constructor
    public SQLClientApp(){
        setName("SQL Client Application");
        setSize(1000, 580);

        // Construct GUI components
        queryArea = new JTextArea("");
        queryArea.setEnabled(false);
        userField = new JTextField("");
        passwordField = new JPasswordField("");

        // Combo box for selecting an action
        actionsCombo = new JComboBox<>(actions);
        actionsCombo.setBounds(10, 150, 300, 25);
        add(actionsCombo);

        // Set up buttons and other UI elements as in original code

        // Execute Button with added functionality selection
        executeBtn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {

                String selectedAction = (String) actionsCombo.getSelectedItem();

                try {

                    // Connect ResultSetTable model to result table
                    resultTable.setEnabled(true);
                    resultTable.setAutoscrolls(true);

                    // Choose SQL query based on selected action
                    switch (selectedAction) {
                        case "Login and View Attempts/Competitions":
                            loginUserAttempts();
                            break;
                        case "Competitions as Writer":
                            showCompetitionsAsWriter();
                            break;
                        case "Problem Sets by Tag":
                            showProblemSetsByTag();
                            break;
                        case "Top 5 Languages by Efficiency":
                            showTopLanguagesByEfficiency();
                            break;
                        case "Top 10 Users by Activity":
                            showTopUsersByActivity();
                            break;
                        case "Top 10 Users by Scores":
                            showTopUsersByScores();
                            break;
                        case "Top 5 Organizations by Rating (Div1 & Div2)":
                            showTopOrganizationsByRating();
                            break;
                        case "Top 5 Users by Participation Frequency":
                            showTopUsersByParticipationFrequency();
                            break;
                        case "Top 10 AUC Users by Rating (Div1 & Div2)":
                            showAUCUsersByRating();
                            break;
                        case "Top 5 Attempted Problems by Egyptian Users":
                            showTopAttemptedProblemsByEgyptUsers();
                            break;
                    }
					
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    // Function for each specific query
    private void loginUserAttempts() throws SQLException {
        String username = userField.getText();
        String query = "SELECT problem_id, competition_id FROM user_activity WHERE screen_name = '" + username + "'";
        executeQuery(query);
    }

    private void showCompetitionsAsWriter() throws SQLException {
        String writer = userField.getText();
        String query = "SELECT competition_name FROM competitions WHERE writer = '" + writer + "'";
        executeQuery(query);
    }

    private void showProblemSetsByTag() throws SQLException {
        String tag = queryArea.getText();
        String query = "SELECT problem_set_id, problem_name FROM problem_sets WHERE tag = '" + tag + "'";
        executeQuery(query);
    }

    private void showTopLanguagesByEfficiency() throws SQLException {
        String query = "SELECT language, COUNT(*) AS usage_count FROM solutions GROUP BY language ORDER BY speed_score DESC, memory_score DESC LIMIT 5";
        executeQuery(query);
    }

    private void showTopUsersByActivity() throws SQLException {
        String query = "SELECT username, consecutive_days, problems_solved FROM users ORDER BY consecutive_days DESC, problems_solved DESC LIMIT 10";
        executeQuery(query);
    }

    private void showTopUsersByScores() throws SQLException {
        String query = "SELECT username, total_score FROM users WHERE division IN ('Div1', 'Div2') ORDER BY total_score DESC LIMIT 10";
        executeQuery(query);
    }

    private void showTopOrganizationsByRating() throws SQLException {
        String query = "SELECT organization, AVG(rating) AS avg_rating FROM users WHERE division IN ('Div1', 'Div2') GROUP BY organization ORDER BY avg_rating DESC LIMIT 5";
        executeQuery(query);
    }

    private void showTopUsersByParticipationFrequency() throws SQLException {
        String query = "SELECT username, COUNT(contest_id)/DATEDIFF(NOW(), registration_date) AS frequency FROM contests GROUP BY username ORDER BY frequency DESC LIMIT 5";
        executeQuery(query);
    }

    private void showAUCUsersByRating() throws SQLException {
        String query = "SELECT username, overall_rating FROM users WHERE organization = 'AUC' ORDER BY overall_rating DESC LIMIT 10";
        executeQuery(query);
    }

    private void showTopAttemptedProblemsByEgyptUsers() throws SQLException {
        String query = "SELECT problem_set_id, COUNT(*) AS attempts FROM attempts WHERE country = 'Egypt' GROUP BY problem_set_id ORDER BY attempts DESC LIMIT 5";
        executeQuery(query);
    }

    // Helper method to execute a query and display the results
    private void executeQuery(String query) throws SQLException {
        tableModel = new ResultSetTable(connection, query);
        tableModel.setQuery(query);
        resultTable.setModel(tableModel);
    }

    public static void main(String[] args) {
        SQLClientApp project = new SQLClientApp();
        project.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        project.setVisible(true);
    }
}
