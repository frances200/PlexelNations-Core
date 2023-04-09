package org.plexelnations.plexelnations;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.event.FPlayerJoinEvent;
import com.massivecraft.factions.event.FactionAttemptCreateEvent;
import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.perms.Relation;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.plexelnations.plexelnations.models.NPlayer;
import org.plexelnations.plexelnations.models.enums.Message;
import org.plexelnations.plexelnations.models.enums.Nations;

public class NEvents implements Listener {

    @EventHandler
    public void onFPlayerJoin(FPlayerJoinEvent event) {
        if (event.getReason() == FPlayerJoinEvent.PlayerJoinReason.CREATE)
            return;

        final Faction faction = event.getFaction();
        final FPlayer fPlayer = event.getfPlayer();

        if (faction.isWarZone() || faction.isSafeZone() || faction.isWilderness())
            return;

        final NPlayer nPlayerJoiner = NPlayer.getNPlayer(fPlayer.getPlayer());
        final NPlayer nPlayerOwner = NPlayer.getNPlayer(faction.getFPlayerAdmin().getPlayer());

        if (nPlayerOwner.getNation() != nPlayerJoiner.getNation()) {
            fPlayer.getPlayer().sendMessage(Message.INCORRECT_FACTION_NATION.format());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionCreate(FactionAttemptCreateEvent event) {
        final FPlayer fPlayer = event.getFPlayer();
        final NPlayer nPlayer = NPlayer.getNPlayer(fPlayer.getPlayer());

        if (nPlayer.getNation() == Nations.NONE || nPlayer.getNation() == null) {
            fPlayer.getPlayer().sendMessage(Message.JOIN_NATION_FIRST.format());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        final Faction faction = event.getFaction();
        final FPlayer fPlayer = event.getFPlayer();
        final NPlayer nPlayer = NPlayer.getNPlayer(fPlayer.getPlayer());

        Factions.getInstance().getAllFactions().forEach(faction1 -> {
            if (faction1.getId().equalsIgnoreCase(faction.getId())) {
                fPlayer.getPlayer().sendMessage("Faction " + faction1.getTag() + " has the same ID");
                return;
            }

            if (faction1.isWarZone() || faction1.isSafeZone() || faction1.isWilderness()) {
                fPlayer.getPlayer().sendMessage("Faction " + faction1.getTag() + " is a system faction");
                return;
            }

            final NPlayer nPlayerAdmin = NPlayer.getNPlayer(faction1.getFPlayerAdmin().getPlayer());
            if (nPlayer.getNation() == nPlayerAdmin.getNation()) {
                fPlayer.getPlayer().sendMessage("Faction " + faction1.getTag() + " is from the same team, making them ally...");
                faction.setRelationWish(faction1, Relation.ALLY);
                faction1.setRelationWish(faction, Relation.ALLY);
                return;
            }

            fPlayer.getPlayer().sendMessage("Faction " + faction1.getTag() + " is from the same team, making them enemy...");
            faction.setRelationWish(faction1, Relation.ENEMY);
            faction1.setRelationWish(faction, Relation.ENEMY);
        });
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if (!(event.getEntity() instanceof Player))
            return;

        final Player player = (Player) event.getEntity();
        final Player damager = getPlayerFromEntity(event.getDamager());
        if (damager == null)
            return;
        if (damager.getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString()))
            return;

        NPlayer nDamager = NPlayer.getNPlayer(damager);
        NPlayer nPlayer = NPlayer.getNPlayer(player);
        if (nDamager.getNation() == Nations.NONE) {
            event.setCancelled(true);
            return;
        }
        if (nDamager.getNation() == nPlayer.getNation()) {
            damager.sendMessage(Message.FRIENDLY_FIRE.format());
            event.setCancelled(true);
        }
    }

    private Player getPlayerFromEntity(Entity entity) {
        Player damager = null;
        if (entity instanceof Player)
            damager = (Player) entity;

        if (entity instanceof Arrow)
            damager = getShooter(entity);

        return damager;
    }

    private Player getShooter(Entity entity) {
        if (!(entity instanceof Arrow))
            return null;

        final Arrow arrow = (Arrow) entity;
        if (!(arrow.getShooter() instanceof Player))
            return null;

        return (Player) arrow.getShooter();
    }
}
