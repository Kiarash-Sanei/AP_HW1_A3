import controller.LoginMenu;
import view.Menus;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginMenu.run(scanner, Menus.LoginMenu);
    }
}