package classworks.chapter13;

import java.util.ArrayList;
import java.util.List;

public class TryList {
    public static void main(String[] args) {
        List list = new ArrayList();
        String str = "Hello";
        for (int i = 0; i < 10; i++) {
            list.add(str+ new Integer(i).toString());
        }

        list.add(1, "laoshi");
        System.out.println(list.get(4));
        list.remove(5);
        list.set(6, "xiugai");

        System.out.println(list);
//        Iterator iterator = list.iterator();
//        while (iterator.hasNext()) {
//            Object next =  iterator.next();
//            System.out.println(next);
//
//        }
    }
}

