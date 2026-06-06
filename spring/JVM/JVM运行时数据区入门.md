# JVM 运行时数据区入门

## 提纲

1. JVM 运行时数据区是什么
2. 线程私有区域：程序计数器、虚拟机栈、本地方法栈
3. 线程共享区域：Java 堆、方法区
4. 运行时常量池和 Class 文件常量池的关系
5. JDK 8 之后：永久代和元空间的变化
6. 本地内存和直接内存
7. 用一段 Java 代码串起来理解
8. 新手记忆版总结

## 1. JVM 运行时数据区是什么

Java 程序运行时，JVM 会把不同类型的数据放到不同的内存区域里。

可以先把 JVM 运行时数据区分成两类：

```text
线程私有：每个线程自己一份
- 程序计数器
- Java 虚拟机栈
- 本地方法栈

线程共享：所有线程共用
- Java 堆
- 方法区
```

简单理解：

```text
对象主要放在堆里
方法调用过程主要放在栈里
类的信息主要放在方法区里
线程执行到哪里由程序计数器记录
```

## 2. Java 堆：放对象

Java 堆是最重要、最常见的内存区域。

例如：

```java
User user = new User();
```

这行代码可以拆开理解：

```text
user 变量：在虚拟机栈里
new User() 对象：在 Java 堆里
```

也就是说：

```text
栈里放引用
堆里放真正的对象
```

Java 堆是所有线程共享的，也是垃圾回收器主要管理的地方。

如果堆里对象太多，内存不够，可能会出现：

```text
java.lang.OutOfMemoryError: Java heap space
```

## 3. Java 虚拟机栈：放方法调用过程

每个线程都有自己的 Java 虚拟机栈。

比如：

```java
public void a() {
    int x = 10;
    b();
}

public void b() {
    int y = 20;
}
```

执行 `a()` 时，会产生 `a()` 方法的栈帧。

`a()` 里面调用了 `b()`，又会产生 `b()` 方法的栈帧。

可以想象成：

```text
线程的虚拟机栈
┌────────────┐
│ b() 的栈帧 │ 里面有 y
├────────────┤
│ a() 的栈帧 │ 里面有 x
└────────────┘
```

每调用一个方法，就压入一个栈帧。

方法执行完，这个栈帧就会弹出。

虚拟机栈里主要保存：

```text
局部变量
方法参数
对象引用
返回地址
临时计算数据
```

如果方法调用层级太深，比如无限递归：

```java
public void test() {
    test();
}
```

就可能出现：

```text
java.lang.StackOverflowError
```

## 4. 程序计数器：记录线程执行到哪里

程序计数器是一块很小的内存区域。

它的作用是记录当前线程执行到了哪一条字节码指令。

为什么需要它？

因为 CPU 会在线程之间切换：

```text
线程 A 执行到一半
切换到线程 B
过一会儿再切回线程 A
```

切回来之后，JVM 必须知道线程 A 刚才执行到哪里了。

所以每个线程都需要一个自己的程序计数器。

## 5. 本地方法栈：给 native 方法用

Java 里有些方法不是 Java 代码实现的，而是由 C/C++ 等底层语言实现的。

这类方法叫 native 方法。

例如：

```java
Thread.start();
System.currentTimeMillis();
```

本地方法栈就是给 native 方法调用时使用的栈。

新手可以先这样记：

```text
Java 方法调用：使用 Java 虚拟机栈
native 方法调用：使用本地方法栈
```

## 6. 方法区：放类的信息

方法区主要存放类级别的信息。

比如有一个类：

```java
public class User {
    private String name;

    public void sayHello() {
        System.out.println("hello");
    }
}
```

JVM 加载 `User` 类之后，会把这个类的相关信息放到方法区，比如：

```text
类名
字段信息
方法信息
父类信息
接口信息
方法字节码
运行时常量池
```

重点区别：

```text
Java 堆：放对象
方法区：放类的信息
```

例如：

```java
User u1 = new User();
User u2 = new User();
```

大致可以理解为：

```text
方法区：User 这个类的信息，只有一份
堆：u1 对象、u2 对象，各一份
栈：u1、u2 这两个引用变量
```

## 7. 运行时常量池

运行时常量池是方法区的一部分。

这里容易混淆两个概念：

```text
Class 文件常量池
运行时常量池
```

Java 源代码编译后会生成 `.class` 文件。

`.class` 文件里有一张常量表，里面可能包括：

```text
类名
字段名
方法名
字符串字面量
符号引用
```

例如：

```java
String s = "hello";
```

编译后的 `.class` 文件里会记录 `"hello"`、`String`、方法名等信息。

这张表在 `.class` 文件中时，叫 Class 文件常量池。

当 JVM 加载这个 `.class` 文件后，会把这些常量信息放到内存中的方法区里，这时就叫运行时常量池。

可以这样理解：

```text
Class 文件常量池：硬盘上的常量表
运行时常量池：类加载后，进入 JVM 内存里的常量表
```

## 8. JDK 8 之后：永久代和元空间

方法区是 JVM 规范里的概念。

永久代和元空间是 HotSpot 虚拟机对方法区的具体实现方式。

三者关系可以这样理解：

```text
方法区：规范中的概念
永久代：JDK 8 之前，HotSpot 对方法区的一种实现
元空间：JDK 8 之后，HotSpot 对方法区的一种实现
```

也就是：

```text
方法区
├── JDK 7 及以前：主要由永久代 PermGen 实现
└── JDK 8 及以后：主要由元空间 Metaspace 实现
```

JDK 8 之后的重要变化：

```text
永久代被移除了
元空间取代了永久代
类的元信息放到了本地内存中的元空间里
```

以前可能会使用这些参数设置永久代大小：

```text
-XX:PermSize
-XX:MaxPermSize
```

JDK 8 之后，改成设置元空间大小：

```text
-XX:MetaspaceSize
-XX:MaxMetaspaceSize
```

## 9. 本地内存和直接内存

本地内存不是 JVM 运行时数据区的一部分。

可以先这样理解：

```text
JVM 内存：JVM 自己管理的内存，比如堆、栈、方法区等
本地内存：操作系统层面的内存，不完全由 Java 堆管理
```

JDK 8 之后，元空间使用的就是本地内存。

直接内存 Direct Memory 也是本地内存的一部分，常见于 NIO。

例如：

```java
ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
```

这里可以理解为：

```text
DirectByteBuffer 对象本身：在 Java 堆里
真正的数据缓冲区：在直接内存里
```

为什么要使用直接内存？

因为做 IO 时，可以减少 Java 堆和 native 内存之间的数据复制，从而提高性能。

## 10. 用一段代码串起来理解

看下面这段代码：

```java
public class Demo {
    static String type = "test";

    public static void main(String[] args) {
        User user = new User();
        int age = 18;
    }
}
```

可以大致这样理解：

```text
方法区/元空间：
- Demo 类信息
- User 类信息
- main 方法信息
- static 字段信息
- 运行时常量池

Java 堆：
- new User() 创建出来的对象
- String 对象等

Java 虚拟机栈：
- main 方法的栈帧
- args 局部变量
- user 引用
- age = 18

程序计数器：
- 记录当前线程执行到 main 方法的哪条字节码指令
```

## 11. 新手记忆版总结

刚开始学习 JVM，不需要一次性记住所有细节。

先记住下面几句话：

```text
1. 堆：放对象，GC 主要回收这里
2. 栈：放方法调用、局部变量、对象引用
3. 程序计数器：记录线程执行位置
4. 方法区：放类的信息
5. 运行时常量池：方法区的一部分，放 class 加载后的常量信息
6. JDK 8 之后：永久代没了，换成元空间
7. 直接内存：不是 Java 堆，常用于 NIO
```

最核心的一句话：

```text
对象在堆里，
方法调用在栈里，
类的信息在方法区里，
JDK 8 后方法区主要由元空间实现。
```
