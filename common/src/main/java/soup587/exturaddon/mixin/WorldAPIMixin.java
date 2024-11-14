package soup587.exturaddon.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.api.world.BlockStateAPI;
import org.figuramc.figura.lua.api.world.WorldAPI;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaMethodOverload;
import org.figuramc.figura.math.vector.FiguraVec3;
import org.figuramc.figura.utils.LuaUtils;
import org.luaj.vm2.LuaError;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import soup587.exturaddon.Exturaddon;

import java.util.ArrayList;
import java.util.List;

import static org.figuramc.figura.lua.api.world.WorldAPI.getCurrentWorld;

@Mixin(value = WorldAPI.class, remap = false)
public class WorldAPIMixin {

    @SuppressWarnings("deprecation")
    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = {FiguraVec3.class, FiguraVec3.class},
                            argumentNames = {"min", "max"}
                    ),
                    @LuaMethodOverload(
                            argumentTypes = {Double.class, Double.class, Double.class, FiguraVec3.class},
                            argumentNames = {"minX", "minY", "minZ", "max"}
                    ),
                    @LuaMethodOverload(
                            argumentTypes = {FiguraVec3.class, Double.class, Double.class, Double.class},
                            argumentNames = {"min", "maxX", "maxY", "maxZ"}
                    ),
                    @LuaMethodOverload(
                            argumentTypes = {Double.class, Double.class, Double.class, Double.class, Double.class, Double.class},
                            argumentNames = {"minX", "minY", "minZ", "maxX", "maxY", "maxZ"}
                    )
            },
            value = "world.get_blocks"
    )
    @Overwrite
    public static List<BlockStateAPI> getBlocks(Object x, Object y, Double z, Double w, Double t, Double h) {
        Pair<FiguraVec3, FiguraVec3> pair = LuaUtils.parse2Vec3("getBlocks", x, y, z, w, t, h, 1);
        List<BlockStateAPI> list = new ArrayList<>();

        BlockPos min = pair.getFirst().asBlockPos();
        BlockPos max = pair.getSecond().asBlockPos();
        if(!Exturaddon.GETBLOCKS_LIMIT.value){
            max = new BlockPos(
                    Math.min(min.getX() + 8, max.getX()),
                    Math.min(min.getY() + 8, max.getY()),
                    Math.min(min.getZ() + 8, max.getZ())
            );
        }
        if (min.compareTo(max) > 0) {
            throw new LuaError("Your max value can't be smaller than your min!");
        }
        Level world = getCurrentWorld();
        if (!world.hasChunksAt(min, max))
            return list;

        BlockPos.betweenClosedStream(min, max).forEach(blockPos -> {
            BlockPos pos = new BlockPos(blockPos);
            list.add(new BlockStateAPI(world.getBlockState(pos), pos));
        });
        return list;
    }
}
