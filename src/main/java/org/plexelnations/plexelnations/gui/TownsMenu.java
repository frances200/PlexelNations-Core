package org.plexelnations.plexelnations.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.ipvp.canvas.Menu;
import org.plexelnations.plexelnations.models.NPlayer;
import org.plexelnations.plexelnations.models.Town;
import org.plexelnations.plexelnations.models.enums.Nations;

import java.util.ArrayList;
import java.util.List;

public class TownsMenu extends ChestMenu {

    public TownsMenu() {
        super(ChatColor.BOLD + "Towns", 4, false, Material.BLACK_STAINED_GLASS_PANE);
    }

    @Override
    public void addItems(Menu menu, Player player) {
        final NPlayer nPlayer = NPlayer.getNPlayer(player);

        List<Town> towns = Town.getTowns();
        for (int i = 0; i < towns.size(); i++) {
            final Town town = towns.get(i);

            List<String> lore = generateLore(town, nPlayer);

            ItemStack townItem = new ItemStack(Material.COARSE_DIRT, 1);
            ItemMeta itemMeta = townItem.getItemMeta();
            itemMeta.setDisplayName(ChatColor.RED + town.getDisplayName());
            itemMeta.setLore(lore);
            townItem.setItemMeta(itemMeta);
            menu.getSlot(ChestMenu.PLACEABLE_SLOTS[i]).setItem(townItem);
        }
    }

    private List<String> generateLore(Town town, NPlayer nPlayer) {
        final List<String> lore = new ArrayList<>();

        lore.add(ChatColor.DARK_GRAY + getNationSubtitle(town, nPlayer));
        lore.add(" ");
        lore.add(ChatColor.GRAY + "Owned by " + town.getNation().getNationColor() + town.getNation().getNationName());
        lore.add(ChatColor.GRAY + "Has " + ChatColor.GOLD + town.getBank() + ChatColor.GRAY + " in the bank");
        lore.add(ChatColor.GRAY + "Current tier is " + ChatColor.GOLD + town.getTier());
        lore.add(" ");
        lore.add(ChatColor.BOLD + "" + ChatColor.GRAY + "---------------");
        lore.add(ChatColor.DARK_GRAY + "" + ChatColor.UNDERLINE + "Click for town options");

        return lore;
    }

    private String getNationSubtitle(Town town, NPlayer nPlayer) {
        if (town.getNation() == Nations.NONE) {
            return "This town is unclaimed";
        }
        if (town.getNation() == nPlayer.getNation()) {
            return "This town is owned by " + ChatColor.BLUE + "your" + ChatColor.DARK_GRAY + " nation";
        }
        return "This town is owned by " + ChatColor.RED + "another" + ChatColor.DARK_GRAY + " nation";
    }

    @Override
    public void setClickOptions(Menu menu) {

    }
}
