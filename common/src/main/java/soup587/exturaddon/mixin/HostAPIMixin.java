package soup587.exturaddon.mixin;

import net.minecraft.client.Minecraft;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.utils.LuaUtils;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.figuramc.figura.lua.api.HostAPI;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = HostAPI.class, remap = false)
public class HostAPIMixin {

    @Shadow @Final private Minecraft minecraft;

    @Unique
    @LuaWhitelist
    @LuaMethodDoc("host.set_velocity")
    public void setVelocity(Object x, Double y, Double z) {
        this.minecraft.player.setDeltaMovement(LuaUtils.parseVec3("player_setVelocity", x, y, z).asVec3());
    }
}
