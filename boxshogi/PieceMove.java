package boxshogi;

import java.util.Objects;


public class PieceMove {
    private final int deltaRow;
    private final int deltaCol;

    public PieceMove(int deltaRow, int deltaCol) {
        this.deltaRow = deltaRow;
        this.deltaCol = deltaCol;
    }

    public int getDeltaRow() {
        return deltaRow;
    }


    public int getDeltaCol() {
        return deltaCol;
    }


    public boolean isEqual(int deltaRow, int deltaCol) {
        return ((this.deltaRow == deltaRow) && (this.deltaCol == deltaCol));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PieceMove)) return false;
        PieceMove m = (PieceMove)o;
        return (m.deltaCol == deltaCol)&&(m.deltaRow == deltaRow);
    }

    @Override
    public int hashCode() {
        return Objects.hash(deltaCol, deltaRow);
    }
}
