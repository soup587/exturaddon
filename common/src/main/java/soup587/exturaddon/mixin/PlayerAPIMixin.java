package soup587.exturaddon.mixin;

import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.entity.EntityAPI;
import org.figuramc.figura.lua.api.entity.LivingEntityAPI;
import org.figuramc.figura.lua.api.entity.PlayerAPI;
import org.figuramc.figura.lua.api.world.BlockStateAPI;
import org.figuramc.figura.lua.api.world.ItemStackAPI;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = PlayerAPI.class, remap = false)
public abstract class PlayerAPIMixin extends LivingEntityAPIMixin {

    @Shadow private PlayerInfo playerInfo;

    @LuaWhitelist
    @LuaMethodDoc("player.get_destroy_speed")
    public float getDestroySpeed(BlockStateAPI block) {
        checkEntity();
        return entity.getDestroySpeed(block.blockState);
    }
    @LuaWhitelist
    @LuaMethodDoc("player.has_correct_tool_for_drops")
    public boolean hasCorrectToolForDrops(BlockStateAPI block) {
        checkEntity();
        return entity.hasCorrectToolForDrops(block.blockState);
    }
    @LuaWhitelist
    @LuaMethodDoc("player.can_be_seen_as_enemy")
    public boolean canBeSeenAsEnemy() {
        checkEntity();
        return entity.canBeSeenAsEnemy();
    }
    @LuaWhitelist
    @LuaMethodDoc("player.can_harm_player")
    public boolean canHarmPlayer(PlayerAPI otherPlayer) {
        checkEntity();
        return entity.canHarmPlayer(otherPlayer.entity);
    }
    @LuaWhitelist
    @LuaMethodDoc("player.is_using_spy_glass")
    public boolean isUsingSpyGlass() {
        checkEntity();
        return entity.isScoping();
    }
    @LuaWhitelist
    @LuaMethodDoc("player.get_projectile")
    public ItemStackAPI getProjectile(ItemStackAPI item) {
        checkEntity();
        return ItemStackAPI.verify(entity.getProjectile(item.itemStack));
    }
    @LuaWhitelist
    @LuaMethodDoc("player.get_fishing_bobber")
    public EntityAPI getFishingBobber() {
        checkEntity();
        return EntityAPI.wrap(entity.fishing);
    }


    @LuaWhitelist
    @LuaMethodDoc("player.get_tablist_display_name")
    public Object getTabListDisplayName() {
        checkEntity();
        return (checkPlayerInfo() ? playerInfo.getTabListDisplayName() : Component.empty());
    }
    @LuaWhitelist
    @LuaMethodDoc("player.get_latency")
    public int getLatency() {
        checkEntity();
        return (checkPlayerInfo() ? playerInfo.getLatency() : -1 );
    }

}
