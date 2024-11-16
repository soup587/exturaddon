package soup587.exturaddon.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.api.ClientAPI;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaMethodOverload;
import org.figuramc.figura.mixin.gui.PlayerTabOverlayAccessor;
import org.figuramc.figura.utils.EntityUtils;
import org.figuramc.figura.utils.PlatformUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import soup587.exturaddon.ExturaPlatformUtils;
import soup587.exturaddon.ducks.ClientAPIAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Mixin(value = ClientAPI.class, remap = false)
public class ClientAPIMixin implements ClientAPIAccess {

}