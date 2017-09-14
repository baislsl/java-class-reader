package com.baislsl.decompiler.javap.descriptor;

import com.baislsl.decompiler.DecompileException;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.List;

public class FieldDescriptor implements Descriptor {
    private Class<?> type = null;
    private int arrayCount = 0;

    // use when type == null
    private String name;

    public static FieldDescriptor[] getFieldDescriptors(String str) throws DecompileException {
        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        int i = 0;
        while(i < str.length()){
            int arrayCount = 0;
            while (str.charAt(i) == '[' && i < str.length()) {
                ++i;
                ++arrayCount;
            }
            Class type = getType(str.charAt(i));
            String name = null;
            if (type == null) {
                int j = i;
                while(str.charAt(i) != ';') i++;
                name = str.substring(j, i);
            }
            ++i;
            fieldDescriptors.add(new FieldDescriptor(type, arrayCount, name));
        }

        return fieldDescriptors.toArray(new FieldDescriptor[0]);
    }

    private FieldDescriptor(Class type, int arrayCount, String name){
        this.type = type;
        this.arrayCount = arrayCount;
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder array = new StringBuilder();
        for (int i = 0; i < arrayCount; i++) {
            array.append("[]");
        }

        if (name == null)
            return type.getName() + array.toString();
        else
            return name + array.toString();
    }

    public Class<?> getType() {
        return type;
    }

    private static Class<?> getType(char cc) throws DecompileException {
        switch (cc) {
            case 'B':
                return byte.class;
            case 'C':
                return char.class;
            case 'D':
                return double.class;
            case 'F':
                return float.class;
            case 'I':
                return int.class;
            case 'J':
                return long.class;
            case 'L':
                return null;
            case 'S':
                return short.class;
            case 'Z':
                return boolean.class;
            default:
                throw new DecompileException("No field type with term " + cc);
        }
    }


}
