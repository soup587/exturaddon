package soup587.exturaddon;

import org.figuramc.figura.entries.FiguraPermissions;
import org.figuramc.figura.permissions.Permissions;

import java.util.Collection;
import java.util.List;

public class ExturaddonPermissions implements FiguraPermissions {



    @Override
    public String getTitle() {
        return Exturaddon.PLUGIN_ID;
    }

    @Override
    public Collection<Permissions> getPermissions() {
        return List.of();
    }
}
