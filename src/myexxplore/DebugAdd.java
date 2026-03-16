package myexxplore;

import java.util.ArrayList;

public class DebugAdd {
    public static void main(String[] args) {
        ArrayList list = new ArrayList(8);
//使用for 给list 集合添加 1-10数据
        for (int i = 1; i <= 10; i++) {
            list.add(i);
        }
//使用for 给list 集合添加 11-15数据
        for (int i = 11; i <= 15; i++) {
            list.add(i);
        }
        list.add(100);
        list.add(200);
        list.add(null);
    }
}
