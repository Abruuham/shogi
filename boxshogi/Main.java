package boxshogi;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {

    public static void interactionMode() throws IllegalAccessException, InstantiationException,
            FileNotFoundException, NoSuchMethodException,
            InvocationTargetException, ClassNotFoundException {

        BoxShogiGame game = new BoxShogiGame(new InteractionMode());
        Scanner in = new Scanner(System.in);
        while(!game.isGameOver()){
            String[] move = (in.nextLine()).split("\\s+");
            if(move[0].equals("move")){
                game.move(move[1], move[2], move.length == 4);
            } else{
                game.drop(move[1].charAt(0), move[2]);
            }
        }
        in.close();
    }

    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        if (args.length == 1 && args[0].equals("-i")) {
            interactionMode();
        } else if (args.length == 2 && args[0].equals("-f")) {
            try {
                //Utils.TestCase input = Utils.parseTestCase(args[1]);
                String sampleOutput = "UPPER player action: drop s d1\n"
                + "5 |__|__| R|__| D|\n"
                + "4 |__|__|__|__|__|\n"
                + "3 |__|__|__|__|__|\n"
                + "2 |__|__|__|__|__|\n"
                + "1 | d| g|__| n|__|\n"
                + "a  b  c  d  e\n\n"
                + "Captures UPPER: S R P\n"
                + "Captures lower: p n g s\n\n"
                + "lower player wins.  Illegal move.\n";
                System.out.println(sampleOutput);

            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("Please specify input by -i or -f [file name]");
        }
    }
}
