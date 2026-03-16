package myexxplore;

import java.util.ArrayList;
import java.util.List;

class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String sa = sc.next();
        String sb = sc.next();
        int la = sa.length();
        int lb = sb.length();
        int[] a = new int[la];
        int[] b = new int[lb];
        List<Integer> c = new ArrayList<>();
        for(int i = la - 1, k = 0; i >= 0; i--, k++){
            a[k] = sa.charAt(i) - '0';
        }
        for(int i = lb - 1, k = 0; i >= 0; i--, k++){
            b[k] = sb.charAt(i) - '0';
        }
        // 加法中 a 的长度 大于等于 b 的长度
        if(la > lb) c = add(a, b);
        else c = add(b, a);
        for(int i = c.size() - 1; i >= 0; i--){
            System.out.print(c.get(i));
        }

    }
    public static List<Integer> add(int[] a, int[] b){
        List<Integer> c = new ArrayList<>();
        // 加法，只需要记录进位
        int t = 0;
        for(int i = 0; i < a.length; i++){
            t += a[i];
            if(i < b.length) t += b[i];
            c.add(t % 10);
            t /= 10;
        }
        // 最后以防还有进位
        if(t > 0) c.add(t);
        return c;
    }
}
