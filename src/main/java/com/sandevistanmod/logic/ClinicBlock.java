package com.sandevistanmod.logic;

import com.sandevistanmod.data.Tx;
import com.sandevistanmod.registry.ModRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

/** Clinica de Ripperdoc: e aqui que o Sandevistan e implantado (paga em esmeraldas e causa dano). */
public class ClinicBlock extends Block {
    public ClinicBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
                                              net.minecraft.world.InteractionHand hand, BlockHitResult hit) {
        if (!stack.is(ModRegistries.SANDEVISTAN_ITEM.get())) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            if (SandevistanLogic.tryImplantAtClinic(sp) && !sp.getAbilities().instabuild) stack.shrink(1);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer sp) {
            sp.sendSystemMessage(Tx.t(ChatFormatting.AQUA, "clinic.info", SandevistanLogic.CLINIC_EMERALDS));
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
