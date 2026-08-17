package ui;

import model.Piece;
import model.RepertoireTracker;
import model.StatusType;
import persistence.JsonReader;
import persistence.JsonWriter;

import static model.StatusType.NOT_STARTED;
import static model.StatusType.LEARNING;
import static model.StatusType.MASTERED;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// Represents the console-based UI for the Digital Repertoire Tracker
// Handles interaction by the user to add, remove, and modify aspects of a piece.
// Allows user to view pieces based on its features, and relies on the Piece and
// RepertoireTracker class to retrieve data
public class RepertoireApp {
    private static final String JSON_STORE = "./data/repertoire.json";
    private RepertoireTracker repertoire;
    private Scanner userInput;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs the repertoire and runs application
    public RepertoireApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runRepertoire();

    }

    // EFFECTS: initializes the repertoire
    private void init() {
        repertoire = new RepertoireTracker();
        userInput = new Scanner(System.in);
    }

    // MODIFIES: this
    // EFFECTS: processes the user input
    private void runRepertoire() {
        boolean isRunning = true;
        init();

        while (isRunning) {
            displayMenu();
            String input = userInput.next();
            userInput.nextLine();

            if (input.equals("10")) {
                isRunning = false;

            } else {
                processUserInput(input);
            }

        }
        System.out.println("Keep practicing!");

    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\n--Welcome to your Repertoire!--");
        System.out.println("1. Add a new piece");
        System.out.println("2. Remove a piece");
        System.out.println("3. View your pieces");
        System.out.println("4. Update the piece status");
        System.out.println("5. View pieces in NOT STARTED");
        System.out.println("6. View pieces in LEARNING");
        System.out.println("7. View pieces in MASTERED");
        System.out.println("8. Save repertoire to file");
        System.out.println("9. Load repertoire from file");
        System.out.println("10. Quit");
    }

    // EFFECTS: processes the user's input from the menu
    @SuppressWarnings("methodlength")
    private void processUserInput(String input) {
        switch (input) {
            case "1":
                addPiece();
                break;
            case "2":
                removePiece();
                break;
            case "3":
                viewPieces();
                break;
            case "4":
                updatePieceStatus();
                break;
            case "5":
                viewPiecesinNotStarted();
                break;
            case "6":
                viewPiecesinLearning();
                break;
            case "7":
                viewPiecesinMastered();
                break;
            case "8":
                saveRepertoire();
                break;
            case "9":
                loadRepertoire();
                break;
            default:
                System.out.println("Invalid User Input. Please try again.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new piece
    private void addPiece() {
        System.out.println("Enter the name of the piece: ");
        String name = userInput.nextLine();
        System.out.println("\nEnter the name of the composer: ");
        String composer = userInput.nextLine();
        System.out.println("\nEnter the genre of the piece: ");
        String genre = userInput.nextLine();
        System.out.println("\nEnter the number of pages: ");
        int numPages = userInput.nextInt();
        userInput.nextLine();

        Piece newPiece = new Piece(name, composer, genre, numPages, null);
        setNewPieceStatus(newPiece);
    }

    // MODIFIES: this
    // EFFECTS: sets the piece status to add into the repertoire
    private void setNewPieceStatus(Piece newPiece) {
        StatusType status = null;
        while (status == null) {
            System.out.println("\nSelect the status of the piece: \n - NOT STARTED \n - LEARNING \n - MASTERED");
            String statusChoice = userInput.nextLine();

            switch (statusChoice) {
                case "NOT STARTED":
                    status = StatusType.NOT_STARTED;
                    break;
                case "LEARNING":
                    status = StatusType.LEARNING;
                    break;
                case "MASTERED":
                    status = StatusType.MASTERED;
                    break;
                default:
                    System.out.println("\nInvalid user input. Please try again");
            }
        }
        newPiece.setStatus(status);
        repertoire.addPiecetoRep(newPiece);
        System.out.println("\nPiece has successfully been added under the repertoire: " + status);
    }

    // REQUIRES: the piece has to be in the repertoire already
    // MODIFIES: this
    // EFFECTS: remove a piece from the repertoire
    private void removePiece() {
        System.out.println("\nEnter the name of the piece you would like to remove: ");
        String name = userInput.nextLine();
        if (repertoire.removePiece(name)) {
            System.out.println(name + " has been removed successfully");
        } else {
            System.out.println("\nInvalid input, piece " + name + " does not exist.");
        }
    }

    // EFFECTS: user can view all pieces from all repertoire lists
    private void viewPieces() {
        if (repertoire.repertoireList().isEmpty()) {
            System.out.println("Your repertoire is empty");
        } else {
            System.out.println("\nYour repertoire list: ");
            for (Piece p : repertoire.repertoireList()) {
                System.out.println("- " + p.getPieceName() + " by " + p.getComposer());
            }
            System.out.println("\nHow would you like to view your pieces?");
            System.out.println(" A. Filter by Composer \n B. Filter by Genre \n C. Filter by total number of pages");
            String choice = userInput.nextLine();
            processInputFromViewPiecesByType(choice);
        }
    }

    // EFFECTS: allows user to choose how to view pieces from the repertoire
    private void processInputFromViewPiecesByType(String choice) {
        switch (choice) {
            case "A":
                viewPiecesByComposer();
                break;
            case "B":
                viewPiecesByGenre();
                break;
            case "C":
                viewPiecesByTotalNumberOfPages();
                break;
            default:
                System.out.println("Invalid user input. Please try again");
        }
    }

    // EFFECTS: allows user to filter pieces by composer
    private void viewPiecesByComposer() {
        System.out.println("\nWhich composer's pieces would you like to view?");
        String composerChoice = userInput.nextLine();
        List<Piece> onlyComposerChoice = repertoire.getPiecesByComposer(composerChoice);

        if (onlyComposerChoice.isEmpty()) {
            System.out.println("\nThere are no pieces for this composer");
        } else {
            System.out.println("\nThe pieces by the composer " + composerChoice + " are: ");
            for (Piece p : onlyComposerChoice) {
                System.out.println("- " + p.getPieceName() + " composed in the " + p.getGenre() + " era ");
            }
        }
    }

    // EFFECTS: allows user to filter pieces by genre
    private void viewPiecesByGenre() {
        System.out.println("\nWhich genre of pieces would you like to view?");
        String genreChoice = userInput.nextLine();
        List<Piece> onlyGenreChoice = repertoire.getPiecesInGenre(genreChoice);

        if (onlyGenreChoice.isEmpty()) {
            System.out.println("\nThere are no pieces in this genre");
        } else {
            System.out.println("\nThe pieces in the " + genreChoice + " genre are: ");
            for (Piece p : onlyGenreChoice) {
                System.out.println("- " + p.getPieceName() + " by " + p.getComposer());
            }
        }
    }

    // EFFECTS: allows user to filter pieces by total number of pages
    private void viewPiecesByTotalNumberOfPages() {
        System.out.println("\nWhat is the maximum number of pages in a piece you would like to view?");
        String pagesChoice = userInput.nextLine();
        int maxPages = Integer.parseInt(pagesChoice);
        List<Piece> onlyPagesChoice = repertoire.getPiecesByTotalNumOfPages(maxPages);

        if (onlyPagesChoice.isEmpty()) {
            System.out.println("\nThere are no pieces with less than " + maxPages + " number of pages");
        } else {
            System.out.println("\nThe pieces with less than " + maxPages + " pages are: ");
            for (Piece p : onlyPagesChoice) {
                System.out.println("- " + p.showPiece());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: updates the piece status
    @SuppressWarnings("methodlength")
    private void updatePieceStatus() {
        System.out.println("\nEnter the name of the piece whose status would like to update: ");
        String name = userInput.nextLine();
        Piece piece = repertoire.findPiece(name);
        if (piece != null) {
            System.out.println("\nThe current status is: " + piece.getStatus());
            System.out.println(
                    "\nChoose the status to update the piece to: \n - NOT STARTED \n - LEARNING \n - MASTERED");
            String statusChoice = userInput.nextLine();
            switch (statusChoice) {
                case "NOT STARTED":
                    repertoire.updateStatus(piece, NOT_STARTED);
                    break;
                case "LEARNING":
                    repertoire.updateStatus(piece, LEARNING);
                    break;
                case "MASTERED":
                    repertoire.updateStatus(piece, MASTERED);
                    piece.setRating(0);
                    break;
                default:
                    System.out.println("\nInvalid User Input. Please try again.");
                    return;
            }
            System.out.println("\nStatus for " + name + " has successfully been changed to " + statusChoice);
        } else {
            System.out.println("\nInvalid User Input. This piece does not exist.");
        }
    }

    // EFFECTS: view all pieces in repertoire NOT_STARTED
    private void viewPiecesinNotStarted() {
        List<Piece> piecesInNotStarted = repertoire.getPiecesByStatus(NOT_STARTED);
        if (piecesInNotStarted.isEmpty()) {
            System.out.println("\nThere are no pieces in NOT STARTED");
        } else {
            System.out.println("\nThe pieces in the NOT STARTED repertoire are:");
            for (Piece p : piecesInNotStarted) {
                System.out.println("- " + p.showPiece());
            }
        }
    }

    // EFFECTS: view all pieces in repertoire LEARNING
    private void viewPiecesinLearning() {
        List<Piece> piecesInLearning = repertoire.getPiecesByStatus(LEARNING);
        if (piecesInLearning.isEmpty()) {
            System.out.println("\nThere are no pieces in LEARNING");
            return;
        } else {
            System.out.println("\nThe pieces in the LEARNING repertoire are:");
            for (Piece p : piecesInLearning) {
                System.out.println("- " + p.showPiece());
            }
            System.out.println(" \n Choose from the options below: ");
            System.out.println("A. Log in practice hours for the day");
            System.out.println("B. Add to your practice streak");

            String choice = userInput.nextLine();
            processInputFromLearning(choice);
        }
    }

    // EFFECTS: view all pieces in repertoire MASTERED
    private void viewPiecesinMastered() {
        List<Piece> piecesInMastered = repertoire.getPiecesByStatus(MASTERED);
        if (piecesInMastered.isEmpty()) {
            System.out.println("\nThere are no pieces in MASTERED");
            return;
        } else {
            System.out.println("\nThe pieces in the MASTERED repertoire are: ");
            for (Piece p : piecesInMastered) {
                System.out.println("\n- " + p.showPiece());
                System.out.println("Difficulty: " + p.getRating() + "/5");
            }
            System.out.println(" \n Which of these options would you like to update? ");
            System.out.println("A. Set rating for a piece");
            System.out.println("B. Filter pieces by rating");
            String choice = userInput.nextLine();
            processInputFromMastered(choice);
        }
    }

    // EFFECTS: processes input from learning
    private void processInputFromLearning(String choice) {
        switch (choice) {
            case "A":
                logPractice();
                break;
            case "B":
                logPracticeStreak();
                break;
            default:
                System.out.println("Invalid User Input. Please try again");
        }
    }

    // EFFECTS: processes input from mastered
    private void processInputFromMastered(String choice) {
        switch (choice) {
            case "A":
                setOfficialRating();
                break;
            case "B":
                filterPiecesByRating();
                break;
            default:
                System.out.println("Invalid User Input. Please try again");
        }
    }

    // REQUIRES: piece has to be in the LEARNING list
    // MODIFIES: this
    // EFFECTS: logs practice for piece
    private void logPractice() {
        System.out.println("\nEnter the name of the piece to log practice hours for: ");
        String name = userInput.nextLine();
        Piece piece = repertoire.findPiece(name);
        if (piece != null && piece.getStatus().equals(LEARNING)) {
            System.out.println("\nYou have worked on this piece for: " + piece.getTotalHoursPracticed() + " hours");
            System.out.println("\nHow many hours did you practice for?");
            String input = userInput.nextLine();
            int practiced = Integer.parseInt(input);
            piece.addTotalHoursPracticed(practiced);
            System.out.println("\nPractice has been logged! You have now worked on this piece for: "
                    + piece.getTotalHoursPracticed() + " hours!");
        } else {
            System.out.println("\nThis piece does not exist in the LEARNING repertoire, please try again.");
        }
    }

    // REQUIRES: piece has to be in the LEARNING list
    // MODIFIES: this
    // EFFECTS: logs practice streak for piece
    private void logPracticeStreak() {
        System.out.println("\nEnter the name of the piece to log the practice streak for: ");
        String name = userInput.nextLine();
        Piece piece = repertoire.findPiece(name);
        if (piece == null || !piece.getStatus().equals(LEARNING)) {
            System.out.println("\nThis piece does not exist in the LEARNING repertoire, please try again.");
        } else {
            piece.incramentPracticeStreak();
            System.out.println("\nYour practice streak for " + name + " is now " + piece.getPracticeStreak());
        }
    }

    // REQUIRES: piece has to be in the LEARNING list
    // EFFECTS: filters pieces by rating
    private void filterPiecesByRating() {
        System.out.println("\nWhat rating of pieces would you like to see? ");
        String ratingChoice = userInput.nextLine();
        int choice = Integer.parseInt(ratingChoice);
        List<Piece> piecesInMastered = repertoire.getPiecesByStatus(MASTERED);

        int count = 0;
        System.out.println("The pieces that are rated " + choice + " are: ");
        for (Piece p : piecesInMastered) {
            if (p.getRating() == choice) {
                System.out.println("- " + p.showPiece());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("\nThere are no pieces with this rating");
        }
    }

    // REQUIRES: piece has to be in the MASTERED list
    // MODIFIES: this
    // EFFECTS: sets the rating for the piece when moved to the MASTERED list
    private void setOfficialRating() {
        System.out.println("\nEnter the name of the piece to rate its difficulty: ");
        String name = userInput.nextLine();
        Piece piece = repertoire.findPiece(name);
        if (piece != null) {
            System.out.println("\nWhat would you like to rate the piece " + name + " out of 5?");
            String input = userInput.nextLine();
            int rating = Integer.parseInt(input);
            piece.setRating(rating);
            System.out.println("\nThe piece " + name + " has been rated " + piece.getRating() + "/5");
        } else {
            System.out.println("\nThis piece does not exist, please try again.");
        }
    }

    // EFFECTS: saves the repertoire to file
    // referenced JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
    private void saveRepertoire() {
        try {
            jsonWriter.open();
            jsonWriter.write(repertoire);
            jsonWriter.close();
            System.out.println("\nSaved repertoire to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("\nUnable to write to file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads repertoire from file
    // referenced JsonSerializationDemo (https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git)
    private void loadRepertoire() {
        try {
            repertoire = jsonReader.read();
            System.out.println("\nLoaded repertoire from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("\nUnable to read from file: " + JSON_STORE);
        }
    }
}
