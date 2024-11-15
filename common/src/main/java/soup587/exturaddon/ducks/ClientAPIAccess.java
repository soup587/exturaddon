package soup587.exturaddon.ducks;

import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaMethodOverload;
import org.figuramc.figura.utils.PlatformUtils;
import soup587.exturaddon.ExturaPlatformUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface ClientAPIAccess {

    @LuaWhitelist
    @LuaMethodDoc("client.get_mod_name")
    static String getModName(@LuaNotNil String id) {
        return PlatformUtils.isModLoaded(id) ? ExturaPlatformUtils.getModName(id) : "";
    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = @LuaMethodOverload(
                    argumentTypes = String.class,
                    argumentNames = "modID"
            ),
            value = "host.get_mod_version"
    )
    static String getModVersion(String id) {
        return PlatformUtils.isModLoaded(id) ? PlatformUtils.getModVersion(id) : "" ;
    }

    @LuaWhitelist
    @LuaMethodDoc("client.get_uuid_from_player")
    private static String getUUIDFromPlayer(String name) {
        return FiguraMod.playerNameToUUID(name).toString();
    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(argumentTypes = {String.class, String.class}, argumentNames = {"string", "expression"})
            },
            value = "client.regex_match"
    )
    static Object[] regexMatch(@LuaNotNil String str, @LuaNotNil String expression) {


        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(str);

        List<String> matched = new ArrayList<>();

        while (matcher.find()){
            matched.add(matcher.group());
        }
        return matched.toArray();
    }
    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(argumentTypes = {String.class, String.class}, argumentNames = {"string", "expression"})
            },
            value = "client.regex_find"
    )
    static Boolean regexFind(@LuaNotNil String str, @LuaNotNil String expression) {
        return Pattern.compile(expression).matcher(str).find();

    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(argumentTypes = {String.class, String.class, String.class}, argumentNames = {"string", "expression"})
            },
            value = "client.regex_replace"
    )
    static String regexReplace(@LuaNotNil String str, @LuaNotNil String replaceWith, @LuaNotNil String expression) {

        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(str);

        return matcher.replaceAll(replaceWith);
    }

}
