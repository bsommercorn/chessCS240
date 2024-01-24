package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Queen extends ChessPiece{
    public Queen(ChessGame.TeamColor teamColor) {
        super(teamColor, PieceType.QUEEN);
        System.out.println("Queen was created");
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();

        int myColumn = myPosition.getColumn() + 1;
        int myRow = myPosition.getRow() + 1;
        //while we haven't hit the board or another piece, go right
        int goright = myColumn + 1;
        ChessPosition newPossible = new ChessPosition(myRow, goright);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goright = goright + 1;
                newPossible = new ChessPosition(myRow, goright);
            }
        }

        int goleft = myColumn - 1;
        newPossible = new ChessPosition(myRow, goleft);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goleft = goleft - 1;
                newPossible = new ChessPosition(myRow, goleft);
            }
        }

        int goup = myRow + 1;
        newPossible = new ChessPosition(goup, myColumn);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goup = goup + 1;
                newPossible = new ChessPosition(goup, myColumn);
            }
        }

        int godown = myRow - 1;
        newPossible = new ChessPosition(godown, myColumn);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                godown = godown - 1;
                newPossible = new ChessPosition(godown, myColumn);
            }
        }

        goright = myColumn + 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup, goright);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goright = goright + 1;
                goup = goup + 1;
                newPossible = new ChessPosition(goup, goright);
            }
        }

        goleft = myColumn - 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup, goleft);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
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
        godown = myRow - 1;
        newPossible = new ChessPosition(godown, goleft);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
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
            myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                godown = godown - 1;
                goright = goright + 1;
                newPossible = new ChessPosition(godown, goright);
            }
        }
        myMoves = protectKing(board, myMoves);
        return myMoves;
    }
}
