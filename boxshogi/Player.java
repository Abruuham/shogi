package boxshogi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


enum Position {
    TOP, BOTTOM;
}
public final class Player {
    private final String name;
    private final Position position;
    private final boolean topPlayer;
    private final ArrayList<Piece> captured;

    Player(boolean topPlayer){
        this.topPlayer = topPlayer;
        captured = new ArrayList<>();
        if (topPlayer) {
            position = Position.TOP;
            name = "TOP";
        } else {
            position = Position.BOTTOM;
            name = "BOTTOM";
        }
    }

    public void setCaptured(Piece piece){
        captured.add(piece);
    }

    public Piece getCaptured(char label){
        for(int i = 0; i < captured.size(); i++){
            if(captured.get(i).getLabel() == label){
                captured.remove(captured.get(i));
                return captured.get(i);
            }
        }
        return null;
    }

    public ArrayList<Piece> returnCapturedPieces(){
        return captured;
    }

    public Position getPosition(){
        return position;
    }

    public char getLabel(char label){
        if (topPlayer) return Character.toUpperCase(label);
        else return Character.toLowerCase(label);
    }

}