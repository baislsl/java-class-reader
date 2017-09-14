package com.baislsl.decompiler.javap.descriptor;

public class VoidDescriptor implements Descriptor {

    @Override
    public Class<?> getType() {
        return void.class;
    }

    @Override
    public String toString(){
        return "void";
    }
}
