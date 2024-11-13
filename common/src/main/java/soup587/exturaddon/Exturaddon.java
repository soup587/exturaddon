package soup587.exturaddon;

import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.entries.FiguraAPI;
import org.figuramc.figura.entries.annotations.FiguraAPIPlugin;
import org.figuramc.figura.lua.LuaWhitelist;
import org.luaj.vm2.LuaFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@FiguraAPIPlugin
@LuaWhitelist
public class Exturaddon implements FiguraAPI {
    public static final String PLUGIN_ID = "exturaddon";
    public static final Logger LOGGER = LoggerFactory.getLogger(PLUGIN_ID);
    private Avatar owner;


    public Exturaddon(Avatar avatar) {
        this.owner = avatar;
    }

    public static void init() {

    }

    @Override
    public FiguraAPI build(Avatar avatar) {
        return new Exturaddon(avatar);
    }

    @Override
    public String getName() {
        return PLUGIN_ID;
    }

    @LuaWhitelist
    public void asyncLuaFunction(LuaFunction func) {
        if (!owner.isHost) return;
        CompletableFuture.runAsync(() -> {
            func.call();
        });
    }

    @Override
    public Collection<Class<?>> getWhitelistedClasses() {
        List<Class<?>> classesToRegister = new ArrayList<>();
        for (Class<?> aClass : EXTURADDON_CLASSES) {
            if (aClass.isAnnotationPresent(LuaWhitelist.class)) {
                classesToRegister.add(aClass);
            }
        }
        return classesToRegister;
    }

    @Override
    public Collection<Class<?>> getDocsClasses() {
        return List.of();
    }

    public static final Class<?>[] EXTURADDON_CLASSES = new Class[] {
            Exturaddon.class
    };
}
