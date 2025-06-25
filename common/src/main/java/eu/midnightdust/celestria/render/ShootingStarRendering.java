package eu.midnightdust.celestria.render;

import eu.midnightdust.celestria.CelestriaClient;
import eu.midnightdust.celestria.ShootingStar;
import eu.midnightdust.celestria.config.CelestriaConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.profiler.Profilers;
import org.joml.Matrix4f;

import static eu.midnightdust.celestria.Celestria.id;
import static java.lang.Math.pow;

public class ShootingStarRendering {
    public void renderShootingStars(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers) {
        if (MinecraftClient.getInstance().world == null) return;
        Profilers.get().swap("shooting_stars");
        CelestriaClient.shootingStars.forEach(star -> renderShootingStar(matrices, vertexConsumers, star));
    }

    @SuppressWarnings("SuspiciousNameCombination")
    private void renderShootingStar(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers, ShootingStar star) {
        if (MinecraftClient.getInstance().world != null && CelestriaConfig.enableShootingStars && !CelestriaClient.shootingStars.isEmpty()) {
            matrices.push();
            float alpha = (float) Math.clamp((star.progress - pow(1f / star.progress, 4)) / CelestriaConfig.shootingStarPathLength, 0, 1);
            matrices.scale(CelestriaConfig.shootingStarDistance, CelestriaConfig.shootingStarDistance, CelestriaConfig.shootingStarDistance);
            int direction = isEven(star.type) ? -1 : 1;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-star.y));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-star.rotation * direction));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-star.x - (star.progress * CelestriaConfig.shootingStarSpeed * 0.05f)));
            matrices.translate(star.progress * CelestriaConfig.shootingStarSpeed * direction, 0, 0);
            Matrix4f posMat4f = matrices.peek().getPositionMatrix();
            // draw the star
            float height = star.size / 100f * 20.0F;
            float width = star.size / 100f * 100.0F;
            VertexConsumer vertexconsumer = vertexConsumers.getBuffer(RenderLayer.getCelestial(id("textures/environment/shooting_star" + (star.type + 1) + ".png")));
            vertexconsumer.vertex(posMat4f, -height, -width, height).texture(0.0F, star.getPhase()).color(1.0F, 1.0F, 1.0F, alpha);
            vertexconsumer.vertex(posMat4f, height, -width, height).texture(1.0F, star.getPhase()).color(1.0F, 1.0F, 1.0F, alpha);
            vertexconsumer.vertex(posMat4f, height, -width, -height).texture(1.0F, star.getPhase() + 0.25F).color(1.0F, 1.0F, 1.0F, alpha);
            vertexconsumer.vertex(posMat4f, -height, -width, -height).texture(0.0F, star.getPhase() + 0.25F).color(1.0F, 1.0F, 1.0F, alpha);
            matrices.pop();
        }
    }

    public static boolean isEven(int i) {
        return (i | 1) > i;
    }
}
