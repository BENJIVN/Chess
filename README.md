# 2-Player Chess Game

## Overview

This project implements a 2-player chess game using object-oriented design principles. The game is played on the terminal, with the board displayed in text format. Players alternate turns, making moves using standard chess notation.

## Implementation Details

### Board

- The chessboard has 8 ranks (rows) and 8 files (columns), identified as FileRank (e.g., `e4`).
- Initial positions:
  - White pieces: Ranks 1 and 2
  - Black pieces: Ranks 7 and 8
  - Queen starts on the `d` file

### Moves

- Moves are entered in FileRank FileRank notation (e.g., `e2 e4`).
- Special moves:
  - **Castling:** e.g., `e1 g1`
  - **Pawn Promotion:** e.g., `g7 g8 N` (default promotion is to queen)
  - **Resign:** e.g., `resign`
  - **Draw:** e.g., `g1 f3 draw?`
- Illegal moves are not executed, and the same player must make a legal move.

### Ending the Game

- **Checkmate:** The player that applies the checkmate wins.
- **Resign:** The other player wins.
- **Draw:** Players can propose a draw, which the opponent must accept.

### Implementation

- The project is implemented using multiple classes within a package named `chess`.
- The main class is `Chess.java`, containing the `start` and `play` methods.
- Use the supplied `PlayChess.java` application to test your implementation.

### Development Environment

- Use any Java development environment (e.g., Eclipse, IntelliJ, VS Code).
- Ensure your code is correctly structured within the `chess` package.
