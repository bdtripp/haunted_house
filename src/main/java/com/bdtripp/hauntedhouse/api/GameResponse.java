package com.bdtripp.hauntedhouse.api;

public class GameResponse {
    private String output;

    public GameResponse() {}

    public GameResponse(String output)
    {
        this.output = output;
    }

    public String getOutput()
    {
        return output;
    }

    public void setOutput(String output)
    {
        this.output = output;
    }
}
