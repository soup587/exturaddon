package soup587.exturaddon.mixin;


import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.avatar.AvatarManager;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.entity.LivingEntityAPI;
import org.figuramc.figura.lua.api.world.ItemStackAPI;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mixin(value = LivingEntityAPI.class, remap = false)
public abstract class LivingEntityAPIMixin<T extends LivingEntity> extends EntityAPIMixin<T> {

    @Unique

    @LuaWhitelist
    @LuaMethodDoc("entity.get_curio")
    public ItemStackAPI getCurio(@LuaNotNil String slotType, int slot) {
        if(!ClientAPI.HAS_CURIOS) return null;
        try {
            Class<?> CuriosAPI = Class.forName("top.theillusivec4.curios.api.CuriosApi");
            Method getCuriosInventory = CuriosAPI.getDeclaredMethod("getCuriosInventory", LivingEntity.class);
            Object cInventoryLazyOptionalOBJ = getCuriosInventory.invoke(CuriosAPI,entity);

            Class<?> LazyOptional = cInventoryLazyOptionalOBJ.getClass();
            Method resolve = LazyOptional.getDeclaredMethod("resolve");
            Object cInventoryOptionalOBJ = resolve.invoke(cInventoryLazyOptionalOBJ);

            Class<?> Optional = cInventoryOptionalOBJ.getClass();
            Method get = Optional.getDeclaredMethod("get");
            Object curiosInventoryOBJ = get.invoke(cInventoryOptionalOBJ);

            Class<?> curiosInventory = curiosInventoryOBJ.getClass();
            Method getStacksHandler = curiosInventory.getDeclaredMethod("getStacksHandler", String.class);
            Object sHandlerOptionalOBJ = getStacksHandler.invoke(curiosInventoryOBJ,slotType);

            Object stacksHandlerOBJ = get.invoke(sHandlerOptionalOBJ);

            Class<?> stacksHandler = stacksHandlerOBJ.getClass();
            Method getStacks = stacksHandler.getDeclaredMethod("getStacks");
            Object dynamicStackOBJ = getStacks.invoke(stacksHandlerOBJ);

            Class<?> dynamicStack = Class.forName("net.minecraftforge.items.IItemHandler");
            Method getStackInSlot = dynamicStack.getDeclaredMethod("getStackInSlot", int.class);
            Object itemStackOBJ = getStackInSlot.invoke(dynamicStackOBJ,slot);

            return ItemStackAPI.verify((ItemStack) itemStackOBJ);

        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }

    }

    @Unique
    @LuaWhitelist
    @LuaMethodDoc("living_entity.get_nameplate")
    public String getNameplate(@LuaNotNil String type) {
        checkEntity();
        Avatar avi = AvatarManager.getAvatar(entity);
        if(avi == null) return null;
        return switch (type.toUpperCase()) {
            case "ENTITY" -> avi.luaRuntime.nameplate.ENTITY.getText();
            case "LIST" -> avi.luaRuntime.nameplate.LIST.getText();
            case "CHAT" -> avi.luaRuntime.nameplate.CHAT.getText();
            default -> null;
        };
    }
}
