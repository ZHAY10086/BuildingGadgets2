package com.direwolf20.buildinggadgets2.common.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.energy.IEnergyStorage;


import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityEnergyProvider implements ICapabilityProvider {
    private ItemStack stack;
    private int energyCapacity;
    private LazyOptional<IEnergyStorage> capability = LazyOptional.of(() -> new EnergisedItem(stack, energyCapacity));

    public CapabilityEnergyProvider(ItemStack stack, int energyCapacity) {
        this.stack = stack;
        this.energyCapacity = energyCapacity;
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ForgeCapabilities.ENERGY ? capability.cast() : LazyOptional.empty();
    }
}
