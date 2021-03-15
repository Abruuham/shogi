package boxshogi;

public class Piece {

    private int owner;
    private char type = ' ';
    private char symbol = ' ';
    protected boolean isPromoted = false;


    public Piece(int owner) {
        this.owner = owner;
    }

    public char getType(){
        return type;
    }

    public char getSymbol(){
        return symbol;
    }

    public int getOwner(){
        return owner;
    }

    public void setOwner(int owner){
        this.owner = owner;
    }

    public void setSymbol(char symbol){
        this.symbol = symbol;
    }

    public void setType(char type){
        this.type = type;
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
