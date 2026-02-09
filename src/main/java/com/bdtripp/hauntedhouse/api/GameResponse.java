package com.bdtripp.hauntedhouse.api;

import com.bdtripp.hauntedhouse.GameStatus;

public class GameResponse {
    private String output;
    private GameStatus status;

    public GameResponse() {}

    public GameResponse(String output, GameStatus status)
    {
        this.output = output;
        this.status = status;
    }

    public String getOutput()
    {
        return output;
    }

    public void setOutput(String output)
    {
        this.output = output;
    }

    public GameStatus getStatus()
    {
        return status;
    }

    public void setStatus(GameStatus status)
    {
        this.status = status;
    }
}
