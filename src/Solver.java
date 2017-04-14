import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver {
    // args[0] is the filename of the initial puzzle configuration
    // args[1] is the filename of the goal	
    public static void main(String[] args) {
        
        // i really dont know whats supposed to be in try and what isnt
        try {
            BufferedReader in1 = new BufferedReader(new FileReader(args[0]));
            String line = in1.readLine();
            Tray game = null;
            if (line != null) {
                System.out.println(line);
                game = new Tray(Integer.parseInt(line.substring(0,1)), Integer.parseInt(line.substring(2)));
            }
            else {
                System.out.println("invalid tray");
                System.exit(0); // i dont know if this is right
            }
            line = in1.readLine();
            while (line != null) {
                System.out.println(line);
                game.addBlock(line);
                line = in1.readLine();
            }
            in1.close();
            
            System.out.println(game.printTray());
            
            BufferedReader in2 = new BufferedReader(new FileReader(args[1]));
            line = in2.readLine();
            while (line != null) {
                System.out.println(line);
                line = in2.readLine();
            }
            in2.close();
        }
        catch (IOException e) { // is this the right exception?
            System.out.println("aaa"); // change this
        }
        
        
        
    }
	
}
