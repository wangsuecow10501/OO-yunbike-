package com.guidance.YunBike.Class.FactoryMethod;

public class RecordCreator implements Creator {
    public  Record FactoryMethod(int user, int bike, String date){//Create a Record object.
        return new Record(user,bike,date);
    }
}
