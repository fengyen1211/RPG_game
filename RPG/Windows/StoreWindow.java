package Windows;

import Appearance_role.Person;
import Store.Store;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class StoreWindow extends JFrame {
    private Store store;
    private MainMenu mainMenu;
    private Person player;
    private HashMap<String, Integer> buyMap = new HashMap<>(); // 用於儲存購買的物品和數量

    public StoreWindow(Store store, MainMenu mainMenu, Person player) {
        this.player = player;
        this.store = store;
        this.mainMenu = mainMenu;

        setTitle("商店");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        MusicController.playChild3("C:\\Users\\user\\Desktop\\RPG(Feng)\\Windows\\xDeviruchi - Take some rest and eat some food!.wav"); // 播放商店資訊音樂

        // 顯示玩家的金錢
        JLabel moneyLabel = new JLabel("你有: " + player.getMoney() + "塊錢", SwingConstants.CENTER);
        moneyLabel.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 16));
        // 購買按鈕
        final int[] total = {0}; // 使用陣列來保持總價的可變性，final int[] 是為了能在匿名內部類中修改
        JButton buyButton = new JButton("購買($" + total[0] + ")");
        //藥水的六個按鈕
        JPanel Potionbutton = new JPanel();
        Potionbutton.setLayout(new GridLayout(0, 1));
        String[] Line = store.showInventory().split("\n");
        for (String line : Line) {
            String itemName = line.split(":")[0];
            JPanel rowPanel  = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton itemButton = new JButton(line);
            JButton minusButton = new JButton("-");
            JLabel quantityLabel = new JLabel("0");
            JButton plusButton = new JButton("+");

            final int[] quantity = {0}; // 使用陣列來保持數量的可變性，final int[] 是為了能在匿名內部類中修改
            buyMap.put(itemName, 0); // 初始化購買數量為0
            itemButton.addActionListener(e -> {
                // 點擊物品按鈕時的行為
                JOptionPane.showMessageDialog(this, store.PotionEffect(itemName));
            });
            //-號按鈕
            minusButton.addActionListener(e -> {
                if (quantity[0] > 0) {
                    quantity[0]--;
                    quantityLabel.setText(String.valueOf(quantity[0]));
                    total[0] -= store.getItemPrice(itemName);
                    buyMap.put(itemName, quantity[0]); // 更新購買數量
                    buyButton.setText("購買($" + total[0] + ")");
                } else {
                    quantity[0] = 0; // 確保數量不會小於0
                }
            });
            //+號按鈕
            plusButton.addActionListener(e -> {
                if (quantity[0] < store.getItemCount(itemName)) {
                    quantity[0]++;
                    quantityLabel.setText(String.valueOf(quantity[0]));
                    total[0] += store.getItemPrice(itemName);
                    buyMap.put(itemName, quantity[0]); // 更新購買數量
                    buyButton.setText("購買($" + total[0] + ")");
                } else {
                    quantity[0] = store.getItemCount(itemName); // 確保數量不會超過庫存
                }
                
            });

            rowPanel.add(itemButton);
            rowPanel.add(minusButton);
            rowPanel.add(quantityLabel);
            rowPanel.add(plusButton);
            Potionbutton.add(rowPanel);
        }
        add(Potionbutton, BorderLayout.WEST);
        add(moneyLabel, BorderLayout.NORTH);

        // 購買按鈕
        buyButton.addActionListener(e -> {
            if (total[0] > player.getMoney()) {
                JOptionPane.showMessageDialog(this, "你太窮了。");
            } else if (total[0] == 0) {
                JOptionPane.showMessageDialog(this, "你沒選= =。");
            } else {
                player.spendMoney(total[0]);
                JOptionPane.showMessageDialog(this, "購買成功！你剩: " + player.getMoney() + "塊錢。");
                moneyLabel.setText("你有: " + player.getMoney() + "塊錢");
                for(Map.Entry<String, Integer> entry : buyMap.entrySet()) {
                    String item = entry.getKey();
                    int count = entry.getValue();
                    if (count > 0 && store.hasItem(item)) {
                        store.sellItem(item, count, player); // 賣出物品
                    }
                }
                // 重置所有購買數量
                for (Component comp : Potionbutton.getComponents()) {
                    if (comp instanceof JPanel rowPanel) {
                        for (Component c : rowPanel.getComponents()) {
                            if (c instanceof JLabel label) {
                                label.setText("0");
                            }
                        }
                    }
                }
                total[0] = 0; // 重置總價
                buyButton.setText("購買($" + total[0] + ")");
            }
        });

        JButton backButton = new JButton("回主選單");
        backButton.addActionListener(e -> {
            MusicController.stopAndCloseChild3(); // 停止背景音樂
            mainMenu.setVisible(true);
            dispose();
            MusicController.resumeMain();
            store.refreshInventory(); // 刷新商店庫存
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 2));
        buttonPanel.add(buyButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
