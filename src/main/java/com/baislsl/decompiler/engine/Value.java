package com.baislsl.decompiler.engine;

public class Value {
    // public Integer value;
    public String name;
    public boolean used = true;

    public Value(){

    }

    public Value(String name){
        this.name = name;
    }

    public Value(String name, boolean used){
        this.name = name;
        this.used = used;
    }

    public Value(int value){
        this.name = Integer.toString(value);
    }

    public Value(int value, boolean used){
        this.name = Integer.toString(value);
        this.used = used;
    }

    public Value(int value, String name){
        // this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean getUsed(){
        return used;
    }

    public void setUsed(boolean userd){
        this.used = userd;
    }
}
