package soup587.exturaddon.mixin;

import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.avatar.AvatarManager;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.event.EventsAPI;
import org.figuramc.figura.lua.api.event.LuaEvent;
import org.figuramc.figura.lua.docs.LuaFieldDoc;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.luaj.vm2.LuaError;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import soup587.exturaddon.ducks.AvatarAccess;
import soup587.exturaddon.ducks.EventsAPIAccess;

import java.util.Locale;
import java.util.Map;

@Mixin(value = EventsAPI.class, remap = false)
public abstract class EventsAPIMixin implements EventsAPIAccess {

    @Shadow @Final private Map<String, LuaEvent> events;

    @Unique
    @LuaWhitelist
    @LuaFieldDoc("events.attack")
    public LuaEvent ATTACK;

    @Inject(method = "<init>", at = @At("RETURN"))
    void a(CallbackInfo ci) {
        ATTACK = new LuaEvent();
        events.put("ATTACK", ATTACK);
    }

    @LuaWhitelist
    @LuaMethodDoc("events.new_event")
    public LuaEvent newEvent(@LuaNotNil String name) {
        name = name.toUpperCase(Locale.US);
        LuaEvent ev = events.get(name);
        if(ev != null) throw new LuaError("Event \"" + name + "\" already exists!");
        LuaEvent event = new LuaEvent();
        events.put(name, event);
        return event;
    }

    @LuaWhitelist
    @LuaMethodDoc("events.fire_event")
    public void fireEvent(@LuaNotNil String name) {
        Avatar avatar = AvatarManager.getAvatarForPlayer(FiguraMod.getLocalPlayerUUID());
        if (avatar != null) {
            ((AvatarAccess) avatar).exturaddon$customEvent(name);
        }
    }
}
