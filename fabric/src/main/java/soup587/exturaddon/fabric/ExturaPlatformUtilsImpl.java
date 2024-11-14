package soup587.exturaddon.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class ExturaPlatformUtilsImpl {

    public static String getModName(String modId) {
        return FabricLoader.getInstance().getModContainer(modId).get().getMetadata().getName();
    }

}
