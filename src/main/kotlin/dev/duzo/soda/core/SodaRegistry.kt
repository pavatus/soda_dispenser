package dev.duzo.soda.core

import net.minecraft.component.DataComponentTypes
import net.minecraft.component.type.ConsumableComponents
import net.minecraft.component.type.FoodComponent
import net.minecraft.component.type.PotionContentsComponent
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.util.Identifier

class SodaRegistry {
    companion object {
        val instance = SodaRegistry()

        fun createSoda(id: Identifier, registerItem: Boolean = true, registerLookup : Boolean = true) : Item {
            val key = RegistryKey.of(RegistryKeys.ITEM, id)
            val soda = Item(Item.Settings().registryKey(key).maxCount(1)
                .component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)
                .component(DataComponentTypes.CONSUMABLE, ConsumableComponents.DRINK))

            if (registerItem) {
                Registry.register(Registries.ITEM, key, soda)
            }
            if (registerLookup) {
                instance.lookup[id] = soda
            }

            return soda
        }
    }

    val lookup = mutableMapOf<Identifier, Item>()

    fun random() : Item {
        return lookup.values.random()
    }
}