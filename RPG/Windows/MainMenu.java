package Windows;
import Adventure.Adventure;
import Appearance_role.*;
import Store.*;
import java.awt.*;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
public class MainMenu extends JFrame {
    private Adventure adventure;
    private Person person;
    private Store store;


    public MainMenu(Adventure adventure, Person person, Store store) {
        this.adventure = adventure;
        this.person = person;
        this.store = store;
        try {
            // 套用 Windows Classic 樣式
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());  // 設置 Windows Classic 樣式
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("無法套用 Windows Classic 樣式，使用預設樣式。");
        }
        setUIFont(new Font("微軟正黑體", Font.PLAIN, 16)); // 設置全局字體
        setTitle("主選單");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 設置關閉操作
        setLayout(null);  // 設置布局
        setLocationRelativeTo(null);  // 設置視窗在螢幕中央
        setBackgroundRecursively(getContentPane(), new Color(255, 255, 204)); // 設置背景顏色
        
        // 冒險、角色、商店按鈕
        JButton btnA = new JButton("冒險");
        JButton btnB = new JButton("角色");
        JButton btnC = new JButton("商店");

        // 按鈕大小位置
        btnA.setBounds(150, 40, 100, 40);
        btnB.setBounds(150, 100, 100, 40);
        btnC.setBounds(150, 160, 100, 40);

        add(btnA);
        add(btnB);
        add(btnC);

        btnA.addActionListener(e -> {
            MusicController.stopMain();
            new AdventureWindow(adventure, this);
            setVisible(false);
                       
        });

        btnB.addActionListener(e -> {
            MusicController.stopMain();
            new PersonWindow(person, this);
            setVisible(false);
        });

        btnC.addActionListener(e -> {
            MusicController.stopMain();
            new StoreWindow(store, this, person);
            setVisible(false);
        });

        setVisible(true);
        MusicController.playMain("C:\\Users\\user\\Desktop\\RPG(Feng)\\Windows\\xDeviruchi - Title Theme.wav");
    }
    
    // 設置全局字體
    public static void setUIFont(Font font) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, new FontUIResource(font));
            }
        }
    }
    // 遞歸設置背景顏色
    public static void setBackgroundRecursively(Component comp, Color color) {
        if (comp instanceof JComponent) {
            comp.setBackground(color);
            ((JComponent) comp).setOpaque(true);
        }

        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                setBackgroundRecursively(child, color);
            }
        }
    }

}

