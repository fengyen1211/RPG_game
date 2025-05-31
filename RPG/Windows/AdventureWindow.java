package Windows;
import Adventure.Adventure;
import Appearance_role.Monster;
import java.awt.*;
import java.io.PrintStream;
import javax.sound.sampled.*;
import javax.swing.*;

public class AdventureWindow extends JFrame {
    private Adventure adventure;
    private MainMenu mainMenu;
    private JTextArea outputArea;
    private Clip bgmclip;
    private JPanel levelPanel; // 用於顯示關卡按鈕
    private JPanel panelWithBack; // 用於包含關卡按鈕和返回按鈕的面板
    private JButton backButton; // 返回按鈕

    public AdventureWindow(Adventure adventure, MainMenu mainMenu) {
        this.adventure = adventure;
        this.mainMenu = mainMenu;

        setTitle("冒險");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        setLayout(new BorderLayout());  
        MusicController.playChild1("C:\\Users\\user\\Desktop\\RPG(Feng)\\Windows\\xDeviruchi - And The Journey Begins.wav");

        JLabel label = new JLabel("請選擇關卡", SwingConstants.CENTER);
        add(label, BorderLayout.NORTH);

        // 返回按鈕
        backButton = new JButton("返回");
        backButton.addActionListener(e -> {
            adventure.getPlayer().resetTemporaryEffects(); // 返回主選單前重置玩家的暫時效果
            MusicController.stopAndCloseChild1(); // 停止背景音樂
            mainMenu.setVisible(true);
            dispose(); // 關閉視窗
            MusicController.resumeMain();
        });

        // 中間先放五個關卡按鈕
        levelPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        for (int i = 1; i <= 5; i++) {
            JButton levelButton = new JButton("關卡 " + i);
            int level = i;
            levelButton.addActionListener(e -> {
                // 檢查關卡是否已解鎖
                if(level <= adventure.getMaxUnlockedLevel()){
                    // 點選後移除levelPanel，顯示outputArea
                    remove(panelWithBack); // 移除關卡按鈕面板
                    showAdventureArea(level); // 顯示冒險區域
                    revalidate();
                    repaint();
                } else {
                    JOptionPane.showMessageDialog(this, "關卡 " + level + " 尚未解鎖。");
                }
            });
            levelPanel.add(levelButton);
        }

        panelWithBack = new JPanel(new BorderLayout());
        panelWithBack.add(levelPanel, BorderLayout.NORTH);
        panelWithBack.add(backButton, BorderLayout.SOUTH);
        add(panelWithBack, BorderLayout.CENTER);

        setVisible(true);
    }
    // 顯示冒險區域，包含輸出區域和按鈕
    private void showAdventureArea(int level) {
        // 根據關卡設定不同怪物
        Monster monster;
        switch (level) {
            case 1:
                monster = new Monster("史萊姆", 5, 50, 50, 25, 12, 80, 50);
                break;
            case 2:
                monster = new Monster("哥布林", 8, 75, 75, 30, 14, 96, 60);
                break;
            case 3:
                monster = new Monster("狼人", 11, 113, 113, 36, 17, 115, 72);
                break;
            case 4:
                monster = new Monster("食人魔", 23, 225, 225, 43, 21, 138, 86);
                break;
            case 5:
                monster = new Monster("火龍", 45, 450, 450, 52, 25, 166, 104);
                break;
            default:
                monster = new Monster("史萊姆", 5, 50, 50, 25, 10, 80, 50);
        }
        adventure.setMonster(monster);
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Microsoft JhengHei", Font.PLAIN, 16)); // 設置字體
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton useButton = new JButton("使用藥水");
        JButton attackButton = new JButton("開始");
        backButton.setText("逃跑/返回");

        final boolean[] pressAttack = {false}; // 按鈕是否被按下的標記
        attackButton.addActionListener(e -> {
            if(!pressAttack[0]) {
                outputArea.setText(""); // 清空舊輸出
            }
            // 使用重導 System.out 印出到 outputArea
            PrintStream printStream = new PrintStream(new OutputToTextArea(outputArea));
            PrintStream originalOut = System.out;
            System.setOut(printStream);
            // 如果按鈕未被按下，則開始冒險；如果已被按下，則執行攻擊
            if(!pressAttack[0]) {
                adventure.start(); // 執行冒險
                attackButton.setText("攻擊");
            } else{
                adventure.fight(); // 執行攻擊
            }
            pressAttack[0] = true; // 標記按鈕已被按下
            if(adventure.monsterDie() || adventure.playerDie()) {
                adventure.getPlayer().resetTemporaryEffects(); // 如果怪物或玩家死亡，重置玩家的暫時效果
                if(adventure.monsterDie()) adventure.setMaxUnlockedLevel(level + 1); // 如果怪物死了，解鎖下一關
                attackButton.setText("重新挑戰");
                pressAttack[0] = false; // 重置按鈕狀態
                adventure.monsterRevive(); // 如果怪物死了，復活它
            }
            System.setOut(originalOut); // 還原 System.out
        });
        // 使用藥水按鈕
        useButton.addActionListener(e -> {
            UsePotionWindow usePotionWindow = new UsePotionWindow(adventure.getPlayer(), this); // 打開使用藥水視窗
            usePotionWindow.setVisible(true);
        });

        buttonPanel.add(useButton);
        buttonPanel.add(attackButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
    // 更新輸出區域的內容
    public void updateOutputArea(String text) {
        outputArea.append(text + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}