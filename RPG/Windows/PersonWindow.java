package Windows;

import Appearance_role.*;
import java.awt.*;
import javax.swing.*;


public class PersonWindow extends JFrame {
    private Person person;
    private MainMenu mainMenu;
    private JTextArea infoArea;

    public PersonWindow(Person person, MainMenu mainMenu) {
        this.person = person;
        this.mainMenu = mainMenu;

        setTitle("角色的資訊");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        MusicController.playChild2("C:\\Users\\user\\Desktop\\RPG(Feng)\\Windows\\xDeviruchi - Exploring The Unknown.wav"); // 播放角色資訊音樂
        infoArea = new JTextArea(person.getinfo());
        infoArea.setEditable(false);        // 設置為不可編輯
        infoArea.setLineWrap(true);      // 啟用自動換行
        infoArea.setWrapStyleWord(true); // 斷詞換行
        add(new JScrollPane(infoArea));       // 使用滾動面板包裹 JTextArea

        // 下方按鈕區域
        JButton refreshButton = new JButton("重新載入");
        refreshButton.addActionListener(e -> infoArea.setText(person.getinfo()));

        JButton backButton = new JButton("回主選單");
        backButton.addActionListener(e -> {
            MusicController.stopAndCloseChild2(); // 停止背景音樂
            mainMenu.setVisible(true);
            dispose();
            MusicController.resumeMain();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
