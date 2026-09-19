package net.yxiao233.industrialforegoingextra.compact.kubejs;

import com.hrznstudio.titanium.block.RotatableBlock;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.registry.BuilderTypeRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.yxiao233.industrialforegoingextra.compact.kubejs.items.*;
import net.yxiao233.industrialforegoingextra.compact.kubejs.schemas.*;
import net.yxiao233.industrialforegoingextra.util.TooltipHelper;

public class IFEKubeJSPlugin implements KubeJSPlugin {
    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("TooltipHelper", TooltipHelper.class);
        bindings.add("RotatableBlock", RotatableBlock.class);
    }

    @Override
    public void registerBuilderTypes(BuilderTypeRegistry registry) {
        registry.of(Registries.ITEM, reg ->{
            reg.add(getFromString("industrialforegoing:speed_addon"), SpeedAddonItemBuilder.class, SpeedAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoing:efficiency_addon"), EfficiencyAddonItemBuilder.class, EfficiencyAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoing:processing_addon"), ProcessingAddonItemBuilder.class, ProcessingAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoing:range_addon"), RangeAddonItemBuilder.class,RangeAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoingextra:apple_addon"), AppleAddonItemBuilder.class,AppleAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoingextra:heal_addon"), HealAddonItemBuilder.class,HealAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoingextra:energy_addon"), EnergyAddonItemBuilder.class,EnergyAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoingextra:thread"), ThreadAddonItemBuilder.class,ThreadAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoingextra:looting"), LootingAddonItemBuilder.class,LootingAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoingextra:fortune"), FortuneAddonItemBuilder.class,FortuneAddonItemBuilder::new);
            reg.add(getFromString("industrialforegoingextra:simulated_card"), SimulatedCardItemBuilder.class,SimulatedCardItemBuilder::new);
        });
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry event) {
        event.namespace("industrialforegoingextra")
                .register("infuser", InfuserSchema.SCHEMA)
                .register("arcane_dragon_egg_forging", ArcaneDragonEggForgingSchema.SCHEMA)
                .register("shaped", ShapedSchema.SCHEMA)
                .register("shapeless",ShapelessSchema.SCHEMA);

        event.namespace("industrialforegoing")
                .register("crusher", CrusherSchema.SCHEMA)
                .register("dissolution_chamber", DissolutionChamberSchema.SCHEMA)
                .register("fluid_extractor", FluidExtractorSchema.SCHEMA)
                .register("stonework_generate", StoneWorkGenerateSchema.SCHEMA);
    }


    private ResourceLocation getFromString(String rl){
        String nameSpace = rl.substring(0,rl.indexOf(":"));
        String location = rl.substring(rl.indexOf(":") + 1);
        return ResourceLocation.fromNamespaceAndPath(nameSpace,location);
    }
}
