---
AIGC:
  ContentProducer: '001191110102MAD55U9H0F10002'
  ContentPropagator: '001191110102MAD55U9H0F10002'
  Label: '1'
  ProduceID: '86af31d2-8e71-4eec-9782-a9c3c5983d38'
  PropagateID: '86af31d2-8e71-4eec-9782-a9c3c5983d38'
  ReservedCode1: 'e4e6f9aa-e0d5-45bf-b18f-1029c8e92abf'
  ReservedCode2: 'e4e6f9aa-e0d5-45bf-b18f-1029c8e92abf'
---

# Java集合--Queue

## 整体表现

本次面试共 4 题（第 25~28 题），全部作答、无跳过。对 Deque 是 Queue 子接口、二叉堆实现、默认小顶堆等核心概念理解到位，并主动补充了参考答案以外的合理要点（如接口继承关系、PriorityQueue 归属 Queue 实现类）。主要短板集中在精确命名（remove() 空队列抛 NoSuchElementException）以及参考答案点名的多维度遗漏（引入版本、NULL 支持、线程安全与 non-comparable 限制）。

## 各题表现概览

| 题号 | 状态 | 评价 |
| ---- | ---- | ---- |
| 25 | 已作答（重答1次） | 重答后覆盖接口关系、用途、实现类三维度；首次未展开操作方法与 ConcurrentLinkedQueue，且出现 ArrayList 笔误 |
| 26 | 已作答（重答1次） | 首次将 remove() 误解为按元素删除，重答后相同点与空队列行为差异命中，仅剩异常名未点出 |
| 27 | 已作答（重答1次） | 重答后覆盖底层实现、空值支持、扩容三维度；引入版本与性能结论（均摊 O(1)、选 ArrayDeque 更优）未点出 |
| 28 | 已作答 | 二叉堆、默认小顶堆、O(log n) 覆盖；优先级出队规则、非线程安全与 non-comparable、Comparator 自定义未展开 |

## 需重点学习

以下为参考答案中未覆盖的要点，按题号列出：

**第25题** — Queue 的操作方法：add/offer（添加）、remove/poll（移除）、peek（查看）。Deque 的两端操作方法：push/pop、addFirst/addLast、offerFirst/offerLast、pollFirst/pollLast 等。Queue 接口的实现类除 PriorityQueue 外还有 ConcurrentLinkedQueue（线程安全）。

**第26题** — 无参 `Queue.remove()` 与 poll 一样移除并返回队头元素，区别仅在空队列：poll() 返回 null，remove() 抛 NoSuchElementException。注意与 `Collection.remove(Object o)`（按对象删除）区分，后者是 Collection 接口的方法。

**第27题** — ArrayDeque 于 JDK1.6 引入，LinkedList 于 JDK1.2 存在；ArrayDeque 插入均摊 O(1)，LinkedList 每次插入需申请新堆空间均摊更慢；性能上选用 ArrayDeque 实现队列更优。

**第28题** — PriorityQueue 于 JDK1.5 引入，优先级最高的元素先出队；底层用可变长数组存储；非线程安全且不支持 NULL 与 non-comparable 对象；默认小顶堆，可传 Comparator 自定义优先级。面试手撕常考堆排序、求第 K 大数等。

> AI生成