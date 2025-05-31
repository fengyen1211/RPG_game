package Backpack;
import java.util.*;
public class Backpack {
    // 屬性
    private Map<String, Integer> items = new HashMap<>();
    //增加物品
    public void addItem(String item, int count) {
        items.put(item, items.getOrDefault(item, 0) + count);
    }
    //使用物品
    public String useItem(String item) {
        if (items.containsKey(item)) {
            int count = items.get(item);
            if (count > 1) {
                items.put(item, count - 1);
            } else {
                items.remove(item);
            }
            return "使用了 " + item + "。";
        } else {
            return "背包中沒有 " + item + "。";
        }
    }
    //資料
    public String showItems() {
        if (items.isEmpty()) {
            return "背包為空。";
        } else {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                sb.append(entry.getKey())
                  .append(": ")
                  .append(entry.getValue())
                  .append("\n");
            }
            return sb.toString();
        }
    }
    // 回傳物品清單
    public Map<String, Integer> getItems() {
        return items;
    }
}
