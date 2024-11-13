package soup587.exturaddon.mixin;


import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.lua.FiguraLuaRuntime;

import org.figuramc.figura.utils.PathUtils;
import net.minecraft.nbt.ByteArrayTag;
import org.luaj.vm2.*;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseOsLib;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import soup587.exturaddon.Exturaddon;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Mixin(value = FiguraLuaRuntime.class, remap = false)
public abstract class FiguraLuaRuntimeMixin {

    @Shadow @Final public Avatar owner;
    @Shadow @Final protected Map<String, String> scripts = new HashMap<>();
    @Shadow @Final private Map<String, Varargs> loadedScripts = new HashMap<>();
    @Shadow @Final private LuaFunction getInfoFunction;

    @Shadow @Final private Globals userGlobals;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lorg/luaj/vm2/compiler/LuaC;install(Lorg/luaj/vm2/Globals;)V"))
    public void FiguraLuaRuntime(Avatar avatar, Map scripts, CallbackInfo ci) {
        if(avatar.isHost){
            userGlobals.set("isHost", LuaValue.TRUE);
            if(Exturaddon.EXPOSE_SENSITIVE_LIBRARIES.value){
                userGlobals.load(new JseIoLib());
                userGlobals.load(new JseOsLib());
                userGlobals.load(new CoroutineLib());
                userGlobals.rawset("expose_sensitive_libraries", LuaValue.TRUE);
            }
        }else{
            userGlobals.set("isHost", LuaValue.FALSE);
        }
    }

    private final OneArgFunction getScripts = new OneArgFunction() {
        @Override
        public LuaValue call(LuaValue path) {
            // iterate over all script names and add them if their name starts with the path query

            LuaTable table = new LuaTable();
            String _path = path.isnil() ? "" : path.checkjstring();
            if(_path.isEmpty()){
                for (String s : scripts.keySet()) {
                    table.set(s, scripts.get(s));
                }
            }else{
                for (String s : scripts.keySet()) {
                    if(!s.startsWith(_path)) continue;
                    table.set(s, scripts.get(s));
                }
            }

            return table;
        }
        @Override
        public String tojstring() {
            return "function: getScripts";
        }
    };


    private final OneArgFunction getScript = new OneArgFunction() {
        @Override
        public LuaValue call(LuaValue arg) {
            Path path = PathUtils.getPath(arg.checkstring(1));
            Path dir = PathUtils.getWorkingDirectory(getInfoFunction);
            return LuaValue.valueOf(String.valueOf(scripts.get(PathUtils.computeSafeString(PathUtils.isAbsolute(path) ? path : dir.resolve(path)))));
        }
        @Override
        public String tojstring() {
            return "function: getscript";
        }
    };
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
            scripts.put(scriptName,scriptContent);

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
        this.setGlobal("add_script", addScript);
        this.setGlobal("addScript", addScript);
        this.setGlobal("get_scripts", getScripts);
        this.setGlobal("getScripts", getScripts);
        this.setGlobal("get_script", getScript);
        this.setGlobal("getScript", getScript);
    }

}
