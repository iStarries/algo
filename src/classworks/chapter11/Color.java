package classworks.chapter11;

enum Color implements IL{
    R(1, 1, 1), G(2, 2, 2), B(3, 3, 3);
    private int r;
    private int g;
    private int b;

    Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public void show() {
        System.out.println("" + r + b + g);
    }
}
interface IL{
    void show();
}
class TestColor{
    public static void main(String[] args) {
        Color.B.show();
    }

}
