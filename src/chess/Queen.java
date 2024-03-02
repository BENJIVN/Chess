package chess;

//Queen Class

import java.util.ArrayList;

public class Queen{
    public static ArrayList<String> possibleMoves = new ArrayList<>();
    public static boolean move(int x, int y){
        possibleMoves = new ArrayList<>();
        // addRookMoves(x,y);

        // addBishopMoves(x,y);

        // return true;
        
        //Bishop Moves
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
        //Queen Moves
        for (i = 1; i < 8; i++) {
            if (onBoard(x+i, y)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x + i, y))) {
                    if (Chess.getPiece(Chess.toMove(x+i, y)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x + i, y));
                        break;
                    }

                } else {
                    possibleMoves.add(Chess.toMove(x + i, y));
                }
            }
        }

        for (i = 1; i < 8; i++) {
            if (onBoard(x-i, y)) {
//                    System.out.println("x:" + (x + i) + " y:" + (y + i) + " i:" + i);
                if (Chess.pieceExistsAtLocation(Chess.toMove(x - i, y))) {
                    if (Chess.getPiece(Chess.toMove(x-i, y)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x - i, y));
                        break;
                    }

                } else {
                    possibleMoves.add(Chess.toMove(x - i, y));
                }
            }
        }



        for (i = 1; i < 8; i++) {
            if (onBoard(x, y+i)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x, y+i))) {
                    if (Chess.getPiece(Chess.toMove(x, y+i)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x, y + i));
                        break;
                    }
                } else {
                    possibleMoves.add(Chess.toMove(x, y+i));
                }
            }
        }


        for (i = 1; i < 8; i++) {
            if (onBoard(x, y-i)) {
                if (Chess.pieceExistsAtLocation(Chess.toMove(x, y-i))) {
                    if (Chess.getPiece(Chess.toMove(x, y-i)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
                        possibleMoves.add(Chess.toMove(x, y - i));
                        break;
                    }
                } else {
                    possibleMoves.add(Chess.toMove(x, y-i));
                }
            }
        }

        return true;

    }

    // public static void addRookMoves(int x, int y){
    //     for (int i = 1; i < 8; i++){
    //         //horizontal rigt move 
    //         addMoveIfPossible(x + i, y);
    //         //horizontal left move
    //         addMoveIfPossible(x - i, y);
    //         //vertical up move
    //         addMoveIfPossible(x, y + i);
    //         //vertical down move
    //         addMoveIfPossible(x, y - i);
    //         }
    //     }
    
    // public static void addBishopMoves(int x, int y){
    //     for (int i = 1; i < 8; i++){
    //         //diagonal up-right
    //         addMoveIfPossible(x + i, y + i);
    //         //diagonal up-left
    //         addMoveIfPossible(x-i, y + i);
    //         //diagonal down-right
    //         addMoveIfPossible(x + i, y - i);
    //         //diagonal down-left
    //         addMoveIfPossible(x - i, y - i);

    //     }
    // }

    // public static void addMoveIfPossible(int x, int y){
    //     if (onBoard(x, y)) {
    //         if (Chess.pieceExistsAtLocation(Chess.toMove(x, y))) {
    //             if (Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0) != Chess.getPiece(Chess.toMove(x, y)).pieceType.name().charAt(0)) {
    //                 possibleMoves.add(Chess.toMove(x, y ));
    //             }

    //         } else {
    //             possibleMoves.add(Chess.toMove(x, y));
    //         }
    //     }
    // }
    
    

    public static boolean onBoard(int x, int y){
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

        return false;
    }
    
}
