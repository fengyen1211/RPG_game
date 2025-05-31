import Adventure.Adventure;
import Appearance_role.*;
import Store.*;
import Windows.MainMenu;
public class test {
    public static void main(String[] args) {
        Person player = new Person();
        MainMenu mainMenu = new MainMenu(
            new Adventure(player, new Monster()),
            player,
            new Store(player)
        );
        mainMenu.setVisible(true);
    }
}
