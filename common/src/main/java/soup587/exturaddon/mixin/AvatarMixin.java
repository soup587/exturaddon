package soup587.exturaddon.mixin;

import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.lua.api.entity.EntityAPI;
import org.figuramc.figura.math.vector.FiguraVec3;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.Varargs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import soup587.exturaddon.ducks.AvatarAccess;

@Mixin(value = Avatar.class, remap = false)
public abstract class AvatarMixin implements AvatarAccess {

    @Shadow public boolean loaded;

    @Shadow public abstract @Nullable Varargs run(Object toRun, Avatar.Instructions limit, Object... args);

    @Shadow @Final public Avatar.Instructions tick;

    @Unique
    public void customEvent(String event) {
        if (loaded) run(event,tick);
    }

    @Unique
    public void exturaddon$attackEvent(String sourceType, EntityAPI<?> loser, FiguraVec3 sourcePosition) {
        if (loaded) run("ATTACK", tick, sourceType, loser, sourcePosition);
    }

}
