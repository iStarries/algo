package classworks.chapter13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Format {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String name = bufferedReader.readLine();
        System.out.println(NameFormat.nameFormat(name));
    }
}
class NameFormat{
    public static String nameFormat(String name){
        String[] s = name.split(" ");
        char first = s[1].charAt(0);
        String format = "%s,%s,%c";
        return String.format(format, s[2], s[0], first);
    }
}
