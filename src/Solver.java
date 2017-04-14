import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver {
    // args[0] is the filename of the initial puzzle configuration
    // args[1] is the filename of the goal	
    public static void main(String[] args) {
        
        // implement option debugging messages?
        
        // i really dont know whats supposed to be in try and what isnt
        try {
            BufferedReader in1 = new BufferedReader(new FileReader(args[0]));
            String line = in1.readLine();
            Tray game = null;
            if (line != null) {
                game = new Tray(Integer.parseInt(line.substring(0,1)), Integer.parseInt(line.substring(2)));
            }
            else {
                System.out.println("invalid tray");
                System.exit(1);
            }
            line = in1.readLine();
            while (line != null) {
                game.addBlock(line);
                line = in1.readLine();
            }
            in1.close();
            
            System.out.println(game.printTray());
            
            BufferedReader in2 = new BufferedReader(new FileReader(args[1]));
            line = in2.readLine();
            while (line != null) {
                // do something here to put data into the goal
                line = in2.readLine();
            }
            in2.close();
            
            // begin generating moves
            // how in the world are we going to go about doing this
            
        }
        catch (IOException e) { // is this the right exception?
            System.out.println("aaa"); // change this
        }
        
        
        
    }
	
}
