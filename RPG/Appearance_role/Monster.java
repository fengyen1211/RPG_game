package Appearance_role;
import java.util.Random;;
public class Monster extends Appearance_role{
    //屬性
    protected int value;
    protected int exp;
    protected Random random = new Random();
    protected int maxmoney;
    protected int minmoney;
    protected int maxvalue;
    protected int minvalue;
    //建構子
    public Monster(String n, int a, int h, int mh, int maxmoney, int minmoney, int maxvalue, int minvalue) {
        name = n;
        atk = a;
        hp = h;
        maxhp = mh;
        this.maxmoney = maxmoney;
        this.minmoney = minmoney;
        this.maxvalue = maxvalue;
        this.minvalue = minvalue;
        value = random.nextInt(maxmoney - minmoney + 1) + minmoney; //隨機金錢min-max
        exp = random.nextInt(maxvalue - minvalue + 1) + minvalue; //隨機經驗值min-max
    }
    public Monster(){
        name = "怪物";
        atk = 1;
        hp = 1;
        maxhp = 1;
        maxmoney = 1;
        minmoney = 1;
        maxvalue = 1;
        minvalue = 1;
        value = 1;
        exp = 1;
    }
    //資料
    public String getinfo() {
        return "名字: " + name + "\n" +
               "攻擊力: " + atk + "\n" +
               "血量: " + hp + "/" + maxhp + "\n";
    }

    public String getName() {
        return name;
    }
    public int getATK() {
        return atk;
    }
    public int getHP() {
        return hp;
    }
    public int getMaxHP() {
        return maxhp;
    }
    public int getExp() {
        return exp;
    }
    public int getValue() {
        return value;
    }

    //設定經驗值和金錢
    public void setExp(){
        this.exp = random.nextInt(maxvalue - minvalue + 1) + minvalue; //隨機經驗值
    }
    public void setValue(){
        this.value = random.nextInt(maxmoney - minmoney + 1) + minmoney; //隨機金錢
    }

    // 復活
    public void revive() {
        hp = maxhp; // 恢復到最大血量
    }

    //攻擊
    public void attack(Person p) {
        System.out.println(name + " 對 " + p.getName() + " 造成 " + atk + " 點傷害!");
        p.gethit(atk);
        if (p.isdead()) {
            p.die();
        } else {
            System.out.println(p.getName() + " 剩下 " + p.getHP() + " 點血量!");
        }
    }
    //死掉
    @Override
    public void die() {
        System.out.println(name + " 死了! 你得到 " + exp + " 點經驗值和" + value + " 塊錢。");
    }
}
