package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-Tier")
public class SavedMoveTest {

    private SavedMove CuT;

    @BeforeEach
    public void setUp() {
        CuT = new SavedMove(new Space[8][8], Piece.Color.RED);
    }

    //Everything gets testing in the SavedGameTest
    @Test
    public void doNothingTest() {

    }
}
