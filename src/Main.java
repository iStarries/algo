import java.io.*;

class Main {
    public static int N = 100003;
    /*
    先判断长度一不一样
    长度一样：双指针i j同时扫描
    双指针扫描到不一样的就停止，如果扫描结束就成功
     */
    public static String val;
    public static int n;
    public static int m;
    public static int l1;
    public static int l2;
    public static int r1;
    public static int r2;
    public static String[] input;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));
        input = bufferedReader.readLine().split(" ");
        int n = Integer.parseInt(input[0]);
        int m = Integer.parseInt(input[1]);
        val = bufferedReader.readLine();

        while (m-- > 0) {
            input = bufferedReader.readLine().split(" ");
            l1 = Integer.parseInt(input[0]) - 1;
            r1 = Integer.parseInt(input[1]) - 1;
            l2 = Integer.parseInt(input[2]) - 1;
            r2 = Integer.parseInt(input[3]) - 1;

            if (r1 - l1 != r2 - l2){
                bufferedWriter.write("No\n");
                continue;
            }
            int i = l1, j = l2;
            for (; i <= r1; i++, j++) {
                if(val.charAt(i) == val.charAt(j)) continue;
                else break;
            }
            if (i > r1) bufferedWriter.write("Yes\n");
            else bufferedWriter.write("No\n");

        }
        bufferedReader.close();
        bufferedWriter.close();
    }


}