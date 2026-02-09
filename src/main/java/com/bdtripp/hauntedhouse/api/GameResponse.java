package com.bdtripp.hauntedhouse.api;

public class GameResponse {
    private String output;
    private String status;

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

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }
}
