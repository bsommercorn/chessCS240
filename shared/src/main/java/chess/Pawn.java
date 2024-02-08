package chess;

import java.util.ArrayList;
import java.util.Collection;

public class Pawn extends ChessPiece {
    boolean passantW = false;
    boolean passantE = false;

    public void setPassantW () {
        passantW = true;
    }
    public void setPassantE () { //maybe instead of setting the enemy to en passant, set ourselves to capturable by en passant
        passantE = true;
    }
    public void noPassant() {
        passantW = false;
        passantE = false;
    }
    public Pawn(ChessGame.TeamColor teamColor) {
        super(teamColor, PieceType.PAWN);
        System.out.println("Pawn was created");
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> myMoves = new ArrayList<>();

        int myRow = myPosition.getRow() + 1;
        int myCol = myPosition.getColumn() + 1;

        int goup = myRow + 1;
        int godown = myRow - 1;
        int goleft = myCol - 1;
        int goright = myCol + 1;

        ChessPosition newpossible;

        //split into colors
        //black only moves down, white only moves up
        //on starting row, can move forward twice
        //black checks down diagonals for captures, white checks up diagonals
        //on second row, black can promote, on second to last row, white can promote

        if (myColor == ChessGame.TeamColor.WHITE) {
            newpossible = new ChessPosition(goup,myCol); //single up only
            if (onBoard(newpossible) && board.getPiece(newpossible) == null) {
                if (myRow == 7) { //check for promotion
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.BISHOP));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.ROOK));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.QUEEN));
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
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.BISHOP));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.ROOK));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.QUEEN));
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                }
            }
            newpossible = new ChessPosition(goup,goleft); //diagonal attack left
            if (onBoard(newpossible) && pieceCheck(board, newpossible) && board.getPiece(newpossible) != null) {
                if (myRow == 7) { //check for promotion
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.BISHOP));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.ROOK));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.QUEEN));
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
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.BISHOP));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.ROOK));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.QUEEN));
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
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.BISHOP));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.ROOK));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.QUEEN));
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                }
            }
            newpossible = new ChessPosition(godown,goleft); //diagonal attack left
            if (onBoard(newpossible) && pieceCheck(board, newpossible) && board.getPiece(newpossible) != null) {
                if (myRow == 2) { //check for promotion
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.BISHOP));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.ROOK));
                    myMoves.add(new ChessMove(myPosition, newpossible, PieceType.QUEEN));
                }
                else {
                    myMoves.add(new ChessMove(myPosition, newpossible));
                }
            }
        }

        return myMoves;
    }
}

    /*
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ArrayList<ChessMove> myMoves = new ArrayList<ChessMove>();

        int myColumn = myPosition.getColumn() + 1;
        int myRow = myPosition.getRow() + 1;

        if (myColor == ChessGame.TeamColor.BLACK) {
            int godown = myRow - 1;
            ChessPosition newPossible = new ChessPosition(godown, myColumn);
            if (onBoard(newPossible) && board.getPiece(newPossible) == null) {
                if (myRow == 2) {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.QUEEN));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.ROOK));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
                }
                else {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                }
            }
            newPossible = new ChessPosition(godown, myColumn + 1); //upper right attack
            if (onBoard(newPossible) && board.getPiece(newPossible) != null && board.getPiece(newPossible).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (myRow == 2) {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.QUEEN));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.ROOK));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
                }
                else {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                }
            }
            newPossible = new ChessPosition(godown, myColumn - 1); //upper left attack
            if (onBoard(newPossible) && board.getPiece(newPossible) != null && board.getPiece(newPossible).getTeamColor() == ChessGame.TeamColor.WHITE) {
                if (myRow == 2) {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.QUEEN));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.ROOK));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
                }
                else {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                }
            }
            newPossible = new ChessPosition(godown, myColumn);
            if (myRow == 7 && board.getPiece(newPossible) == null) { //check to make sure we can't hop over pieces
                godown = godown - 1;
                newPossible = new ChessPosition(godown, myColumn);
                if (onBoard(newPossible) && board.getPiece(newPossible) == null) {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                    //at this point, trigger an en passant check for enemy pawns near this position
                    ChessPosition passantcheck = new ChessPosition(godown, myColumn - 1);
                    ChessPiece posenemy = board.getPiece(passantcheck);
                    if (posenemy != null && posenemy.getClass() == this.getClass() && posenemy.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        ((Pawn) posenemy).setPassantE();
                        System.out.println("En passant available!");
                    }
                    passantcheck = new ChessPosition(godown, myColumn + 1);
                    posenemy = board.getPiece(passantcheck);
                    if (posenemy != null && posenemy.getClass() == this.getClass() && posenemy.getTeamColor() == ChessGame.TeamColor.WHITE) {
                        ((Pawn) posenemy).setPassantW();
                        System.out.println("En passant available!");
                    }
                }
            }
            if (passantE) { //a pawn might have moved to our col+1, if they did add en passant to our moves
                ChessPosition passantcheck = new ChessPosition(myRow, myColumn + 1);
                ChessPiece postarget = board.getPiece(passantcheck);
                if (postarget != null && postarget.getClass() == this.getClass() && postarget.getTeamColor() != this.getTeamColor()) {
                    newPossible = new ChessPosition(myRow - 1, myColumn + 1);
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                    //passantE = false;
                }
            }
            if (passantW) { //a pawn might have moved to our col-1, if they did add en passant to our moves
                ChessPosition passantcheck = new ChessPosition(myRow, myColumn - 1);
                ChessPiece postarget = board.getPiece(passantcheck);
                if (postarget != null && postarget.getClass() == this.getClass() && postarget.getTeamColor() != this.getTeamColor()) {
                    newPossible = new ChessPosition(myRow - 1, myColumn - 1);
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                    //passantW = false;
                }
            }
        }

        if (myColor == ChessGame.TeamColor.WHITE) {
            int goup = myRow + 1;
            ChessPosition newPossible = new ChessPosition(goup, myColumn);
            if (onBoard(newPossible) && board.getPiece(newPossible) == null) {//regular move forward 1
                if (myRow == 7) {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.QUEEN));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.ROOK));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
                }
                else {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                }
            }
            newPossible = new ChessPosition(goup, myColumn + 1); //upper right attack
            if (onBoard(newPossible) && board.getPiece(newPossible) != null && board.getPiece(newPossible).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (myRow == 7) {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.QUEEN));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.ROOK));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
                }
                else {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                }
            }
            newPossible = new ChessPosition(goup, myColumn - 1); //upper left attack
            if (onBoard(newPossible) && board.getPiece(newPossible) != null && board.getPiece(newPossible).getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (myRow == 7) {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.QUEEN));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.ROOK));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.KNIGHT));
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible, PieceType.BISHOP));
                }
                else {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                }
            }
            newPossible = new ChessPosition(goup, myColumn);
            if (myRow == 2 && board.getPiece(newPossible) == null) {//irregular jump 2 at the start
                goup = goup + 1;
                newPossible = new ChessPosition(goup, myColumn);
                if (onBoard(newPossible) && board.getPiece(newPossible) == null) {
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));

                    ChessPosition passantcheck = new ChessPosition(goup, myColumn - 1);
                    ChessPiece posenemy = board.getPiece(passantcheck);
                    if (posenemy != null && posenemy.getClass() == this.getClass() && posenemy.getTeamColor() == ChessGame.TeamColor.BLACK) {
                        ((Pawn) posenemy).setPassantE();
                        System.out.println("En passant available!");
                    }
                    passantcheck = new ChessPosition(goup, myColumn + 1);
                    posenemy = board.getPiece(passantcheck);
                    if (posenemy != null && posenemy.getClass() == this.getClass() && posenemy.getTeamColor() == ChessGame.TeamColor.BLACK) {
                        ((Pawn) posenemy).setPassantW();
                        System.out.println("En passant available!");
                    }
                }
            }
            if (passantE) { //a pawn might have moved to our col+1, if they did add en passant to our moves
                System.out.println("White may capture by en passant to the right");
                ChessPosition passantcheck = new ChessPosition(myRow, myColumn + 1);
                ChessPiece postarget = board.getPiece(passantcheck);
                if (postarget != null && postarget.getClass() == this.getClass() && postarget.getTeamColor() != this.getTeamColor()) {
                    newPossible = new ChessPosition(myRow + 1, myColumn + 1);
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                    //passantE = false;
                }
            }
            if (passantW) { //a pawn might have moved to our col-1, if they did add en passant to our moves
                System.out.println("White may capture by en passant to the left");
                ChessPosition passantcheck = new ChessPosition(myRow, myColumn - 1);
                ChessPiece postarget = board.getPiece(passantcheck);
                if (postarget != null && postarget.getClass() == this.getClass() && postarget.getTeamColor() != this.getTeamColor()) {
                    newPossible = new ChessPosition(myRow + 1, myColumn - 1);
                    myMoves.add(new ChessMove((ChessPosition) myPosition,newPossible));
                    //passantW = false;
                }
            }
        }
        //passantE = false;
        //passantW = false;
        //maybe have game keep track of if a pawn just barely moved 2 spaces forward,
        //or it can be pawns themselves that ask the board when they move 2 spaces forward
        //then have some trigger that allows en passant, but only lasts for one turn
        //maybe after we've added the en passant into our moveset, then set en passant to false

        //if black, then go down and capture down diagonally


        //if white, then go up and capture up diagonally

        //if we promote, then the pawn will delete itself and be replaced with the promotion piece
        myMoves = protectKing(board, myMoves);
        return myMoves;
    }
}

     */
