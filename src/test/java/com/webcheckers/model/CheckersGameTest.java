package com.webcheckers.model;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class CheckersGameTest {
    private CheckersGame CuT;
    private Player redPlayer;
    private Player whitePlayer;

    @BeforeEach
    public void setUp() {
        redPlayer = new Player("player 1");
        whitePlayer = new Player("player 2");
        CuT = new CheckersGame(redPlayer, whitePlayer, 1);
    }

    @Test
    public void getBoard_test() {
        assertNotNull(CuT.getBoard());
    }

    @Test
    public void getRedBoardView_test() {
        assertNotNull(CuT.getRedBoardView());
    }

    @Test
    public void getWhiteBoardView_test() {
        assertNotNull(CuT.getWhiteBoardView());
    }

    @Test
    public void getRedPlayer_test() {
        assertSame(redPlayer, CuT.getRedPlayer());
    }

    @Test
    public void getWhitePlayer_test() {
        assertSame(whitePlayer, CuT.getWhitePlayer());
    }

    @Test
    public void getActiveColor_test() {
        assertNotNull(CuT.getActiveColor());
    }

    @Test
    public void nullPlayerColor_test() {
        Player player3 = new Player("player3");
        assertNull(CuT.getPlayerColor(player3));
    }

    @Test
    public void validSimpleMove_test() {
        Position start = new Position(5, 0);
        Position end = new Position(4, 1);

        Move move = new Move(start, end);
        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Valid move");
        assertSame(message.getType(), Message.Type.INFO);

        Message applyMessage = CuT.applyAttemptedMoves();
        assertSame(applyMessage.getText(), "Move applied");
        assertSame(applyMessage.getType(), Message.Type.INFO);
    }

    @Test
    public void whiteValidSimpleMove_test() throws NoSuchFieldException {
        Position start = new Position(2, 1);
        Position end = new Position(3, 2);
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("activeColor"), Piece.Color.WHITE);

        Move move = new Move(start, end);
        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Valid move");
        assertSame(message.getType(), Message.Type.INFO);

        Message applyMessage = CuT.applyAttemptedMoves();
        assertSame(applyMessage.getText(), "Move applied");
        assertSame(applyMessage.getType(), Message.Type.INFO);
    }

    @Test
    public void invalidSimpleMove_test() {
        Position start = new Position(5, 0);
        Position end = new Position(4, 2);

        Move move = new Move(start, end);
        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Move is too far");
        assertSame(message.getType(), Message.Type.ERROR);
    }

    @Test
    public void validJumpMove() throws NoSuchFieldException {
        Position start = new Position(4, 7);
        Position end = new Position(2, 5);
        Move move = new Move(start, end);

        Space[][] board = CuT.getBoard();
        board[2][5] = new Space(5, Space.State.OPEN);
        board[3][6] = new Space(6, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        board[4][7] = new Space(7, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("board"), board);

        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Valid move");
        assertSame(message.getType(), Message.Type.INFO);

        Message applyMessage = CuT.applyAttemptedMoves();
        assertSame(applyMessage.getText(), "Move applied");
        assertSame(applyMessage.getType(), Message.Type.INFO);
    }

    @Test
    public void whiteValidJumpMove() throws NoSuchFieldException {
        Position start = new Position(2, 7);
        Position end = new Position(4, 5);
        Move move = new Move(start, end);

        Space[][] board = CuT.getBoard();
        board[3][6] = new Space(6, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("board"), board);
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("activeColor"), Piece.Color.WHITE);

        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Valid move");
        assertSame(message.getType(), Message.Type.INFO);

        Message applyMessage = CuT.applyAttemptedMoves();
        assertSame(applyMessage.getText(), "Move applied");
        assertSame(applyMessage.getType(), Message.Type.INFO);
    }

    @Test
    public void invalidJumpMove() {
        Position start = new Position(5, 0);
        Position end = new Position(3, 2);
        Move move = new Move(start, end);

        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Move is too far");
        assertSame(message.getType(), Message.Type.ERROR);
    }

    @Test
    public void haveToMakeJump_test() throws NoSuchFieldException {
        Position start = new Position(2, 1);
        Position end = new Position(3, 0);
        Move move = new Move(start, end);

        Space[][] board = CuT.getBoard();
        board[3][6] = new Space(6, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("board"), board);
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("activeColor"), Piece.Color.WHITE);

        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "There is a jump you must make");
        assertSame(message.getType(), Message.Type.ERROR);
    }

    @Test
    public void makeKing_test() throws NoSuchFieldException {
        Position start = new Position(2, 5);
        Position end = new Position(0, 3);
        Move move = new Move(start, end);

        Space[][] board = CuT.getBoard();
        board[2][5] = new Space(5, new Piece(Piece.Type.SINGLE, Piece.Color.RED));
        board[1][4] = new Space(4, new Piece(Piece.Type.SINGLE, Piece.Color.WHITE));
        board[0][3] = new Space(3, Space.State.OPEN);
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("board"), board);

        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Valid move");
        assertSame(message.getType(), Message.Type.INFO);
    }

    @Test
    public void kingSimpleMove_test() throws NoSuchFieldException {
        Position start = new Position(0, 5);
        Position end = new Position(1, 6);
        Move move = new Move(start, end);

        Space[][] board = CuT.getBoard();
        board[0][5] = new Space(5, new Piece(Piece.Type.KING, Piece.Color.RED));
        board[1][6] = new Space(6, Space.State.OPEN);
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("board"), board);

        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Valid move");
        assertSame(message.getType(), Message.Type.INFO);
    }

    @Test
    public void kingJumpMove_test() throws NoSuchFieldException {
        Position start = new Position(0, 5);
        Position end = new Position(2, 3);
        Move move = new Move(start, end);

        Space[][] board = CuT.getBoard();
        board[0][5] = new Space(5, new Piece(Piece.Type.KING, Piece.Color.RED));
        board[2][3] = new Space(4, Space.State.OPEN);
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("board"), board);

        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Valid move");
        assertSame(message.getType(), Message.Type.INFO);
    }

    @Test
    public void resetMove_test() throws NoSuchFieldException {
        Position start = new Position(0, 5);
        Position end = new Position(2, 3);
        Move move = new Move(start, end);

        LinkedList<Move> moves = new LinkedList<>();
        moves.add(move);

        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("movesQueue"), moves);
        Message message = CuT.resetAttemptedMove();
        assertSame(message.getText(), "Attempted move was removed");
        assertSame(message.getType(), Message.Type.INFO);
    }

    @Test
    public void redResign_test() {
        assertTrue(CuT.resignGame(redPlayer));
        assertSame(CuT.getWinner(), whitePlayer);
        assertSame(CuT.getLoser(), redPlayer);

        assertTrue(CuT.isResigned());
    }

    @Test
    public void whiteResign_test() throws NoSuchFieldException {
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("activeColor"), Piece.Color.WHITE);
        assertTrue(CuT.resignGame(whitePlayer));
        assertSame(CuT.getWinner(), redPlayer);
        assertSame(CuT.getLoser(), whitePlayer);
    }

    @Test
    public void nullResign_test() throws NoSuchFieldException {
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("activeColor"), null);
        assertFalse(CuT.resignGame(whitePlayer));
    }
}
