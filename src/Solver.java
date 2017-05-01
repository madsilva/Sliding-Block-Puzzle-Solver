import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Solver {
    // args[0] is the filename of the initial puzzle configuration
    // args[1] is the filename of the goal	
    public static void main(String[] args) {
        
        
        String[] oarguments = null;
        String[] arguments = null;
        for (String arg : args){
            // help menu if the first argument is help, exits program, assumes -h will be only argument
            if (arg.equals("-h")){
                System.out.println("No options implemented (yet)");
                System.exit(0);
            }
            // finds main arguments
            if (!arg.substring(0, 1).equals("-")){
                // separates arguments into separate arrays
                if (!arg.equals(args[0]))
                    oarguments = Arrays.copyOfRange(args, 0, Arrays.asList(args).indexOf(arg));
                arguments = Arrays.copyOfRange(args, Arrays.asList(args).indexOf(arg), args.length);
                // stops looping
                break;
            }
        }
        
        // prints all optional arguments for debugging purposes
        if (oarguments != null){
            for (String arg : oarguments){
                System.out.println(arg);
            }
        }
        
        try {
            
            // Intializing the game tray
            BufferedReader in1 = new BufferedReader(new FileReader(arguments[0]));
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
            BufferedReader in2 = new BufferedReader(new FileReader(arguments[1]));
            line = in2.readLine();
            while (line != null) {
                goal.addBlock(line);
                line = in2.readLine();
            }
            in2.close();
                        
            // if the goal is already met by the given tray, no action is taken 
            // and the program terminates normally.
            if (game.checkGoal(goal)) {
                System.exit(0);
            } 
            // if the goal is not met, attempt to generate a list of solution moves
            // using the game tray's solve method.
            else {
                ArrayList<int[]> moves = game.solve(goal);
                // if solve doesn't return any moves, the puzzle is impossible and
                // the program terminates with an error
                if (moves.isEmpty()) {
                    System.exit(1);
                }
                else {
                    // print out the generated solution moves. 
                    for (int[] move : moves) {
                        System.out.println(move[0] + " " + move[1] + " " + move[2] + " " + move[3]);
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Invalid Argument");
        }  
    }	
}
