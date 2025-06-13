package com.calemi.ccore.api.item;

import com.calemi.ccore.api.boat.CBoatType;
import com.calemi.ccore.api.dispense.CBoatDispenseItemBehavior;
import com.calemi.ccore.api.entity.CBoat;
import com.calemi.ccore.api.entity.CChestBoat;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class CBoatItem extends Item {

    private static final Predicate<Entity> ENTITY_PREDICATE = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    private final CBoatType type;
    private final boolean hasChest;

    public CBoatItem(CBoatType type, boolean hasChest, Properties properties) {
        super(properties);
        this.type = type;
        this.hasChest = hasChest;
        DispenserBlock.registerBehavior(this, new CBoatDispenseItemBehavior(hasChest, type));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {

        ItemStack itemstack = player.getItemInHand(hand);
        HitResult hitresult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);

        if (hitresult.getType() != HitResult.Type.MISS) {

            Vec3 viewVector = player.getViewVector(1.0F);
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(viewVector.scale(5.0D)).inflate(1.0D), ENTITY_PREDICATE);

            if (!list.isEmpty()) {

                Vec3 eyePosition = player.getEyePosition();

                for (Entity entity : list) {

                    AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());

                    if (aabb.contains(eyePosition)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (hitresult.getType() == HitResult.Type.BLOCK) {

                Boat boat;

                if (hasChest) {
                    boat = new CChestBoat(level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
                    ((CChestBoat) boat).setBoatType(type);
                }

                else {
                    boat = new CBoat(level, hitresult.getLocation().x, hitresult.getLocation().y, hitresult.getLocation().z);
                    ((CBoat) boat).setBoatType(type);
                }

                boat.setYRot(player.getYRot());

                if (!level.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(itemstack);
                }

                else {

                    if (!level.isClientSide) {

                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, hitresult.getLocation());

                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            }
        }

        return InteractionResultHolder.pass(itemstack);
    }
}
