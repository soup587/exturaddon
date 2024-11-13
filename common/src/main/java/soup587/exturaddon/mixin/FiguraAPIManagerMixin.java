package soup587.exturaddon.mixin;

import org.figuramc.figura.lua.FiguraAPIManager;
import org.figuramc.figura.lua.FiguraLuaRuntime;
import org.figuramc.figura.config.Configs;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import soup587.exturaddon.Exturaddon;
import soup587.exturaddon.lua.ExturaAPI;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@Mixin(value = FiguraAPIManager.class, remap = false)
public class FiguraAPIManagerMixin {

    @Shadow
    @Final
    public static Set<Class<?>> WHITELISTED_CLASSES;

    @Shadow
    @Final
    public static Map<String, Function<FiguraLuaRuntime, Object>> API_GETTERS;

    static {
        WHITELISTED_CLASSES.add(ExturaAPI.class);
        if(Exturaddon.EXPOSE_EXTURA_API.value) API_GETTERS.put("extura", r -> new ExturaAPI(r.owner)); API_GETTERS.put("extura", r -> new ExturaAPI(r.owner));
    }
}
