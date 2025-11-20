package top.zamc.serverannouncement;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.text.Text;

import java.util.List;

public class PlayerJoinServerListener {
    public PlayerJoinServerListener(){
        ServerPlayerEvents.JOIN.register(serverPlayerEntity -> {
            List<String> message = ServerAnnouncement.getMessage();
            serverPlayerEntity.sendMessage(Text.of("§b"+serverPlayerEntity.getName().getLiteralString() + " §a欢迎来到服务器！"));
            if (!message.isEmpty()) {
                message.forEach(messageLine ->{
                    messageLine = messageLine.replace("&", "§");
                    serverPlayerEntity.sendMessage(Text.of(messageLine));
                });
            }
        });
    }

}