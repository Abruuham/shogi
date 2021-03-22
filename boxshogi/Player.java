package boxshogi;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


enum Position {
    UPPER, LOWER;
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
            position = Position.UPPER;
            name = "UPPER";
        } else {
            position = Position.LOWER;
            name = "lower";
        }
    }

    public int getCapturedPieceIndex(char p) {
        for (int i = 0; i < captured.size(); i++) {
            Piece piece = captured.get(i);
            if (Character.toLowerCase(piece.getLabel()) == p) {
                return i;
            }
        }
        return -1;
    }

    public void addCapturedPiece(Piece p) {
        captured.add(p);
    }


    public void addCapturedPieceToIndex(Piece p, int index) {
        if (index < captured.size()) {
            captured.add(index, p);
        }
        else {
            captured.add(p);
        }
    }

    public Piece getPiece(char symbol) {
        for (Piece p : captured) {
            if (Character.toLowerCase(p.getLabel()) == symbol) {
                captured.remove(p);
                return p;
            }
        }
        return null;
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

    public List<String> getCapturedSnapshot(){
        List<String> rtn = new LinkedList<>();
        for(Piece piece : captured){
            rtn.add(piece.toString());
        }

        return rtn;
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

    @Override
    public String toString(){
        return name;
    }

}