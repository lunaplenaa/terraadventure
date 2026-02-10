package net.lunaplenaa.terraadventure.entity;

import net.lunaplenaa.terraadventure.TerraAdventure;
import net.lunaplenaa.terraadventure.entity.mobs.bitey.BiteyEntity;
import net.lunaplenaa.terraadventure.entity.mobs.faeseedling.FaeSeedlingEntity;
import net.lunaplenaa.terraadventure.entity.mobs.mysterytrader.MysteryTraderEntity;
import net.lunaplenaa.terraadventure.entity.mobs.slugnormal.SlugNormalEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITYTYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, TerraAdventure.MODID);

    public static final RegistryObject<EntityType<BiteyEntity>> INKSPIRITCOMMON =
            ENTITYTYPES.register("bitey", () -> EntityType.Builder.of(BiteyEntity::new, MobCategory.CREATURE)
                    .sized(1f, 1.5f).build("bitey"));

    public static final RegistryObject<EntityType<FaeSeedlingEntity>> FAESEEDLING =
            ENTITYTYPES.register("fae_seedling", () -> EntityType.Builder.of(FaeSeedlingEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("fae_seedling"));

    public static final RegistryObject<EntityType<SlugNormalEntity>> SLUGNORMAL =
            ENTITYTYPES.register("slug_normal", () -> EntityType.Builder.of(SlugNormalEntity::new, MobCategory.CREATURE)
                    .sized(0.6f, 0.4f).build("slug_normal"));

    public static final RegistryObject<EntityType<MysteryTraderEntity>> MYSTERYTRADER =
            ENTITYTYPES.register("cannot_goodenough", () -> EntityType.Builder.of(MysteryTraderEntity::new, MobCategory.CREATURE)
                    .sized(1.0f, 3f).build("cannot_goodenough"));

    public static void register(IEventBus eventBus) {
        ENTITYTYPES.register(eventBus);
    }
}
