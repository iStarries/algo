# AcWing 算法基础课 -- 数据结构

## AcWing 240. 食物链 

`难度：中等`

### 题目描述

动物王国中有三类动物A,B,C，这三类动物的食物链构成了有趣的环形。

A吃B， B吃C，C吃A。

现有N个动物，以1－N编号。

每个动物都是A,B,C中的一种，但是我们并不知道它到底是哪一种。

有人用两种说法对这N个动物所构成的食物链关系进行描述：

第一种说法是”1 X Y”，表示X和Y是同类。

第二种说法是”2 X Y”，表示X吃Y。

此人对N个动物，用上述两种说法，一句接一句地说出K句话，这K句话有的是真的，有的是假的。

当一句话满足下列三条之一时，这句话就是假话，否则就是真话。

1. 当前的话与前面的某些真的话冲突，就是假话；
2. 当前的话中X或Y比N大，就是假话；
3. 当前的话表示X吃X，就是假话。

你的任务是根据给定的N和K句话，输出假话的总数。

**输入格式**

第一行是两个整数N和K，以一个空格分隔。

以下K行每行是三个正整数 D，X，Y，两数之间用一个空格隔开，其中D表示说法的种类。

若D=1，则表示X和Y是同类。

若D=2，则表示X吃Y。

**输出格式**

只有一个整数，表示假话的数目。

**数据范围**

$1≤N≤50000,$
$0≤K≤100000$

```r
输入样例：

100 7
1 101 1 
2 1 2
2 2 3 
2 3 3 
1 1 3 
2 3 1 
1 5 5

输出样例：

3
```

### Solution

并查集的题目。

主要思想是维护每个节点到根节点的距离，详细看注释。

```java
// 思想：维护每个节点到共同根节点的距离，
import java.util.*;
import java.io.*;

class Main{
    static final int N = 50010;
    static int[] p = new int[N];
    static int[] dist = new int[N];
    public static int find(int x){
        if(p[x] != x) {
            // 重点是怎么更新 dist[x]
            // d[x]存储的是x到p[x]的距离，find(x)之后p[x]就是根节点了，所以d[x]存的就是到根节点的距离了
            int t = p[x];
            p[x] = find(p[x]);
            dist[x] = dist[t] + dist[x];
        }
        // return p[x];
        // if(p[x] != x){//不是祖宗节点的话
        //     int t = find(p[x]);
        //     dist[x] += dist[p[x]];
        //     p[x] = t;
        // }
        return p[x];
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int k = Integer.parseInt(s[1]);
        for(int i = 1; i <= n; i++) p[i] = i;
        int res = 0;
        while(k -- > 0){
            s = br.readLine().split(" ");
            int d = Integer.parseInt(s[0]);
            int x = Integer.parseInt(s[1]);
            int y = Integer.parseInt(s[2]);
            if(x > n || y > n) res++;
            else{
                // 先找 x，y 的根节点
                int px = find(x), py = find(y);
                // 判断是不是同类关系
                if(d == 1){
                    // 如果 x 和 y 在一个集合中，判断两者距离根节点的距离是否刚好被 3 整除
                    if(px == py && (dist[x] - dist[y]) % 3 != 0) res ++;
                    // 如果 x 和 y 不在一个集合中，就把两者连起来
                    // 连起来要保证 dist[x] - dist[y] 能够被 3 整除
                    else if(px != py){
                        p[px] = py;
                        dist[px] = dist[y] - dist[x];
                    }
                }
                // 判断是不是吃与被吃的关系
                else if(d == 2){
                    // 如果在一个集合中，就要判断是否关系属实，就是差 1
                    // 如果不在一个集合中，就建立关系
                    // x 吃 y
                    // 我们定义 
                    // y - x 模 3 为 1 表示 y 吃 x
                    // y - x 模 3 为 2 表示 x 吃 y
                    // 防止出现取模出现负数不好判断，就先减去相应的值，看最后的模是否为 0
                    if(x == y) res ++;
                    else if(px == py && (dist[x] - dist[y] - 1) % 3 != 0) res++;
                    else if(px != py){
                        p[px] = p[y];
                        dist[px] = dist[y] + 1 - dist[x];
                    }
                }
            }
        }
        System.out.println(res);
    }
}
```

这题是**并查集 + 关系维护**的经典题，属于你目录里的**数据结构：并查集**。我直接按学习顺序给你讲。题目与题解来自你上传的文件。

## 一、先判断题型与核心考点

这题表面是在判断“真假话”，本质其实是：

* 有很多个元素（动物）
* 不断给出它们之间的关系
* 要一边合并集合，一边判断新关系是否和旧关系冲突

这就是一个很明显的 **并查集判定关系冲突** 的模型。

但它又不是普通并查集，因为普通并查集只能维护：

* 是不是一个集合
* 两个点是否连通

而这题还要维护：

* 同类
* 谁吃谁

也就是不仅要知道“在不在一个集合里”，还要知道“**集合内相对关系**”。

所以这题的核心考点是：

**带权并查集（维护节点到根的相对关系）**

---

## 二、核心思路与整体做法

### 1. 为什么普通并查集不够

普通并查集只能回答：

* `x` 和 `y` 是否属于同一个集合

但这题需要回答：

* `x` 和 `y` 是否同类
* `x` 是否吃 `y`
* `y` 是否吃 `x`

这其实是三种相对关系。

所以我们要给每个点额外维护一个“相对信息”。

---

### 2. 怎么表示这三种关系

题解里用的是一个非常经典的设计：

定义 `dist[x]` 表示：

> **x 到根节点的距离（关系值）**

并且这个距离是模 3 的。

我们把三类关系编码成模 3：

* `0`：x 和 y 是同类
* `1`：x 吃 y
* `2`：x 被 y 吃

更准确地说，题解实际维护的是：

> `dist[x] - dist[y]` 的模 3 值，表示 x 和 y 的关系

于是：

* `(dist[x] - dist[y]) % 3 == 0`：x 和 y 同类
* `(dist[x] - dist[y]) % 3 == 1`：x 吃 y
* `(dist[x] - dist[y]) % 3 == 2`：x 被 y 吃

这个设计的好处是：

* 同类关系可以表达
* 吃与被吃关系也能表达
* 合并两个集合时，可以通过方程把新边权算出来

这就是这题最核心的地方。

---

### 3. 为什么能用“模 3”

因为动物只有三类 A、B、C，而且关系是环：

* A 吃 B
* B 吃 C
* C 吃 A

这天然就是一个 **3 个状态循环**。

只要遇到这种“循环关系、三种状态、相对差值有意义”的题，就应该想到：

**用模运算维护相对关系。**

---

### 4. 如何判断一句话是真是假

题目说以下三种是假话：

#### 第一类：编号越界

如果 `x > n` 或 `y > n`，直接是假话。

#### 第二类：自己吃自己

如果 `d == 2 && x == y`，直接是假话。

#### 第三类：和前面的真话冲突

这个要分情况。

---

### 5. 对于 “1 x y”：x 和 y 同类

#### 情况 A：x 和 y 已经在一个集合里

那就不能随便说了，必须检查原有关系是否满足“同类”。

也就是要看：

```java
(dist[x] - dist[y]) % 3 == 0
```

如果不是 0，说明冲突，这句话是假话。

#### 情况 B：x 和 y 不在一个集合里

那就把两个集合合并。

合并时，我们希望新关系满足：

```java
dist[x] - dist[y] = 0
```

由此可以推出根节点之间该怎么连、边权该怎么设。

---

### 6. 对于 “2 x y”：x 吃 y

#### 情况 A：x == y

直接是假话，因为不可能自己吃自己。

#### 情况 B：x 和 y 已经在一个集合里

要检查已有关系是否满足“x 吃 y”。

也就是看：

```java
(dist[x] - dist[y]) % 3 == 1
```

如果不是 1，说明冲突，这句话是假话。

#### 情况 C：x 和 y 不在一个集合里

那就合并两个集合，并且让新关系满足：

```java
dist[x] - dist[y] = 1
```

再去推根节点之间的距离。

---

## 三、朴素做法和当前做法的区别

### 朴素做法

你可能会想：

* 每次新来一句话，就根据已有关系去搜
* 看能不能推出矛盾

问题是：

* 关系是动态增加的
* 查询很多，最多 10 万条
* 每次重新搜会很慢

### 当前做法

带权并查集把“集合归属 + 相对关系”一次性维护起来：

* `find(x)` 时顺便路径压缩
* 同时把 `x` 到根的关系也更新好
* 查询和合并都接近常数时间

所以总复杂度大约是：

**O(K α(N))**

这也是这题能过的关键。

---

## 四、再讲题解代码怎么实现

下面开始进入你这份 Java 代码的实现逻辑。

---

### 1. 数组定义

```java
static int[] p = new int[N];
static int[] dist = new int[N];
```

* `p[x]`：x 的父节点
* `dist[x]`：x 到父节点 / 根节点的关系值

注意：

在路径压缩前，`dist[x]` 先表示 **x 到父节点** 的距离。
在执行完 `find(x)` 路径压缩后，`dist[x]` 会变成 **x 到根节点** 的距离。

这是这题最容易绕的点。

---

### 2. `find(x)` 的意义

```java
public static int find(int x){
    if(p[x] != x) {
        int t = p[x];
        p[x] = find(p[x]);
        dist[x] = dist[t] + dist[x];
    }
    return p[x];
}
```

这个函数做了两件事：

#### 第一件：找根

这是普通并查集的功能。

#### 第二件：更新 `dist[x]`

因为原来：

* `dist[x]` 是 x 到父节点 `t`
* `dist[t]` 在递归后变成了 t 到根节点

所以：

```java
dist[x] = dist[x] + dist[t]
```

就变成了：

> x 到根节点的距离

这一步非常关键，是带权并查集的核心。

---

### 3. 初始化

```java
for(int i = 1; i <= n; i++) p[i] = i;
```

开始时每个点自己是一个集合的根，`dist[i] = 0`，表示自己和自己关系为 0。

---

### 4. 处理越界

```java
if(x > n || y > n) res++;
```

只要越界，直接是假话，不参与合并。

---

### 5. 处理 `d == 1`：x 和 y 同类

```java
if(d == 1){
    if(px == py && (dist[x] - dist[y]) % 3 != 0) res ++;
    else if(px != py){
        p[px] = py;
        dist[px] = dist[y] - dist[x];
    }
}
```

#### 已在同一集合

检查：

```java
(dist[x] - dist[y]) % 3 == 0
```

不成立就是假话。

#### 不在同一集合

要把 `px` 挂到 `py` 上。

设合并后要满足：

```java
dist[x] - dist[y] = 0
```

而：

* `dist[x]` 是 x 到 px 的距离
* `dist[px]` 是 px 到 py 的距离（这是我们要设置的）
* `dist[y]` 是 y 到 py 的距离

所以合并后：

```java
dist[x] + dist[px] - dist[y] = 0
```

移项得：

```java
dist[px] = dist[y] - dist[x]
```

这就是这一句的来源：

```java
dist[px] = dist[y] - dist[x];
```

---

### 6. 处理 `d == 2`：x 吃 y

```java
else if(d == 2){
    if(x == y) res ++;
    else if(px == py && (dist[x] - dist[y] - 1) % 3 != 0) res++;
    else if(px != py){
        p[px] = p[y];
        dist[px] = dist[y] + 1 - dist[x];
    }
}
```

#### 先判断 `x == y`

自己吃自己，假话。

#### 若已在同一集合

要满足：

```java
dist[x] - dist[y] = 1
```

所以检查：

```java
(dist[x] - dist[y] - 1) % 3 == 0
```

否则是假话。

#### 若不在同一集合

要合并，并让“x 吃 y”成立。

合并后要求：

```java
dist[x] - dist[y] = 1
```

同理可得：

```java
dist[x] + dist[px] - dist[y] = 1
```

移项：

```java
dist[px] = dist[y] + 1 - dist[x]
```

所以代码是：

```java
dist[px] = dist[y] + 1 - dist[x];
```

---

## 五、这份代码里你最该抓住的本质

这题代码不难背，但如果只背公式，很容易忘。

你真正要记住的是这个统一套路：

### 关系维护题的并查集模型

先定义：

* `dist[x]` 表示 x 到根的“相对关系”

再利用：

* 已在同集合：检查关系是否满足
* 不在同集合：通过题目要求列方程，算新根边权

也就是说，所有公式本质都来自一句话：

> **把题目给的关系，翻译成 dist 的等式。**

然后去解这个等式。

---

## 六、为什么能想到这种解法

这部分最重要，是训练你以后识别题型。

### 1. 看到什么信号，应该想到并查集

这题有几个很强的触发信号：

#### 信号一：不断输入若干“关系”

比如：

* 同类
* 吃与被吃
* 真话假话判断

这种“动态加入关系”的题，很容易想到并查集。

#### 信号二：要判断是否与之前冲突

这基本就是并查集的高频用途：

* 连通性
* 关系一致性
* 矛盾检测

#### 信号三：关系不是简单相等，而是“相对差值”

这里不是单纯问“是否同类”，而是有三种相对关系。

这说明普通并查集不够，要考虑：

* 带权并查集
* 扩展域并查集

这题官方常用的是**带权并查集**写法。

---

### 2. 为什么不是图搜索

因为题目是在线处理的：

* 前面真话会影响后面判断
* 每次都重新建图搜索太慢

而并查集特别适合处理这种：

* 边不断加入
* 快速判断是否冲突

---

### 3. 以后遇到什么题可以类比

你以后如果看到下面这些特征，可以优先想“带权并查集”：

* `x` 和 `y` 的关系不是只有相同/不同，而是有“差值”
* 关系可以写成：`value[x] - value[y] = 某个常数`
* 关系带有模意义，比如模 2、模 3
* 需要动态合并并判断矛盾

例如：

* 食物链
* 奇偶关系
* 区间差值约束
* 敌人 / 朋友 / 同类关系

---

## 七、这题一句话总结

**这题本质是：用带权并查集维护每个点到根的相对关系，并通过模 3 编码“同类 / 吃 / 被吃”，从而在线判断新语句是否与已有真话冲突。**

如果你愿意，我下一步可以继续给你讲这题的 **“手推一组样例，带你把 `dist` 的变化过程走一遍”**，这样你会彻底明白为什么这些公式成立。

```java
import java.io.*;

class Main {
    /*
        并查集（带权并查集 + 路径压缩）
    
        p[] 表示父节点，初始时每个点的父节点都是自己。
        dist[] 表示当前节点到父节点的关系值；
        在执行 find() 路径压缩后，dist[x] 会被更新为 x 到根节点的关系值。
        初始时每个点单独成集合，所以 dist[i] = 0。
    
        对于任意两个节点 x、y，
        (dist[x] - dist[y]) % 3 表示 x 和 y 的相对关系：
        0 表示 x 和 y 同类；
        1 表示 x 吃 y；
        2 表示 x 被 y 吃。
    
        因此在合并两个集合时，
        不仅要连父节点，还要同时维护根节点之间的关系值，
        使合并后的集合满足题目给出的“同类”或“吃”的条件。
    */
    public static String[] input;
    public static int N;
    public static int K;
    public static int[] p = new int[50010];
    public static int[] dist = new int[50010];

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        input = bufferedReader.readLine().split("\\s+");
        N = Integer.parseInt(input[0]);
        K = Integer.parseInt(input[1]);

        for (int i = 1; i <= N; i++) {
            p[i] = i;
            dist[i] = 0;
        }

        int res = 0;

        while (K-- > 0) {
            input = bufferedReader.readLine().split("\\s+");
            int id = Integer.parseInt(input[0]);
            int x = Integer.parseInt(input[1]);
            int y = Integer.parseInt(input[2]);
            if (x >= 1 && x <= N && y >= 1 && y <= N) {
                int px = find(x);
                int py = find(y);
                if (id == 1){
                    if(px == py){
                        //必须比较 dist[x] 和 dist[y]，不能比较 dist[px] 和 dist[py]
                        if ((dist[x] - dist[y]) % 3 != 0) res++;
                    }else{
                        p[px] = py;
                        dist[px] = dist[y] - dist[x];
                    }
                }else{
                    if (x == y) res++;
                    else if(px == py){
                        //“判断等于 1”容易错，而“减 1 后判断是否等于 0”就稳
                        if ((dist[x] - dist[y] - 1) % 3 !=0) res++;
                    }else{
                        p[px] = py;
                        //x -> px 的关系是 dist[x]
                        //px -> py 的关系是 dist[px]
                        //y -> py 的关系是 dist[y]
                        //需要满足的关系是 dist[x] + dist[px] - dist[y] = 1
                        dist[px] = dist[y] - dist[x] + 1;
                    }
                }
            }else{
                res++;
            }
        }
        bufferedWriter.write(String.valueOf(res));

        bufferedReader.close();
        bufferedWriter.close();
    }

    private static int find(int x) {
        if (x != p[x]) {
            int t = p[x];
            p[x] = find(p[x]);
            //在递归过程中，dist保存的内容是到根节点的距离（递归到根节点的时候开始从上到下更新dist）
            //每个更新好dist[x]，递归返回上一层，上一层的dist[t]就是现在的dist[x]，已经变成到跟的距离了
            dist[x] = dist[x] + dist[t];
        }
        return p[x];
    }


}
```