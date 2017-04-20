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
            Tray game = new Tray(Integer.parseInt(line.substring(0,1)), Integer.parseInt(line.substring(2)));
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
            if (game.checkGoal(goal)) {
                System.out.println("already solved"); // not correct action
            } 
            else {
                ArrayList<int[]> moves = game.solve(goal);
                if (moves.isEmpty()) {
                     System.out.println("impossible to solve");
                    // exits the game if theres no solution
                    // commented out because theres no solve method yet
                    // System.exit(1);
                }
                else {
                    for (int[] move : moves) {
                        System.out.println(move[0] + " " + move[1] + " " + move[2] + " " + move[3]);
                    }
                }
            }
        }
        catch (IOException e) { // is this the right exception?
            System.out.println("invalid file");
        }  
    }	
}
