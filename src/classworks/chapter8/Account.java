package classworks.chapter8;

public class Account {
    private String name;
    private int[] key;
    private double remainMoney;

    public Account(String name, int[] key, double remainMoney) {
        setKey(key);
        setName(name);
        setRemainMoney(remainMoney);
    }

    public Account() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.length() > 1 && name.length() < 5) {
            this.name = name;
        } else {
            System.out.println("名字不合法");
            this.name = "nobody";
        }
    }

    public int[] getKey() {
        return key;
    }

    public void setKey(int[] key) {
        if (key.length == 6) {
            this.key = key;
        } else {
            System.out.println("密码不合法");
            this.key = new int[]{0, 0, 0, 0, 0, 0};
        }
    }

    public double getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(double remainMoney) {
        if(remainMoney > 20){
            this.remainMoney = remainMoney;
        }else{
            System.out.println("余额不对");
            this.remainMoney = 0;
        }

    }
    public void show(){
        System.out.println("name" + name + "\t" + remainMoney + "\t" + key);
    }
}
