package com.anar4732.croodaceous.common.blocks;

import com.anar4732.croodaceous.registry.CEItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RamuNestBlock extends Block {
	private static final VoxelShape SHAPE = Block.box(0D, 0D, 0D, 16.0D, 8.0D, 16.0D);
	public static final BooleanProperty WITH_EGG = BooleanProperty.create("with_egg");
	
	public RamuNestBlock() {
		super(BlockBehaviour.Properties.of(Material.GRASS, MaterialColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS));
		this.registerDefaultState(this.stateDefinition.any().setValue(WITH_EGG, Boolean.FALSE));
	}
	
	@Override
	public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
		if (pLevel.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			if (pState.getValue(WITH_EGG)) {
				pLevel.setBlock(pPos, this.getStateDefinition().any().setValue(WITH_EGG, Boolean.FALSE), 3);
				pPlayer.addItem(new ItemStack(CEItems.RAMU_EGG.get()));
				return InteractionResult.CONSUME;
			} else {
				if (pPlayer.getMainHandItem().getItem() == CEItems.RAMU_EGG.get()) {
					pLevel.setBlock(pPos, this.getStateDefinition().any().setValue(WITH_EGG, Boolean.TRUE), 3);
					pPlayer.getMainHandItem().shrink(1);
					return InteractionResult.CONSUME;
				} else {
					return InteractionResult.PASS;
				}
			}
		}
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
		pBuilder.add(WITH_EGG);
	}
	
	@Override
	public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
		return SHAPE;
	}
	
	@Override
	public boolean skipRendering(BlockState pState, BlockState pAdjacentBlockState, Direction pSide) {
		return false;
	}
	
}