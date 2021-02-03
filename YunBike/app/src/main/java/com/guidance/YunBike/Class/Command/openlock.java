package com.guidance.YunBike.Class.Command;

public class openlock implements Command{
    private Reciever on;
    private int bike_id;
    public openlock(Reciever on , int bike_id) {
        this.on = on;
        this.bike_id = bike_id;
    }
    @Override
    public void execute() {
        on.open();
    }
}
