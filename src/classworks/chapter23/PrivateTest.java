package classworks.chapter23;

import java.lang.reflect.Field;

public class PrivateTest {
    /*
    获取Class对象
    实例化加载类的对象
    获取Class的属性对象
    爆破私有属性，调用set方法
    getName输出
    */
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class<?> pt = Class.forName("classworks.chapter23.PT");
        Object o = pt.newInstance();
        Field df = pt.getDeclaredField("name");
        df.setAccessible(true);
        df.set(o, "123123");

        PT pto = (PT)o;
        System.out.println(pto.getName());
    }
}

class PT{
    private String name = "123";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
