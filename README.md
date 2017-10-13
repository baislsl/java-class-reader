# java-class-decompiler
Decompiler intended to decompile *.class format files

## 当前进度
完成了class文件读取， 大体上完成了javap -v指令的功能，
但是在很多细节上可能还有待改进， 特别是部分较少用的attribute解析
还没写好， 注解类、泛型的解析也还没处理

正在写反汇编, 处理while, for, 循环中, 暂时使用while(true){..., continue..., break... }来处理循环

循环处理完成了部分, 没想出什么好的严谨的算法, 先停一下循环处理部分, 
现在正完善剩余指令和处理异常的try, catch
### class file structure
```
ClassFile {
    u4             magic;
    u2             minor_version;
    u2             major_version;
    u2             constant_pool_count;
    cp_info        constant_pool[constant_pool_count-1];
    u2             access_flags;
    u2             this_class;
    u2             super_class;
    u2             interfaces_count;
    u2             interfaces[interfaces_count];
    u2             fields_count;
    field_info     fields[fields_count];
    u2             methods_count;
    method_info    methods[methods_count];
    u2             attributes_count;
    attribute_info attributes[attributes_count];
}
```

### 4种循环的反汇编， （for目前直接用while循环取代）

循环：
1. 单纯的if， else(在if或else语句中不包括break,continue)
2. while(true){..continue, break...} 形式可以包括一切循环, 包括for, while, do...while
3. 出现先面跳转的肯定是有while, 如果全是向后跳转, 就是单纯的if和else










