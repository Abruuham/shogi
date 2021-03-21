package boxshogi;

public class Piece {

    private Player owner;
    private char type = ' ';
    private char label = ' ';
    protected boolean isPromoted = false;


    public Piece(Player owner) {
        this.owner = owner;
    }

    public char getType(){
        return type;
    }

    public char getLabel(){
        return label;
    }

    public Player getOwner(){
        return owner;
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }

    public void setLabel(char label){
        this.label = label;
    }

    public void setType(char type){
        this.type = type;
    }

    public final void capturePiece(){

    }
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board){
        return false;
    }

    public void promote(){

    }

    public void demote(){

    }

    public String toString() {
        return "";
    }
}
