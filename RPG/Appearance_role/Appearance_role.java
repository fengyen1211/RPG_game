package Appearance_role;
public abstract class Appearance_role {
    //屬性
    protected String name;
    protected int hp;
    protected int maxhp;
    protected int atk;
    //建構子
    public Appearance_role() {
        name = "aa";
        atk = 1;
        hp = 1;
        maxhp = 1;
    }
    //被打扣血
    public void gethit(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }
    // 判斷是否死亡
    public boolean isdead(){
        return hp <= 0;
    }
    //死掉
    abstract public void die();
}