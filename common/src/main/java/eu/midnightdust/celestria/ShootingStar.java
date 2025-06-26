package eu.midnightdust.celestria;

import eu.midnightdust.celestria.config.CelestriaConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ShootingStar {
    public int progress;
    public final int type, x, y, rotation, size;
    public int phaseScore;
    public ShootingStar(int progress, int type, int x, int y, int rotation, int size) {
        this.progress = progress;
        this.type = type;
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.size = size;
        this.phaseScore = 0;
    }

    public void tick() {
        --progress;
        phaseScore++;
        phaseScore %= CelestriaConfig.shootingStarPhaseCycle;
    }

    /**
     * Get the current phase of the shooting star (UV offset of the texture)
     * @see ShootingStar#phaseScore
     * @see CelestriaConfig#shootingStarPhaseCycle
     * @return [0, 25%) -> 0.0F, [25%, 50%) -> 0.25F, [50%, 75%) -> 0.5F, [75%, 100%) -> 0.75F
     */
    public float getPhase() {
        if ((double) this.phaseScore / CelestriaConfig.shootingStarPhaseCycle < 0.25) return 0.0F;
        else if ((double) this.phaseScore / CelestriaConfig.shootingStarPhaseCycle < 0.5) return 0.25F;
        else if ((double) this.phaseScore / CelestriaConfig.shootingStarPhaseCycle < 0.75) return 0.5F;
        else return 0.75F;
    }
}
