import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Pexeso {
    private static final int BOARD_SIZE = 4;
    private static final char HIDDEN = '*';
    private static char[][] board = new char[BOARD_SIZE][BOARD_SIZE];
    private static boolean[][] revealed = new boolean[BOARD_SIZE][BOARD_SIZE];
    private static ArrayList<Character> cards = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        displayWelcomeScreen();
        initializeGame();
        
        boolean playAgain;
        do {
            playGame();
            playAgain = askToPlayAgain();
            resetGame();
        } while (playAgain);
        
        System.out.println("Děkujeme za hraní!");
    }
    
    private static void displayWelcomeScreen() {
        System.out.println("Vítejte ve hře Pexeso!");
        System.out.println("Vaším úkolem je najít všechny dvojice stejných karet tím, že je budete postupně otáčet.");
        System.out.println("Hrajete na matici 4x4, zadáváte souřadnice karet v podobě: řádek;sloupec (např. 2;3).\n");
    }
    
    private static void initializeGame() {
        // Připravíme karty (8 dvojic)
        for (char c = 'A'; c < 'A' + (BOARD_SIZE * BOARD_SIZE) / 2; c++) {
            cards.add(c);
            cards.add(c);
        }
        // Zamícháme karty
        Collections.shuffle(cards);
        
        // Naplníme desku kartami a vše skryjeme
        int index = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = cards.get(index++);
                revealed[i][j] = false;
            }
        }
    }
    
    private static void playGame() {
        int moves = 0;
        int pairsFound = 0;
        
        while (pairsFound < (BOARD_SIZE * BOARD_SIZE) / 2) {
            printBoard();
            int[] firstMove = getMove();
            revealCard(firstMove[0], firstMove[1]);
            printBoard();
            
            int[] secondMove = getMove();
            revealCard(secondMove[0], secondMove[1]);
            printBoard();
            
            // Kontrola, zda se karty shodují
            if (board[firstMove[0]][firstMove[1]] == board[secondMove[0]][secondMove[1]]) {
                System.out.println("Nalezli jste shodný pár!");
                pairsFound++;
            } else {
                System.out.println("Karty se neshodují. Znovu je skryjeme.");
                hideCard(firstMove[0], firstMove[1]);
                hideCard(secondMove[0], secondMove[1]);
            }
            moves++;
        }
        
        System.out.println("Gratulujeme, našli jste všechny páry! Počet tahů: " + moves);
    }
    
    private static void printBoard() {
        System.out.println("\nHerní deska:");
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (revealed[i][j]) {
                    System.out.print(board[i][j] + " ");
                } else {
                    System.out.print(HIDDEN + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
    
    private static int[] getMove() {
        int row = -1, col = -1;
        while (true) {
            System.out.print("Zadejte souřadnice (řádek;sloupec): ");
            String input = scanner.nextLine();
            String[] parts = input.split(";");
            if (parts.length == 2) {
                try {
                    row = Integer.parseInt(parts[0]) - 1;
                    col = Integer.parseInt(parts[1]) - 1;
                    if (row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && !revealed[row][col]) {
                        break;
                    }
                } catch (NumberFormatException e) {
                    // Ignorujeme chybné formátování
                }
            }
            System.out.println("Neplatné souřadnice, zkuste to znovu.");
        }
        return new int[]{row, col};
    }
    
    private static void revealCard(int row, int col) {
        revealed[row][col] = true;
    }
    
    private static void hideCard(int row, int col) {
        revealed[row][col] = false;
    }
    
    private static boolean askToPlayAgain() {
        System.out.print("Chcete hrát znovu? (ano/ne): ");
        String answer = scanner.nextLine().toLowerCase();
        return answer.equals("ano");
    }
    
    private static void resetGame() {
        cards.clear();
        initializeGame();
    }
}
