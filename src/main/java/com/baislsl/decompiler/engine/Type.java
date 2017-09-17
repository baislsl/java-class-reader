package com.baislsl.decompiler.engine;

public class Type {
    public int value;
    public String name;

    public Type(int value){
        this.value = value;
    }

    public Type(int value, String name){
        this.value = value;
        this.name = name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
