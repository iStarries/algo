import java.io.*;

class Main {
    /*
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


        bufferedReader.close();
        bufferedWriter.close();
    }



}