package chess;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ReturnPiece {
    static enum PieceType {WP, WR, WN, WB, WQ, WK,
        BP, BR, BN, BB, BK, BQ};
    static enum PieceFile {a, b, c, d, e, f, g, h};

    PieceType pieceType;
    PieceFile pieceFile;
    int pieceRank;  // 1..8
    public String toString() {
        return ""+pieceFile+pieceRank+":"+pieceType;
    }
    public boolean equals(Object other) {
        if (other == null || !(other instanceof ReturnPiece)) {
            return false;
        }
        ReturnPiece otherPiece = (ReturnPiece)other;
        return pieceType == otherPiece.pieceType &&
                pieceFile == otherPiece.pieceFile &&
                pieceRank == otherPiece.pieceRank;
    }
}

class ReturnPlay {
    enum Message {ILLEGAL_MOVE, DRAW,
        RESIGN_BLACK_WINS, RESIGN_WHITE_WINS,
        CHECK, CHECKMATE_BLACK_WINS,	CHECKMATE_WHITE_WINS,
        STALEMATE};

    ArrayList<ReturnPiece> piecesOnBoard;
    Message message;
}

public class Chess {

    enum Player { white, black }

    public static Player currentPlayer = Player.white;
    //public static ArrayList<String> allMoves;
    public static String lastMove = "";

    /**
     * Plays the next move for whichever player has the turn.
     *
     * @param move String for next move, e.g. "a2 a3"
     *
     * @return A ReturnPlay instance that contains the result of the move.
     *         See the section "The Chess class" in the assignment description for details of
     *         the contents of the returned ReturnPlay instance.
     */
    public static ReturnPlay play(String move) {
        String[] moveArray = move.split(" ");

//        System.out.println(currentPlayer); //MAKE SURE TO REMOVE THIS
        if (returnPlay.message == ReturnPlay.Message.CHECKMATE_BLACK_WINS || returnPlay.message == ReturnPlay.Message.CHECKMATE_WHITE_WINS || returnPlay.message == ReturnPlay.Message.RESIGN_BLACK_WINS || returnPlay.message == ReturnPlay.Message.RESIGN_WHITE_WINS) {
            return returnPlay;
        }

        returnPlay.message = null;

        if (move.equals("resign")) {

            if (currentPlayer == Player.white) {
                returnPlay.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
            } else {
                returnPlay.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
            }

            return returnPlay;
        }

        if (moveArray.length < 2 || moveArray.length > 3) {
            returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return returnPlay;
        }

        String currentPosition = moveArray[0];
        String nextPosition = moveArray[1];

        String regex = "[abcdefgh][1-8] [abcdefgh][1-8]( (B|N|Q|R|draw\\?))?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(move);
        if (!matcher.matches()) {
            returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return returnPlay;
        }

        ReturnPiece currentPiece = getPiece(currentPosition);

        if (currentPiece == null) {
            returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return returnPlay;
        }


        if (currentPiece.pieceType.name().charAt(0) == 'W' && currentPlayer != Player.white) {
            returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return returnPlay;
        }

        else if (currentPiece.pieceType.name().charAt(0) == 'B' && currentPlayer != Player.black) {
            returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return returnPlay;
        }
        int x = getFile(currentPiece);
        int y = currentPiece.pieceRank-1;

        int nx = getFile(nextPosition);
        int ny = Integer.parseInt(nextPosition.substring(1))-1;

        String promotionChoice = "";
        if (moveArray.length == 3) {
            if (!moveArray[2].contains("draw?"))
                promotionChoice = moveArray[2];
        }




/** IF IN CHECK -> RESTRICT MOVES TO KING ONLY **/
    if (isCheck(getKing(playerSwitch(currentPlayer).name()), currentPlayer.name())) { // ALWAYS FALSE

        if (currentPiece.pieceType.name().charAt(1) != 'K') {
            returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            return returnPlay;
        } else {
            if (currentPiece.pieceType.name().charAt(1) == 'K') {
                King.move(x, y);
                if (King.checkMove(nx, ny)) {
                    //CASTLING
                    if ((!KingHasMoved_WHITE && currentPlayer == Player.white) && (Math.abs(nx - x) == 2)) {
                        if (King.possibleMoves.contains(toMove(nx,ny))) {
                            if (nx == 6) {
                                ReturnPiece rook = gPiece("h1");
                                currentPiece.pieceFile = ReturnPiece.PieceFile.g;
                                currentPiece.pieceRank = 1;
                                rook.pieceFile = ReturnPiece.PieceFile.f;
                                rook.pieceRank = 1;
                            } else if (nx == 2) {
                                ReturnPiece rook = gPiece("a1");
                                currentPiece.pieceFile = ReturnPiece.PieceFile.c;
                                currentPiece.pieceRank = 1;
                                rook.pieceFile = ReturnPiece.PieceFile.d;
                                rook.pieceRank = 1;
                            }
                            KingHasMoved_WHITE = true;
                        }
                    } else if ((!KingHasMoved_BLACK && currentPlayer == Player.black) && (Math.abs(nx - x) == 2)) {
                        if (King.possibleMoves.contains(toMove(nx,ny))) {
                            if (nx == 6) {
                                ReturnPiece rook = gPiece("h8");
                                currentPiece.pieceFile = ReturnPiece.PieceFile.g;
                                currentPiece.pieceRank = 8;
                                rook.pieceFile = ReturnPiece.PieceFile.f;
                                rook.pieceRank = 8;
                            } else if (nx == 2) {
                                ReturnPiece rook = gPiece("a8");
                                currentPiece.pieceFile = ReturnPiece.PieceFile.c;
                                currentPiece.pieceRank = 8;
                                rook.pieceFile = ReturnPiece.PieceFile.d;
                                rook.pieceRank = 8;
                            }
                            KingHasMoved_BLACK = true;
                        }
                    } else {
//                    System.out.println(2);
                        if (pieceExistsAtLocation(toMove(nx, ny))) {
                            ReturnPiece temp = gPiece(toMove(nx,ny));
                            returnPlay.piecesOnBoard.remove(gPiece(toMove(nx,ny)));
                            currentPiece.pieceFile = temp.pieceFile;
                            currentPiece.pieceRank = temp.pieceRank;

                        } else {
                            currentPiece.pieceFile = toFile(toMove(nx,ny).charAt(0));
                            currentPiece.pieceRank = ny+1;
                        }


                        if (currentPlayer == Player.white) {
                            KingHasMoved_WHITE = true;
                        } else {
                            KingHasMoved_BLACK = true;
                        }

                    }
                } else {
//                System.out.println(1);
                    returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
                }

            }
        }
/**
 *  IF NOT IN CHECK -> REGULAR MOVES
 * */
    } else {
//        System.out.println(2);
//        if (isCheck(getKing(playerSwitch(currentPlayer).name()), currentPlayer.name())) {
//            System.out.println(3);
//            if (!King.possibleMoves.isEmpty()) {
//
//                for (String m : King.possibleMoves) {
//                    if (isCheck(m, currentPlayer.name())) {
//
//                        if (currentPlayer.name() == "black") {
//                            returnPlay.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
//                            return returnPlay;
//                        } else {
//                            returnPlay.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
//                            return returnPlay;
//                        }
//                    }
//                }
//            }
//        }

        if (currentPiece.pieceType.name().charAt(1) == 'B') {
            //BISHOP
            Bishop.move(x, y);
            if (Bishop.checkMove(nx, ny)) {
                if (pieceExistsAtLocation(toMove(nx, ny))) {
                    ReturnPiece temp = gPiece(toMove(nx,ny));
                    returnPlay.piecesOnBoard.remove(gPiece(toMove(nx,ny)));
                    currentPiece.pieceFile = temp.pieceFile;
                    currentPiece.pieceRank = temp.pieceRank;
                } else {
                    currentPiece.pieceFile = toFile(toMove(nx,ny).charAt(0));
                    currentPiece.pieceRank = ny+1;
                }
            } else {
                returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            }

        }
        if (currentPiece.pieceType.name().charAt(1) == 'R') {
            Rook.move(x, y);
            if (Rook.checkMove(nx, ny)) {
                if (pieceExistsAtLocation(toMove(nx, ny))) {
                    ReturnPiece temp = gPiece(toMove(nx,ny));
                    returnPlay.piecesOnBoard.remove(gPiece(toMove(nx,ny)));
                    currentPiece.pieceFile = temp.pieceFile;
                    currentPiece.pieceRank = temp.pieceRank;
                } else {
                    currentPiece.pieceFile = toFile(toMove(nx,ny).charAt(0));
                    currentPiece.pieceRank = ny+1;
                }
            } else {

                returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            }
        }

        if (currentPiece.pieceType.name().charAt(1) == 'N') {
            Knight.move(x, y);
            if (Knight.checkMove(nx, ny)) {
                if (pieceExistsAtLocation(toMove(nx, ny))) {
                    ReturnPiece temp = gPiece(toMove(nx,ny));
                    returnPlay.piecesOnBoard.remove(gPiece(toMove(nx,ny)));
                    currentPiece.pieceFile = temp.pieceFile;
                    currentPiece.pieceRank = temp.pieceRank;
                } else {
                    currentPiece.pieceFile = toFile(toMove(nx,ny).charAt(0));
                    currentPiece.pieceRank = ny+1;
                }
            } else {

                returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            }
        }

        if (currentPiece.pieceType.name().charAt(1) == 'K') {
            King.move(x, y);
            if (King.checkMove(nx, ny)) {
                //CASTLING
                if ((!KingHasMoved_WHITE && currentPlayer == Player.white) && (Math.abs(nx - x) == 2)) {
                    if (King.possibleMoves.contains(toMove(nx,ny))) {
                        if (nx == 6) {
                            ReturnPiece rook = gPiece("h1");
                            currentPiece.pieceFile = ReturnPiece.PieceFile.g;
                            currentPiece.pieceRank = 1;
                            rook.pieceFile = ReturnPiece.PieceFile.f;
                            rook.pieceRank = 1;
                        } else if (nx == 2) {
                            ReturnPiece rook = gPiece("a1");
                            currentPiece.pieceFile = ReturnPiece.PieceFile.c;
                            currentPiece.pieceRank = 1;
                            rook.pieceFile = ReturnPiece.PieceFile.d;
                            rook.pieceRank = 1;
                        }
                        KingHasMoved_WHITE = true;
                    }
                } else if ((!KingHasMoved_BLACK && currentPlayer == Player.black) && (Math.abs(nx - x) == 2)) {
                    if (King.possibleMoves.contains(toMove(nx,ny))) {
                        if (nx == 6) {
                            ReturnPiece rook = gPiece("h8");
                            currentPiece.pieceFile = ReturnPiece.PieceFile.g;
                            currentPiece.pieceRank = 8;
                            rook.pieceFile = ReturnPiece.PieceFile.f;
                            rook.pieceRank = 8;
                        } else if (nx == 2) {
                            ReturnPiece rook = gPiece("a8");
                            currentPiece.pieceFile = ReturnPiece.PieceFile.c;
                            currentPiece.pieceRank = 8;
                            rook.pieceFile = ReturnPiece.PieceFile.d;
                            rook.pieceRank = 8;
                        }
                        KingHasMoved_BLACK = true;
                    }
                } else {
//                    System.out.println(2);

                        if (pieceExistsAtLocation(toMove(nx, ny))) {
                            ReturnPiece temp = gPiece(toMove(nx,ny));
                            returnPlay.piecesOnBoard.remove(gPiece(toMove(nx,ny)));
                            currentPiece.pieceFile = temp.pieceFile;
                            currentPiece.pieceRank = temp.pieceRank;
                            if (inCheckmate()) {
                                return returnPlay;
                            }

                        } else {
                            currentPiece.pieceFile = toFile(toMove(nx,ny).charAt(0));
                            currentPiece.pieceRank = ny+1;
                            if (inCheckmate()) {
                                return returnPlay;
                            }
                        }




                    if (currentPlayer == Player.white) {
                        KingHasMoved_WHITE = true;
                    } else {
                        KingHasMoved_BLACK = true;
                    }

                }
            } else {
//                System.out.println(1);
                returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            }

        }
        if (currentPiece.pieceType.name().charAt(1) == 'Q') {
            Queen.move(x, y);
            if (Queen.checkMove(nx, ny)) {
                if (pieceExistsAtLocation(toMove(nx, ny))) {
                    ReturnPiece temp = gPiece(toMove(nx,ny));
                    returnPlay.piecesOnBoard.remove(gPiece(toMove(nx,ny)));
                    currentPiece.pieceFile = temp.pieceFile;
                    currentPiece.pieceRank = temp.pieceRank;
                } else {
                    currentPiece.pieceFile = toFile(toMove(nx,ny).charAt(0));
                    currentPiece.pieceRank = ny+1;
                }
            } else {

                returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            }
        }
/** PAWN */
        if (currentPiece.pieceType.name().charAt(1) == 'P') {
//            System.out.println(toMove(x,y) + "    " + toMove(nx, ny));
            Pawn.move(x, y, currentPlayer.name());
//            Pawn.possibleMoves.forEach(a-> System.out.println(a));
            if (Pawn.checkMove(nx, ny, x, y, currentPlayer.name())) {  //!!!!!! fix it for enpassant
//                Pawn.printPossibleMoves();
                if (pieceExistsAtLocation(toMove(nx, ny))) { //normal capture
                    ReturnPiece temp = gPiece(toMove(nx,ny));
                    returnPlay.piecesOnBoard.remove(gPiece(toMove(nx,ny)));
                    currentPiece.pieceFile = temp.pieceFile;
                    currentPiece.pieceRank = temp.pieceRank;
                }
                if (Chess.canEnPassant(x, y, currentPlayer.name())){
//                    int captureY = currentPlayer == Player.white ? y + 1 : y - 1;
                    String capturedPawnPosition = toMove(nx, y);
                    ReturnPiece capturedPawn = gPiece(capturedPawnPosition);
                    if(capturedPawn != null && capturedPawn.pieceType.name().charAt(1) == 'P'){
                        returnPlay.piecesOnBoard.remove(capturedPawn);
                        currentPiece.pieceFile = toFile(toMove(nx,ny).charAt(0));
                        currentPiece.pieceRank = ny+1;

                    }
                } else { ///normal move type
                    currentPiece.pieceFile = toFile(toMove(nx,ny).charAt(0));
                    currentPiece.pieceRank = ny+1;
                    if (!promotionChoice.isEmpty() && ((ny == 0  && currentPlayer == Player.black) || (ny == 7 && currentPlayer == Player.white))) {
                        performPromotion(nx, ny, promotionChoice, currentPlayer.name());
                    }
                }

            } else {
//                System.out.println("PAWN ILLEGAL");
                returnPlay.message = ReturnPlay.Message.ILLEGAL_MOVE;
            }
        }

//        System.out.println("llll");
    }






        lastMove = move;
//        System.out.println("Last move updated to: " + lastMove);

        if (inCheckmate()) {
            return returnPlay;
        }



        if (isCheck(toMove(getKing("white")), "white")) {
            if (isCheck(toMove(nx, ny), playerSwitch(currentPlayer).name())) {

                if (currentPlayer.name() == "black") {

                    returnPlay.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
                    return returnPlay;
                } else if (currentPlayer.name() == "white") {

                    returnPlay.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
                    return returnPlay;
                }
            }

            returnPlay.message = returnPlay.message.CHECK;
        } else if (isCheck(toMove(getKing("black")), "black")) {
            if (isCheck(toMove(nx, ny), playerSwitch(currentPlayer).name())) {

                if (currentPlayer.name() == "black") {

                    returnPlay.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
                    return returnPlay;
                } else if (currentPlayer.name() == "white") {

                    returnPlay.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
                    return returnPlay;
                }
            }

            returnPlay.message = returnPlay.message.CHECK;
        }



        if (moveArray.length == 3 && moveArray[2].contains("draw?")) {
            returnPlay.message = returnPlay.message.DRAW;
            return returnPlay;
        }

        //Switch Turns
        if(returnPlay.message != ReturnPlay.Message.ILLEGAL_MOVE) {
            if (currentPlayer == Player.white) {
                currentPlayer = Player.black;
            } else {
                currentPlayer = Player.white;
            }
        }


        return returnPlay;
    }


    public static ReturnPlay returnPlay;
    public static boolean KingHasMoved_WHITE = false;
    public static boolean KingHasMoved_BLACK = false;
    public static ArrayList<String> previousMoves;
    /**
     * This method should reset the game, and start from scratch.
     */
    public static void start() {
        // ArrayList<ReturnPiece> piecesOnBoard = new ArrayList<ReturnPiece>();
        returnPlay = new ReturnPlay();
        returnPlay.piecesOnBoard = new ArrayList<ReturnPiece>();


//      Black pawns
            for (int i = 0; i < 8; i++) {
                ReturnPiece chessPiece = getPiece(ReturnPiece.PieceType.BP, ReturnPiece.PieceFile.values()[i], 7);
                returnPlay.piecesOnBoard.add(chessPiece);
            }
//      Black pieces
        ReturnPiece blackRook = getPiece(ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.a, 8);
        returnPlay.piecesOnBoard.add(blackRook);
        ReturnPiece blackKnight = getPiece(ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.b, 8);
        returnPlay.piecesOnBoard.add(blackKnight);
        ReturnPiece blackBishop = getPiece(ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.c, 8);
        returnPlay.piecesOnBoard.add(blackBishop);
        ReturnPiece blackQueen = getPiece(ReturnPiece.PieceType.BQ, ReturnPiece.PieceFile.d, 8);
        returnPlay.piecesOnBoard.add(blackQueen);
        ReturnPiece blackKing = getPiece(ReturnPiece.PieceType.BK, ReturnPiece.PieceFile.e, 8);
        returnPlay.piecesOnBoard.add(blackKing);
        ReturnPiece blackBishop2 = getPiece(ReturnPiece.PieceType.BB, ReturnPiece.PieceFile.f, 8);
        returnPlay.piecesOnBoard.add(blackBishop2);
        ReturnPiece blackKnight2 = getPiece(ReturnPiece.PieceType.BN, ReturnPiece.PieceFile.g, 8);
        returnPlay.piecesOnBoard.add(blackKnight2);
        ReturnPiece blackRook2 = getPiece(ReturnPiece.PieceType.BR, ReturnPiece.PieceFile.h, 8);
        returnPlay.piecesOnBoard.add(blackRook2);
        // White pawns
        for (int i = 0; i < 8; i++) {
            ReturnPiece chessPiece = getPiece(ReturnPiece.PieceType.WP, ReturnPiece.PieceFile.values()[i], 2);
            returnPlay.piecesOnBoard.add(chessPiece);
        }

        //White Pieces
        ReturnPiece whiteRook = getPiece(ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.a, 1);
        returnPlay.piecesOnBoard.add(whiteRook);
        ReturnPiece whiteKnight = getPiece(ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.b, 1);
        returnPlay.piecesOnBoard.add(whiteKnight);
        ReturnPiece whiteBishop = getPiece(ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.c, 1);
        returnPlay.piecesOnBoard.add(whiteBishop);
        ReturnPiece whiteQueen = getPiece(ReturnPiece.PieceType.WQ, ReturnPiece.PieceFile.d, 1);
        returnPlay.piecesOnBoard.add(whiteQueen);
        ReturnPiece whiteKing = getPiece(ReturnPiece.PieceType.WK, ReturnPiece.PieceFile.e, 1);
        returnPlay.piecesOnBoard.add(whiteKing);
        ReturnPiece whiteBishop2 = getPiece(ReturnPiece.PieceType.WB, ReturnPiece.PieceFile.f, 1);
        returnPlay.piecesOnBoard.add(whiteBishop2);
        ReturnPiece whiteKnight2 = getPiece(ReturnPiece.PieceType.WN, ReturnPiece.PieceFile.g, 1);
        returnPlay.piecesOnBoard.add(whiteKnight2);
        ReturnPiece whiteRook2 = getPiece(ReturnPiece.PieceType.WR, ReturnPiece.PieceFile.h, 1);
        returnPlay.piecesOnBoard.add(whiteRook2);


        PlayChess.printPiecesOnBoard(returnPlay.piecesOnBoard, PlayChess.makeBlankBoard());
        PlayChess.printBoard(returnPlay.piecesOnBoard);

        /* FILL IN THIS METHOD */
    }

    public static ReturnPiece getPiece(ReturnPiece.PieceType pieceType, ReturnPiece.PieceFile pieceFile, int pieceRank) {
        ReturnPiece piece = new ReturnPiece();
        piece.pieceType = pieceType;
        piece.pieceFile = pieceFile;
        piece.pieceRank = pieceRank;

        return piece;
    }

    public static int getFile(ReturnPiece currentPiece) {
        if (currentPiece.pieceFile == ReturnPiece.PieceFile.a) {
            return 0;
        }
        else if (currentPiece.pieceFile == ReturnPiece.PieceFile.b) {
            return 1;
        }
        else if (currentPiece.pieceFile == ReturnPiece.PieceFile.c) {
            return 2;
        }
        else if (currentPiece.pieceFile == ReturnPiece.PieceFile.d) {
            return 3;
        }
        else if (currentPiece.pieceFile == ReturnPiece.PieceFile.e) {
            return 4;
        }
        else if (currentPiece.pieceFile == ReturnPiece.PieceFile.f) {
            return 5;
        }
        else if (currentPiece.pieceFile == ReturnPiece.PieceFile.g) {
            return 6;
        }
        else {
            return 7;
        }
    }

    public static int getFile(String move) {
        switch(move.charAt(0)) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return -1;
        }
    }

    public static String toMove(String move) {
        String moverly = "";
        if (move.charAt(0) == '0') {
            moverly += 'a';
        } else if (move.charAt(0) == '1') {
            moverly += 'b';
        } else if (move.charAt(0) == '2') {
            moverly += 'c';
        } else if (move.charAt(0) == '3') {
            moverly += 'd';
        } else if (move.charAt(0) == '4') {
            moverly += 'e';
        } else if (move.charAt(0) == '5') {
            moverly += 'f';
        } else if (move.charAt(0) == '6') {
            moverly += 'g';
        } else {
            moverly += 'h';
        }
        moverly += Integer.parseInt(String.valueOf(move.charAt(1)))+1;
        return moverly;
    }

    public static String toMove(int x, int y) {
        String moverly = "";
        String move = Integer.toString(x) + Integer.toString(y);
        if (move.charAt(0) == '0') {
            moverly += 'a';
        } else if (move.charAt(0) == '1') {
            moverly += 'b';
        } else if (move.charAt(0) == '2') {
            moverly += 'c';
        } else if (move.charAt(0) == '3') {
            moverly += 'd';
        } else if (move.charAt(0) == '4') {
            moverly += 'e';
        } else if (move.charAt(0) == '5') {
            moverly += 'f';
        } else if (move.charAt(0) == '6') {
            moverly += 'g';
        } else {
            moverly += 'h';
        }
        moverly += Integer.parseInt(String.valueOf(move.charAt(1)))+1;
        return moverly;
    }

    public static boolean pieceExistsAtLocation(String move) {
        char fileChar = move.charAt(0);
        int rank = Integer.parseInt(move.substring(1));

        for (ReturnPiece piece : returnPlay.piecesOnBoard){
            char pieceFileChar = piece.pieceFile.name().charAt(0);

            if(pieceFileChar == fileChar && piece.pieceRank == rank){
                return true;
            }
        }
        return false;
    }

    public static ReturnPiece getPiece(String location) {
        char fileChar = location.charAt(0);
        int rank = Integer.parseInt(location.substring(1));

        for (ReturnPiece piece : returnPlay.piecesOnBoard){
            char pieceFileChar = piece.pieceFile.name().charAt(0);

            if (pieceFileChar == fileChar && piece.pieceRank == rank){
                return piece;
            }
        }
        return null;
    }

    public static ReturnPiece gPiece(String location) {
        ArrayList<ReturnPiece> pieces = returnPlay.piecesOnBoard;
        for (ReturnPiece piece : pieces) {
            if (piece.pieceFile.name().equals(location.substring(0, 1))
                    && piece.pieceRank == Integer.parseInt(location.substring(1))) {
                return piece;
            }
        }
        return null;
    }

    public static ReturnPiece.PieceFile toFile(char file) {
        if (file == 'a') {
            return ReturnPiece.PieceFile.a;
        } else if (file == 'b') {
            return ReturnPiece.PieceFile.b;
        } else if (file == 'c') {
            return ReturnPiece.PieceFile.c;
        } else if (file == 'd') {
            return ReturnPiece.PieceFile.d;
        } else if (file == 'e') {
            return ReturnPiece.PieceFile.e;
        } else if (file == 'f') {
            return ReturnPiece.PieceFile.f;
        } else if (file == 'g') {
            return ReturnPiece.PieceFile.g;
        } else {
            return ReturnPiece.PieceFile.h;
        }
    }

    public static void performPromotion(int x, int y, String promotionChoice, String color){
        ReturnPiece.PieceType newType = PromotionSelection(color, promotionChoice);
        Chess.gPiece(toMove(x,y)).pieceType = newType;
    }
    public static ReturnPiece.PieceType PromotionSelection (String color, String promoteChoice){
        switch(promoteChoice.toUpperCase()){
            case "R":
                return color.equals("white") ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
            case "N":
                return color.equals("white") ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
            case "B":
                return color.equals("white") ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
            default: // Defaults to Queen if no match
                return color.equals("white") ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
        }
    }

    public static String getColor(int x, int y, ReturnPiece piece) {
        if (getPiece(toMove(x, y)).pieceType.name().charAt(0) == 'B') {
            return "black";
        } else {
            return "white";
        }
    }

    public static boolean canEnPassant(int x, int y, String color) {
//        System.out.println(lastMove);
        if (lastMove == null || lastMove.split(" ").length < 2) {
//            System.out.println("te");
            return false; 
        }
        String[] parts = lastMove.split(" ");
        String fromLastMove = parts[0];
        String toLastMove = parts[1];
//        System.out.println(fromLastMove);
//        System.out.println(toLastMove);
        int fromX = getFile(fromLastMove.substring(0, 1));
        int fromY = Integer.parseInt(fromLastMove.substring(1)) - 1; 
        int toX = getFile(toLastMove.substring(0, 1));
        int toY = Integer.parseInt(toLastMove.substring(1)) - 1; 
    
        int direction = color.equals("white") ? 1 : -1;

        boolean isTwoSquareMove = (fromY == 1 && toY == 3) || (fromY == 6 && toY == 4);
    
        if (color.equals("white") && y == 4 && direction == 1) {
            return isTwoSquareMove && (toX == x - 1 || toX == x + 1) && toY == y;
        } else if (color.equals("black") && y == 3 && direction == -1) {
            return isTwoSquareMove && (toX == x - 1 || toX == x + 1) && toY == y;
        }
    
        return false;
    }

    public static String getKing(String Color) {
        String move = "";
        for (ReturnPiece piece : returnPlay.piecesOnBoard) {
            if (piece.pieceType.name().equals("WK") && Color == "white") {
                move += getFile(piece.pieceFile.name());
//                System.out.println(move);
                move += Integer.toString(piece.pieceRank-1);
//                System.out.println(move);

            } else if (piece.pieceType.name().equals("BK") && Color == "black") {

                move += getFile(piece.pieceFile.name());
//                System.out.println(move);

                move += Integer.toString(piece.pieceRank-1);
//                System.out.println(move);

            }
        }
        return move;
    }

    public static boolean isCheck(String KingLocation, String KingColor) {
//        System.out.println(1);
        for (ReturnPiece piece : returnPlay.piecesOnBoard) {
            if (KingColor == "white") {
//                System.out.print(2);
                if (piece.pieceType.name().charAt(0) == 'B') { //CHECK IF CURRENT PIECE COLOR IS NOT SAME AS KING
//                    System.out.print(3);
                    if (piece.pieceType.name().charAt(1) == 'B') { //BISHOP
                        Bishop.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (Bishop.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'Q') { //QUEEN
                        Queen.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (Queen.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'P') { //PAWN
                        Pawn.move(getFile(piece.pieceFile.name()), piece.pieceRank-1, "black");
                        if (Pawn.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'K') { //KING
                        King.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (King.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'R') { //ROOK
                        Rook.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (Rook.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'N') { //KNIGHT
                        Knight.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (Knight.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                }
            } else if (KingColor == "black"){
                if (piece.pieceType.name().charAt(0) == 'W') { //CHECK IF CURRENTPIECE COLOR IS NOT SAME AS KING

                    if (piece.pieceType.name().charAt(1) == 'B') { //BISHOP
                        Bishop.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
//                        Bishop.possibleMoves.forEach(move-> System.out.println(move));
                        if (Bishop.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'Q') { //QUEEN
                        Queen.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (Queen.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'P') { //PAWN
                        Pawn.move(getFile(piece.pieceFile.name()), piece.pieceRank-1, "white");
                        if (Pawn.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'K') { //KING
                        King.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (King.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'R') { //ROOK
                        Rook.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (Rook.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                    if (piece.pieceType.name().charAt(1) == 'N') { //KNIGHT
                        Knight.move(getFile(piece.pieceFile.name()), piece.pieceRank-1);
                        if (Knight.possibleMoves.contains(KingLocation)) {
                            return true;
                        }
                    }
                }
            }

        }
        return false;
    }

    public static boolean inCheckmate() {
        if (isCheck(toMove(getKing("white")), "white")) {
            King.move(Integer.parseInt(String.valueOf(getKing("white").charAt(0))), Integer.parseInt(String.valueOf(getKing("white").charAt(1))));

            for (int i = 0; i < King.possibleMoves.size(); i++) {
//                System.out.println(i);
                if (isCheck(King.possibleMoves.get(i), "white")) {
                    King.possibleMoves.remove(i);
                }
            }
            if (King.possibleMoves.size() == 0) {
                returnPlay.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
                return true;
            }
        } if (isCheck(toMove(getKing("black")), "black")) {
            King.move(Integer.parseInt(String.valueOf(getKing("black").charAt(0))), Integer.parseInt(String.valueOf(getKing("black").charAt(1))));

            for (int i = 0; i < King.possibleMoves.size(); i++) {
//                System.out.println(i);
                if (isCheck(King.possibleMoves.get(i), "black")) {
                    King.possibleMoves.remove(i);
                }
            }
            if (King.possibleMoves.size() == 0) {
                returnPlay.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
                return true;
            }

        }
        return false;
    }

    public static boolean potentialCheckmate(String KingLocation) {
        if (isCheck(KingLocation, "white")) {
            King.move(getFile(String.valueOf(KingLocation.charAt(0))), Integer.parseInt(String.valueOf(KingLocation.charAt(1)))-1);
            for (int i = 0; i < King.possibleMoves.size(); i++) {
//                King.possibleMoves.forEach(move-> System.out.print(move+" "));
//                System.out.println();
//                System.out.println(i);
                if (isCheck(King.possibleMoves.get(i), "white")) {
                    King.possibleMoves.remove(i);
                }
            }
            if (King.possibleMoves.size() == 0) {
                returnPlay.message = ReturnPlay.Message.CHECKMATE_BLACK_WINS;
                return true;
            }
        } if (isCheck(KingLocation, "black")) {
            King.move(getFile(String.valueOf(KingLocation.charAt(0))), Integer.parseInt(String.valueOf(KingLocation.charAt(1)))-1);

            for (int i = 0; i < King.possibleMoves.size(); i++) {
//                System.out.println(i);
                if (isCheck(King.possibleMoves.get(i), "black")) {
                    King.possibleMoves.remove(i);
                }
            }
            if (King.possibleMoves.size() == 0) {
                returnPlay.message = ReturnPlay.Message.CHECKMATE_WHITE_WINS;
                return true;
            }

        }
        return false;
    }

    public static Player playerSwitch(Player P) {
        if (P.equals("white")) {
            return Player.black;
        }
        return Player.white;
    }
}

        


        // if (currentPlayer == Player.white && y == 4 && ny == 5 && (nx == x + 1 || nx == x - 1)) {
        //     // Check if last move was a black pawn moving two squares adjacent to the capturing pawn
        //     System.out.println("En Passant check - From: " + toMove(x, y) + " To: " + toMove(nx, ny) + ", Last Move: " + lastMove);
        //     if (lastMove.equals(toMove(nx, 4))) {
        //         return true;
        //     }
        // } else if (currentPlayer == Player.black && y == 3 && ny == 2 && (nx == x + 1 || nx == x - 1)) {
        //     // Check if last move was a white pawn moving two squares adjacent to the capturing pawn
        //     System.out.println("En Passant check - From: " + toMove(x, y) + " To: " + toMove(nx, ny) + ", Last Move: " + lastMove);
        //     if (lastMove.equals(toMove(nx, 3))) {
        //         return true;
        //     }
        // }
        // return false;
    

