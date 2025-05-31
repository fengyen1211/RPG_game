package Store;
import Appearance_role.Person;
import java.util.*;

public class Store {
    // 屬性
    // 商店庫存，使用Map來儲存物品名稱和數量
    private Map<String, Integer> inventory = new HashMap<>();
    private Map<String, Integer> Prices = new HashMap<>();
    private Person player;
    // 建構子，初始化商店庫存
    public Store(Person player) {
        this.player = player;
        inventory.put("生命藥水", 10);
        inventory.put("生長藥水", 5);
        inventory.put("力量藥水", 5);
        inventory.put("爆發藥水", 3);
        inventory.put("抗性藥水", 3);
        inventory.put("狂怒藥水", 1);

        Prices.put("生命藥水", 10);
        Prices.put("生長藥水", 30);
        Prices.put("力量藥水", 30);
        Prices.put("爆發藥水", 20);
        Prices.put("抗性藥水", 50);
        Prices.put("狂怒藥水", 100);
    }
    //資料
    public String showInventory() {
        if (inventory.isEmpty()) {
            return "商店庫存為空。";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
                sb.append(entry.getKey())
                .append(": ")
                .append("庫存: ")
                .append(entry.getValue())
                .append(" , 價格: ")
                .append(Prices.get(entry.getKey()))
                .append("\n");
            }
            return sb.toString();
        }
    }
    // 藥水效果
    public String PotionEffect(String item) {
        switch (item) {
            case "生命藥水":
                return "生命藥水:\n恢復30點生命值。";
            case "生長藥水":
                return "生長藥水:\n永久增加20點最大生命值。";
            case "力量藥水":
                return "力量藥水:\n永久增加10點攻擊力。";
            case "爆發藥水":
                return "爆發藥水:\n在本次冒險中增加20點攻擊力。";
            case "抗性藥水":
                return "抗性藥水:\n在本次冒險中所受到的傷害*0.8。";
            case "狂怒藥水":
                return "狂怒藥水:\n在本次冒險中對敵人傷害2倍，同時受到的傷害也為2倍。";
            default:
                return "未知的物品效果。";
        }
    }
    // 回傳物品價格
    public int getItemPrice(String item) {
        return Prices.getOrDefault(item, 0);
    }
    // 回傳物品數量
    public int getItemCount(String item) {
        return inventory.getOrDefault(item, 0);
    }
    // 刷新庫存
    public void refreshInventory() {
        inventory.put("生命藥水", 10);
        inventory.put("生長藥水", 5);
        inventory.put("力量藥水", 5);
        inventory.put("爆發藥水", 3);
        inventory.put("抗性藥水", 3);
        inventory.put("狂怒藥水", 1);
    }
    //是否有此物品
    public boolean hasItem(String item) {
        return inventory.containsKey(item) && inventory.get(item) > 0;
    }
    // 賣出物品
    public void sellItem(String item, int quantity, Person player) {
        inventory.put(item, inventory.get(item) - quantity);
        player.addItem(item, quantity);
        player.spendMoney(getItemPrice(item) * quantity);
    }
}
