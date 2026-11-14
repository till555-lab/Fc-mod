package com.femboycompanion.entity;

import java.util.Random;
import javax.annotation.Nullable;

import com.femboycompanion.registry.ModItems;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.network.NetworkHooks;

public class FemboyEntity extends CreatureEntity {
    public static final int VARIANT_WHITE = 0;
    public static final int VARIANT_BLACK = 1;

    private static final DataParameter<Integer> VARIANT =
            EntityDataManager.defineId(FemboyEntity.class, DataSerializers.INT);

    private int giftLimit;
    private int giftsGiven;
    private int refusals;
    private int angerTicks;
    private long limitResetTime = -1L;
    private int lastInteractTick = -100;

    public FemboyEntity(EntityType<? extends FemboyEntity> type, World world) {
        super(type, world);
        this.giftLimit = 2 + this.random.nextInt(9);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ARMOR, 1.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D);
    }

    public static boolean checkSpawnRules(EntityType<FemboyEntity> type, IServerWorld world,
            SpawnReason reason, BlockPos pos, Random random) {
        return world.getRawBrightness(pos, 0) > 8
                && MobEntity.checkMobSpawnRules(type, world, reason, pos, random);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(VARIANT, VARIANT_WHITE);
    }

    public int getVariant() {
        return this.entityData.get(VARIANT);
    }

    public void setVariant(int variant) {
        this.entityData.set(VARIANT, variant);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Variant", this.getVariant());
        tag.putInt("GiftLimit", this.giftLimit);
        tag.putInt("GiftsGiven", this.giftsGiven);
        tag.putInt("Refusals", this.refusals);
        tag.putInt("AngerTicks", this.angerTicks);
        tag.putLong("LimitReset", this.limitResetTime);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT tag) {
        super.readAdditionalSaveData(tag);
        this.setVariant(tag.getInt("Variant"));
        if (tag.contains("GiftLimit")) {
            this.giftLimit = tag.getInt("GiftLimit");
        }
        this.giftsGiven = tag.getInt("GiftsGiven");
        this.refusals = tag.getInt("Refusals");
        this.angerTicks = tag.getInt("AngerTicks");
        if (tag.contains("LimitReset")) {
            this.limitResetTime = tag.getLong("LimitReset");
        }
    }

    private static class GroupData implements ILivingEntityData {
        final int variant;

        GroupData(int variant) {
            this.variant = variant;
        }
    }

    private int rollVariant(IServerWorld world) {
        Biome biome = world.getBiome(this.blockPosition());
        Biome.Category category = biome.getBiomeCategory();
        ResourceLocation name = biome.getRegistryName();
        int whiteChance = 55;
        if (category == Biome.Category.ICY || category == Biome.Category.TAIGA) {
            whiteChance = 80;
        } else if (category == Biome.Category.SWAMP || category == Biome.Category.DESERT
                || category == Biome.Category.MESA
                || (name != null && name.getPath().equals("dark_forest"))) {
            whiteChance = 20;
        }
        return this.random.nextInt(100) < whiteChance ? VARIANT_WHITE : VARIANT_BLACK;
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld world, DifficultyInstance difficulty,
            SpawnReason reason, @Nullable ILivingEntityData data, @Nullable CompoundNBT tag) {
        int variant;
        if (data instanceof GroupData) {
            variant = ((GroupData) data).variant;
        } else {
            variant = this.rollVariant(world);
            data = new GroupData(variant);
        }
        this.setVariant(variant);
        return super.finalizeSpawn(world, difficulty, reason, data, tag);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide && this.angerTicks > 0) {
            this.angerTicks--;
            if (this.angerTicks == 0) {
                this.setTarget(null);
            }
        }
    }

    @Override
    public ActionResultType mobInteract(PlayerEntity player, Hand hand) {
        if (hand != Hand.MAIN_HAND || !player.getItemInHand(hand).isEmpty()) {
            return super.mobInteract(player, hand);
        }
        if (this.level.isClientSide) {
            return ActionResultType.SUCCESS;
        }
        if (this.tickCount - this.lastInteractTick < 10) {
            return ActionResultType.CONSUME;
        }
        this.lastInteractTick = this.tickCount;

        long now = this.level.getGameTime();
        if (now >= this.limitResetTime) {
            this.giftLimit = 2 + this.random.nextInt(9);
            this.giftsGiven = 0;
            this.refusals = 0;
            this.limitResetTime = now + 24000L;
        }
        this.getLookControl().setLookAt(player, 30.0F, 30.0F);

        if (this.angerTicks > 0) {
            this.say(player, "angry");
            return ActionResultType.CONSUME;
        }
        if (this.giftsGiven < this.giftLimit) {
            this.giftsGiven++;
            this.giveGift(player);
            this.say(player, "gift");
        } else {
            this.refusals++;
            if (this.refusals <= 3) {
                this.say(player, "refuse" + this.refusals);
            } else {
                this.angerTicks = 6000;
                this.say(player, "angry");
                if (this.level.getDifficulty() != Difficulty.PEACEFUL) {
                    this.setTarget(player);
                }
            }
        }
        return ActionResultType.SUCCESS;
    }

    private void say(PlayerEntity player, String key) {
        player.displayClientMessage(new TranslationTextComponent("message.femboycompanion." + key), false);
    }

    private void giveGift(PlayerEntity player) {
        ItemStack stack = rollGift(this.random);
        ItemEntity item = new ItemEntity(this.level, this.getX(), this.getY() + 1.0D, this.getZ(), stack);
        Vector3d dir = player.position().subtract(this.position());
        Vector3d flat = new Vector3d(dir.x, 0.0D, dir.z).normalize().scale(0.25D);
        item.setDeltaMovement(flat.x, 0.2D, flat.z);
        item.setPickUpDelay(20);
        this.level.addFreshEntity(item);
    }

    private static ItemStack rollGift(Random random) {
        int r = random.nextInt(100);
        if (r < 65) {
            return pick(random, new Item[] {Items.BREAD, Items.APPLE, Items.CARROT, Items.COAL}, 3);
        }
        if (r < 85) {
            return pick(random, new Item[] {Items.GOLDEN_CARROT, Items.IRON_INGOT}, 2);
        }
        if (r < 95) {
            return pick(random, new Item[] {Items.GOLD_INGOT, Items.LAPIS_LAZULI}, 1);
        }
        if (r < 99) {
            return new ItemStack(Items.DIAMOND);
        }
        return new ItemStack(Items.GOLDEN_APPLE);
    }

    private static ItemStack pick(Random random, Item[] items, int maxCount) {
        return new ItemStack(items[random.nextInt(items.length)], 1 + random.nextInt(maxCount));
    }

    @Override
    protected void dropCustomDeathLoot(DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        int v = this.getVariant();
        double boost = v == VARIANT_BLACK ? 1.25D : 1.0D;
        if (this.random.nextDouble() < 0.20D * boost) {
            this.spawnAtLocation(new ItemStack(ModItems.ears(v)));
        }
        if (this.random.nextDouble() < 0.15D * boost) {
            this.spawnAtLocation(new ItemStack(ModItems.tail(v)));
        }
        if (this.random.nextDouble() < 0.20D * boost) {
            this.spawnAtLocation(new ItemStack(ModItems.paws(v)));
        }
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
