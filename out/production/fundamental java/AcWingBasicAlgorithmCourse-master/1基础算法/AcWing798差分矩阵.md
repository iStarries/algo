# AcWing 算法基础课 -- 基础算法

## AcWing 798. 差分矩阵

`难度：简单`

### 题目描述

输入一个n行m列的整数矩阵，再输入q个操作，每个操作包含五个整数x1, y1, x2, y2, c，其中(x1, y1)和(x2, y2)表示一个子矩阵的左上角坐标和右下角坐标。

每个操作都要将选中的子矩阵中的每个元素的值加上c。

请你将进行完所有操作后的矩阵输出。

**输入格式**

第一行包含整数n,m,q。

接下来n行，每行包含m个整数，表示整数矩阵。

接下来q行，每行包含5个整数x1, y1, x2, y2, c，表示一个操作。

**输出格式**

共 n 行，每行 m 个整数，表示所有操作进行完毕后的最终矩阵。

```r
数据范围

1≤n,m≤1000,
1≤q≤100000,
1≤x1≤x2≤n,
1≤y1≤y2≤m,
−1000≤c≤1000,
−1000≤矩阵内元素的值≤1000

输入样例：

3 4 3
1 2 2 1
3 2 2 1
1 1 1 1
1 1 2 2 1
1 3 2 3 2
3 1 3 4 1

输出样例：

2 3 4 1
4 3 4 1
2 2 2 2
```

### Solution

```java
import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out)); 
        String[] s = in.readLine().split(" ");
        int n = Integer.parseInt(s[0]);
        int m = Integer.parseInt(s[1]);
        int q = Integer.parseInt(s[2]);
        int[][] a = new int[n + 10][m + 10];
        int[][] b = new int[n + 10][m + 10];
        for(int i = 1; i <= n; i++){
            s = in.readLine().split(" ");
            for(int j = 1; j <= m; j++){
                int c = Integer.parseInt(s[j - 1].trim());
                insert(a, i, j, i, j, c);
            }
        }
        while(q > 0){
            q--;
            s = in.readLine().split(" ");
            int x1 = Integer.parseInt(s[0]);
            int y1 = Integer.parseInt(s[1]);
            int x2 = Integer.parseInt(s[2]);
            int y2 = Integer.parseInt(s[3]);
            int c = Integer.parseInt(s[4]);
            insert(a, x1, y1, x2, y2, c);
        }
        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                b[i][j] = a[i][j] + b[i - 1][j] + b[i][j - 1] - b[i - 1][j - 1];
                // 如果不是以字符串的形式输出，就输出不了数字
                out.write(b[i][j] + " ");
                if(j == m){
                    out.write("\n");
                }
                
            }
            out.flush();

        }
        
    }
    public static void insert(int[][] a, int x1, int y1, int x2, int y2, int c){
        a[x1][y1] += c;
        a[x2 + 1][y1] -= c;
        a[x1][y2 + 1] -= c;
        a[x2 + 1][y2 + 1] += c;
    }
}
```

这题就是你刚才问的那种差分的 **二维版本**。
一维差分是“对一个区间加”，二维差分是“对一个子矩阵加”。

你这次没有打字提问，但既然你发了 `AcWing798差分矩阵.md`，那我就直接按教学流程带你讲。

---

# 一、这题属于什么类型

## 题型

* **二维差分**
* **矩阵区域修改**
* **最后统一还原结果**

## 核心考点

把一次对子矩阵 `[x1..x2][y1..y2]` 的整体加法，转化成 **4 个角的标记**，最后通过二维前缀和把整个矩阵还原出来。

---

# 二、先和一维差分对应起来

你刚学完一维差分，所以这里最重要的是先建立联系。

## 一维差分

如果要给区间 `[l, r]` 加 `c`，做：

```java
diff[l] += c;
diff[r + 1] -= c;
```

因为：

* 从 `l` 开始生效
* 到 `r+1` 结束生效

---

## 二维差分

如果要给子矩阵 `(x1,y1)` 到 `(x2,y2)` 全部加 `c`，做：

```java
a[x1][y1] += c;
a[x2 + 1][y1] -= c;
a[x1][y2 + 1] -= c;
a[x2 + 1][y2 + 1] += c;
```

这 4 个点就是二维差分的核心模板。

你可以先把它记成一句话：

> **一维差分改两个点，二维差分改四个角。**

---

# 三、这 4 个角为什么这样改

这是这题最关键的地方。

假设你要给这个矩形整体加 `c`：

```text
左上角:     (x1, y1)
右下角:     (x2, y2)
```

那么差分矩阵里：

```java
a[x1][y1] += c;
a[x2 + 1][y1] -= c;
a[x1][y2 + 1] -= c;
a[x2 + 1][y2 + 1] += c;
```

你可以这样理解：

* `a[x1][y1] += c`
  表示从这个左上角开始，后面区域先整体多出一个 `c`

* `a[x2 + 1][y1] -= c`
  表示超过下边界后，这个影响要取消

* `a[x1][y2 + 1] -= c`
  表示超过右边界后，这个影响也要取消

* `a[x2 + 1][y2 + 1] += c`
  前面减了两次，右下角外侧那块被多减了一次，所以这里补回来

这个思想其实和二维前缀和里的“加加减减”完全一致。

---

# 四、你可以把它想成“二维版的开始与结束标记”

一维里你记录的是：

* 从 `l` 开始加
* 从 `r+1` 开始不加

二维里你记录的是：

* 从左上角开始加
* 超过下边界要取消
* 超过右边界要取消
* 右下角外部重复取消了一次，要补回去

---

# 五、这题的代码在做什么

你给的代码总体是标准模板。

---

## 1. 读入 `n, m, q`

```java
String[] s = in.readLine().split(" ");
int n = Integer.parseInt(s[0]);
int m = Integer.parseInt(s[1]);
int q = Integer.parseInt(s[2]);
```

* `n`：行数
* `m`：列数
* `q`：操作次数

---

## 2. 定义数组

```java
int[][] a = new int[n + 10][m + 10];
int[][] b = new int[n + 10][m + 10];
```

这里：

* `a`：前半段其实是 **二维差分数组**
* `b`：最后还原出来的 **最终矩阵**

这点和你上一题一样，最容易看混。

---

## 3. 把原矩阵先“插入”进差分矩阵

```java
for(int i = 1; i <= n; i++){
    s = in.readLine().split(" ");
    for(int j = 1; j <= m; j++){
        int c = Integer.parseInt(s[j - 1].trim());
        insert(a, i, j, i, j, c);
    }
}
```

这一步非常像上一题。

意思不是“直接把原矩阵存进 a”，而是：

> 把原矩阵的每个点 `(i,j)` 看成一个 `1×1` 的小矩形，加上它原本的值 `c`

也就是：

```java
insert(a, i, j, i, j, c);
```

这样做完以后，`a` 就构造成了原矩阵的二维差分形式。

---

## 4. 处理每次子矩阵加法操作

```java
while(q > 0){
    q--;
    s = in.readLine().split(" ");
    int x1 = Integer.parseInt(s[0]);
    int y1 = Integer.parseInt(s[1]);
    int x2 = Integer.parseInt(s[2]);
    int y2 = Integer.parseInt(s[3]);
    int c = Integer.parseInt(s[4]);
    insert(a, x1, y1, x2, y2, c);
}
```

每次操作都是：

> 给子矩阵 `(x1,y1) ~ (x2,y2)` 全部加 `c`

在二维差分数组里只记 4 个角。

所以每次操作是 **O(1)**。

---

## 5. 最后通过二维前缀和还原答案

```java
for(int i = 1; i <= n; i++){
    for(int j = 1; j <= m; j++){
        b[i][j] = a[i][j] + b[i - 1][j] + b[i][j - 1] - b[i - 1][j - 1];
        out.write(b[i][j] + " ");
        if(j == m){
            out.write("\n");
        }
    }
}
```

这个式子你必须熟：

```java
b[i][j] = a[i][j] + b[i - 1][j] + b[i][j - 1] - b[i - 1][j - 1];
```

含义是：

> 二维差分矩阵 `a` 经过二维前缀和，还原成真正的矩阵 `b`

这和一维里的：

```java
a[i] += a[i - 1];
```

是一个道理，只不过二维要同时考虑“上”和“左”，然后减掉重复算的左上角。

---

# 六、`insert` 函数才是这题灵魂

```java
public static void insert(int[][] a, int x1, int y1, int x2, int y2, int c){
    a[x1][y1] += c;
    a[x2 + 1][y1] -= c;
    a[x1][y2 + 1] -= c;
    a[x2 + 1][y2 + 1] += c;
}
```

这段模板必须会背，但更重要的是理解：

> 它的作用是：在二维差分数组里记录一次“对子矩阵整体加 c”

以后只要遇到：

* 子矩阵统一加值
* 多次矩形区域修改
* 最后统一输出矩阵

你就要想到这段。

---

# 七、和一维差分怎么对应

你可以这样对照记忆：

## 一维差分

区间 `[l, r]` 加 `c`

```java
diff[l] += c;
diff[r + 1] -= c;
```

---

## 二维差分

子矩阵 `[x1..x2][y1..y2]` 加 `c`

```java
diff[x1][y1] += c;
diff[x2 + 1][y1] -= c;
diff[x1][y2 + 1] -= c;
diff[x2 + 1][y2 + 1] += c;
```

所以二维差分就是：

> **把一维的“左右边界”扩展成“上下左右边界”**

---

# 八、时间复杂度

## 构造初始矩阵

`n*m` 个点，每个点做一次 `insert`

复杂度：

```text
O(nm)
```

## 处理 `q` 次操作

每次 `O(1)`：

```text
O(q)
```

## 最后还原矩阵

做一遍二维前缀和：

```text
O(nm)
```

## 总复杂度

```text
O(nm + q)
```

这比暴力做法强很多。

---

# 九、朴素做法为什么不行

如果每次操作都真的去改整个子矩阵：

```java
for (int i = x1; i <= x2; i++) {
    for (int j = y1; j <= y2; j++) {
        mat[i][j] += c;
    }
}
```

一次最坏就要改 `n*m` 个数。
而 `q` 最多 `100000`，肯定会超时。

所以这题本质上和上一题一样：

> **暴力修改区域太慢，所以用差分把区域修改压缩成常数次操作。**

---

# 十、Java 实现细节

## 1. 为什么数组开大一点

因为会访问：

```java
x2 + 1
y2 + 1
```

所以数组必须至少能访问到 `n+1` 和 `m+1`。

代码里开成：

```java
n + 10, m + 10
```

是常见保险写法。

---

## 2. 为什么用 1 下标

因为题目本来就是从 `(1,1)` 开始，写二维前缀和、二维差分时，1 下标最自然。

否则边界会更容易出错。

---

## 3. `out.write(b[i][j] + " ")` 为什么可以

因为 `BufferedWriter.write()` 不能直接写 int，必须先变成字符串。
而：

```java
b[i][j] + " "
```

会自动拼成字符串，所以能写。

你代码里的注释说得没错。

---

# 十一、常见错误

## 1. 少写一个角

很多同学只会写 3 个位置，忘了最后这个：

```java
a[x2 + 1][y2 + 1] += c;
```

这是用来补偿重复减掉的一块，不能漏。

---

## 2. 把还原公式写错

正确是：

```java
b[i][j] = a[i][j] + b[i - 1][j] + b[i][j - 1] - b[i - 1][j - 1];
```

少减了左上角，或者符号写反，结果都会错。

---

## 3. 没搞清楚 `a` 和 `b` 的身份

* `a`：差分矩阵
* `b`：最终矩阵

这一定要在脑子里分清。

---

## 4. 边界越界

因为会访问 `x2+1`、`y2+1`，数组不能只开 `n×m`。

---

# 十二、更清晰的 Java 写法

我把变量名改得更直观一点：

```java
import java.io.*;

public class Main {

    static void insert(int[][] diff, int x1, int y1, int x2, int y2, int c) {
        diff[x1][y1] += c;
        diff[x2 + 1][y1] -= c;
        diff[x1][y2 + 1] -= c;
        diff[x2 + 1][y2 + 1] += c;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

        String[] first = br.readLine().split(" ");
        int n = Integer.parseInt(first[0]);
        int m = Integer.parseInt(first[1]);
        int q = Integer.parseInt(first[2]);

        int[][] diff = new int[n + 2][m + 2];
        int[][] ans = new int[n + 2][m + 2];

        // 把原矩阵构造成二维差分矩阵
        for (int i = 1; i <= n; i++) {
            String[] row = br.readLine().split(" ");
            for (int j = 1; j <= m; j++) {
                int x = Integer.parseInt(row[j - 1]);
                insert(diff, i, j, i, j, x);
            }
        }

        // 处理 q 次子矩阵加法
        while (q-- > 0) {
            String[] op = br.readLine().split(" ");
            int x1 = Integer.parseInt(op[0]);
            int y1 = Integer.parseInt(op[1]);
            int x2 = Integer.parseInt(op[2]);
            int y2 = Integer.parseInt(op[3]);
            int c = Integer.parseInt(op[4]);
            insert(diff, x1, y1, x2, y2, c);
        }

        // 二维前缀和还原最终矩阵
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                ans[i][j] = diff[i][j] + ans[i - 1][j] + ans[i][j - 1] - ans[i - 1][j - 1];
                out.write(ans[i][j] + " ");
            }
            out.write("\n");
        }

        out.flush();
    }
}
```

---

# 十三、为什么能想到这种解法

这一部分最重要，是训练题型识别能力。

看到下面这些信号，就应该往二维差分上想：

## 1. 操作是对子矩阵整体修改

比如：

* 给一个矩形区域全部加 `c`
* 给一个子矩阵全部减 `c`

这是二维差分最强信号。

---

## 2. 操作次数很多

如果每次都暴力修改整个矩形，复杂度太高。

---

## 3. 最后统一输出

题目不是让你每次操作后马上查询，而是：

> 所有操作结束后，输出最终矩阵

这非常适合差分。

---

## 4. 本质是“一大块区域一起受同样影响”

这就是差分最擅长处理的情况。

---

# 十四、怎么举一反三

你学完这题后，可以迁移到这些题：

## 1. 矩阵区间加法

标准二维差分模板题。

## 2. 矩形覆盖次数统计

比如很多矩形区域覆盖，最后问每个点被覆盖几次。
本质上就是每次对子矩阵加 `1`。

## 3. 地图、图像、棋盘类区域修改

表面包装不同，但本质仍是：

* 某一块矩形区域整体变化

这时都能考虑二维差分。

---

# 十五、你现在最该记住的三个模板

## 模板 1：二维差分插入

```java
diff[x1][y1] += c;
diff[x2 + 1][y1] -= c;
diff[x1][y2 + 1] -= c;
diff[x2 + 1][y2 + 1] += c;
```

## 模板 2：用单点插入构造初始矩阵

```java
insert(diff, i, j, i, j, val);
```

## 模板 3：二维前缀和还原

```java
ans[i][j] = diff[i][j] + ans[i - 1][j] + ans[i][j - 1] - ans[i - 1][j - 1];
```

---

# 十六、一句话总结

这题的本质就是：

> **把对子矩阵的整体修改，转化成对四个角的标记，最后用二维前缀和统一还原。**

---

你现在如果还觉得“四个角为什么这么改”有点虚，我下一条可以直接给你画一个 **3×4 样例表格**，一步一步演示这 4 个角如何最终只影响目标矩形。

# 注释位置问题：
你这份代码的核心思路是对的：
你想自己先构造二维差分，再做区间修改，最后还原。

**但你有两个关键地方写错了**，所以结果不对。

---

# 一、先说结论

你错在这两处：

## 错误 1：二维差分初始化公式写错了

你写的是：

```java
res[i][j] = arr[i][j] - arr[i - 1][j] - arr[i][j - 1];
```

但正确的二维差分应该是：

```java
res[i][j] = arr[i][j] - arr[i - 1][j] - arr[i][j - 1] + arr[i - 1][j - 1];
```

注意最后这个：

```java
+ arr[i - 1][j - 1]
```

你漏掉了。

---

## 错误 2：二维前缀和还原公式写错了

你写的是：

```java
res[i][j] += res[i - 1][j] + res[i][j - 1];
```

但正确写法是：

```java
res[i][j] += res[i - 1][j] + res[i][j - 1] - res[i - 1][j - 1];
```

这里也必须减去左上角：

```java
- res[i - 1][j - 1]
```

否则左上角那块会被重复算两次。

---

# 二、为什么会这样

这是二维题里最常见的坑：
**上边加一遍，左边加一遍，左上角就被重复统计了。**

所以无论是：

* 从原矩阵构造二维差分
* 还是从二维差分还原原矩阵

都要遵循同一个“加上面，加左边，减左上角”的原则。

---

# 三、你这段代码具体哪里错

---

## 1. 这段构造二维差分错了

你现在写的是：

```java
res[i][j] = arr[i][j] - arr[i - 1][j] - arr[i][j - 1];
```

正确应该是：

```java
res[i][j] = arr[i][j] - arr[i - 1][j] - arr[i][j - 1] + arr[i - 1][j - 1];
```

### 为什么要加回来左上角？

因为：

* `arr[i - 1][j]` 里包含了左上区域
* `arr[i][j - 1]` 里也包含了左上区域

你减了两次，所以要补一次。

这和二维前缀和完全同理。

---

## 2. 这段还原也错了

你现在写的是：

```java
res[i][j] += res[i - 1][j] + res[i][j - 1];
```

正确应该是：

```java
res[i][j] += res[i - 1][j] + res[i][j - 1] - res[i - 1][j - 1];
```

### 为什么这里也要减左上角？

因为：

* `res[i - 1][j]` 已经包含左上部分
* `res[i][j - 1]` 也包含左上部分

加起来时左上部分被算了两次，所以要减掉一次。

---

# 四、你可以直接改成这样

我先尽量保持你的原写法不变，只改错的地方：

```java
package myexxplore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int m = Integer.parseInt(s1[1]);
        int q = Integer.parseInt(s1[2]);

        int[][] arr = new int[n + 2][m + 2];
        int[][] res = new int[n + 2][m + 2];

        String[] s2;
        for (int i = 1; i <= n; i++) {
            s2 = bufferedReader.readLine().split(" ");
            for (int j = 1; j <= m; j++) {
                arr[i][j] = Integer.parseInt(s2[j - 1]);

                // 正确的二维差分初始化
                res[i][j] = arr[i][j]
                        - arr[i - 1][j]
                        - arr[i][j - 1]
                        + arr[i - 1][j - 1];
            }
        }

        while (q > 0) {
            q--;
            String[] s = bufferedReader.readLine().split(" ");
            int x1 = Integer.parseInt(s[0]);
            int y1 = Integer.parseInt(s[1]);
            int x2 = Integer.parseInt(s[2]);
            int y2 = Integer.parseInt(s[3]);
            int c = Integer.parseInt(s[4]);

            res[x1][y1] += c;
            res[x2 + 1][y1] -= c;
            res[x1][y2 + 1] -= c;
            res[x2 + 1][y2 + 1] += c;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                // 正确的二维前缀和还原
                res[i][j] = res[i][j]
                        + res[i - 1][j]
                        + res[i][j - 1]
                        - res[i - 1][j - 1];

                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }
}
```

---

# 五、不过你这里还有一个“思路层面”的问题

你现在是这样做的：

1. 先把原矩阵读到 `arr`
2. 再手动算二维差分到 `res`
3. 再在 `res` 上做修改
4. 最后还原

这个做法**可以**，但更容易写错，因为你要自己手推二维差分公式。

---

## 更推荐的写法

像一维差分一样，直接把每个原始值当成一次单点插入：

```java
insert(diff, i, j, i, j, val);
```

这样你根本不用自己手写：

```java
res[i][j] = arr[i][j] - arr[i - 1][j] - arr[i][j - 1] + arr[i - 1][j - 1];
```

更不容易错。

---

# 六、为什么 AcWing 模板更喜欢“单点插入构造”

因为统一。

## 一维是这样

```java
insert(diff, i, i, a[i]);
```

## 二维是这样

```java
insert(diff, i, j, i, j, a[i][j]);
```

这样你只要记住一个 `insert` 模板，前后逻辑完全统一。

---

# 七、你这题最该记住的公式

## 1. 二维差分公式

由原矩阵 `a` 构造差分矩阵 `b`：

```java
b[i][j] = a[i][j] - a[i - 1][j] - a[i][j - 1] + a[i - 1][j - 1];
```

---

## 2. 二维前缀和还原公式

由差分矩阵 `b` 还原原矩阵 `a`：

```java
a[i][j] = b[i][j] + a[i - 1][j] + a[i][j - 1] - a[i - 1][j - 1];
```

---

## 3. 子矩阵修改公式

对子矩阵 `(x1,y1) ~ (x2,y2)` 加 `c`：

```java
diff[x1][y1] += c;
diff[x2 + 1][y1] -= c;
diff[x1][y2 + 1] -= c;
diff[x2 + 1][y2 + 1] += c;
```

---

# 八、你这份代码为什么“看起来很像对的，但就是错”

因为二维问题里最容易出现这种情况：

> 式子差一个 `+ 左上角` 或 `- 左上角`

代码整体结构都对，但只要漏掉左上角这一项，结果就会系统性错误。

所以以后你碰到二维前缀和 / 二维差分时，脑子里一定要自动冒出一句话：

> **上面一份，左边一份，左上角会重复，要减掉；如果前面减重了，后面就要补回来。**

---

# 九、给你一个更短的定位方式

以后你自己检查这类题，可以直接问自己两句：

### 1.

“我这里是不是同时用了上边和左边的信息？”
如果是，那大概率要处理左上角。

### 2.

“左上角是不是被重复算了两次？”
如果是，就要减掉一次；如果前面减重了，就要加回来。

---

# 十、最后帮你总结成一句话

你这份代码不对，不是区间修改那 4 个角有问题，
**而是二维差分初始化和二维前缀和还原时，都漏掉了左上角那一项。**

---

你要是愿意，我下一条可以直接拿一个 **2×2 或 3×3 小矩阵**，把你这两个公式为什么一定有 `左上角`，给你手推一遍。

# res是差分序列，为什么也想前缀和序列包含了左上角？
你这个问题问得非常好，而且正好问到了**二维差分最容易混的地方**：

> **“res 明明是差分数组，为什么还说 `res[i-1][j]`、`res[i][j-1]` 包含左上角？”**

答案是：

> **在那一行还原代码里，`res` 已经不再只是“纯差分数组”了。**
> 你是一边遍历，一边把它改造成“还原后的矩阵值”。

所以我前面那句“包含左上角”，说的是 **还原过程中的 `res`**，不是最开始那个原始差分数组。

---

# 一、先把这个误区拆开

你代码最后写的是：

```java
for (int i = 1; i <= n; i++) {
    for (int j = 1; j <= m ; j++) {
        res[i][j] += res[i - 1][j] + res[i][j - 1];
        System.out.print(res[i][j]+ " ");
    }
}
```

你以为这里一直在操作“差分数组 `res`”。

其实不是。

当循环跑到某个位置 `(i, j)` 时：

* `res[i][j]` 当前位置，开始时还是“差分值”
* 但 `res[i-1][j]` 已经被你前面算过了，**它已经不是差分值了**
* `res[i][j-1]` 也已经被你前面算过了，**它也不是差分值了**

它们已经变成了：

> **还原后的原矩阵值**

所以这里本质上是在做：

```java
原矩阵[i][j] = 差分[i][j] + 上边的原矩阵值 + 左边的原矩阵值 - 左上角的原矩阵值
```

---

# 二、关键点：同一个 `res`，前后身份变了

这和一维差分其实一样。

## 一维里

你写：

```java
diff[i] += diff[i - 1];
```

看起来还是 `diff`，但其实做完以后：

* 原来的 `diff[i]` 是差分值
* 新的 `diff[i]` 已经变成原数组值了

也就是说：

> **数组名没变，但含义变了**

---

## 二维里也是一样

你写：

```java
res[i][j] = res[i][j] + res[i - 1][j] + res[i][j - 1] - res[i - 1][j - 1];
```

这里：

* 等号右边的 `res[i][j]` 是当前点的差分值
* 但 `res[i - 1][j]`、`res[i][j - 1]`、`res[i - 1][j - 1]` 已经是前面恢复出来的矩阵值

所以它不是“差分和差分相加”，而是：

> **当前差分值 + 已恢复出的上方区域信息 + 已恢复出的左方区域信息 - 重复的左上区域**

---

# 三、为什么会“包含左上角”

我们别说抽象话，直接看二维前缀和的意义。

假设你已经恢复到某个位置 `(i,j)`。

那么：

## `res[i - 1][j]` 表示什么？

它表示从 `(1,1)` 到 `(i-1,j)` 这一块累计恢复后的结果。

这块区域里当然包含左上角区域 `(1,1) ~ (i-1,j-1)`。

---

## `res[i][j - 1]` 表示什么？

它表示从 `(1,1)` 到 `(i,j-1)` 这一块累计恢复后的结果。

这块区域里也包含左上角区域 `(1,1) ~ (i-1,j-1)`。

---

所以：

* 上边算了一次左上角
* 左边又算了一次左上角

左上角被重复算了 2 次。

因此要减掉一次：

```java
- res[i - 1][j - 1]
```

---

# 四、你混淆的点本质是什么

你现在脑子里想的是：

> “差分数组某个位置，不就是一个单独的变化量吗？怎么会包含左上角？”

这个想法本身没错。

**如果单看“原始差分数组”本身，某个点当然不包含左上角。**

但是你最后这一步不是在“看原始差分数组”，而是在做：

> **二维前缀和恢复**

一旦进入恢复过程，前面位置上的 `res` 元素就已经不再表示“单个差分值”，而表示“累计恢复结果”。

所以“包含左上角”是说：

* `res[i-1][j]` 这个**已恢复值**
* `res[i][j-1]` 这个**已恢复值**

都覆盖到了左上那块区域。

---

# 五、用一个最小 2×2 例子看最清楚

假设差分矩阵是：

```text
d:
1 2
3 4
```

现在我们想通过二维前缀和恢复原矩阵。

设恢复后的矩阵也存在 `res` 里。

---

## 第一步：算 `res[1][1]`

```java
res[1][1] = d[1][1] = 1
```

---

## 第二步：算 `res[1][2]`

```java
res[1][2] = d[1][2] + res[1][1]
          = 2 + 1
          = 3
```

---

## 第三步：算 `res[2][1]`

```java
res[2][1] = d[2][1] + res[1][1]
          = 3 + 1
          = 4
```

---

## 第四步：算 `res[2][2]`

如果你按你原来的写法：

```java
res[2][2] = d[2][2] + res[1][2] + res[2][1]
          = 4 + 3 + 4
          = 11
```

但这里有问题：

* `res[1][2]` 里面已经包含了 `res[1][1]`
* `res[2][1]` 里面也已经包含了 `res[1][1]`

所以 `res[1][1]` 被算了两次。

应该减掉一次：

```java
res[2][2] = d[2][2] + res[1][2] + res[2][1] - res[1][1]
          = 4 + 3 + 4 - 1
          = 10
```

你看，这里的“左上角被重复”说的不是差分点 `d[1][1]`，而是恢复过程里的累计结果。

---

# 六、换个角度：二维版的一维前缀和

一维时：

```java
a[i] = a[i] + a[i - 1]
```

这里 `a[i-1]` 已经是恢复后的前缀和结果了。

二维只是多了一维：

```java
a[i][j] = 当前差分值 + 上方累计 + 左方累计 - 左上重复累计
```

所以它自然会出现“左上角重复”。

---

# 七、你这句话应该这样改着理解

你原来的疑问是：

> “这是差分序列，为什么也像前缀和序列包含了左上角？”

更准确地说应该是：

> **原始差分矩阵本身不包含左上角。**
> **但在恢复过程中，`res[i-1][j]` 和 `res[i][j-1]` 已经是累计结果，所以它们都覆盖了左上区域。**

所以必须减去：

```java
res[i - 1][j - 1]
```

---

# 八、最本质的一句话

> **差分矩阵负责“记录变化”**
> **二维前缀和负责“累计恢复”**

你最后那一行代码已经进入“累计恢复”阶段了，
所以必须按照二维前缀和的规则处理左上角重复。

---

# 九、你可以这样记

## 原始差分矩阵

每个点只是一个“变化标记”，不谈“包含左上角”。

## 恢复时

当你写出：

```java
res[i][j] = res[i][j] + ...
```

这时数组前面部分已经逐步变成“累计值”了。

于是：

* 上边累计值包含左上角
* 左边累计值也包含左上角
* 所以要减左上角

---

# 十、最后用一句话帮你彻底定住

你不是在问“差分为什么包含左上角”，而是在问：

> **为什么用差分恢复原矩阵时，恢复公式里要减左上角？**

答案就是：

> 因为恢复时用的是二维前缀和思想，而上方和左方的累计结果会把左上区域重复计算一次。

---

你要的话，我下一条直接给你画一个 **3×3 的恢复表格**，把每一步 `res[i][j]` 是“差分值”还是“恢复值”标出来，你会一下彻底不混。



```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String[] s1 = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(s1[0]);
        int m = Integer.parseInt(s1[1]);
        int q = Integer.parseInt(s1[2]);
        int[][] arr = new int[n + 2][m + 2];
        int[][] res = new int[n + 2][m + 2];
        String[] s2;
        for (int i = 1; i <= n; i++) {
            s2 = bufferedReader.readLine().split(" ");
            for (int j = 1; j <= m; j++) {

                arr[i][j] = Integer.parseInt(s2[j - 1]);
                //这里的arr作用是前缀和序列，作用是左上角元素的和，注意处理重复部分
                res[i][j] = arr[i][j] - arr[i - 1][j] - arr[i][j - 1] + arr[i - 1][j - 1];
            }
        }
        String[] s;
        while (q > 0) {
            q--;
            s = bufferedReader.readLine().split(" ");
            int x1 = Integer.parseInt(s[0]);
            int y1 = Integer.parseInt(s[1]);
            int x2 = Integer.parseInt(s[2]);
            int y2 = Integer.parseInt(s[3]);
            int c = Integer.parseInt(s[4]);
            //这时构建差分序列，作用是对右下角所有元素的影响，注意处理重复部分
            res[x1][y1] += c;
            res[x2 + 1][y1] -= c;
            res[x1][y2 + 1] -= c;
            res[x2 + 1][y2 + 1] += c;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                //这里操作完res的某一个位置之后，就变成了前缀和序列了，发挥的作用就变成了包含左上角的所有元素，所以在还原的时候注意要减去重复的部分
                res[i][j] += res[i - 1][j] + res[i][j - 1] - res[i - 1][j - 1];
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }
    }
}

```