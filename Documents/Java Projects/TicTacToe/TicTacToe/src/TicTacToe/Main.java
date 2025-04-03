package TicTacToe;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class Main extends Application{
    public String currentPlayerLabel = "X";
    Label turnOrder = new Label("Turn");

    // buttons for the tic tac toe grid
    Button[][] buttons = new Button[5][5];


    @Override
    // what is in the window
    public void start(Stage primaryStage) {
        primaryStage.setTitle("2 Player Tic Tac Toe");
        
        turnOrder.setFont(Font.font("System", FontWeight.BOLD, 30));

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10)); // makes it so the lines don't take up the whole window
        grid.setHgap(5);
        grid.setVgap(5);

        grid.setStyle("-fx-background-color:lightgray;");
                
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                buttons[row][col] = new Button (" ");
                buttons[row][col].setPrefSize(100, 100);
                buttons[row][col].setFont(Font.font("System", FontWeight.BOLD, 30));
                final int r = row;
                final int c = col;
                buttons[row][col].setOnAction(e -> markBoard(r, c));
                grid.add(buttons[row][col], col, row); // this takes the size from
            }
        }

        // create a vbox to put the tic tac toe board in the bottom center
        // this codes ensures the turn order text is aligned in the middle of the blank spcae above the grid
        Region spaceAbove = new Region();
        Region spaceBelow = new Region();
        VBox.setVgrow(spaceAbove, Priority.ALWAYS);
        VBox.setVgrow(spaceBelow, Priority.ALWAYS);

        VBox vbox = new VBox(spaceAbove, turnOrder, spaceBelow, grid);
        vbox.setAlignment(Pos.BOTTOM_CENTER);

        
        Scene scene = new Scene(vbox, 500, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // marking the board and checking for the winner
    public void markBoard(int row, int col) {
        if (buttons[row][col].getText().equals(" ")) { // if the box is empty the player will mark it
            buttons[row][col].setText(currentPlayerLabel);
            if (checkWin()) {
                turnOrder.setText(currentPlayerLabel + " Wins!");
                winningLine();
            } else if (boardFullCheck()) {
                turnOrder.setText("It's a draw!");
            } else {
                // go to the next players turn
                currentPlayerLabel = currentPlayerLabel.equals("X") ? "O" : "X"; // if the player is X, it turns to O, if not, it stays X
                // for some reason an if statement wouldn't work here
                // something about setText and getText not being defined
            }
        }
    }

    private boolean boardFullCheck() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (buttons[row][col].getText().equals(" ")) {
                    return false;
                }
            }
        }
        return true;
    }

    // check if someone has won
    private boolean checkWin() {
        // check if a player has won horizontally
        for (int row = 0; row < 5; row++) {
            if (buttons[row][0].getText().equals(currentPlayerLabel) && buttons[row][1].getText().equals(currentPlayerLabel) && buttons[row][2].getText().equals(currentPlayerLabel) && buttons[row][3].getText().equals(currentPlayerLabel) && buttons[row][4].getText().equals(currentPlayerLabel)) {
                winningLine(row, 0, row, 1, row, 2, row, 3, row, 4);
                return true;
            }
        }

        // check vertically
        for (int col = 0; col < 5; col++) {
            if (buttons[col][0].getText().equals(currentPlayerLabel) && buttons[col][1].getText().equals(currentPlayerLabel) && buttons[col][2].getText().equals(currentPlayerLabel) && buttons[col][3].getText().equals(currentPlayerLabel) && buttons[col][4].getText().equals(currentPlayerLabel)) {
                winningLine(col, 0, col, 1, col, 2, col, 3, col, 4);
                return true;
            }
        }

        // check horizontally
        if (buttons[0][0].getText().equals(currentPlayerLabel) && buttons[1][1].getText().equals(currentPlayerLabel) && buttons[2][2].getText().equals(currentPlayerLabel) && buttons[3][3].getText().equals(currentPlayerLabel) && buttons[4][4].getText().equals(currentPlayerLabel)) {
            winningLine(0, 0, 1, 1, 2, 2, 3, 3, 4, 4);
            return true;
        }

        // check horizontally the other way
        if (buttons[0][4].getText().equals(currentPlayerLabel) && buttons[1][3].getText().equals(currentPlayerLabel) && buttons[2][2].getText().equals(currentPlayerLabel) && buttons[3][1].getText().equals(currentPlayerLabel) && buttons[4][0].getText().equals(currentPlayerLabel)) {
            winningLine(0, 4, 1, 3, 2, 2, 3, 1, 4, 0);
            return true;
        }

        return false;
    }

    public void winningLine(int... boardSpot) {
        for (int row = 0; row < 5; row++) {
            normalLoop:
            for (int col = 0; col < 5; col++) {
                for (int i = 0; i < boardSpot.length; i += 2) {
                    buttons[row][col].setDisable(true);
                    if (row == boardSpot[i] && col == boardSpot[i+1]) {
                        buttons[row][col].setStyle("-fx-background-color:lightgreen;");
                        buttons[row][col].setDisable(false);
                        continue normalLoop;
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
