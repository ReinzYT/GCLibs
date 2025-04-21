package net.gammacube.gCLibs.PacketHandler;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerDestroyEntities;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnLivingEntity;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class Hologram {
    private static final Random RANDOM = new Random();
    private final int entityId = RANDOM.nextInt(200000) + 100000;
    private final UUID uuid = UUID.randomUUID();
    private final Location location;
    private final String text;

    public Hologram(Location location, String text) {
        this.location = location;
        this.text = text;
    }

    public void show(Player player) {
        Vector3d v3d = new Vector3d(location.getX(), location.getY(), location.getZ());

        List<EntityData> entityData = new ArrayList<>();
        entityData.add(new EntityData(0, EntityDataTypes.BOOLEAN, 0x20));
        entityData.add(new EntityData(2, EntityDataTypes.OPTIONAL_COMPONENT, new TextComponent("&dHello World")));

        entityData.add(new EntityData(3, EntityDataTypes.BOOLEAN, true));

        entityData.add(new EntityData(5, EntityDataTypes.BOOLEAN, true));

        byte armorStandFlags = 0x01; // small = 0x01
        entityData.add(new EntityData(15, EntityDataTypes.BYTE, armorStandFlags));

        // Spawn fake Armor Stand
        WrapperPlayServerSpawnLivingEntity spawnPacket = new WrapperPlayServerSpawnLivingEntity(
                entityId,
                uuid,
                EntityTypes.ARMOR_STAND,
                v3d,
                0,
                0,
                0,
                new Vector3d(0,0,0),
                entityData
        );

        PacketEvents.getAPI().getPlayerManager().sendPacket(player, spawnPacket);
    }

    public void hide(Player player) {
        WrapperPlayServerDestroyEntities destroyPacket = new WrapperPlayServerDestroyEntities(entityId);
        PacketEvents.getAPI().getPlayerManager().sendPacket(player, destroyPacket);
    }
}
