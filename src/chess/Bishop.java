package chess;

//Bishop class

import java.util.ArrayList;

public class Bishop {
    
//    public Bishop(String pieceName){
//        super(pieceName);
//    }
    public static ArrayList<String> possibleMoves = new ArrayList<>();
    public static boolean move(int x, int y) {
//        System.out.println(x + "" + y);
        possibleMoves = new ArrayList<>();
        int i = 0;
        for (i = 1; i < 8; i++) {
            if (onBoard(x+i, y+i)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x + i, y + i))) {
                    if (Chess.getPiece(Chess.toMove(x+i, y+i)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x + i, y + i));
                        break;
                    }

                } else {
                    possibleMoves.add(Chess.toMove(x + i, y + i));
                }
            }
        }

        for (i = 1; i < 8; i++) {
            if (onBoard(x+i, y-i)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x+i, y-i))) {
                    if (Chess.getPiece(Chess.toMove(x+i, y-i)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x+i, y-i));
                        break;
                    }
                } else {
                    possibleMoves.add(Chess.toMove(x+i, y-i));
                }
            }
        }



        for (i = 1; i < 8; i++) {
            if (onBoard(x-i, y+i)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x-i, y+i))) {
                    if (Chess.getPiece(Chess.toMove(x-i, y+i)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x - i, y + i));
                        break;
                    }
                } else {
                    possibleMoves.add(Chess.toMove(x-i, y+i));
                }
            }
        }


        for (i = 1; i < 8; i++) {
            if (onBoard(x-i, y-i)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x-i, y-i))) {
                    if (Chess.getPiece(Chess.toMove(x-i, y-i)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x - i, y - i));
                        break;
                    }
                } else {
                    possibleMoves.add(Chess.toMove(x-i, y-i));
                }
            }
        }
        return true;
    }

    public static boolean onBoard(int x, int y) {
        if ((x>=0 && x<8) && (y>=0 && y<8)) {
            return true;
        }
        return false;
    }

    public static boolean checkMove(int nx, int ny){
        for (int i = 0; i < possibleMoves.size(); i++) {
            if (possibleMoves.get(i).equals(Chess.toMove(nx, ny))) {
                return true;
            }
        }
        //The bishop can take accordingly but the bishop can't move forward if there is a piece in the way
        //we call canCapture and blockingPiece to see if the bishop can move
        return false;
    }
//
//private boolean canCapture(){
//    //we'll determine if we can capture that piece
//}
//
//private boolean blockingPiece(){
//    //check to see if the piece if blocked by own piece, or opposing piece
//}
//
//@Override
//public boolean isCheck(String move){
//    //
//    return false;
//}
}
