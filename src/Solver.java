import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Solver {
    // args[0] is the filename of the initial puzzle configuration
    // args[1] is the filename of the goal	
    public static void main(String[] args) {
        try {
            BufferedReader in1 = new BufferedReader(new FileReader(args[0]));
            String line = in1.readLine();
            while (line != null) {
                System.out.println(line);
                line = in1.readLine();
            }
            in1.close();
            System.out.println();
            BufferedReader in2 = new BufferedReader(new FileReader(args[1]));
            line = in2.readLine();
            while (line != null) {
                System.out.println(line);
                line = in2.readLine();
            }
            in2.close();
        }
        catch (IOException e) { // is this the right exception?
            System.out.println("aaa");
        }
        
        
        
    }
	
}
