package classworks.chapter11;

public class Frock {
    private static int currentNum = 10000;
    private int serialNum;

    public Frock() {
        serialNum = getCurrentNum();
    }

    public static int getCurrentNum() {
        int res = currentNum;
        currentNum += 100;
        return res;
    }

//    @Override
//    public String toString() {
//        return "Frock{" +
//                "serialNum=" + serialNum +
//                '}';
//    }
}
