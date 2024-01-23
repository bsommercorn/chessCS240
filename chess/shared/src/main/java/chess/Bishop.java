package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends ChessPiece {

    public Bishop(ChessGame.TeamColor pieceColor, PieceType type) {
        super(pieceColor, type);
    }
    @Override
    public ChessPiece.PieceType getPieceType() {
        return ChessPiece.PieceType.BISHOP;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();

        int myColumn = myPosition.getColumn();
        int myRow = myPosition.getRow();
        //while we haven't hit the board or another piece, go right
        int goright = myColumn + 1;
        int goup = myRow + 1;
        ChessPosition newPossible = new ChessPosition(goup, goright);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition, newPossible, PieceType.BISHOP));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goright = goright + 1;
                goup = goup + 1;
                newPossible = new ChessPosition(goup, goright);
            }
        }

        int goleft = myColumn - 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup, goleft);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goleft = goleft - 1;
                goup = goup + 1;
                newPossible = new ChessPosition(goup, goleft);
            }
        }

        goleft = myColumn - 1;
        int godown = myRow - 1;
        newPossible = new ChessPosition(godown, goleft);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                godown = godown - 1;
                goleft = goleft - 1;
                newPossible = new ChessPosition(godown, goleft);
            }
        }

        godown = myRow - 1;
        goright = myColumn + 1;
        newPossible = new ChessPosition(godown, goright);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                godown = godown - 1;
                goright = goright + 1;
                newPossible = new ChessPosition(godown, goright);
            }
        }
        //myMoves = protectKing(board, myMoves);
        /*
        if (isInCheck(activePiece.getTeamColor())) {
                myboard.removePiece(move.getEndPosition());
                ChessPiece restore = myboard.getLastCaptured();
                myboard.addPiece(move.getEndPosition(), restore);
                myboard.addPiece(move.getStartPosition(), activePiece);
            }
         */
        //iterate through moves
        //actually do them on the board
        //see if they put the king in check, if they did, delete this move from the list
        //undo them on the board
        return myMoves;
    }
}
