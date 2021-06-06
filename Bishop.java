public class Bishop extends Piece {
    
    public Bishop(boolean clr, int stt) {
        super(clr, stt);
        this.setRepresentation(clr ? Piece.WHITE_BISHOP_UNICODE : Piece.BLACK_BISHOP_UNICODE);
    }

    public Bishop(boolean clr, int stt, String pos) {
        super(clr, stt, pos);
        this.setRepresentation("B");
    }

    // other functions --> 

    public String[] getNextMoves() { // todo 
        String moves = "";
        String diagonalLeft = "";
        String diagonalRight = "";
        String pos = this.getPosition();
        // list left up diagonal
        int col = Board.positionToInt(Board.farthestLeftUpDiagonally(pos))[0];
        int line = Board.positionToInt(Board.farthestLeftUpDiagonally(pos))[1];
        if(!Board.intToPosition(col, line).equals(pos)) { diagonalLeft += Board.intToPosition(col, line) + ","; } // if we're not already at the edge, add the farthest cell diagonally to the list
        if(Board.isCorrectPosition(col + 1, line - 1)) { col++; line--; } 
        while(!Board.intToPosition(col, line).equals(Board.farthestRightDownDiagonally(pos))) { // while we've not reached the end of the board
            if(!Board.intToPosition(col, line).equals(pos)) { diagonalLeft += Board.intToPosition(col, line) + ","; }
            col++;
            line--;
        }
        diagonalLeft += Board.intToPosition(col, line) + ",";
        // list right up diagonal
        col = Board.positionToInt(Board.farthestRightUpDiagonally(pos))[0];
        line = Board.positionToInt(Board.farthestRightUpDiagonally(pos))[1];
        if(!Board.intToPosition(col, line).equals(pos)) { diagonalRight += Board.intToPosition(col, line) + ","; }
        if(Board.isCorrectPosition(col - 1, line - 1)) { col--; line--; }
        while(!Board.intToPosition(col, line).equals(Board.farthestLeftDownDiagonally(pos))) { // while we've not reached the end of the board
            if(!Board.intToPosition(col, line).equals(pos)) { diagonalRight += Board.intToPosition(col, line) + ","; }
            col--;
            line--;
        }
        diagonalRight += Board.intToPosition(col, line) + ",";
        moves = diagonalLeft + diagonalRight.substring(0, diagonalRight.length()-1);
        return moves.split(",");
    }

    public String[] getValidMoves(Board b) {
        String validNextMoves = String.join(",", this.getNextMoves());
        String[] nextMoves = this.getNextMoves();
        String pos = this.getPosition();
        int currentCol = Board.positionToInt(pos)[0];
        int currentLine = Board.positionToInt(pos)[1];
        for(String dest : nextMoves) {
            Piece p = b.getCell(dest).getPiece();
            if(p != null) { // if there's already a piece at the destination
                validNextMoves = p.getColor() == this.getColor() ? validNextMoves.replace(dest + ",", "") : validNextMoves; // don't move there if it's the same color
            }
            // check whether there are pieces obstructing our way
            int col = Board.positionToInt(dest)[0];
            int line = Board.positionToInt(dest)[1];
            String[] path;
            if(col < currentCol && line > currentLine) { // if the destination is on the left up diagonal
                path = Board.leftUpDiagonal(dest);
            } else if(col < currentCol && line < currentLine) { // left down diagonal
                path = Board.leftDownDiagonal(dest);
            } else if(col > currentCol && line > currentLine) { // right up diagonal
                path = Board.RightUpDiagonal(dest);
            } else /* if(col > currentCol && line < currentLine) */ { // right down diagonal
                path = Board.RightDownDiagonal(dest);
            }
            for(String c : path) {
                validNextMoves = b.getCell(c).getPiece() != null ? validNextMoves.replace(dest + ",", "") : validNextMoves;
            }
        }
        return validNextMoves.split(",");
    }

    @Override
    public String toString() {
        return "Bishop[state=" + this.getState() + ", position=" + this.getPosition() + "]";
    }
}
