package net.lunaplenaa.terraadventure.entity.mobs.bitey;

import net.lunaplenaa.terraadventure.entity.ai.BiteyAttackGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;

public class BiteyEntity extends TamableAnimal implements NeutralMob {
    private static final EntityDataAccessor<Integer> BITEYCOLORID =
            SynchedEntityData.defineId(BiteyEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME =
            SynchedEntityData.defineId(BiteyEntity.class, EntityDataSerializers.INT);
    @Nullable
    private UUID persistentAngerTarget;

    private static final EntityDataAccessor<Integer> BITEYUPGRADEAMOUNT =
            SynchedEntityData.defineId(BiteyEntity.class, EntityDataSerializers.INT);

    public static final float eliteChance = 0.1f;

    private static final EntityDataAccessor<Boolean> ATTACKING =
            SynchedEntityData.defineId(BiteyEntity.class, EntityDataSerializers.BOOLEAN);

    public BiteyEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public final AnimationState attackAnimationState = new AnimationState();
    public int attackAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide()) {
            setUpAnimationStates();
        }
    }

    private void setUpAnimationStates() {
        if(this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.tickCount);
        }
        else {
            --this.idleAnimationTimeout;
        }

        if(this.isAttacking() && attackAnimationTimeout <= 0) {
            attackAnimationTimeout = 20; //animation tick length
            attackAnimationState.start(this.tickCount);
        }
        else {
            --this.attackAnimationTimeout;
        }

        if(!this.isAttacking()) {
            attackAnimationState.stop();
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        f = Math.min(pPartialTick * 6F, 1f);

        this.walkAnimation.update(f, 0.2f);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        // panic goal ?
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, new BiteyAttackGoal( this, 1.3D, true));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0D, Ingredient.of(Items.GLOW_INK_SAC), false));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 1D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 3f));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, true));

        // FOR DUEL CHANNEL ONLY
        /*
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Pillager.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Wolf.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Husk.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Skeleton.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, WitherBoss.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, WitherSkeleton.class, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
         */
    }

    protected void promoteBitey() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(25.0D);
        this.heal(9.0f);
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.2D);
        this.getAttribute(Attributes.ARMOR).setBaseValue(4.0D);
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(24.0D);
    }

    public void trainAttributes() {
        upgradeBiteyOnce();
        if(howManyUpgrades() <= 8) {
            this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(
                    new AttributeModifier("bitey_upgrade_health_bonus", 0.1D, AttributeModifier.Operation.MULTIPLY_BASE));
            this.heal(5);
        }
        if(howManyUpgrades() == 4) {
            this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(
                    new AttributeModifier("bitey_upgrade_attack_bonus", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
            this.getAttribute(Attributes.ARMOR).addPermanentModifier(
                    new AttributeModifier("bitey_upgrade_amrmor_bonus", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
        if(howManyUpgrades() == 8) {
            this.getAttribute(Attributes.ARMOR).addPermanentModifier(
                    new AttributeModifier("bitey_upgrade_armor_bonus", 0.5D, AttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    public void upgradeBiteyOnce() {
        this.entityData.set(BITEYUPGRADEAMOUNT, 1 + this.entityData.get(BITEYUPGRADEAMOUNT));
        if(this.entityData.get(BITEYUPGRADEAMOUNT) > 8) {
            this.entityData.set(BITEYUPGRADEAMOUNT, 8);
        }
    }

    public Integer howManyUpgrades() {
        return this.entityData.get(BITEYUPGRADEAMOUNT);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 17D)
                .add(Attributes.ATTACK_DAMAGE, 3D)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5D)
                .add(Attributes.ARMOR, 2D)
                .add(Attributes.MOVEMENT_SPEED, .15D)
                .add(Attributes.FOLLOW_RANGE, 16D);
    }

    public DyeColor getColor() {
        return DyeColor.byId(this.entityData.get(BITEYCOLORID) & 15);
    }

    public void setColor(DyeColor dyeColor) {
        this.entityData.set(BITEYCOLORID, dyeColor.getId());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pTag) {
        super.addAdditionalSaveData(pTag);
        pTag.putByte("accent_color", (byte) this.getColor().getId());
        pTag.putInt("bitey_upgrades", this.howManyUpgrades());
        this.addPersistentAngerSaveData(pTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pTag) {
        super.readAdditionalSaveData(pTag);
        if (pTag.contains("accent_color", 99)) {
            this.setColor(DyeColor.byId(pTag.getInt("accent_color")));
        }

        this.readPersistentAngerSaveData(this.level(), pTag);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BITEYCOLORID, DyeColor.LIGHT_BLUE.getId());
        this.entityData.define(ATTACKING, false);
        this.entityData.define(DATA_REMAINING_ANGER_TIME, 0);
        this.entityData.define(BITEYUPGRADEAMOUNT, 0);
    }

    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        Item item = itemStack.getItem();

        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || itemStack.is(Items.PRISMARINE_SHARD) && !this.isTame() && !this.isAngry();
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
        }
        else if (this.isTame()) {
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                if(itemStack.is(Items.INK_SAC)) {
                    this.heal(3.0f);
                }
                else if(itemStack.is(Items.GLOW_INK_SAC)) {
                    this.heal(6.0f);
                }

                if (!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                this.gameEvent(GameEvent.EAT, this);
                return InteractionResult.SUCCESS;
            }
            else if (itemStack.is(Items.PRISMARINE_SHARD) && this.isOwnedBy(pPlayer)) {
                if(!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }

                if(howManyUpgrades() <= 8) {
                    trainAttributes();
                }
                return InteractionResult.SUCCESS;
            }
            else {
                InteractionResult interactionresult = super.mobInteract(pPlayer, pHand);
                if ((!interactionresult.consumesAction()) && this.isOwnedBy(pPlayer)) {
                    this.setOrderedToSit(!this.isOrderedToSit());
                    this.jumping = false;
                    this.navigation.stop();
                    this.setTarget((LivingEntity)null);
                    return InteractionResult.SUCCESS;
                } else {
                    return interactionresult;
                }
            }
        }
        else if (itemStack.is(Items.PRISMARINE_SHARD) && !this.isAngry()) {
            if (!pPlayer.getAbilities().instabuild) {
                itemStack.shrink(1);
            }

            if (this.random.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, pPlayer)) {
                this.tame(pPlayer);
                this.navigation.stop();
                this.setTarget((LivingEntity)null);
                this.setOrderedToSit(true);
                this.level().broadcastEntityEvent(this, (byte)7);
            }
            else {
                this.level().broadcastEntityEvent(this, (byte)6);
            }

            return InteractionResult.SUCCESS;
        }
        else if (item instanceof DyeItem) {
            DyeItem dyeitem = (DyeItem)item;
            if (this.isOwnedBy(pPlayer) || !(this.isTame() && !this.isOwnedBy(pPlayer))) {
                DyeColor dyecolor = dyeitem.getDyeColor();
                if (dyecolor != this.getColor()) {
                    this.setColor(dyecolor);
                    if (!pPlayer.getAbilities().instabuild) {
                        itemStack.shrink(1);
                    }

                    return InteractionResult.SUCCESS;
                }
                return super.mobInteract(pPlayer, pHand);
            }
            else {
                return super.mobInteract(pPlayer, pHand);
            }
        }
        else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_, MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_, @Nullable CompoundTag p_146750_) {
        float isElite = this.random.nextFloat();

        if(isElite < eliteChance) {
            this.promoteBitey();
            this.setColor(DyeColor.RED);
        }
        else {
            this.setColor(DyeColor.LIGHT_BLUE);
        }

        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.INK_SAC) || pStack.is(Items.GLOW_INK_SAC);
    }

    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    public void setRemainingPersistentAngerTime(int pPersistentAngerTime) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, pPersistentAngerTime);
    }

    @Nullable
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    public void setPersistentAngerTarget(@Nullable UUID pPersistentAngerTarget) {
        this.persistentAngerTarget = pPersistentAngerTarget;
    }

    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public boolean wantsToAttack(LivingEntity pEntity, LivingEntity pPlayer) {
        if (!(pEntity instanceof Creeper) && !(pEntity instanceof Ghast)) {
            if (pEntity instanceof Wolf) {
                Wolf wolf = (Wolf)pEntity;
                return !wolf.isTame() || wolf.getOwner() != pPlayer;
            }
            if (pEntity instanceof BiteyEntity) {
                BiteyEntity bitey = (BiteyEntity) pEntity;
                return !bitey.isTame() || bitey.getOwner() != pPlayer;
            }
            else if (pEntity instanceof Player && pPlayer instanceof Player && !((Player)pPlayer).canHarmPlayer((Player)pPlayer)) {
                return false;
            }
            else if (pEntity instanceof AbstractHorse && ((AbstractHorse)pEntity).isTamed()) {
                return false;
            }
            else {
                return !(pEntity instanceof TamableAnimal) || !((TamableAnimal)pEntity).isTame();
            }
        }
        else {
            return false;
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pMob) {
        return null;
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 5;
    }

    @Override
    public boolean canBeLeashed(Player pPlayer) {
        return !this.isAngry() && super.canBeLeashed(pPlayer);
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public SoundEvent getEatingSound(ItemStack pItem) {
        return SoundEvents.SLIME_SQUISH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource pSource) {
        return SoundEvents.SLIME_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SLIME_DEATH;
    }
}
