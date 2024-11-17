package soup587.exturaddon.ducks;

import org.figuramc.figura.lua.api.entity.EntityAPI;
import org.figuramc.figura.math.vector.FiguraVec3;

public interface AvatarAccess {
    void exturaddon$customEvent(String event);
    void exturaddon$attackEvent(String sourceType, EntityAPI<?> loser, FiguraVec3 sourcePosition);
}
