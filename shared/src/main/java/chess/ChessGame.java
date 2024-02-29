package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class ChessGame {

    ChessBoard myboard;
    ChessGame.TeamColor whosTurn = ChessGame.TeamColor.WHITE;

    public ChessGame() {

    }

    public TeamColor getTeamTurn() {
        return whosTurn;
        //throw new RuntimeException("Not implemented");
    }

    public void setTeamTurn(TeamColor team) {
        whosTurn = team;
        //throw new RuntimeException("Not implemented");
    }

    public enum TeamColor {
        WHITE,
        BLACK
    }

    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece activePiece = myboard.getPiece(startPosition);
        if (activePiece == null) {
            System.out.println("There was no piece here!");
            return null;
        }
        System.out.println("chess.Piece found and checked for moves: " + activePiece.toString() + "***********");
        ArrayList<ChessMove> testList = (ArrayList<ChessMove>) activePiece.pieceMoves(myboard, startPosition);
        ArrayList<ChessMove> validList = ((ChessPiece)activePiece).protectKing(myboard, testList);

        System.out.println("Board state is: " + myboard.toString());
        return validList;
    }

    public ChessPiece getNewPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type){
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
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPiece activePiece = myboard.getPiece(move.getStartPosition()); //we need to test this
        if (activePiece == null) { //is this not working???
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
                }
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
                }
                else if (activePiece.getTeamColor() == TeamColor.BLACK && rankcheck.getRow() == 0){
                    if (move.getPromotionPiece() != null) {
                        ChessPiece promoteMe = (ChessPiece) getNewPiece(TeamColor.BLACK, move.getPromotionPiece());
                        myboard.addPiece(move.getEndPosition(), promoteMe);
                    }
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
        }
        else {
            throw new InvalidMoveException("chess.Piece cannot move to " + move.getEndPosition().toString());
        }
        System.out.println("Valid move made!");
        System.out.println(myboard.toString());
    }
    public boolean isInCheck(TeamColor teamColor) {
        System.out.println("Beginning to check for check in " + teamColor.toString());
        System.out.println("Board state is : " + myboard.toString());
        ChessPosition kingspot = null;
        if (teamColor == TeamColor.WHITE) {
            kingspot = (ChessPosition) myboard.getMyWhiteKing();
        }
        else {
            kingspot = (ChessPosition) myboard.getMyBlackKing();
        }
        King myKing = (King) myboard.getPiece(kingspot);
        if (myKing == null) {
            System.out.println("There was no king found, he cannot be in check");
            return false;
        }
        if (myKing.newkingCheck(myboard, kingspot)) {
            System.out.println("The " + teamColor + " King is in Check!");
            return true;
        }
        return false;
    }
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }
        ChessPosition kingspot = null;
        if (teamColor == TeamColor.WHITE) {
            kingspot = (ChessPosition) myboard.getMyWhiteKing();
        }
        else {
            kingspot = (ChessPosition) myboard.getMyBlackKing();
        }
        King myKing = (King) myboard.getPiece(kingspot);
        ArrayList<ChessMove> validmoves = (ArrayList<ChessMove>) this.validMoves(kingspot);
        if (validmoves.size() > 0) {
            System.out.println("King not Checkmated Yet!");
            System.out.println("Number of moves for this King is " + validmoves.size());
            for (int i = 0; i < validmoves.size(); i++) {
                System.out.print(validmoves.get(i).getEndPosition().toString() + " ");
            }
            return false;
        }
        for (int i = 0; i < 8; i++) { //for every rank down from 8
            for (int j = 0; j < 8; j++) { //go across every file
                ChessPosition otherPiece = new ChessPosition(i + 1,j + 1);
                validmoves = (ArrayList<ChessMove>) this.validMoves(otherPiece);
                if (myboard.getPiece(otherPiece) != null && myboard.getPiece(otherPiece).getTeamColor() == teamColor && validmoves != null && validmoves.size() > 0) {
                    return false;
                }
            }
        }
        System.out.println("The " + teamColor + " King has been Checkmated!");
        return true;
    }
    public boolean isInStalemate(TeamColor teamColor) {
        System.out.println("Stalemate check, board is " + myboard.toString());
        if (myboard.getPieceCount() == 2 && myboard.getMyWhiteKing() != null && myboard.getMyBlackKing() != null) {
            return true;
        }
        if (isInCheck(teamColor)) {
            return false;
        }
        ChessPosition kingspot = null;
        if (teamColor == TeamColor.WHITE) {
            kingspot = (ChessPosition) myboard.getMyWhiteKing();
        }
        else {
            kingspot = (ChessPosition) myboard.getMyBlackKing();
        }
        King myKing = (King) myboard.getPiece(kingspot);
        ArrayList<ChessMove> validmoves = (ArrayList<ChessMove>) this.validMoves(kingspot);
        if (validmoves.size() > 0) {
            System.out.println("King not Checkmated Yet!");
            System.out.println("Number of moves for this King is " + validmoves.size());
            for (int i = 0; i < validmoves.size(); i++) {
                System.out.print(validmoves.get(i).getEndPosition().toString() + " ");
            }
            return false;
        }
        for (int i = 0; i < 8; i++) { //for every rank down from 8
            for (int j = 0; j < 8; j++) { //go across every file
                ChessPosition otherPiece = new ChessPosition(i + 1,j + 1);
                validmoves = (ArrayList<ChessMove>) this.validMoves(otherPiece);
                if (myboard.getPiece(otherPiece) != null && myboard.getPiece(otherPiece).getTeamColor() == teamColor && validmoves != null && validmoves.size() > 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public void setBoard(ChessBoard board) {
        myboard = board;
        //throw new RuntimeException("Not implemented");
    }

    public ChessBoard getBoard() {
        return myboard;
        //throw new RuntimeException("Not implemented");
    }
}
