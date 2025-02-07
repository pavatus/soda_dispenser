package dev.duzo.soda

import dev.duzo.soda.core.SodaDispenserBlock
import dev.duzo.soda.core.SodaRegistry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

class SodaMod : ModInitializer {
    companion object {
        const val MOD_ID = "soda"
        fun id(path: String): Identifier = Identifier.of(MOD_ID, path)

        fun registerBlockAndItem(block: Block, key: RegistryKey<Block>, itemSettings: Item.Settings?) : Pair<Block, BlockItem?> {
            Registry.register(Registries.BLOCK, key, block)
            val item = if (itemSettings != null) {
                val itemKey = RegistryKey.of(RegistryKeys.ITEM, key.value)
                Registry.register(Registries.ITEM, itemKey, BlockItem(block, itemSettings.registryKey(itemKey)))
            } else null

            return Pair(block, item)
        }

        var sodaDispenser: Block? = null
    }

    override fun onInitialize() {
        register()

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register { entries ->
            entries.add(sodaDispenser?.asItem())
        }

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register { entries ->
            SodaRegistry.instance.lookup.values.forEach { entries.add(it) }
        }
    }

    private fun register() {
        var key = RegistryKey.of(RegistryKeys.BLOCK, id("soda_dispenser"))
        sodaDispenser = registerBlockAndItem(SodaDispenserBlock(
            AbstractBlock.Settings.copy(
                Blocks.DISPENSER).registryKey(key)), key, Item.Settings()).first

        SodaRegistry.createSoda(id("pepsi"))
        SodaRegistry.createSoda(id("coke"))
    }
}
