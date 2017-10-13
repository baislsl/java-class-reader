package com.baislsl.decompiler.engine;

public class Value implements Cloneable {
    // public Integer value;
    public String name;
    public Boolean used;

    public Value(String name) {
        this.name = name;
        this.used = true;
    }

    public Value(String name, boolean used) {
        this.name = name;
        this.used = used;
    }

    public Value(int value) {
        this.name = Integer.toString(value);
        this.used = true;
    }

    public Value(int value, boolean used) {
        this.name = Integer.toString(value);
        this.used = used;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setValue(String name){
        this.name = name;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}
