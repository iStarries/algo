package classworks.chapter14;

import java.util.HashMap;

public class chapterworks3 {
    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
        hashMap.put("1", 12000);
        hashMap.put("2", 13333);
        hashMap.put("3", 44444);

        hashMap.put("1", 99999);

//        Set keySet = hashMap.keySet();
//        for (Object key : keySet) {
//            Object o = hashMap.get(key);
//            Integer sal = (Integer)o;
//            hashMap.put(key, sal + 100);
//
//            System.out.println(hashMap.get(key));
//        }


//        System.out.println(hashMap);
    }
}
