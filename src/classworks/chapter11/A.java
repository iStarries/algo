package classworks.chapter11;

public class A {
    private String name1;

    public A(String name) {
        this.name1 = name;
    }

    public void showA() {
        class B {
            private String name;

            public B(String name) {
                this.name = name;
            }

            void show() {
                System.out.println(name);
                System.out.println(name1);
            }
        }
        B b = new B("zhang");
        b.show();
    }

}

class TestA {
    public static void main(String[] args) {
        A a = new A("wang");
        a.showA();
    }
}
