package top.zamc.serverannouncement.mixin;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import top.zamc.serverannouncement.ServerAnnouncement;

@Mixin(MinecraftServer.class)
public class ServerAnnounceMixin {
    @Shadow private PlayerManager playerManager;
    @Unique
    long lastAnnouncementTime = 0;
    @Unique
    final long ANNOUNCEMENT_INTERVAL = 15 * 60 * 1000; // 15分钟

    @Inject(at = @At("TAIL"), method = "tick")
    private void tick(CallbackInfo ci){
        if (System.currentTimeMillis() - lastAnnouncementTime >= ANNOUNCEMENT_INTERVAL){
            playerManager.getPlayerList().forEach(player -> {
                if (!ServerAnnouncement.getMessage().isEmpty()) {
                    ServerAnnouncement.getMessage().forEach(messageLine ->{
                        messageLine = messageLine.replace("&", "§");
                        player.sendMessage(Text.of(messageLine));
                    });
                }
            });
            lastAnnouncementTime = System.currentTimeMillis();
        }
    }
}