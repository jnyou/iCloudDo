# Java8-Source-Code-Service

# Head First Java8 Sources

试图研读标准库源码而搭建的源码分析项目

# 分析环境

- windows
- idea 2020.1

# 目录结构

```bash
├── doc # 相关分析记录
├── src # 源码
│   └── java
│       ├── io
│       ├── lang
│       │   ├── annotation
│       │   ├── instrument
│       │   ├── invoke
│       │   ├── management
│       │   ├── ref
│       │   └── reflect
│       ├── math
│       ├── net
│       ├── nio
│       │   ├── channels
│       │   │   └── spi
│       │   ├── charset
│       │   │   └── spi
│       │   └── file
│       │       ├── attribute
│       │       └── spi
│       ├── text
│       │   └── spi
│       ├── time
│       │   ├── chrono
│       │   ├── format
│       │   ├── temporal
│       │   └── zone
│       └── util
│           ├── concurrent
│           │   ├── atomic
│           │   └── locks
│           ├── function
│           ├── jar
│           ├── logging
│           ├── prefs
│           ├── regex
│           ├── spi
│           ├── stream
│           └── zip
└── tests # 测试代码
    └── util 
```

# 分析进度

计划首先从J.U.C包下面的类库开始翻译：

翻译进度汇总：

1.ConcurrentHashMap翻译初版。

2.Striped64翻译初版。该类对于并发计数提出了新的解决思路,ConcurrentHashMap元素个数的统计都是基于该思想做的。

3.翻译线程中的状态类,对应Thread类中的枚举类State。

4.翻译线程池相关类中的部分注释。

5.ClassLoader部分注释翻译。

6.StampedLock部分注释翻译。

7.AbstractQueuedSynchronizer类开头注释翻译，内部类Node部分字段翻译。

8.ScheduledThreadPoolExecutor类部分注释翻译。

9.ScheduledExecutorService类注释。

10.Stream接口部分注释翻译。

已翻译的部分类：

- J.lang：ClassLoader.java(部分注释翻译.)

- J.net：Socket.java

- J.nio.
    - ByteBuffer.java
    - channels.SocketChannel.java
- J.util.
    - concurrent.atomic
        - LongAdder.java
        - Striped64.java(Striped64翻译初版.)
    - concurrent.locks
        - AbstractQueuedSynchronizer.java(ScheduledThreadPoolExecutor类部分注释翻译.)
        - StampedLock.java(StampedLock部分注释翻译.)
    - ConcurrentHashMap.java
    - ConcurrentLinkedQueue.java
    - ConcurrentMap.java
    - Executors.java
    - ScheduledExecutorService.java
    - ScheduledThreadPoolExecutor.java
    - ThreadPoolExecutor.java(翻译线程池相关类中的部分注释.)
    - stream.
        - Stream.java(reduce函数解析.)
        - AbstractPipeline.java
        - ReferencePipeline.java
        - SortedOps.java
    - ArrayList.java
    - LinkedList.java
    - Vector.java
    - Optional.java

## java.lang 包

### 基本类型包装类

- java.lang.Boolean   -> TODO
- java.lang.Character -> TODO
- java.lang.Byte      -> TODO
- java.lang.Short     -> TODO
- ✅ [java.lang.Integer](doc/java.lang.Integer.md)
- java.lang.Long      -> TODO
- java.lang.Float     -> TODO
- java.lang.Double    -> TODO

### String 相关

- ✅ [java.lang.String](doc/java.lang.String.md)

### Object 相关

- [java.lang.Object](doc/java.lang.Object.md)  -> TODO

### System 相关

- [java.lang.System](doc/java.lang.System.md)  -> TODO

### Thread 相关

- [java.lang.Thread](doc/java.lang.Thread.md)  -> TODO

## java.io 包

## java.nio 包

## java.net 包

## java.time 包

## java.util 包

### 基本数据结构

### 杂项


## java.util.concurrent 包

## java.util.regex 包

## java.util.function 包

## java.util.stream 包

## java.util.logging 包

# 相关记录

- ✅ [源码分析环境搭建](doc/analysis-env-setup.md)
- ✅ [源码注释规范](doc/annotaion-spec.md)

# 变更日志

- 2020 初始化      
- 202009  ArrayList、LinkedList、Vector