package soup587.exturaddon.mixin;

import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.avatar.AvatarManager;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.event.LuaEvent;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LuaEvent.class, remap = false)
public class LuaEventMixin {

    @LuaWhitelist
    @LuaMethodDoc("event.run")
    public LuaEvent run(Object... args){
        Avatar avatar = AvatarManager.getAvatarForPlayer(FiguraMod.getLocalPlayerUUID());
        if (avatar != null) {
            if (args != null) avatar.run(this,avatar.tick,args);
            else    avatar.run(this,avatar.tick);
        }
        return ((LuaEvent)(Object) this);
    }

}
