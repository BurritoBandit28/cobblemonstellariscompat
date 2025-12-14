package io.github.burritobandit28.csc.mixin;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.util.DataKeys;
import com.st0x0ef.stellaris.Stellaris;
import com.st0x0ef.stellaris.common.oxygen.DimensionOxygenManager;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.cobblemon.mod.common.api.pokemon.feature.FlagSpeciesFeature;

@Mixin(DimensionOxygenManager.class)
public class DimensionOxygenManagerMixin {

    @Inject(at = @At("HEAD"), method = "breath", remap = false, cancellable = true)
    public void doCobblemonBreathing(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!Stellaris.CONFIG.oxygenConfig.enableOxygenSystem) {
            cir.setReturnValue(true);
        }
        if (entity instanceof PokemonEntity pokemon) {
            FlagSpeciesFeature flag = pokemon.getPokemon().getFeature("survives_space");

            if (flag==null){
                return;
            }
            boolean survives_space = flag.getEnabled();
            if (survives_space) {
                cir.setReturnValue(true);
            }

        }
    }

}
