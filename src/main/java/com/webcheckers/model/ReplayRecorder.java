
package com.webcheckers.model;

import java.util.LinkedList;

public class ReplayRecorder
{
    LinkedList<Move> moves_list = new LinkedList<>();
    int current_index = 0;

    public ReplayRecorder()
    {

    }

    public LinkedList<Move> getMovesList()
    {
        return moves_list;
    }

    public int getCurrentIndex() {
        return current_index;
    }

    public void addMove(Move move)
    {
        moves_list.add(move);
    }

    public Move nextMove()
    {
        Move current_move = moves_list.get(current_index);
        current_index++;
        return current_move;
    }

    public Move previousMove()
    {
        current_index--;
        return moves_list.get(current_index);
    }

    public boolean isReplayOver() {
        if(current_index == moves_list.size())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}