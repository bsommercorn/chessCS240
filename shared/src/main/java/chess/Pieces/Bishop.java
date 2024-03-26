package chess.Pieces;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.Pieces.ChessPiece;

import java.util.ArrayList;
import java.util.Collection;

public class Bishop extends ChessPiece {

    public Bishop(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
        //System.out.println("Bishop was created");
    }
    @Override
    public ChessPiece.PieceType getPieceType() {
        return ChessPiece.PieceType.BISHOP;
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        //System.out.println("Called Bishop's pieceMoves");
        //System.out.println(board.toString());
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();
        //HashSet<ChessMove> myMoves = new HashSet<ChessMove>();

        int myColumn = myPosition.getColumn();
        int myRow = myPosition.getRow();
        int goleft;
        int goup;
        int goright;
        int godown;
        ChessPosition newPossible;

        //while we haven't hit the board or another piece, go right
        goright = myColumn + 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup + 1, goright + 1); //ChessPosition newPossible = new ChessPosition(goup, goright);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            //myMoves.add(new ChessMove((ChessPosition) myPosition, newPossible, PieceType.BISHOP));
            myMoves.add(new ChessMove((ChessPosition) myPosition, newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goright = goright + 1;
                goup = goup + 1;
                newPossible = new ChessPosition(goup + 1, goright + 1);
            }
        }

        goleft = myColumn - 1;
        godown = myRow - 1;
        newPossible = new ChessPosition(godown + 1, goleft + 1);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            //myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
            myMoves.add(new ChessMove((ChessPosition) myPosition, newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                godown = godown - 1;
                goleft = goleft - 1;
                newPossible = new ChessPosition(godown + 1, goleft + 1);
            }
        }

        godown = myRow - 1;
        goright = myColumn + 1;
        newPossible = new ChessPosition(godown + 1, goright + 1);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            //myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
            myMoves.add(new ChessMove((ChessPosition) myPosition, newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                godown = godown - 1;
                goright = goright + 1;
                newPossible = new ChessPosition(godown + 1, goright + 1);
            }
        }

        goleft = myColumn - 1;
        goup = myRow + 1;
        newPossible = new ChessPosition(goup + 1, goleft + 1);
        while (onBoard(newPossible) && pieceCheck(board, newPossible)) {
            //myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
            myMoves.add(new ChessMove((ChessPosition) myPosition, newPossible));
            if (board.getPiece(newPossible) != null) {
                break;
            }
            else {
                goleft = goleft - 1;
                goup = goup + 1;
                newPossible = new ChessPosition(goup + 1, goleft + 1);
            }
        }
        //myMoves = protectKing(board, myMoves);
        System.out.println("myMoves was:" + myMoves.toString());
        return myMoves;
    }
}
