package net.lunaplenaa.terraadventure.entity.mobs;

import net.lunaplenaa.terraadventure.TerraAdventure;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModModelLayers {
    public static final ModelLayerLocation INKSPIRITCOMMONLAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(TerraAdventure.MODID, "bitey_layer"), "main");
    public static final ModelLayerLocation INKSPIRITCOMMONACCENTLAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(TerraAdventure.MODID, "bitey_accent_layer"), "main");

    public static final ModelLayerLocation FAESEEDLINGLAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(TerraAdventure.MODID, "fae_seedling_layer"), "main");

    public static final ModelLayerLocation SLUGNORMALLAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(TerraAdventure.MODID, "slug_normal_layer"), "main");
    public static final ModelLayerLocation SLUGNORMALSPIKESLAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(TerraAdventure.MODID, "slug_normal_spikes_layer"), "main");

    public static final ModelLayerLocation MYSTERYTRADERLAYER = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(TerraAdventure.MODID, "mystery_trader_layer"), "main");
}
