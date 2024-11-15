package soup587.exturaddon.mixin;

import net.minecraft.nbt.CompoundTag;
import org.figuramc.figura.avatar.Avatar;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.Varargs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import soup587.exturaddon.ducks.AvatarAccess;

@Mixin(value = Avatar.class, remap = false)
public abstract class AvatarMixin implements AvatarAccess {

    @Shadow public boolean loaded;

    @Shadow public abstract @Nullable Varargs run(Object toRun, Avatar.Instructions limit, Object... args);

    @Shadow @Final public Avatar.Instructions tick;

    public void customEvent(String event) {
        if (loaded) run(event,tick);
    }

}
