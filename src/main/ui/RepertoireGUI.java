package ui;

import model.Piece;
import model.RepertoireTracker;
import model.StatusType;
import model.Event;
import model.EventLog;
import persistence.JsonReader;
import persistence.JsonWriter;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

// Represents the GUI for the Digital Repertoire Tracker
// Handles interaction by the user to add, remove, and modify aspects of a piece.
// Allows user to view pieces based on its features, and relies on the Piece and
// RepertoireTracker class to retrieve data
public class RepertoireGUI extends JFrame {
    private JPanel buttonPanel;
    private JTextArea textArea;
    private RepertoireTracker repertoire;
    private JsonReader reader;
    private JsonWriter writer;
    private static final String JSON_STORE = "./data/repertoire.json";
    private JTextArea pieceDetails;
    private JFrame practiceFrame;
    private JFrame ratingFrame;

    private JButton addPieceButton;
    private JButton viewRepertoireButton;
    private JButton removePieceButton;
    private JButton changeStatusButton;
    private JButton viewNotStartedButton;
    private JButton viewLearningButton;
    private JButton viewMasteredButton;
    private JButton loadDataButton;
    private JButton saveDataButton;
    private JButton exitButton;

    // EFFECTS: constructs the GUI
    public RepertoireGUI() {

        super("Your Repertoire");
        SplashScreen splash = new SplashScreen();
        splash.showSplash();
        repertoire = new RepertoireTracker();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        reader = new JsonReader(JSON_STORE);
        writer = new JsonWriter(JSON_STORE);
        initializeButtons();
        display();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the JFrame and constructs the area
    private void display() {
        setLayout(new BorderLayout());
        setButtons();
        textArea = new JTextArea(20, 50);
        add(buttonPanel, BorderLayout.WEST);
        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll, BorderLayout.CENTER);
        setSize(800, 400);
        setVisible(true);
    }

    // EFFECTS: sets up the buttons to be used on the GUI
    private void initializeButtons() {
        addPieceButton = new JButton("Add a piece");
        viewRepertoireButton = new JButton("View repertoire");
        removePieceButton = new JButton("Remove a piece");
        changeStatusButton = new JButton("Change piece status");
        viewNotStartedButton = new JButton("View pieces in NOT STARTED");
        viewLearningButton = new JButton("View pieces in LEARNING");
        viewMasteredButton = new JButton("View pieces in MASTERED");
        loadDataButton = new JButton("Load Data");
        saveDataButton = new JButton("Save Data");
        exitButton = new JButton("Exit");
    }

    // MODIFIES: this
    // EFFECTS: adds the buttons to the panel
    private void setButtons() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(addPieceButton);
        buttonPanel.add(viewRepertoireButton);
        buttonPanel.add(removePieceButton);
        buttonPanel.add(changeStatusButton);
        buttonPanel.add(viewNotStartedButton);
        buttonPanel.add(viewLearningButton);
        buttonPanel.add(viewMasteredButton);
        buttonPanel.add(loadDataButton);
        buttonPanel.add(saveDataButton);
        buttonPanel.add(exitButton);

        setActionListeners();
    }

    // EFFECTS: adds action listeners for all the buttons
    private void setActionListeners() {
        addPieceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPiece();
            }
        });

        viewRepertoireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewRepertoire();
            }

        });

        removePieceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removePiece();
            }
        });

        changeStatusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changePieceStatus();
            }
        });

        setActionListenersMore();
    }

    // EFFECTS: adds action listeners for all the buttons
    private void setActionListenersMore() {
        viewNotStartedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewNotStarted();
            }
        });

        viewLearningButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewLearning();
            }
        });

        viewMasteredButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewMastered();
            }
        });

        setActionListenersEvenMore();
    }

    // EFFECTS: adds action listeners for all the buttons
    private void setActionListenersEvenMore() {
        loadDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        saveDataButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
                System.exit(0);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds the piece to the repertoire tracker
    private void addPiece() {
        JTextField name = new JTextField(10);
        JTextField composer = new JTextField(10);
        JTextField genre = new JTextField(10);
        JTextField numPages = new JTextField(10);
        JComboBox<String> status = new JComboBox<>(new String[] { "NOT_STARTED", "LEARNING", "MASTERED" });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));
        panel.add(new JLabel("Piece Name:"));
        panel.add(name);
        panel.add(new JLabel("Composer:"));
        panel.add(composer);
        panel.add(new JLabel("Genre:"));
        panel.add(genre);
        panel.add(new JLabel("Number of Pages:"));
        panel.add(numPages);
        panel.add(new JLabel("Status:"));
        panel.add(status);
        addPieceToRepertoire(panel, name, composer, genre, numPages, status);
    }

    // MODIFIES: this
    // EFFECTS: adds the piece to the repertoire tracker
    private void addPieceToRepertoire(JPanel panel, JTextField name, JTextField composer, JTextField genre,
            JTextField numPages, JComboBox<String> status) {
        int userChoice = JOptionPane.showConfirmDialog(this, panel, "Add new Piece", JOptionPane.OK_CANCEL_OPTION);
        if (userChoice == JOptionPane.OK_OPTION) {
            String nameField = name.getText();
            String composerField = composer.getText();
            String genreField = genre.getText();
            int numPagesField = Integer.parseInt(numPages.getText());
            String statusField = (String) status.getSelectedItem();
            StatusType statusThis = StatusType.valueOf(statusField);
            Piece thisPiece = new Piece(nameField, composerField, genreField, numPagesField, statusThis);
            repertoire.addPiecetoRep(thisPiece);
            textArea.append(thisPiece.getPieceName() + " added successfully!" + "\n");
        }
    }

    // MODIFIES: this
    // EFFECTS: removes the piece from the repertoire tracker
    private void removePiece() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Enter the name of the piece you would like to remove: ");
        JTextField name = new JTextField();
        panel.add(label);
        panel.add(name);

        int userChoice = JOptionPane.showConfirmDialog(this, panel,
                "Delete Piece", JOptionPane.OK_CANCEL_OPTION);

        if (userChoice == JOptionPane.OK_OPTION) {
            String nameField = name.getText();
            repertoire.removePiece(nameField);
        }
    }

    // EFFECTS: allows user to view pieces in the repertoire tracker
    private void viewRepertoire() {
        ArrayList<Piece> pieces = repertoire.repertoireList();

        if (pieces.isEmpty()) {
            textArea.setText("");
            textArea.append("No pieces available" + "\n");
        } else {
            textArea.setText("");
            for (Piece p : pieces) {
                textArea.append("Piece - " + p.getPieceName() + " by " + p.getComposer() + "\n" + "Status - "
                        + p.getStatus() + "\n" + "Number of pages - " + p.getNumPages() + "\n" + "\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to change the status of a piece in the repertoire
    // tracker
    private void changePieceStatus() {
        JPanel status = new JPanel();
        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS));
        JLabel label = new JLabel("Enter the name of the piece whose status you want to change: ");
        JTextField name = new JTextField();
        status.add(label);
        status.add(name);

        int userChoice = JOptionPane.showConfirmDialog(this, status, "Change status", JOptionPane.OK_CANCEL_OPTION);
        if (userChoice == JOptionPane.OK_OPTION) {
            changeActualStatus(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to change the status of a piece in the repertoire
    // tracker
    private void changeActualStatus(JTextField name) {
        String nameField = name.getText();
        Piece foundPiece = repertoire.findPiece(nameField);

        JPanel statusPanel = new JPanel();
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.Y_AXIS));
        JLabel currentStatus = new JLabel("Current status: " + foundPiece.getStatus());
        JComboBox<String> statusDropDownBox = new JComboBox<>(
                new String[] { "NOT_STARTED", "LEARNING", "MASTERED" });
        statusDropDownBox.setAlignmentX(Component.LEFT_ALIGNMENT);

        statusPanel.add(currentStatus);
        statusPanel.add(new JLabel("Select new status: "));
        statusPanel.add(statusDropDownBox);

        int statusChoice = JOptionPane.showConfirmDialog(this, statusPanel, "Update Status",
                JOptionPane.OK_CANCEL_OPTION);
        if (statusChoice == JOptionPane.OK_OPTION) {
            String selected = (String) statusDropDownBox.getSelectedItem();
            foundPiece.setStatus(StatusType.valueOf(selected));
            JOptionPane.showMessageDialog(this, "Status updated successfully!");
        }
    }

    // EFFECTS: allows user to view pieces in NOT_STARTED in the repertoire tracker
    private void viewNotStarted() {
        ArrayList<Piece> piecesNotStarted = repertoire.getPiecesByStatus(StatusType.NOT_STARTED);

        if (piecesNotStarted.isEmpty()) {
            textArea.setText("");
            textArea.append("No pieces available");
        } else {
            textArea.setText("");
            for (Piece p : piecesNotStarted) {
                textArea.append("- " + p.getPieceName() + " by " + p.getComposer() + "\n" + "\n");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to view pieces in LEARNING in the repertoire tracker
    // opens new JFrame with buttons to log practice time and streak
    private void viewLearning() {
        ArrayList<Piece> piecesLearning = repertoire.getPiecesByStatus(StatusType.LEARNING);

        if (practiceFrame == null || !practiceFrame.isVisible()) {
            practiceFrame = new JFrame();
            practiceFrame.setSize(600, 400);
            practiceFrame.setLayout(new BorderLayout());

            pieceDetails = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(pieceDetails);
            practiceFrame.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton logPractice = new JButton("Log your daily practice");
            JButton logStreak = new JButton("Add to your daily practice streak");

            addActionListeneres(logPractice, logStreak, piecesLearning);

            buttonPanel.add(logPractice);
            buttonPanel.add(logStreak);
            practiceFrame.add(buttonPanel, BorderLayout.SOUTH);
            practiceFrame.setVisible(true);
        }
        pieceDetails.setText("");
        pieceDetails.append("Hours practiced for each piece: " + "\n");
        for (Piece p : piecesLearning) {
            pieceDetails.append("\n" + p.getPieceName() + " by " + p.getComposer() + ": " + "\n" + "Total Practice - "
                    + p.getTotalHoursPracticed() + "\n" + "Streak - " + p.getPracticeStreak() + "\n");
        }
    }

    // EFFECTS: adds action listeners for view in LEARNING
    private void addActionListeneres(JButton logPractice, JButton logStreak, ArrayList<Piece> piecesLearning) {
        logPractice.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logPractice(piecesLearning);
            }
        });

        logStreak.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logStreak(piecesLearning);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: allows user to add practice hours to piece in LEARNING
    private void logPractice(ArrayList<Piece> piecesLearning) {
        JPanel logPanel = new JPanel();
        logPanel.setLayout(new BoxLayout(logPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Enter the name of the piece whose practice you would like to log: ");
        JTextField name = new JTextField();
        logPanel.add(label);
        logPanel.add(name);

        int userChoice = JOptionPane.showConfirmDialog(this, logPanel,
                "Enter name of the piece", JOptionPane.OK_CANCEL_OPTION);

        if (userChoice == JOptionPane.OK_OPTION) {
            updatePractice(name, piecesLearning);
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to add practice hours to piece in LEARNING
    private void updatePractice(JTextField name, ArrayList<Piece> piecesLearning) {
        String nameField = name.getText();
        Piece foundPiece = repertoire.findPiece(nameField);

        if (piecesLearning.contains(foundPiece)) {
            JPanel practicePanel = new JPanel();
            practicePanel.setLayout(new BoxLayout(practicePanel, BoxLayout.Y_AXIS));

            JLabel addHours = new JLabel("Enter the total number of hours practiced: ");
            JTextField addHoursField = new JTextField();
            practicePanel.add(addHours);
            practicePanel.add(addHoursField);

            int practiceChoice = JOptionPane.showConfirmDialog(this, practicePanel, "Update practice",
                    JOptionPane.OK_CANCEL_OPTION);
            if (practiceChoice == JOptionPane.OK_OPTION) {
                int practiceHoursInInt = Integer.parseInt(addHoursField.getText());
                foundPiece.addTotalHoursPracticed(practiceHoursInInt);
                viewLearning();
            }
        } else {
            JOptionPane.showMessageDialog(this, "This piece does not exist!");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to log practice streak to piece in LEARNING
    private void logStreak(ArrayList<Piece> piecesLearning) {
        JPanel streakPanel = new JPanel();
        streakPanel.setLayout(new BoxLayout(streakPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Enter the name of the piece to update the streak: ");
        JTextField name = new JTextField();
        streakPanel.add(label);
        streakPanel.add(name);

        int userChoice = JOptionPane.showConfirmDialog(this, streakPanel, "Log streak",
                JOptionPane.OK_CANCEL_OPTION);

        if (userChoice == JOptionPane.OK_OPTION) {
            String nameField = name.getText();
            Piece foundPiece = repertoire.findPiece(nameField);
            if (piecesLearning.contains(foundPiece)) {
                foundPiece.incramentPracticeStreak();
                JOptionPane.showMessageDialog(this, "Your streak is now: " + foundPiece.getPracticeStreak());
                viewLearning();
            } else {
                JOptionPane.showMessageDialog(this, "This piece does not exist!");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to view pieces in MASTERED
    // opens new JFrame for user to add rating
    private void viewMastered() {
        ArrayList<Piece> piecesMastered = repertoire.getPiecesByStatus(StatusType.MASTERED);

        if (ratingFrame == null || !ratingFrame.isVisible()) {
            ratingFrame = new JFrame();
            ratingFrame.setSize(600, 400);
            ratingFrame.setLayout(new BorderLayout());
            pieceDetails = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(pieceDetails);
            ratingFrame.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel();
            JButton addRating = new JButton("Add a difficulty rating");

            addActionListeneresForMastered(addRating, piecesMastered);
            buttonPanel.add(addRating);
            ratingFrame.add(buttonPanel, BorderLayout.SOUTH);
            ratingFrame.setVisible(true);
        }
        pieceDetails.setText("");
        pieceDetails.append("Rating for each piece: " + "\n");
        for (Piece p : piecesMastered) {
            pieceDetails.append("\n" + p.getPieceName() + " by " + p.getComposer() + ": " + "\n" + "Rating - "
                    + p.getRating() + "/5" + "\n");
        }
    }

    // EFFECTS: adds action listeners for MASTERED pieces
    private void addActionListeneresForMastered(JButton addRating, ArrayList<Piece> piecesMastered) {
        addRating.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addRating(piecesMastered);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: adds rating for piece in MASTERED
    private void addRating(ArrayList<Piece> piecesMastered) {
        JPanel ratingPanel = new JPanel();
        ratingPanel.setLayout(new BoxLayout(ratingPanel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Enter the name of the piece whose rating you would like to set: ");
        JTextField name = new JTextField();
        ratingPanel.add(label);
        ratingPanel.add(name);

        int userChoice = JOptionPane.showConfirmDialog(this, ratingPanel, "Enter the name of the piece",
                JOptionPane.OK_CANCEL_OPTION);

        if (userChoice == JOptionPane.OK_OPTION) {
            updateRating(name);
        }
    }

    // MODIFIES: this
    // EFFECTS: adds rating for piece in MASTERED
    private void updateRating(JTextField name) {
        String nameField = name.getText();
        Piece foundPiece = repertoire.findPiece(nameField);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel addRating = new JLabel("Enter the rating for the piece: ");
        JComboBox<String> ratingDropBox = new JComboBox<>(new String[] {
                "0", "1", "2", "3", "4", "5" });

        panel.add(addRating);
        panel.add(ratingDropBox);

        int ratingChoice = JOptionPane.showConfirmDialog(this, panel, "Update rating", JOptionPane.OK_CANCEL_OPTION);
        if (ratingChoice == JOptionPane.OK_OPTION) {
            int rating = Integer.parseInt((String) ratingDropBox.getSelectedItem());
            foundPiece.setRating(rating);
            viewMastered();
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to save data to repertoire file
    private void saveData() {
        try {
            writer.open();
            writer.write(repertoire);
            writer.close();
            JOptionPane.showMessageDialog(this, "Repertoire has been saved to:" + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write to file " + JSON_STORE);
        }
    }

    // EFFECTS: allows user to load data from repertoire file
    private void loadData() {
        try {
            repertoire = reader.read();
            JOptionPane.showMessageDialog(this, "Loaded repertoire from: " + JSON_STORE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE);
        }
    }
}
