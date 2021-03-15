package boxshogi;

public class BoxGovernancePiece extends Piece{
    private static final char SYMBOL = 'G';


    /**
     * Constructor for BoxGovernancePiece
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxGovernancePiece(int owner){
        super(owner);
        setSymbol(SYMBOL);
    }

    @Override
    public void promote() {
        isPromoted = true;
    }

    @Override
    public void demote() {
        isPromoted = false;
    }

    @Override
    protected boolean canPlayerMove(int startRow, int startCol, int endRow, int endCol, Board board) {
        return super.canPlayerMove(startRow, startCol, endRow, endCol, board);
    }
}
