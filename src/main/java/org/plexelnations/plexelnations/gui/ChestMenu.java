package org.plexelnations.plexelnations.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;

public abstract class ChestMenu {
    public final String title;
    public final int menuSize;
    public final boolean border;
    public final Material borderMaterial;

    public static final int[] PLACEABLE_SLOTS = {
            10, 11, 12, 13, 14, 15, 16,
            19, 20, 21, 22, 23, 24, 25,
            28, 29, 30, 31, 32, 33, 34
    };

    public ChestMenu(final String title, final int menuSize, final boolean border, final Material borderMaterial) {
        this.title = title;
        this.menuSize = menuSize;
        this.border = border;
        this.borderMaterial = borderMaterial;
    }

    public abstract void addItems(Menu menu, Player player);
    public abstract void setClickOptions(Menu menu);

    public Menu createMenu(Player player) {
        Menu menu = org.ipvp.canvas.type.ChestMenu.builder(menuSize)
                .title(title)
                .build();

        setMenuOptions(menu, player);
        return menu;
    }

    public void setMenuOptions(Menu menu, Player player) {
        addBorder(menu);
        addItems(menu, player);
        setClickOptions(menu);
    }

    public void displayMenu(Player player) {
        Menu menu = createMenu(player);
        menu.open(player);
    }

    public void addBorder(Menu menu) {
        BinaryMask.BinaryMaskBuilder mask = BinaryMask.builder(menu)
                .item(new ItemStack(borderMaterial));
        switch (menuSize) {
            case 6:
                mask.pattern("111111111")
                        .pattern("100000001")
                        .pattern("100000001")
                        .pattern("100000001")
                        .pattern("100000001")
                        .pattern("111111111");
                break;
            case 5:
                mask.pattern("111111111")
                        .pattern("100000001")
                        .pattern("100000001")
                        .pattern("100000001")
                        .pattern("111111111");
                break;
            case 4:
                mask.pattern("111111111")
                        .pattern("100000001")
                        .pattern("100000001")
                        .pattern("111111111");
                break;
            case 3:
                mask.pattern("111111111")
                        .pattern("100000001")
                        .pattern("111111111");
                break;
        }
        mask.build().apply(menu);
    }
}
