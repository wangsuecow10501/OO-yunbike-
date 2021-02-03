package com.guidance.YunBike.Class.Command;

public class locklock implements Command {
    private Reciever lock;
    private int bike_id;

    public locklock( Reciever lock,int bike_id) {
        this.lock = lock;
        this.bike_id = bike_id;
    }
    @Override
    public void execute() {
        lock.lock();
    }
}
