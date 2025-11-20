package top.zamc.serverannouncement.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import top.zamc.serverannouncement.ServerAnnouncement;

import java.io.IOException;
import java.nio.file.Files;

public class AnnouncementSetCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("announce")
                        .then(CommandManager.literal("add")
                            .then(CommandManager.argument("message", StringArgumentType.greedyString())
                                .executes(context -> {
                                    ServerAnnouncement.message.add(StringArgumentType.getString(context, "message"));
                                    try {
                                        if (!Files.exists(ServerAnnouncement.path))
                                            Files.createFile(ServerAnnouncement.path);
                                        Files.write(ServerAnnouncement.path, ServerAnnouncement.message);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    context.getSource().sendMessage(Text.of("已添加一条公告"));
                                    return 1;
                                    }
                                )
                            )
                        )
        );
        dispatcher.register(
                CommandManager.literal("announce")
                        .then(CommandManager.literal("reload")
                                .executes(context -> {
                                    try {
                                        ServerAnnouncement.message = Files.readAllLines(ServerAnnouncement.path);
                                        context.getSource().sendMessage(Text.of("已重载公告"));
                                        return 1;
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                        )
        );
        dispatcher.register(
                CommandManager.literal("announce")
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("index", StringArgumentType.greedyString())
                                        .executes(context -> {
                                            try {
                                                ServerAnnouncement.message.remove(Integer.parseInt(StringArgumentType.getString(context, "index"))-1);
                                                Files.write(ServerAnnouncement.path, ServerAnnouncement.message);
                                                context.getSource().sendMessage(Text.of("已删除一条公告"));
                                                return 1;
                                            } catch (IOException e) {
                                                throw new RuntimeException(e);
                                            }
                                        })
                                )
                        )
        );
        dispatcher.register(
                CommandManager.literal("announce")
                        .then(CommandManager.literal("clear")
                                .executes(context -> {
                                    ServerAnnouncement.message.clear();
                                    try {
                                        Files.write(ServerAnnouncement.path, ServerAnnouncement.message);
                                        context.getSource().sendMessage(Text.of("已清空公告"));
                                        return 0;
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                        )
        );
        dispatcher.register(
                CommandManager.literal("announce")
                        .then(CommandManager.literal("list")
                                .executes(context -> {
                                    for (int i = 0; i < ServerAnnouncement.message.size(); i++) {
                                        context.getSource().sendMessage(Text.of(String.format("[%d] %s. ", i+1, ServerAnnouncement.message.get(i))));
                                    }
                                    return 1;
                                })
                        )
        );
    }
}