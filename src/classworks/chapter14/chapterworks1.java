package classworks.chapter14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class chapterworks1 {
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new news("a的阿萨德的的是的asdasdf发烧的撒是的sad阿萨德阿萨德按时"));
        arrayList.add(new news("b的阿萨德的的是"));
        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                news s1 = (news) o1;
                news s2 = (news) o2;
                return s2.getTitle().compareTo(s1.getTitle());
            }
        });
        for (Object o : arrayList) {
            news n = (news)o;
            if(n.getTitle().length() > 15){
                String sub = n.getTitle().substring(0, 15);
                n.setTitle(sub + "...");System.out.println(n);
            }

        }

    }

}
class news{
    private String title;
    private String context;

    @Override
    public String toString() {
        return "news{" +
                "title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public news(String title) {
        this.title = title;
    }
}
