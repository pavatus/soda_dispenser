package dev.duzo.soda.core

import com.mojang.serialization.MapCodec
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.CarvedPumpkinBlock
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.util.ActionResult
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class SodaDispenserBlock(settings: Settings?) : HorizontalFacingBlock(settings) {
    companion object {
        private val CODEC: MapCodec<SodaDispenserBlock> = createCodec { settings -> SodaDispenserBlock(settings) }
    }

    init {
        this.defaultState = this.stateManager.defaultState.with(FACING, Direction.NORTH);
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hit: BlockHitResult?
    ): ActionResult {
        player?.inventory?.insertStack(SodaRegistry.instance.random().defaultStack)
        world?.playSound(null, pos ?: BlockPos.ORIGIN, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 1.0f, 1.0f)

        return ActionResult.CONSUME
    }

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        return defaultState.with(CarvedPumpkinBlock.FACING, (ctx?.horizontalPlayerFacing?.opposite ?: Direction.NORTH))
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(FACING)
    }

    override fun getCodec(): MapCodec<out HorizontalFacingBlock> {
        return CODEC
    }
}