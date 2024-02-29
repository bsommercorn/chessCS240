package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPiece {
    boolean passantW = false;
    boolean passantE = false;

    ArrayList<ChessMove> myMoves = new ArrayList<>();
    public void noPassant() {
        passantW = false;
        passantE = false;
    }
    public Pawn(ChessGame.TeamColor teamColor) {
        super(teamColor, PieceType.PAWN);
        System.out.println("Pawn was created");
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        int myRow = myPosition.getRow() + 1;
        int myCol = myPosition.getColumn() + 1;

        int goup = myRow + 1;
        int godown = myRow - 1;
        int goleft = myCol - 1;
        int goright = myCol + 1;

        ChessPosition newpossible;

        if (myColor == ChessGame.TeamColor.WHITE) {
            newpossible = new ChessPosition(goup,myCol); //single up only
            if (onBoard(newpossible) && board.getPiece(newpossible) == null) {
                if (myRow == 7) { //check for promotion
                    promote(myPosition, newpossible);
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                    if (myRow == 2) { //check for double
                        newpossible = new ChessPosition(goup + 1,myCol);
                        if (onBoard(newpossible) && board.getPiece(newpossible) == null) {
                            myMoves.add(new ChessMove(myPosition, newpossible));
                        }
                    }
                }
            }
            newpossible = new ChessPosition(goup,goright); //diagonal attack right
            if (onBoard(newpossible) && pieceCheck(board, newpossible) && board.getPiece(newpossible) != null) {
                if (myRow == 7) { //check for promotion
                    promote(myPosition, newpossible);
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                }
            }
            newpossible = new ChessPosition(goup,goleft); //diagonal attack left
            if (onBoard(newpossible) && pieceCheck(board, newpossible) && board.getPiece(newpossible) != null) {
                if (myRow == 7) { //check for promotion
                    promote(myPosition, newpossible);
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                }
            }
        }
        else if (myColor == ChessGame.TeamColor.BLACK){
            newpossible = new ChessPosition(godown,myCol); //single down only
            if (onBoard(newpossible) && board.getPiece(newpossible) == null) {
                if (myRow == 2) { //check for promotion
                    promote(myPosition, newpossible);
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                    if (myRow == 7) { //check for double
                        newpossible = new ChessPosition(godown - 1,myCol);
                        if (onBoard(newpossible) && board.getPiece(newpossible) == null) {
                            myMoves.add(new ChessMove(myPosition, newpossible));
                        }
                    }
                }
            }
            newpossible = new ChessPosition(godown,goright); //diagonal attack right
            if (onBoard(newpossible) && pieceCheck(board, newpossible) && board.getPiece(newpossible) != null) {
                if (myRow == 2) { //check for promotion
                    promote(myPosition, newpossible);
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                }
            }
            newpossible = new ChessPosition(godown,goleft); //diagonal attack left
            if (onBoard(newpossible) && pieceCheck(board, newpossible) && board.getPiece(newpossible) != null) {
                if (myRow == 2) { //check for promotion
                    promote(myPosition, newpossible);
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                }
            }
        }

        return myMoves;
    }

    private void promote(ChessPosition myPosition, ChessPosition newpossible) {
        myMoves.add(new ChessMove(myPosition, newpossible, PieceType.KNIGHT));
        myMoves.add(new ChessMove(myPosition, newpossible, PieceType.BISHOP));
        myMoves.add(new ChessMove(myPosition, newpossible, PieceType.ROOK));
        myMoves.add(new ChessMove(myPosition, newpossible, PieceType.QUEEN));
    }

}
