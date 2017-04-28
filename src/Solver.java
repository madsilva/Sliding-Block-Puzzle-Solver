import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// I removed all checks for invalid data so hopefully we can assume that data read in will be good.

public class Solver {
    // args[0] is the filename of the initial puzzle configuration
    // args[1] is the filename of the goal	
    public static void main(String[] args) {
        
        // implement option debugging messages?
        
        // i really dont know whats supposed to be in try and what isnt
        try {
            // should we make a fillTray method so this code doesn't look so redundant?
            
            // Intializing the game tray
            BufferedReader in1 = new BufferedReader(new FileReader(args[0]));
            String line = in1.readLine();
            String[] lineVals = line.split(" ");
            Tray game = new Tray(Integer.parseInt(lineVals[0]), Integer.parseInt(lineVals[1]));
            line = in1.readLine();
            while (line != null) {
                game.addBlock(line);
                line = in1.readLine();
            }
            in1.close();
            // Intializing the goal tray
            Tray goal = new Tray(game.getRows(), game.getCols());
            BufferedReader in2 = new BufferedReader(new FileReader(args[1]));
            line = in2.readLine();
            while (line != null) {
                goal.addBlock(line);
                line = in2.readLine();
            }
            in2.close();
            
            // debugging prints
            System.out.println(game.printTray());
            System.out.println(goal.printGoal());
            
            // make all this less ugly idk
            // if the goal is already met by the given tray, no action is taken 
            // and the program terminates normally.
            if (game.checkGoal(goal)) {
                System.out.println("already solved"); // debug output
                System.exit(0);
            } 
            // if the goal is not met, attempt to generate a list of solution moves
            // using the game tray's solve method.
            else {
                ArrayList<int[]> moves = game.solve(goal);
                // if solve doesn't return any moves, the puzzle is impossible and
                // the program terminates with an error
                if (moves.isEmpty()) {
                    System.out.println("impossible to solve"); // debug output
                    //commented out because theres no working solve method yet
                    //System.exit(1);
                }
                else {
                    // print out the generated solution moves. 
                    for (int[] move : moves) {
                        System.out.println(move[0] + " " + move[1] + " " + move[2] + " " + move[3]);
                    }
                    System.out.println("number of moves: " + moves.size());
                }
            }
        }
        catch (IOException e) { // is this the right exception?
            System.out.println("File could not be opened.");
        }  
    }	
}
