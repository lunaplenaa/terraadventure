package net.lunaplenaa.terraadventure.entity.mobs.bitey;

import com.mojang.blaze3d.vertex.PoseStack;
import net.lunaplenaa.terraadventure.TerraAdventure;
import net.lunaplenaa.terraadventure.entity.mobs.ModModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class BiteyRenderer extends MobRenderer<BiteyEntity, BiteyModel<BiteyEntity>> {
    public BiteyRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new BiteyModel<>(pContext.bakeLayer(ModModelLayers.INKSPIRITCOMMONLAYER)), 0.5f);
        this.addLayer(new BiteyAccentLayer(this, pContext.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(BiteyEntity p_114482_) {
        return ResourceLocation.fromNamespaceAndPath(TerraAdventure.MODID, "textures/entity/bitey_main.png");
    }

    @Override
    public void render(BiteyEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack,
                       MultiBufferSource pBuffer, int pPackedLight) {

        pMatrixStack.scale(0.75f, 0.75f, 0.75f);

        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
