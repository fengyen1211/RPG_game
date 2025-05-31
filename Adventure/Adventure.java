package Adventure;
import Appearance_role.*;
public class Adventure {
    //屬性
    protected Person player;
    protected Monster monster;
    //建構子
    public Adventure(Person player, Monster monster) {
        this.player = player;
        this.monster = monster;
    }
    //資料
    public Person getPlayer() {
        return player;
    }
    public Monster getMonster() {
        return monster;
    }
    //設定玩家和怪物
    public void setPlayer(Person player) {
        this.player = player;
    }
    public void setMonster(Monster monster) {
        this.monster = monster;
    }
    //開始遊戲
    public void start(){
        System.out.println("冒險開始！你遇到了 " + monster.getName() + "！\n");
        System.out.println("怪物的資訊：\n" + monster.getinfo());
        System.out.println("你的資訊：\n" + player.getinfo());
    }
    public void fight() {
        // 模擬戰鬥過程
        if (!player.isdead() && !monster.isdead()) {
            player.attack(monster);
            if (!monster.isdead()) {
                monster.attack(player);
            }
        }
    }

    //怪物死亡
    public boolean monsterDie() {
        return monster.isdead();
    }
    //玩家死亡
    public boolean playerDie() {
        return player.isdead();
    }

    //怪物復活
    public void monsterRevive() {
        monster.revive();
    }
    
    //玩家可挑戰最高關卡
    public int getMaxUnlockedLevel() {
        return player.getMaxUnlockedLevel();
    }
    //設定玩家可挑戰最高關卡
    public void setMaxUnlockedLevel(int level) {
        player.setMaxUnlockedLevel(level);
    }
}
