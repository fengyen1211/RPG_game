package Appearance_role;
import Backpack.Backpack;
import java.util.Map;
public class Person extends Appearance_role{
    //屬性
    protected int level;
    protected int exp;
    protected Backpack backpack;
    protected int money;
    protected int expToLevelUp;
    protected int maxUnlockedLevel;
    private boolean burstPotionActive = false;   // 爆發藥水
    private boolean resistPotionActive = false;  // 抗性藥水
    private boolean ragePotionActive = false;    // 狂怒藥水
    private int originalAtk = -1;                // 記錄原始攻擊力
    //建構子
    public Person() {
        name = "Hero";
        atk = 10;
        hp = 100;
        maxhp = 100;
        level = 1;
        exp = 0;
        backpack = new Backpack();
        money = 0;
        expToLevelUp = 100;
        maxUnlockedLevel = 1; //最高關卡
    }
    //資料
    public String getinfo() {
        return "名字: " + name + "\n" +
               "等級: " + level + "\n" +
               "經驗值: " + exp + "/" + expToLevelUp + "\n" +
               "攻擊力: " + atk + "\n" +
               "血量: " + hp + "/" + maxhp + "\n" +
               "錢: " + money + "\n" +
               "背包: \n" + backpack.showItems();
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
    public int getLevel() {
        return level;
    }
    public int getExp() {
        return exp;
    }
    public int getexpToLevelUp() {
        return expToLevelUp;
    }
    public int getMoney() {
        return money;
    }
    public Backpack getBackpack() {
        return backpack;
    }
    public int getMaxUnlockedLevel() {
        return maxUnlockedLevel; 
    }
    public void setMaxUnlockedLevel(int level) {
        maxUnlockedLevel = level; //每次解鎖新關卡時增加
    }
    //加經驗
    public void addExp(int exp) {
        this.exp += exp;
        while (this.exp >= expToLevelUp) {
            levelUp();
            this.exp -= expToLevelUp;
            expToLevelUp = (int) (expToLevelUp + 10);
        }
    }
    //升級
    public void levelUp() {
        level++;
        maxhp += 20;
        hp = maxhp;
        atk += 10;
        System.out.println(name + " 升到 " + level + "級了!");
    }
    //加錢
    public void addMoney(int money) {
        this.money += money;
    }
    //花錢
    public void spendMoney(int money) {
        if (this.money >= money) {
            this.money -= money;
        }
    }
    //背包相關方法
    // 添加物品
    public void addItem(String item, int count) {
        backpack.addItem(item, count);
    }
    // 使用物品
    public String useItem(String item) {
        backpack.useItem(item);
        switch (item) {
            case "生命藥水":
                hp += 30;
                if (hp > maxhp) hp = maxhp;
                return name + " 使用了 " + item + "，恢復了 30 點血量。";
            case "生長藥水":
                maxhp += 20;
                hp += 20;
                return name + " 使用了 " + item + "，最大血量增加了 20 點。";
            case "力量藥水":
                atk += 10;
                return name + " 使用了 " + item + "，攻擊力增加了 10 點。";
            case "爆發藥水":
                if (originalAtk == -1) originalAtk = atk;
                atk += 20;
                burstPotionActive = true;
                return name + " 使用了 " + item + "，攻擊力增加了 20 點。";
            case "抗性藥水":
                resistPotionActive = true;
                return name + " 使用了 " + item + "，暫時提高抗性。";
            case "狂怒藥水":
                if (originalAtk == -1) originalAtk = atk;
                atk *= 2;
                ragePotionActive = true;
                return name + " 使用了 " + item + "，攻擊力翻倍!，受到的傷害翻倍!";
            default:
                return name + " 使用了 " + item + "。";
        }
    }
    // 返回藥水清單
    public Map<String, Integer> getItems() {
        return backpack.getItems(); 
    }
    //攻擊
    public void attack(Monster m) {
        System.out.println(name + " 對 " + m.getName() + "造成 " + atk + " 點傷害!");
        m.gethit(atk);
        if (m.isdead()) {
            System.out.println(m.getName() + " 被打敗了!");
            System.out.println("你得到 " + m.getExp() + " 點經驗值和 " + m.getValue() + " 塊錢。");
            addExp(m.getExp());
            addMoney(m.getValue());
        } else {
            System.out.println(m.getName() + " 剩下 " + m.getHP() + " 點血量。");
        }
    }
    //死掉
    @Override
    public void die() {
        System.out.println(name + " 死掉了，遊戲結束。");
    }

    //重寫gethit方法以考慮藥水效果
    @Override
    public void gethit(int damage) {
        double finalDamage = damage;
        if (resistPotionActive) {
            finalDamage *= 0.8;
        }
        if (ragePotionActive) {
            finalDamage *= 2;
        }
        hp -= (int)Math.round(finalDamage);
        if (hp < 0) hp = 0;
    }

    //重製暫時狀態
    public void resetTemporaryEffects() {
        if (burstPotionActive || ragePotionActive) {
            if (originalAtk != -1) {
                atk = originalAtk;
                originalAtk = -1;
            }
        }
        burstPotionActive = false;
        resistPotionActive = false;
        ragePotionActive = false;
    }
}
