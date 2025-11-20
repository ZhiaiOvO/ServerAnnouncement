package top.zamc.serverannouncement.luckperms;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.node.types.PermissionNode;

public class CreatePermissionNodeUtil {
    public static void createPermissionNode(LuckPerms luckPerms) {
        PermissionNode permissionNode = PermissionNode.builder("announcement.admin")
                .value(true)
                .build();
    }
}