package org.plexelnations.plexelnations.tasks;

import org.plexelnations.plexelnations.models.NPlayer;
import org.plexelnations.plexelnations.models.Town;
import org.plexelnations.plexelnations.util.Logger;

public class AutosaveTask implements Runnable {

    private static final String LOG_AUTOSAVE = "Autosaving player data...";
    private static final String LOG_AUTOSAVE_COMPLETE = "Autosaving completed";
    private static final String LOG_AUTOSAVE_ERROR = "An error occurred while autosaving, this could corrupt data!";

    @Override
    public void run() {
        try {
            Logger.getInstance().warn(LOG_AUTOSAVE);
            NPlayer.forceSave();
            Town.forceSave();
            Logger.getInstance().success(LOG_AUTOSAVE_COMPLETE);
        } catch (Exception e) {
            Logger.getInstance().error(LOG_AUTOSAVE_ERROR);
        }
    }
}
