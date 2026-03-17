package classworks.chapter14;

import java.util.*;

public class HashMap_ {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        hashMap.put(101, new Employee("111", 1200, 101));
        hashMap.put(102, new Employee("222", 113000, 102));
        hashMap.put(103, new Employee("333", 114000, 103));
        System.out.println(hashMap);

        Set keySet = hashMap.keySet();
        for (Object key : keySet) {
            Employee e = (Employee) hashMap.get(key);
            if(e.getSal() > 18000) System.out.println(e);
        }
        System.out.println("----------------------");

        Collection values = hashMap.values();
        for (Object value : values) {
            Employee e = (Employee) value;
            if(e.getSal() > 18000) System.out.println(e);
        }
        System.out.println("----------------------");

        Set entrySet = hashMap.entrySet();

        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()){
            Object entry = iterator.next();
            Map.Entry e = (Map.Entry)entry;
            Object e1 = e.getValue();
            Employee e2 = (Employee)e1;
            if(e2.getSal() > 18000) System.out.println(e2);

        }


    }

}
