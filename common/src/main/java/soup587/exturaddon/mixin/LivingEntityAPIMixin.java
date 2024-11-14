package soup587.exturaddon.mixin;


import org.figuramc.figura.lua.api.entity.LivingEntityAPI;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = LivingEntityAPI.class, remap = false)
public abstract class LivingEntityAPIMixin extends EntityAPIMixin {


}
