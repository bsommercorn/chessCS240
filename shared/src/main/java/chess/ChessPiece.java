package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    ChessGame.TeamColor myColor;
    ChessPiece.PieceType myType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        myColor = pieceColor;
        myType = type;
        //System.out.println("Generic Piece created (if this statement is alone)");
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return myColor;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return myType;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        System.out.println("Called generic pieceMoves incorrectly"); //this is not getting overridden by the real classes
        return null;
        //throw new RuntimeException("Not implemented");
    }

    protected boolean onBoard(ChessPosition possible) { //all pieces should have this actually
        if (possible.getColumn() < 0 || possible.getColumn() >= 8) {
            return false;
        }
        if (possible.getRow() < 0 || possible.getRow() >= 8) {
            return false;
        }
        return true;
    }

    protected boolean pieceCheck (ChessBoard board, ChessPosition myPosition) {
        ChessPiece pieceHere = board.getPiece(myPosition);
        if (pieceHere == null) { //pawn needs an override here, can't move diagonal without enemy pieces present
            return true;
        }
        else if (pieceHere.getTeamColor() != myColor) { //there was an enemy piece, potential capture
            return true;
        }
        //System.out.println("Movement blocked by: " + pieceHere.toString());
        return false;
    }

    public ArrayList<ChessMove> protectKing(ChessBoard board, ArrayList<ChessMove> testList) { //stack overflow????
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();
        ChessPosition kingspot = null;
        ChessBoard myboard = (ChessBoard) board;
        if (myColor == ChessGame.TeamColor.WHITE) {
            kingspot = (ChessPosition) myboard.getMyWhiteKing();
        }
        else {
            kingspot = (ChessPosition) myboard.getMyBlackKing();
        }
        if (kingspot != null) {
            King myKing = (King) myboard.getPiece(kingspot);
            for (int i = 0; i < testList.size(); i++){
                if (myColor == ChessGame.TeamColor.WHITE) {
                    kingspot = (ChessPosition) myboard.getMyWhiteKing();
                }
                else {
                    kingspot = (ChessPosition) myboard.getMyBlackKing();
                }
                ChessMove myMove = (ChessMove) testList.get(i);
                myboard.removePiece(myMove.getStartPosition());
                myboard.addPiece(myMove.getEndPosition(), this);
                if (myColor == ChessGame.TeamColor.WHITE) { //kingspot moves if the king himself is the one moving
                    kingspot = (ChessPosition) myboard.getMyWhiteKing();
                }
                else {
                    kingspot = (ChessPosition) myboard.getMyBlackKing();
                }
                //if (!myKing.newkingCheck(myboard, kingspot)) {//shouldn't be the old kingspot if the king himself is moving
                myMoves.add(myMove);
                //}
                myboard.removePiece(myMove.getEndPosition());
                ChessPiece restore = myboard.getLastCaptured();
                if (restore != null) {
                    myboard.addPiece(myMove.getEndPosition(), restore);
                }
                myboard.addPiece(myMove.getStartPosition(), this);
            }
            return myMoves;
        }
        else {
            return testList;
        }
    }

    @Override
    public String toString() {
        String myoutput = "";
        if (myColor == ChessGame.TeamColor.WHITE) {
            switch (myType) {
                case KING:
                    return "K";
                case QUEEN:
                    return "Q";
                case KNIGHT:
                    return "N";
                case BISHOP:
                    return "B";
                case ROOK:
                    return "R";
                case PAWN:
                    return "P";
            }
        }
        else {
            switch (myType) {
                case KING:
                    return "k";
                case QUEEN:
                    return "q";
                case KNIGHT:
                    return "n";
                case BISHOP:
                    return "b";
                case ROOK:
                    return "r";
                case PAWN:
                    return "p";
            }
        }
        return myoutput;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return myColor == that.myColor && myType == that.myType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(myColor, myType);
    }
}
