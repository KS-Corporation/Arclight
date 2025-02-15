package io.izzel.arclight.impl.mixin.optimization.stream;

import com.google.common.collect.ImmutableList;
import io.izzel.arclight.common.bridge.world.TrackedEntityBridge;
import net.minecraft.entity.Entity;
import net.minecraft.world.TrackedEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(TrackedEntity.class)
public class MixinTrackedEntity_Optimize {

    @Redirect(method = "Lnet/minecraft/world/TrackedEntity;tick()V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getPassengers()Ljava/util/List;"))
    public List<Entity> arclight$getPassengers(Entity instance) {
        return instance.passengers;
    }

    @Redirect(method = "Lnet/minecraft/world/TrackedEntity;tick()V",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/world/TrackedEntity;passengers:Ljava/util/List;",
                    opcode = Opcodes.PUTFIELD
            )
    )
    public void arclight$getPassengers$local(TrackedEntity instance, List<Entity> value) {
        ((TrackedEntityBridge) instance).bridge$setPassengers(ImmutableList.copyOf(value));
    }
}
