package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    ChessBoard myboard;
    ChessGame.TeamColor whosTurn = ChessGame.TeamColor.WHITE;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return whosTurn;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        whosTurn = team;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece activePiece = myboard.getPiece(startPosition);
        if (activePiece == null) {
            System.out.println("There was no piece here!");
            return null;
        }
        ArrayList<ChessMove> testList = (ArrayList<ChessMove>) activePiece.pieceMoves(myboard, startPosition);
        //ArrayList<ChessMove> validList = ((ChessPiece)activePiece).protectKing(myboard, testList);

        //System.out.println("chess.Piece found: " + activePiece.toString());
        return testList;
        //return validList;
        //throw new RuntimeException("Not implemented");
    }

    public ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
        //get an enum
        //make a piece that matches that enum
        switch (type) {
            case PAWN:
                return new Pawn(pieceColor);
            case KNIGHT:
                return new Knight(pieceColor);
            case BISHOP:
                return new Bishop(pieceColor);
            case ROOK:
                return new Rook(pieceColor);
            case QUEEN:
                return new Queen(pieceColor);
            case KING:
                return new King(pieceColor);
        }
        return null;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece activePiece = myboard.getPiece(move.getStartPosition()); //we need to test this
        if (activePiece == null) {
            throw new InvalidMoveException("No piece to move!");
        }
        if (whosTurn != null && whosTurn != activePiece.getTeamColor()) {
            throw new InvalidMoveException("Not your turn!");
        }
        //System.out.println("chess.Piece found: " + activePiece.toString());
        ArrayList<ChessMove> validmoves = (ArrayList<ChessMove>) activePiece.pieceMoves(myboard, move.getStartPosition());
        boolean valid = false;
        for (int i = 0; i < validmoves.size(); i++){ //this is infinite looping?
            if (Objects.equals(validmoves.get(i).getEndPosition().toString(), move.getEndPosition().toString())) {
                valid = true;
            }//are move addresses being set incorrectly somehow???
        }
        if (valid) {
            //if a pawn moves diagonally without capturing, it must be doing en passant
            //therefore black must delete the pawn above it
            //and white must delete the pawn below it
            //if this piece moving is a pawn, and its column is going to change, check for en passant
            if (activePiece.getPieceType() == ChessPiece.PieceType.PAWN) {
                ChessPosition colcheck = move.getStartPosition();
                ChessPosition passantcheck = move.getEndPosition();
                if (passantcheck.getColumn() != colcheck.getColumn() && myboard.getPiece(passantcheck) == null) {
                    if (activePiece.getTeamColor() == TeamColor.WHITE){
                        ChessPosition enpassant = new ChessPosition(passantcheck.getRow() - 1 + 1,passantcheck.getColumn() + 1);
                        myboard.removePiece(enpassant);
                    }
                    else {
                        ChessPosition enpassant = new ChessPosition(passantcheck.getRow() + 1 + 1,passantcheck.getColumn() + 1);
                        myboard.removePiece(enpassant);
                    }
                } //add turn timestamp for pawns so they only have 1 turn to do this

                //if the pawn is at row 7 for white or 2 for black, then it can promote
                //get the promotion type from the move
            }

            //before making a move, make a copy of the board so that it's reversible if the king is in check.
            myboard.removePiece(move.getStartPosition());
            myboard.addPiece(move.getEndPosition(), activePiece);
            if (activePiece.getPieceType() == ChessPiece.PieceType.PAWN) {
                ChessPosition rankcheck = (ChessPosition) move.getEndPosition();
                if (activePiece.getTeamColor() == TeamColor.WHITE && rankcheck.getRow() == 7){ //row adjustment
                    if (move.getPromotionPiece() != null) {
                        ChessPiece promoteMe = (ChessPiece) getNewPiece(TeamColor.WHITE, move.getPromotionPiece());
                        myboard.addPiece(move.getEndPosition(), promoteMe);
                    }
                    /*
                    else {
                        throw new InvalidMoveException("White Pawn was not promoted");
                    }

                     */
                }
                else if (activePiece.getTeamColor() == TeamColor.BLACK && rankcheck.getRow() == 0){
                    if (move.getPromotionPiece() != null) {
                        ChessPiece promoteMe = (ChessPiece) getNewPiece(TeamColor.BLACK, move.getPromotionPiece());
                        myboard.addPiece(move.getEndPosition(), promoteMe);
                    }
                    /*
                    else {
                        throw new InvalidMoveException("Black Pawn was not promoted");
                    }

                     */
                }
            }

            if (activePiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                setTeamTurn(ChessGame.TeamColor.BLACK); //Set the turn for the next color team
                for (int i = 0; i < 8; i++) {
                    ChessPosition nopassant = new ChessPosition(5 + 1,i + 1); //along one row, remove en passant from all pawns
                    ChessPiece mypawn = (ChessPiece) myboard.getPiece(nopassant);
                    if (mypawn != null && mypawn.getPieceType() == ChessPiece.PieceType.PAWN) {
                        ((Pawn)mypawn).noPassant();
                    }
                }
            }
            else {
                setTeamTurn(ChessGame.TeamColor.WHITE);
                for (int i = 0; i < 8; i++) {
                    ChessPosition nopassant = new ChessPosition(4 + 1,i + 1);
                    ChessPiece mypawn = (ChessPiece) myboard.getPiece(nopassant);
                    if (mypawn != null && mypawn.getPieceType() == ChessPiece.PieceType.PAWN) {
                        ((Pawn)mypawn).noPassant();
                    }
                }
            }

            //if the king is in check at the end of this move, reverse it and call it invalid
            //this will be nice because then you don't have to worry about a lot of check possibilities.
            //like checking to see if you killed or blocked the piece, or if a discovered attack happened.
            //this would do it automatically.

            //worry about checkmate later!

            //after a move has been made, we could loop through all pawns on that team and nullify their en passant
        }
        else {
            throw new InvalidMoveException("chess.Piece cannot move to " + move.getEndPosition().toString());
        }
        System.out.println("Valid move made!");
        System.out.println(myboard.toString());
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        return false;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return false;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return false;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        myboard = board;
        //throw new RuntimeException("Not implemented");
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return myboard;
        //throw new RuntimeException("Not implemented");
    }
}
