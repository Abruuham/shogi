package boxshogi;

public class CheckMoves {


    public static boolean checkBoxDriveMove(int startRow, int startCol, int endRow, int endCol){
        int row = Math.abs(endRow - startRow);
        int col = Math.abs(endCol - startCol);
        return (row <= 1 && col <= 1);
    }

    public static boolean checkBoxGovernancePiece(int startRow, int startCol, int endRow, int endCol, Board board){
        int row = endRow - startRow;
        int col = endCol - startCol;

        if(Math.abs(row) != Math.abs(col)){
            return false;
        }
        int rowDelta, colDelta;
        if(row < 0) {
            rowDelta = -1;
        } else{
            rowDelta = 1;
        }

        if(col < 0){
            colDelta = -1;
        } else{
            colDelta = 1;
        }



        for(int i = 1; i < Math.abs(row); i++){
            int newRow = startRow + i * rowDelta ;
            int newCol = startCol + i * colDelta;
            if(board.getPiece(newRow, newCol) != null){
                return false;
            }
        }

        return true;
    }

    public static boolean checkBoxNotesPiece(int startRow, int startCol, int endRow, int endCol, Board board){
        int row = endRow - startRow;
        int col = endCol - startCol;
        if(Math.min(Math.abs(row), Math.abs(col)) != 0) return false;

        if(col == 0){
            int newRow;
            if(row < 0){
                newRow = -1;
            } else{
                newRow = 1;
            }

            for(int i = 1; i < Math.abs(row); i++){
                int rowDelta = startRow + newRow * i;
                if(board.getPiece(rowDelta, startCol) != null) return false;
            }
            return true;
        } else {
            assert(row == 0);
            int newCol;
            if(col < 0){
                newCol = -1;
            } else{
                newCol = 1;
            }
            for (int i = 1; i < Math.abs(col); i++) {
                int colDelta = startCol + newCol * i;
                if (board.getPiece(startRow, colDelta) != null) return false;
           }
            return true;
        }

    }

    public static boolean checkBoxShieldPiece(int startRow, int startCol, int endRow, int endCol, Position position){
        int deltaRow = endRow - startRow;
        int deltaCol = endCol - startCol;
        if (deltaRow == 0 && (deltaCol == 1 || deltaCol == -1)) return true;
        else if (deltaCol == 0 && (deltaRow == 1 || deltaRow == -1)) return true;
        else if (position == Position.UPPER) return (deltaRow == 1 && (deltaCol == -1 || deltaCol == 1));
        else return (deltaRow == -1 && (deltaCol == -1 || deltaCol == 1));
    }

    public static boolean checkBoxRelayPiece(int startRow, int startCol, int endRow, int endCol){
        int row = endRow - startRow;
        int col = endCol - startCol;

        if (col == 0 && row == 1) return true;
        else if(col == 1 && (row == 1 || row == -1)) return true;
        else if(col == -1 && (row == 1 || row == 1)) return true;
        return false;
    }

    public static boolean checkBoxPreviewPiece(int startRow, int startCol, int endRow, int endCol){
        int row = endRow - startRow;
        int col = endCol - startCol;

        if(col == 0 && row == -1) return true;
        else return (col == 0 && row == 1);
    }



}
