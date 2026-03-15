package classworks.chapter13;

import java.util.Arrays;
import java.util.Comparator;

public class TryArrays {
    public static void main(String[] args) {
        Book[] books = new Book[4];
        books[0] = new Book("红楼梦", 100);
        books[1] = new Book("金瓶梅新", 90);
        books[2] = new Book("青年文摘 20 年", 5);
        books[3] = new Book("java 从入门到放弃~", 300);

        Arrays.sort(books, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                Book integer1 = (Book)o1;
                Book integer2 = (Book)o2;
                if(integer1.getPrice() > integer2.getPrice()){
                    return 1;
                }
                return -1;
            }
        });
        for (Book i : books){
            System.out.println(i.toString());
        }

    }
}

class Book{
    private String name;
    private double price;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "price=" + price +
                '}';
    }
}
class Sort{
    public static void BB1(Book[] books){
        for (int i = 0; i < books.length; i++) {
            for (int j = i; j < books.length - 1; j++) {
                if (books[i].getPrice() > books[j].getPrice()){
                    double t = books[i].getPrice();
                    books[i].setPrice(books[j].getPrice());
                    books[j].setPrice(t);
                }
            }
        }
    }
    public static void BB2(Book[] books, Comparator comparator){
        for (int i = 0; i < books.length - 1; i++) {
            for (int j = i; j < books.length; j++) {
                if (comparator.compare(books[i].getPrice(), books[j].getPrice()) > 0){
                    double t = books[i].getPrice();
                    books[i].setPrice(books[j].getPrice());
                    books[j].setPrice(t);
                }
            }
        }
    }
}
