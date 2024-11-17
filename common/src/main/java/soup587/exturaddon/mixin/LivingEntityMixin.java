package soup587.exturaddon.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.avatar.AvatarManager;
import org.figuramc.figura.lua.api.entity.EntityAPI;
import org.figuramc.figura.lua.api.event.LuaEvent;
import org.figuramc.figura.math.vector.FiguraVec3;
import org.luaj.vm2.Varargs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import soup587.exturaddon.ducks.AvatarAccess;
import soup587.exturaddon.ducks.EventsAPIAccess;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> variant, Level world) {
        super(variant, world);
    }

    @Inject(at = @At("TAIL"), method = "handleDamageEvent")
    private void handleDamageEvent(DamageSource source, CallbackInfo ci) {
        Avatar avatar = AvatarManager.getAvatar(source.getEntity());
        if (avatar != null) {
            ((AvatarAccess) avatar).exturaddon$attackEvent(
                    source.typeHolder().unwrapKey().get().location().toString(),
                    EntityAPI.wrap(this),
                    source.getSourcePosition() != null ? FiguraVec3.fromVec3(source.getSourcePosition()) : null
            );
        }
    }
}
