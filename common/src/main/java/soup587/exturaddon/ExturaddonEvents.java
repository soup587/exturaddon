package soup587.exturaddon;

import com.mojang.datafixers.util.Pair;
import org.figuramc.figura.entries.FiguraEvent;
import org.figuramc.figura.entries.annotations.FiguraEventPlugin;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.event.LuaEvent;

import java.util.Collection;
import java.util.Collections;

@FiguraEventPlugin
public class ExturaddonEvents implements FiguraEvent {

    @LuaWhitelist
    public static LuaEvent EXAMPLE = new LuaEvent();
    @Override
    public String getID() {
        return Exturaddon.PLUGIN_ID;
    }

    @Override
    public Collection<Pair<String, LuaEvent>> getEvents() {
        return Collections.singleton(new Pair<>("EXAMPLE", EXAMPLE));
    }
}