package boxshogi;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
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

    public static void fileMode(String path) throws Exception {

        Utils.TestCase tc = Utils.parseTestCase(path);
        BoxShogiGame game = new BoxShogiGame(tc);
        List<String> moves = tc.moves;
        GameListener gl = new FileMode(game, moves.size());
        game.registerGameListener(gl);
        while (!game.isGameOver() && (!moves.isEmpty())) {
            String[] move = (moves.remove(0)).split("\\s+");
            if (move[0].equals("move")) {
                game.move(move[1], move[2], move.length == 4);
            } else {
                game.drop(move[1].charAt(0), move[2]);
            }


        }
    }


    public static void main(String[] args) throws NoSuchMethodException, IllegalAccessException, InstantiationException, FileNotFoundException, InvocationTargetException, ClassNotFoundException {
        if (args.length == 1 && args[0].equals("-i")) {
            interactionMode();
        } else if (args.length == 2 && args[0].equals("-f")) {
            try {
                fileMode(args[1]);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } else {
            System.out.println("Please specify input by -i or -f [file name]");
        }
    }
}
