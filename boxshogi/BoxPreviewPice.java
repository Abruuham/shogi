package boxshogi;

public class BoxPreviewPice extends Piece{
    private static final char SYMBOL = 'P';


    /**
     * Constructor for BoxPreviewPice
     * @param owner - the owner of the piece, either player 1 or player 2
     */
    public BoxPreviewPice(int owner){
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
}
