package soup587.exturaddon.forge;

import net.minecraftforge.fml.ModList;

public class ExturaPlatformUtilsImpl {

    public static String getModName(String modId) {
        return ModList.get().getModContainerById(modId).get().getModInfo().getDisplayName();
    }

}
