package net.yxiao233.industrialforegoingextra.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DragonEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.yxiao233.industrialforegoingextra.common.registry.IFEItems;
import net.yxiao233.industrialforegoingextra.util.BlockBoxHelper;
import org.jetbrains.annotations.NotNull;

public class DeadDragonEggBlock extends DragonEggBlock {
    public DeadDragonEggBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hitResult) {
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }


    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack stack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if(level.isClientSide()){
            return super.useItemOn(stack,state,level, pos, player, hand, hitResult);
        }

        if(stack.is(IFEItems.DRAGON_STAR)){
            level.setBlock(hitResult.getBlockPos(), Blocks.DRAGON_EGG.defaultBlockState(),3);
            return ItemInteractionResult.CONSUME;
        }else{
            return super.useItemOn(stack,state,level, pos, player, hand, hitResult);
        }
    }

    //TODO
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return new BlockBoxHelper("dead_dragon_egg").getVoxelShapes();
    }
}
