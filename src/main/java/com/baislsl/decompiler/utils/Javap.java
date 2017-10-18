package com.baislsl.decompiler.utils;

import com.baislsl.decompiler.Constants;
import com.baislsl.decompiler.DecompileException;
import com.baislsl.decompiler.Result;
import com.baislsl.decompiler.structure.attribute.*;
import com.baislsl.decompiler.structure.constantPool.*;

import java.util.ArrayList;
import java.util.List;

public class Javap {

    public static String[] getAccessFlagDescription(int accessFlag) {
        List<String> ans = new ArrayList<>();
        int cur = 1, cnt = 16;
        for (int i = 0; i < cnt; i++) {
            if ((accessFlag & cur) != 0) {
                ans.add(Constants.accessName[i]);
            }
            cur <<= 1;
        }
        return ans.toArray(new String[0]);
    }

    public static String accessFlagDescription(int accessFlag){
        String[] description = getAccessFlagDescription(accessFlag);
        StringBuilder ans = new StringBuilder();
        for(String s : description){
            ans.append(s).append(" ");
        }
        return ans.toString();
    }

    public static String getConstantPoolInfo(ConstantPool cp, Result clazz)
            throws DecompileException {
        String[] cpDescription = cp.description(clazz);
        StringBuilder ans = new StringBuilder();
        if (cpDescription.length <= 3) {
            for (String s : cpDescription) {
                ans.append(s);
                ans.append("  ");
            }
        } else {
            ans.append(cpDescription[0]);
            ans.append("  ");
            ans.append(cpDescription[1]);
            ans.append("  ");
            for (int i = 2; i < cpDescription.length; i++) {
                ans.append(cpDescription[i]);
                if (i != cpDescription.length - 1) ans.append(":");
            }
        }
        return ans.toString();
    }

}
