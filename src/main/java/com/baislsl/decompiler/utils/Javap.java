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

}
