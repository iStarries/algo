package classworks.chapter10;

public class Annoy {
    public static void main(String[] args) {
        Annoy000 annoy000 = new Annoy000();
        annoy000.test();
    }
}
class Annoy000{
    private int a000 = 111;
    public void test(){
        Annoy001 annoy001 = new Annoy001() {
            public void info(){
                System.out.println(a000);
            }
        };
//        annoy001.a000;
    }
}

class Annoy001{
    private int a001 = 1;
}