import java.util.Scanner;

import TicTacToe.src.main.TicTacToe;

public class Main {
    public static void main(String[] args) {
        int numPlayers = 0;
        int winCond = 0;

        Scanner in = new Scanner(System.in);
        while (!(3 <= numPlayers && numPlayers <= 10)) {
            System.out.print("Enter number of players (3 - 10): ");
            if (in.hasNextInt()) {
                numPlayers = in.nextInt();
                in.nextLine();
            }
            else {
                System.out.println("Please enter a valid number!\n\n");
                in.nextLine();
                continue;
            }
        }

        while (!(3 <= winCond && winCond <= numPlayers+1)) {
            System.out.print(String.format("Enter win condition (3 - %d): ", numPlayers+1));
            if (in.hasNextInt()) {
                winCond = in.nextInt();
                in.nextLine();
            }
            else {
                System.out.println("Please enter a valid number!\n\n");
                in.nextLine();
                continue;
            }
        }

        TicTacToe game = new TicTacToe(numPlayers+1, numPlayers+1, winCond);
        int cond = -1;
        while (cond != 0) {
            System.out.print("Enter letters to represent each player seperated by a comma: ");
            cond = game.setSymbols(in.nextLine());

            if (cond == TicTacToe.UNDERTOTAL) {
                System.out.println("\n\nNot enough symbols!");
            }
            else if (cond == TicTacToe.OVERTOTAL) {
                System.out.println("\n\nToo much symbols!");
            }
            else if (cond == TicTacToe.ONLYLETTERS) {
                System.out.println("\n\nOnly letters!");
            }
        }
        
        int row = 0;
        int col = 0;
        int turn = 0;
        char status = 0;
        while (status == 0) {
            System.out.println(game.printPlayerGrid());

            System.out.println(String.format("\"%c\"'s turn", game.getSymbols()[turn]));
            String selection = "";
            System.out.print("Enter selection (Ex. A0): ");
            selection = in.nextLine();
            row = selection.charAt(1) - '0' - 1;
            col = selection.charAt(0) - 'A';
                
            while (!game.addSymbol(game.getSymbols()[turn], row, col)) {
                System.out.print("Enter selection (Ex. A0): ");
                selection = in.nextLine();
                row = selection.charAt(1) - '0' - 1;
                col = selection.charAt(0) - 'A';
            }

            if (status == TicTacToe.FILLED) {
                System.out.println(game.printPlayerGrid());
                System.out.println("Board filled! No one wins!");
                break;
            }

            if (turn < game.getSymbols().length - 1) {
                turn++;
            }
            else {
                turn = 0;
            }

            status = game.checkStatus(row, col);
        }

        if (status != TicTacToe.FILLED) {
            System.out.println("\n\n" + game.printPlayerGrid());
            System.out.println(String.format("\"%c\" has WON!!", status));
        }

        in.close();
    }
}
