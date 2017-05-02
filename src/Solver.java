import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/*
TODO:
need to implement some possible options 
ideas for options:
-omaxmemory
return max memory used
how to do this? use runtime.memory, check that every time we pop off queue, if memory currently used is > max memory, set max to that


for totaltrays and maxmemory, make values in tray to hold them and keep track as we solve, then in solver, print them if requested

need to make sure inputted arguments are valid before doing things with them
*/
public class Solver {
    // args[n-1] is the filename of the initial puzzle configuration
    // args[n] is the filename of the goal	
    public static void main(String[] args) {
        // flags for -o args
        boolean totalMoves = false;
        boolean totalTrays = false;
        boolean maxMemory = false;
        
        String[] oArguments = null;
        String[] fileArguments = null;
        for (String arg : args){
            // help menu if the first argument is help, exits program, assumes -h will be only argument
            if (arg.equals("-h")){
                String output = "Optional arguments:\n";
                output += "-ototalmoves: Prints the total number of moves in the solution if one is found.\n";
                output += "-ototaltrays: Prints the total number of trays looked at while searching for the solution.\n";
                output += "-omaxmemory: Prints the maximum memory the solver uses.";
                System.out.println(output);
                System.exit(0);
            }
            // finds main arguments
            if (!arg.substring(0, 1).equals("-")){
                // separates arguments into separate arrays
                if (!arg.equals(args[0]))
                    oArguments = Arrays.copyOfRange(args, 0, Arrays.asList(args).indexOf(arg));
                fileArguments = Arrays.copyOfRange(args, Arrays.asList(args).indexOf(arg), args.length);
                // stops looping
                break;
            }
        }
        
        if (oArguments != null){
            for (String arg : oArguments){
                // this could be a case structure
                if (arg.equals("-ototalmoves")) {
                    totalMoves = true;
                }
                else if (arg.equals("-ototaltrays")) {
                    totalTrays = true;
                }
                else if (arg.equals("-omaxmemory")) {
                    maxMemory = true;
                }
                else {
                    System.out.println("invalid option arg: " + arg);
                }
            }
        }
        
        try {
            // Intializing the game tray
            BufferedReader in1 = new BufferedReader(new FileReader(fileArguments[0]));
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
            BufferedReader in2 = new BufferedReader(new FileReader(fileArguments[1]));
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
                    // print out further output depening on the given option args
                    if (totalMoves) {
                        System.out.println("Total moves in solution: " + moves.size());
                    }
                    if (totalTrays) {
                        System.out.println("Total trays looked at: " + game.getTotalTrays());
                    }
                    if (maxMemory) {
                        long bytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                        System.out.println("Max memory used: " + (bytes/1000000) + " megabytes");
                    }
                }
            }
        }
        catch (IOException e) {
            System.out.println("Invalid filename");
        }  
    }	
}
