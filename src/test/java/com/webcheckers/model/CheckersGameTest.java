package com.webcheckers.model;

import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.reflection.FieldSetter;
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
        CuT = new CheckersGame(redPlayer, whitePlayer);
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
    public void validSimpleMove_test() {
        Position start = new Position(5, 0);
        Position end = new Position(4, 1);

        Move move = new Move(start, end);
        Message message = CuT.saveAttemptedMove(move);
        assertSame(message.getText(), "Valid move");
        assertSame(message.getType(), Message.Type.INFO);
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
    }

    @Test
    public void redResign_test() {
        assertTrue(CuT.resignGame(redPlayer));
    }

    @Test
    public void whiteResign_test() throws NoSuchFieldException {
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("activeColor"), Piece.Color.WHITE);
        assertTrue(CuT.resignGame(whitePlayer));
    }

    @Test
    public void nullResign_test() throws NoSuchFieldException {
        FieldSetter.setField(CuT, CuT.getClass().getDeclaredField("activeColor"), null);
        assertFalse(CuT.resignGame(whitePlayer));
    }
}
