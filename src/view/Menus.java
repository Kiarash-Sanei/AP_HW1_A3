package view;

public enum Menus {
    LoginMenu,
    MainMenu,
    ProfileMenu,
    ShopMenu,
    GameMenu;

    @Override
    public String toString() {
        switch (this) {
            case LoginMenu:
                return "login menu";
            case MainMenu:
                return "main menu";
            case ProfileMenu:
                return "profile menu";
            case ShopMenu:
                return "shop menu";
            case GameMenu:
                return "game menu";
            default:
                return null;
        }
    }
}
