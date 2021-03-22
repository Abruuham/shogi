package boxshogi;

public abstract class Piece {

    private Player owner;
    private char label;
    protected boolean isPromoted = false;
    protected Position position;


    public Piece(char label, Player owner) {
        this.label = owner.getLabel(label);
        this.owner = owner;
        this.position = owner.getPosition();
        isPromoted = false;
    }


    public char getLabel(){
        return label;
    }

    public Player getOwner(){
        return owner;
    }


    public final void capturePiece(){

    }



    public boolean isMoveValid(int startRow, int endRow, int startCol, int endCol, Board board){
        if(startRow == endRow && startCol == endCol) return false;
        Piece piece = board.getPiece(endRow, endCol);
        if(piece != null && piece.getOwner() == owner) return false;
        return canPlayerMove(startRow, endRow, startCol, endCol, board);
    }
    protected abstract boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board);

    public void promote(){
    }

    public void demote(){
    }

    public String toString() {
        return String.valueOf(label);
    }
}
