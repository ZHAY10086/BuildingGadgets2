package com.direwolf20.buildinggadgets2.common.items;

import com.direwolf20.buildinggadgets2.util.BuildingUtils;
import com.direwolf20.buildinggadgets2.util.GadgetNBT;
import com.direwolf20.buildinggadgets2.util.GadgetUtils;
import com.direwolf20.buildinggadgets2.util.VectorHelper;
import com.direwolf20.buildinggadgets2.util.modes.BuildToMe;
import com.direwolf20.buildinggadgets2.util.modes.StatePos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;

public class GadgetBuilding extends BaseGadget {
    public GadgetBuilding() {
        super(new Item.Properties());
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack gadget = player.getItemInHand(hand);
        if (level.isClientSide()) //No client
            return InteractionResultHolder.success(gadget);

        BlockHitResult lookingAt = VectorHelper.getLookingAt(player, ClipContext.Fluid.NONE);

        if (player.isShiftKeyDown()) {
            BlockState blockState = level.getBlockState(lookingAt.getBlockPos());
            if (GadgetUtils.setBlockState(gadget, blockState))
                return InteractionResultHolder.success(gadget);
            return InteractionResultHolder.pass(gadget);
        }

        BlockState setState = GadgetNBT.getGadgetBlockState(gadget);
        if (setState.isAir()) return InteractionResultHolder.pass(gadget);

        BuildToMe buildToMe = new BuildToMe();
        ArrayList<StatePos> buildList = buildToMe.collect(lookingAt.getDirection(), player, lookingAt.getBlockPos(), setState);

        BuildingUtils.build(level, buildList, setState, lookingAt.getBlockPos());

        return InteractionResultHolder.success(gadget);
    }
}