package soup587.exturaddon.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import org.figuramc.figura.lua.FiguraLuaPrinter;
import org.figuramc.figura.lua.LuaTypeManager;
import org.figuramc.figura.utils.TextUtils;
import org.luaj.vm2.LuaString;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static org.figuramc.figura.lua.FiguraLuaPrinter.df;

@Mixin(value = FiguraLuaPrinter.class, remap = false)
public abstract class FiguraLuaPrinterMixin {


    @Shadow private static Style getTypeColor(LuaValue value) {
        throw new AssertionError();
    }

    @Shadow private static Component userdataToText(LuaTypeManager typeManager, LuaValue value, int depth, int indent, boolean hasTooltip) {
        throw new AssertionError();
    }

    @Shadow private static MutableComponent getTableEntry(LuaTypeManager typeManager, String spacing, LuaValue key, LuaValue value, boolean hasTooltip, int depth, int indent) {
        return null;
    }

    private static Component tableToTextLimited(LuaTypeManager typeManager, LuaValue value, int depth, int indent, boolean hasTooltip) {
        // attempt to parse top
        if (value.isuserdata())
            return userdataToText(typeManager, value, depth, indent, hasTooltip);
        // normal print when invalid type or depth limit
        if (!value.istable() || depth <= 0)
            return getPrintText(typeManager, value, hasTooltip, true);
        // format text
        MutableComponent text = Component.empty()
                .append(Component.literal("table:").withStyle(getTypeColor(value)))
                .append(Component.literal(" {\n").withStyle(ChatFormatting.GRAY));
        String spacing = "\t".repeat(indent - 1);
        LuaTable table = value.checktable();
        int limit = 150;
        for (LuaValue key : table.keys()){
            if(limit <= 0){
                text.append(Component.literal("...").withStyle(ChatFormatting.GRAY));
                break;
            }
            text.append(getTableEntry(typeManager, spacing, key, table.get(key), hasTooltip, depth, indent));
            limit--;
        }
        text.append(spacing).append(Component.literal("}").withStyle(ChatFormatting.GRAY));
        return text;
    }

    @Overwrite
    private static MutableComponent getPrintText(LuaTypeManager typeManager, LuaValue value, boolean hasTooltip, boolean quoteStrings) {
        String ret;
        if (!(value instanceof LuaString) && value.isnumber()) {
            Double d = value.checkdouble();
            ret = d == Math.rint(d) ? value.tojstring() : df.format(d);
        } else {
            ret = value.tojstring();
            if (value.isstring() && quoteStrings) {
                ret = "\"" + ret + "\"";
            }
        }

        MutableComponent text = Component.literal(ret).withStyle(getTypeColor(value));
        if (hasTooltip && (value.istable() || value.isuserdata())) {
            Component table = TextUtils.replaceTabs(tableToTextLimited(typeManager, value, 1, 1, false));
            text.withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, table)));
        }

        return text;
    }
}
