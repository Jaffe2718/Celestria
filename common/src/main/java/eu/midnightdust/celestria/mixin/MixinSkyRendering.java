package eu.midnightdust.celestria.mixin;

import eu.midnightdust.celestria.render.ShootingStarRendering;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SkyRendering.class)
public abstract class MixinSkyRendering {
    @Unique private final ShootingStarRendering celestria$shootingStarRendering = new ShootingStarRendering();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;draw()V"), method = "renderCelestialBodies")
    private void celestria$renderShootingStars(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, float rot, int phase, float alpha, float starBrightness, CallbackInfo ci) {
        celestria$shootingStarRendering.renderShootingStars(matrices, vertexConsumers);
    }
}
