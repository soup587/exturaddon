package soup587.exturaddon.forge;

import soup587.exturaddon.Exturaddon;
import net.minecraftforge.fml.common.Mod;

@Mod(Exturaddon.PLUGIN_ID)
public final class ExturaddonForge {
    public ExturaddonForge() {
        // Run our common setup.
        Exturaddon.init();
    }
}
