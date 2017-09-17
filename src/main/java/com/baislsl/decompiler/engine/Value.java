package com.baislsl.decompiler.engine;

public class Value {
    // public Integer value;
    public String name;

    public Value(){

    }

    public Value(String name){
        this.name = name;
    }

    public Value(int value){
        this.name = Integer.toString(value);
    }

    public Value(int value, String name){
        // this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
