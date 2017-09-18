# java-class-decompiler
Decompiler intended to decompile *.class format files

## 当前进度
完成了class文件读取， 大体上完成了javap -v指令的功能，
但是在很多细节上可能还有待改进， 特别是部分较少用的attribute解析
还没写好， 注解类、泛型的解析也还没处理

正在写反汇编

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

#### if
```$xslt
if ( condition ){
    loop
}
outer

```
对应汇编代码
```$xslt
if_cond jump --+
               |
 loop          |
               |
 +-------------+
 |
outer  

```

#### if else 
```$xslt
if ( condition ) {
    loop1
} else {
    loop2
}
outer
```
对应汇编代码
```$xslt

if_cond jump ----+
                 |
  loop2          |
                 |
goto    jump --+ |
               | |
  loop1 ---------+
               |
               |
  outer -------+
```

#### while
```$xslt
while ( condition ) {
    loop
}
outer
```
对应汇编代码

```$xslt
 +-------------------+
 |                   |
if_cond jump ----+   |
                 |   |
  loop           |   |
                 |   |
goto ----------------+    
                 |
  outer ---------+


```
#### do while
```$xslt
do {
    loop
} while ( condition )
outer

```
对应汇编代码

```$xslt

  loop --------- +   
                 |
                 |
if_cond jump ----+

outer

```









