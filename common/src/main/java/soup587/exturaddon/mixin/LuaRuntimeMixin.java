package soup587.exturaddon.mixin;


import net.minecraft.nbt.ByteArrayTag;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.lua.FiguraLuaRuntime;
import org.figuramc.figura.utils.PathUtils;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.TwoArgFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;

@Mixin(value = FiguraLuaRuntime.class, remap = false)
public abstract class LuaRuntimeMixin {

    @Shadow @Final private Map<String, Varargs> loadedScripts;
    @Shadow @Final public Avatar owner;
    @Shadow @Final protected Map<String, Varargs> scripts;

    private final TwoArgFunction addScript = new TwoArgFunction() {
        @Override
        public LuaValue call(LuaValue arg,LuaValue contents) {
            String scriptName = arg.checkjstring();

            loadedScripts.remove(scriptName);
            if(contents.isnil()){
                owner.nbt.getCompound("scripts").remove(scriptName);
                scripts.remove(scriptName);
                return LuaValue.NIL;
            }
            String scriptContent = contents.checkjstring();
            // scripts.put(scriptName,scriptContent);
            // in case you look at this, apparently scripts.put only accepts varargs?

            owner.nbt.getCompound("scripts").put(scriptName,new ByteArrayTag(scriptContent.getBytes(StandardCharsets.UTF_8)));
            return LuaValue.NIL;
        }
        @Override
        public String tojstring() {
            return "function: addScript";
        }
    };

    @Shadow public abstract void setGlobal(String name, Object obj);

    @Inject(method = "loadExtraLibraries", at = @At("TAIL"))
    private void loadExtraLibraries(CallbackInfo ci) {
        this.setGlobal("addScript",addScript);
    }

}
