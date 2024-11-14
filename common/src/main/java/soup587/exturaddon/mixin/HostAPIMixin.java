package soup587.exturaddon.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.lua.LuaNotNil;
import org.figuramc.figura.lua.api.entity.EntityAPI;
import org.figuramc.figura.math.vector.FiguraVec2;
import org.figuramc.figura.math.vector.FiguraVec3;
import org.figuramc.figura.permissions.Permissions;
import org.figuramc.figura.lua.LuaWhitelist;
import org.figuramc.figura.lua.docs.LuaMethodDoc;
import org.figuramc.figura.lua.docs.LuaMethodOverload;
import org.figuramc.figura.utils.LuaUtils;
import org.luaj.vm2.LuaError;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.figuramc.figura.lua.api.HostAPI;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import soup587.exturaddon.Exturaddon;
import soup587.exturaddon.overrides.ExturaInput;
import soup587.exturaddon.overrides.NoInput;

import java.util.Optional;

@Mixin(value = HostAPI.class, remap = false)
public class HostAPIMixin {

    @Shadow @Final private Avatar owner;
    @Shadow @Final private boolean isHost;
    @Shadow @Final private Minecraft minecraft;

    public Boolean canExturaCheat() {
        if(!this.isHost) return false;
        LocalPlayer player = this.minecraft.player;
        if(player == null) return false;
        if(player.hasPermissions(2)  ||
                this.minecraft.isLocalServer() ||
                (player.getScoreboard().hasObjective("extura_can_cheat"))
        ) return true;
        if(!owner.noPermissions.contains(Exturaddon.EXTURA_CHEATING)){
            owner.noPermissions.add(Exturaddon.EXTURA_CHEATING);
        }
        return false;
    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = Boolean.class,
                            argumentNames = "vec"
                    ),
            },
            value = "host.set_velocity"
    )
    public void setVelocity(Object x, Double y, Double z) {
        if(!canExturaCheat()) return;
        this.minecraft.player.setDeltaMovement(LuaUtils.parseVec3("player_setVelocity", x, y, z).asVec3());

    }
    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = Boolean.class,
                            argumentNames = "vec"
                    ),
            },
            value = "host.travel"
    )
    public void travel(Object x, Double y, Double z) {
        if(!canExturaCheat()) return;
        this.minecraft.player.travel(LuaUtils.parseVec3("player_travel", x, y, z).asVec3());

    }
    @LuaWhitelist
    @LuaMethodDoc("host.set_pose")
    public void setPose(String pose) {
        if(!canExturaCheat()) return;
        try{
            Pose _pose = Pose.valueOf(pose);
            this.minecraft.player.setPose(_pose);
        }catch(IllegalArgumentException ignored){
            throw new LuaError("Invalid pose " + pose);
        }
    }
    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = Boolean.class,
                            argumentNames = "pos"
                    ),
            },
            value = "host.set_pos"
    )
    public void setPos(Object x, Double y, Double z) {
        if (!canExturaCheat()) return;
        if(x == null) return;
        LocalPlayer player = this.minecraft.player;
        player.setPos(LuaUtils.parseVec3("player_setPos", x, y, z).asVec3());
    }
    @LuaWhitelist
    @LuaMethodDoc("host.start_riding")
    public void startRiding(EntityAPI entity, boolean bool) {
        if (!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        if(entity == null) player.removeVehicle();
        Entity t = entity.getEntity();
        if(t == player) throw new LuaError("You cannot ride yourself!");
        player.startRiding(t,bool);
    }

    @LuaWhitelist
    @LuaMethodDoc("host.drop_item")
    public void dropItem(boolean dropAll) {
        if (!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        player.drop(dropAll == true);
    }
    @LuaWhitelist
    @LuaMethodDoc("host.close_container")
    public void closeContainer() {
        if (!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        player.closeContainer();
    }
    @LuaWhitelist
    @LuaMethodDoc("host.start_using_item")
    public void startUsingItem(boolean offHand) {
        if (!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        player.startUsingItem(offHand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);
    }
    @LuaWhitelist
    @LuaMethodDoc("host.stop_using_item")
    public void stopUsingItem() {
        if (!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        player.stopUsingItem();
    }
    @LuaWhitelist
    @LuaMethodDoc("host.send_open_inventory")
    public void sendOpenInventory() {
        if (!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        player.sendOpenInventory();
    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = {Boolean.class},
                            argumentNames = {"playerMovement"}
                    )
            },
            value = "host.set_player_movement"
    )
    public void setPlayerMovement(Boolean playerMovement) {
        LocalPlayer player;
        if (!this.isHost || (player = this.minecraft.player) == null) return;
        player.input = (playerMovement ? new ExturaInput(this.minecraft.options) : new NoInput());

    }
    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = {String.class,Boolean.class},
                            argumentNames = {"input","state"}
                    ),
                    @LuaMethodOverload(
                            argumentTypes = {String.class},
                            argumentNames = {"input","state"}
                    ),
            },
            value = "host.override_player_movement"
    )
    public void overridePlayerMovement(@LuaNotNil String input, Boolean sta) {
        if(!canExturaCheat()) return;
        LocalPlayer player;
        if (!this.isHost || (player = this.minecraft.player) == null) return;
        if(!(player.input instanceof ExturaInput)){
            player.input = new ExturaInput(this.minecraft.options);
        }
        int state = sta == null ? 0 : sta ? 2 : 1;
        ExturaInput inputObj = (ExturaInput) player.input;
        switch(input.toLowerCase()){
            case "up": inputObj.upOverride = state; break;
            case "down": inputObj.downOverride = state; break;
            case "left": inputObj.leftOverride = state; break;
            case "right": inputObj.rightOverride = state; break;
            case "jump": inputObj.jumpOverride = state; break;
            case "shift": inputObj.shiftOverride = state; break;
            default: throw new LuaError("Invalid input");
        }
    }
    @LuaWhitelist
    @LuaMethodDoc("host.get_player_movement")
    public Boolean getPlayerMovement() {
        LocalPlayer player;
        if (!this.isHost || (player = this.minecraft.player) == null) return true;
        return (player.input instanceof NoInput);
    }

    @LuaWhitelist
    @LuaMethodDoc("host.get_last_death_pos")
    public FiguraVec3 getLastDeathPos() {
        if(!isHost) return null;
        LocalPlayer player = this.minecraft.player;
        if (player != null) {
            Optional<GlobalPos> deathLocation = player.getLastDeathLocation();
            if(deathLocation.isPresent()) return FiguraVec3.fromBlockPos(deathLocation.get().pos());
        }
        return null;
    }


    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = FiguraVec2.class,
                            argumentNames = "vec"
                    ),
                    @LuaMethodOverload(
                            argumentTypes = {Double.class, Double.class},
                            argumentNames = {"x", "y"}
                    )
            },
            value = "host.set_rot"
    )
    public void setRot(Object x, Double y) {
        if(!canExturaCheat()) return;
        FiguraVec2 vec = LuaUtils.parseVec2("player_setRot", x, y);
        LocalPlayer player = this.minecraft.player;
        player.setXRot((float) vec.x);
        player.setYRot((float) vec.y);

    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = {Double.class},
                            argumentNames = {"angle"}
                    )
            },
            value = "host.set_body_rot"
    )
    public void setBodyRot(Double angle) {
        if(!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        player.setYBodyRot(angle.floatValue());

    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = {Double.class},
                            argumentNames = {"angle"}
                    )
            },
            value = "host.set_body_offset_rot"
    )
    public void setBodyOffsetRot(Double angle) {
        if(!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        player.setYBodyRot( angle.floatValue() + player.getYRot() );
    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = {Boolean.class},
                            argumentNames = {"hasForce"}
                    )
            },
            value = "host.set_gravity"
    )
    public void setGravity(Boolean hasForce) {
        if(!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        if (player == null) return;
        player.setNoGravity(!hasForce);

    }

    @LuaWhitelist
    @LuaMethodDoc(
            overloads = {
                    @LuaMethodOverload(
                            argumentTypes = {Boolean.class},
                            argumentNames = {"hasForce"}
                    )
            },
            value = "host.set_drag"
    )
    public void setDrag(Boolean hasForce) {
        if(!canExturaCheat()) return;
        LocalPlayer player = this.minecraft.player;
        if (player == null) return;
        player.setDiscardFriction(hasForce != true);
    }
}
