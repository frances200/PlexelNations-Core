package org.plexelnations.plexelnations.files;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import org.plexelnations.plexelnations.models.NPlayer;
import org.plexelnations.plexelnations.models.enums.Nations;
import org.plexelnations.plexelnations.util.DataFile;

public class PlayerFile extends DataFile {

    private static PlayerFile playerFile;

    public static PlayerFile getInstance() {
        if (playerFile == null) {
            playerFile = new PlayerFile("players", "yml", "players");
        }
        return playerFile;
    }

    private PlayerFile(String fileName, String fileExtension, String defaultSection) {
        super(fileName, fileExtension, defaultSection);
    }

    public void savePlayer(NPlayer nPlayer) {
        final String playerUuid = nPlayer.getfPlayer().getId();
        saveData(getDefaultSection() + playerUuid + ".name", nPlayer.getfPlayer().getName());
        saveData(getDefaultSection() + playerUuid + ".nation", nPlayer.getNation().toString());
        saveData(getDefaultSection() + playerUuid + ".changed-nation", nPlayer.isChangedNation());
    }

    public NPlayer loadPlayer(String uuid) {
        final String nation = getData(getDefaultSection() + uuid + ".nation").toString();
        final boolean changedNation = Boolean.parseBoolean(getData(getDefaultSection() + uuid + ".changed-nation").toString());
        final FPlayer fPlayer = FPlayers.getInstance().getById(uuid);

        return new NPlayer(fPlayer, Nations.valueOf(nation), changedNation);
    }
}
