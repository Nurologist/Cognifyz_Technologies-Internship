import java.util.Scanner;

public class TicTacToe {
    private static final char EMPTY = '-';
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain;

        do {
            char[][] board = initializeBoard();
            char currentPlayer = PLAYER_X;
            boolean gameWon = false;
            boolean isDraw = false;

            System.out.println("Welcome to Tic-Tac-Toe!");

            while (!gameWon && !isDraw) {
                printBoard(board);
                System.out.println("Player " + currentPlayer + ", it's your turn.");

                int row, col;
                while (true) {
                    System.out.print("Enter row (0-2): ");
                    row = scanner.nextInt();
                    System.out.print("Enter column (0-2): ");
                    col = scanner.nextInt();

                    if (isValidMove(board, row, col)) {
                        break;
                    } else {
                        System.out.println("Invalid move. Try again.");
                    }
                }

                makeMove(board, row, col, currentPlayer);
                gameWon = checkWin(board, currentPlayer);
                isDraw = checkDraw(board);

                if (!gameWon && !isDraw) {
                    currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
                }
            }

            printBoard(board);
            if (gameWon) {
                System.out.println("Player " + currentPlayer + " wins!");
            } else {
                System.out.println("It's a draw!");
            }

            System.out.print("Do you want to play again? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");
        } while (playAgain);

        System.out.println("Thanks for playing Tic-Tac-Toe!");
        scanner.close();
    }

    private static char[][] initializeBoard() {
        char[][] board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
        return board;
    }

    private static void printBoard(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    private static boolean isValidMove(char[][] board, int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == EMPTY;
    }

    private static void makeMove(char[][] board, int row, int col, char player) {
        board[row][col] = player;
    }

    private static boolean checkWin(char[][] board, char player) {
        // Check rows and columns
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == player && board[i][1] == player && board[i][2] == player) ||
                (board[0][i] == player && board[1][i] == player && board[2][i] == player)) {
                return true;
            }
        }

        // Check diagonals
        return (board[0][0] == player && board[1][1] == player && board[2][2] == player) ||
               (board[0][2] == player && board[1][1] == player && board[2][0] == player);
    }

    private static boolean checkDraw(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }
}
