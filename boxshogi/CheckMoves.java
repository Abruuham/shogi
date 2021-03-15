package boxshogi;

public class CheckMoves {
    static boolean checkBoxDriveMove(int startRow, int endRow, int startCol, int endCol){
        int row = Math.abs(endRow - startRow);
        int col = Math.abs(endCol - startCol);
        return (row <= 1 && col <= 1);
    }
}
