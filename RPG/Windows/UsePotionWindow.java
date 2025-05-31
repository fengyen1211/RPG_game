package Windows;

import Appearance_role.*;
import java.awt.*;
import java.util.Map;
import javax.swing.*;

public class UsePotionWindow extends JFrame {
    private Person player;
    private AdventureWindow adventureWindow;
    public UsePotionWindow(Person player, AdventureWindow adventureWindow) {
        this.player = player;
        this.adventureWindow = adventureWindow;
        Map<String, Integer> potionMap = player.getItems(); // 獲取玩家的藥水清單
        setTitle("使用藥水");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (potionMap.isEmpty()) {
            panel.add(new JLabel("目前沒有任何藥水"));
        } else {
            for (Map.Entry<String, Integer> entry : potionMap.entrySet()) {
                String name = entry.getKey();
                int count = entry.getValue();
                JButton btn = new JButton(name + " x " + count);
                btn.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 16));
                btn.addActionListener(e -> {
                    String result = player.useItem(name); // 使用藥水
                    JOptionPane.showMessageDialog(this, result);
                    adventureWindow.updateOutputArea(result); // 更新冒險視窗的輸出區域
                    dispose(); // 關閉使用藥水視窗
                });
                panel.add(btn);
            }
        }

        // 返回按鈕
        JButton backBtn = new JButton("返回");
        backBtn.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 16));
        backBtn.addActionListener(e -> dispose());

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backBtn);

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}