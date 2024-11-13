package soup587.exturaddon;

import net.minecraft.ChatFormatting;
import org.figuramc.figura.config.ConfigType;
import org.figuramc.figura.permissions.Permissions;
import org.figuramc.figura.utils.FiguraText;

public class Exturaddon {
    public static final String PLUGIN_ID = "exturaddon";

    public Exturaddon() {
    }

    public static final ConfigType.Category EXTURA = new ConfigType.Category("extura") {{
        this.name = this.name.copy().withStyle(ChatFormatting.LIGHT_PURPLE);
    }};
    public static final ConfigType.Category DANGEROUS = new ConfigType.Category("dangerous") {{
        this.name = this.name.copy().withStyle(ChatFormatting.RED);
    }};

    public static final ConfigType.BoolConfig GETBLOCKS_LIMIT = new ConfigType.BoolConfig("get_blocks_limit", EXTURA, false) {{
        this.name = this.name.copy().withStyle(ChatFormatting.RED);
        this.tooltip = FiguraText.of("config.get_blocks_limit.tooltip");
    }};

    public static final ConfigType.BoolConfig GET_TARGET_LIMIT = new ConfigType.BoolConfig("get_target_limit", EXTURA, false) {{
        this.name = this.name.copy().withStyle(ChatFormatting.RED);
        this.tooltip = FiguraText.of("config.get_target_limit.tooltip");
    }}; // Did not know getTargetedEntity and getTargetedBlock were limited to 20 blocks
    public static final ConfigType.BoolConfig EXPOSE_SENSITIVE_LIBRARIES = new ConfigType.BoolConfig("expose_sensitive_libraries", DANGEROUS, false) {{
        this.name = this.name.copy().withStyle(ChatFormatting.RED);
        this.tooltip = FiguraText.of("config.expose_sensitive_libraries.tooltip");
    }};
    public static final ConfigType.BoolConfig EXPOSE_EXTURA_API = new ConfigType.BoolConfig("expose_extura_api", EXTURA, true) {{
        this.name = this.name.copy().withStyle(ChatFormatting.RED);
        this.tooltip = FiguraText.of("expose_extura_api.tooltip");
    }};
    public static final ConfigType.BoolConfig EXPOSE_JAVA_API = new ConfigType.BoolConfig("expose_java_api", DANGEROUS, true) {{
        this.name = this.name.copy().withStyle(ChatFormatting.RED);
        this.tooltip = FiguraText.of("expose_java_api.tooltip");
    }};
    public static final ConfigType.BoolConfig USE_GLOBAL_SCRIPTS = new ConfigType.BoolConfig("use_global_scripts", EXTURA, false) {{
        this.name = this.name.copy();
        this.tooltip = FiguraText.of("config.use_global_scripts.tooltip");
    }};
    public static final ConfigType.BoolConfig EXPOSE_HTTP = new ConfigType.BoolConfig("expose_http", DANGEROUS, false) {{
        this.name = this.name.copy();
        this.tooltip = FiguraText.of("config.expose_http.tooltip");
    }};
    public static final ConfigType.BoolConfig ANNUAL_CACHE_DELETION = new ConfigType.BoolConfig("annual_cache_deletion", EXTURA, true) {{
        this.name = this.name.copy();
        this.tooltip = FiguraText.of("config.annual_cache_deletion.tooltip");
    }};
    public static final ConfigType.BoolConfig HELPER_ERRORS = new ConfigType.BoolConfig("helper_errors", EXTURA, true) {{
        this.name = this.name.copy();
        this.tooltip = FiguraText.of("config.helper_errors.tooltip");
    }};

    public static final Permissions EXTURA_CHEATING = new Permissions("EXTURA_CHEATING", 0,0,0,1,1);

    public static void init() {

    }

}
