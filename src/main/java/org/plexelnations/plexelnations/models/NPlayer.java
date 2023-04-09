package org.plexelnations.plexelnations.models;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import org.bukkit.entity.Player;
import org.plexelnations.plexelnations.files.PlayerFile;
import org.plexelnations.plexelnations.models.enums.Nations;

import java.util.HashMap;

public class NPlayer {
    private final FPlayer fPlayer;
    private Nations nation;
    private boolean changedNation;

    public static HashMap<String, NPlayer> nPlayers = new HashMap<>();

    public NPlayer(FPlayer fPlayer, Nations nation, boolean changedNation) {
        this.fPlayer = fPlayer;
        this.nation = nation;
        this.changedNation = changedNation;
    }

    public FPlayer getfPlayer() {
        return fPlayer;
    }

    public Nations getNation() {
        return nation;
    }

    public boolean isChangedNation() {
        return changedNation;
    }

    public void setNation(Nations nation) {
        this.nation = nation;
    }

    public void setChangedNation(boolean changedNation) {
        this.changedNation = changedNation;
    }

    /*
     * --------------------
     * NPlayer Management
     * --------------------
     */

    public static NPlayer getNPlayer(Player player) {
        final NPlayer nPlayer = nPlayers.get(player.getUniqueId().toString());

        if (nPlayer == null)
            return generateNPlayer(player);

        return nPlayer;
    }

    public static NPlayer getNPlayer(String uuid) {
        final NPlayer nPlayer = nPlayers.get(uuid);

        if (nPlayer == null)
            return generateNPlayer(uuid);

        return nPlayer;
    }

    public static NPlayer generateNPlayer(Player player) {
        final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        final NPlayer nPlayer = new NPlayer(fPlayer, Nations.NONE, false);

        nPlayers.put(player.getUniqueId().toString(), nPlayer);
        return nPlayer;
    }

    public static NPlayer generateNPlayer(String uuid) {
        final FPlayer fPlayer = FPlayers.getInstance().getById(uuid);
        final NPlayer nPlayer = new NPlayer(fPlayer, Nations.NONE, false);

        nPlayers.put(uuid, nPlayer);
        return nPlayer;
    }

    public static void load() {
        final PlayerFile playerFile = PlayerFile.getInstance();
        for (String uuid : playerFile.getStringList(playerFile.getDefaultSection())) {
            nPlayers.put(uuid, playerFile.loadPlayer(uuid));
        }
    }

    public static void unload() {
        nPlayers.clear();
    }

    public static void forceSave() {
        final PlayerFile playerFile = PlayerFile.getInstance();
        for (NPlayer nPlayer : nPlayers.values()) {
            playerFile.savePlayer(nPlayer);
        }
        playerFile.flush();
    }
}
