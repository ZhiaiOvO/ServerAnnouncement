package top.zamc.serverannouncement;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import top.zamc.serverannouncement.command.AnnouncementSetCommand;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ServerAnnouncement implements ModInitializer {
    public static final String MOD_ID = "serverannouncement";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static List<String> getMessage() {
        return message;
    }
    public static Path path = Paths.get("announcement.txt");
    public static List<String> message = new ArrayList<>();

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> AnnouncementSetCommand.register(dispatcher));
        new PlayerJoinServerListener();
        try {
            List<String> announcement = Files.readAllLines(path);
            if (!announcement.isEmpty()) {
                message = announcement;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
