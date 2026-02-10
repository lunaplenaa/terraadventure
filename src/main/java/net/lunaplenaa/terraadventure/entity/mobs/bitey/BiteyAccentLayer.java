package net.lunaplenaa.terraadventure.entity.mobs.bitey;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.lunaplenaa.terraadventure.TerraAdventure;
import net.lunaplenaa.terraadventure.entity.mobs.ModModelLayers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BiteyAccentLayer extends RenderLayer<BiteyEntity, BiteyModel<BiteyEntity>> {
    private final BiteyModel<BiteyEntity> model;
    private static final ResourceLocation BITEYACCENTMODELLOCATION = ResourceLocation.fromNamespaceAndPath(TerraAdventure.MODID, "textures/entity/bitey_color.png");

    public BiteyAccentLayer(RenderLayerParent<BiteyEntity, BiteyModel<BiteyEntity>> pParent, EntityModelSet pSet) {
        super(pParent);
        this.model = new BiteyModel<>(pSet.bakeLayer(ModModelLayers.INKSPIRITCOMMONACCENTLAYER));
    }

    @Override
    public ResourceLocation getTextureLocation(BiteyEntity p_117348_) {
        return BITEYACCENTMODELLOCATION;
    }

    @Override
    public void render(PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight, BiteyEntity pEntity, float limbSwing, float limbSwingAmount, float idkMan, float ageInTicks, float netHeadYaw, float headPitch) {

        if (pEntity.isInvisible()) {
            Minecraft minecraft = Minecraft.getInstance();
            boolean flag = minecraft.shouldEntityAppearGlowing(pEntity);
            if(flag) {
                this.getParentModel().copyPropertiesTo(this.model);
                this.model.prepareMobModel(pEntity, limbSwing, limbSwingAmount, idkMan);
                this.model.setupAnim(pEntity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.outline(BITEYACCENTMODELLOCATION));
                this.model.renderToBuffer(pMatrixStack, vertexconsumer, pPackedLight, LivingEntityRenderer.getOverlayCoords(pEntity, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
            }

        }
        else {
            float f;
            float f1;
            float f2;

            if (pEntity.hasCustomName() && "jeb_".equals(pEntity.getName().getString())) {
                int i1 = 25;
                int i = pEntity.tickCount / 25 + pEntity.getId();
                int j = DyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f3 = ((float)(pEntity.tickCount % 25) + idkMan) / 25.0F;
                float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
                float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
                f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            } else {
                float[] afloat = pEntity.getColor().getTextureDiffuseColors();
                f = afloat[0];
                f1 = afloat[1];
                f2 = afloat[2];
            }
            renderColoredCutoutModel(this.getParentModel(), BITEYACCENTMODELLOCATION, pMatrixStack, pBuffer, pPackedLight, pEntity, f, f1, f2);
        }
    }
}
