package classworks.chapter10;

public class OuterClass {
    public static void main(String[] args) {
        Out out = new Out();
        Out.in in01 = out.new in();
    }
}
class Out{
    public class in{}
}
