package soup587.exturaddon.mixin;

import net.minecraft.ChatFormatting;
import org.figuramc.figura.config.ConfigType;
import org.figuramc.figura.config.Configs;
import org.figuramc.figura.utils.FiguraText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.HashMap;

@Mixin(value = Configs.class, remap = false)
public class FiguraConfigsMixin {

    @Shadow
    @Final
    public static HashMap<Integer, HashMap<ConfigType<?>, String>> CONFIG_UPDATES;


}
