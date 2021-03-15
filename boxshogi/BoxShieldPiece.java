package boxshogi;


public class BoxShieldPiece extends Piece{
    private static final char SYMBOL = 'S';

    /**
     * Constructor for BoxShieldPiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxShieldPiece(int owner){
        super(owner);
        setSymbol(SYMBOL);
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        return super.canPlayerMove(startRow, startCol, endRow, endCol, board);
    }


    @Override
    public void promote() {
        // this piece cannot be promoted
    }

    @Override
    public void demote() {
        // this piece cannot be demoted
    }


}
