package snownee.jade.addon.vanilla;

import java.util.Map;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.animal.frog.Tadpole;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrewingStandBlock;
import net.minecraft.world.level.block.ChiseledBookShelfBlock;
import net.minecraft.world.level.block.CommandBlock;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.JukeboxBlock;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.NoteBlock;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.entity.CommandBlockEntity;
import net.minecraft.world.level.block.entity.ComparatorBlockEntity;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.entity.JukeboxBlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import snownee.jade.JadeClient;
import snownee.jade.addon.harvest.HarvestToolProvider;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.Identifiers;
import snownee.jade.api.WailaPlugin;
import snownee.jade.overlay.DatapackBlockManager;
import snownee.jade.util.ClientPlatformProxy;
import snownee.jade.util.PlatformProxy;

@WailaPlugin
public class VanillaPlugin implements IWailaPlugin {

	public static IWailaClientRegistration CLIENT_REGISTRATION;

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(BrewingStandProvider.INSTANCE, BrewingStandBlockEntity.class);
		registration.registerBlockDataProvider(BeehiveProvider.INSTANCE, BeehiveBlockEntity.class);
		registration.registerBlockDataProvider(CommandBlockProvider.INSTANCE, CommandBlockEntity.class);
		registration.registerBlockDataProvider(JukeboxProvider.INSTANCE, JukeboxBlockEntity.class);
		registration.registerBlockDataProvider(LecternProvider.INSTANCE, LecternBlockEntity.class);
		registration.registerBlockDataProvider(RedstoneProvider.INSTANCE, ComparatorBlockEntity.class);
		registration.registerBlockDataProvider(RedstoneProvider.INSTANCE, HopperBlockEntity.class);
		registration.registerBlockDataProvider(FurnaceProvider.INSTANCE, AbstractFurnaceBlockEntity.class);
		registration.registerBlockDataProvider(ChiseledBookshelfProvider.INSTANCE, ChiseledBookShelfBlockEntity.class);

		registration.registerEntityDataProvider(AnimalOwnerProvider.INSTANCE, Entity.class);
		registration.registerEntityDataProvider(PotionEffectsProvider.INSTANCE, LivingEntity.class);
		registration.registerEntityDataProvider(MobGrowthProvider.INSTANCE, AgeableMob.class);
		registration.registerEntityDataProvider(MobGrowthProvider.INSTANCE, Tadpole.class);
		registration.registerEntityDataProvider(MobBreedingProvider.INSTANCE, Animal.class);
		registration.registerEntityDataProvider(MobBreedingProvider.INSTANCE, Allay.class);
		registration.registerEntityDataProvider(ChickenEggProvider.INSTANCE, Chicken.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		CLIENT_REGISTRATION = registration;

		registration.addConfig(Identifiers.MC_EFFECTIVE_TOOL, true);
		registration.addConfig(Identifiers.MC_HARVEST_TOOL_NEW_LINE, false);
		registration.addConfig(Identifiers.MC_SHOW_UNBREAKABLE, true);
		registration.addConfig(Identifiers.MC_BREAKING_PROGRESS, true);
		registration.addConfig(Identifiers.MC_ANIMAL_OWNER_FETCH_NAMES, true);

		registration.addConfig(Identifiers.MC_ENTITY_ARMOR_MAX_FOR_RENDER, 40, 10, 100, false);
		registration.addConfig(Identifiers.MC_ENTITY_HEALTH_MAX_FOR_RENDER, 40, 10, 100, false);
		registration.addConfig(Identifiers.MC_ENTITY_HEALTH_ICONS_PER_LINE, 10, 1, 30, false);
		registration.addConfig(Identifiers.MC_ENTITY_HEALTH_SHOW_FRACTIONS, false);

		registration.registerBlockComponent(BlockStatesProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(BrewingStandProvider.INSTANCE, BrewingStandBlock.class);
		registration.registerEntityComponent(HorseStatsProvider.INSTANCE, AbstractHorse.class);
		registration.registerEntityComponent(ItemFrameProvider.INSTANCE, ItemFrame.class);
		registration.registerEntityComponent(PotionEffectsProvider.INSTANCE, LivingEntity.class);
		registration.registerEntityComponent(MobGrowthProvider.INSTANCE, AgeableMob.class);
		registration.registerEntityComponent(MobGrowthProvider.INSTANCE, Tadpole.class);
		registration.registerEntityComponent(MobBreedingProvider.INSTANCE, Animal.class);
		registration.registerEntityComponent(MobBreedingProvider.INSTANCE, Allay.class);
		registration.registerBlockComponent(TNTStabilityProvider.INSTANCE, TntBlock.class);
		registration.registerBlockComponent(BeehiveProvider.INSTANCE, BeehiveBlock.class);
		registration.registerBlockComponent(NoteBlockProvider.INSTANCE, NoteBlock.class);
		registration.registerEntityComponent(ArmorStandProvider.INSTANCE, ArmorStand.class);
		registration.registerEntityComponent(PaintingProvider.INSTANCE, Painting.class);
		registration.registerEntityComponent(ChickenEggProvider.INSTANCE, Chicken.class);
		registration.registerBlockComponent(HarvestToolProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(CommandBlockProvider.INSTANCE, CommandBlock.class);
		registration.registerBlockComponent(EnchantmentPowerProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(TotalEnchantmentPowerProvider.INSTANCE, EnchantmentTableBlock.class);
		registration.registerBlockComponent(PlayerHeadProvider.INSTANCE, AbstractSkullBlock.class);
		registration.registerBlockIcon(PlayerHeadProvider.INSTANCE, AbstractSkullBlock.class);
		registration.registerEntityComponent(VillagerProfessionProvider.INSTANCE, Villager.class);
		registration.registerEntityComponent(VillagerProfessionProvider.INSTANCE, ZombieVillager.class);
		registration.registerEntityComponent(ItemTooltipProvider.INSTANCE, ItemEntity.class);
		registration.registerBlockComponent(FurnaceProvider.INSTANCE, AbstractFurnaceBlock.class);
		registration.registerEntityComponent(AnimalOwnerProvider.INSTANCE, Entity.class);
		registration.registerEntityComponent(FallingBlockProvider.INSTANCE, FallingBlockEntity.class);
		registration.registerEntityIcon(FallingBlockProvider.INSTANCE, FallingBlockEntity.class);
		registration.registerEntityComponent(EntityHealthProvider.INSTANCE, LivingEntity.class);
		registration.registerEntityComponent(EntityArmorProvider.INSTANCE, LivingEntity.class);
		registration.registerBlockComponent(RedstoneProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(CropProgressProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(JukeboxProvider.INSTANCE, JukeboxBlock.class);
		registration.registerBlockComponent(LecternProvider.INSTANCE, LecternBlock.class);
		registration.registerBlockComponent(MobSpawnerProvider.INSTANCE, SpawnerBlock.class);
		registration.registerEntityComponent(MobSpawnerProvider.INSTANCE, MinecartSpawner.class);
		registration.registerBlockComponent(ChiseledBookshelfProvider.INSTANCE, ChiseledBookShelfBlock.class);
		registration.registerBlockIcon(ChiseledBookshelfProvider.INSTANCE, ChiseledBookShelfBlock.class);

		ClientPlatformProxy.registerReloadListener(HarvestToolProvider.INSTANCE);

		registration.addRayTraceCallback(-10, JadeClient::builtInOverrides);
		registration.addRayTraceCallback(5000, DatapackBlockManager::override);
		registration.addAfterRenderCallback(JadeClient::drawBreakingProgress);

		registration.markAsClientFeature(Identifiers.MC_EFFECTIVE_TOOL);
		registration.markAsClientFeature(Identifiers.MC_HARVEST_TOOL_NEW_LINE);
		registration.markAsClientFeature(Identifiers.MC_SHOW_UNBREAKABLE);
		registration.markAsClientFeature(Identifiers.MC_BREAKING_PROGRESS);
		registration.markAsClientFeature(Identifiers.MC_ENTITY_ARMOR_MAX_FOR_RENDER);
		registration.markAsClientFeature(Identifiers.MC_ENTITY_HEALTH_MAX_FOR_RENDER);
		registration.markAsClientFeature(Identifiers.MC_ENTITY_HEALTH_ICONS_PER_LINE);
		registration.markAsClientFeature(Identifiers.MC_ENTITY_HEALTH_SHOW_FRACTIONS);
		registration.markAsClientFeature(Identifiers.MC_BLOCK_STATES);
		registration.markAsClientFeature(Identifiers.MC_HORSE_STATS);
		registration.markAsClientFeature(Identifiers.MC_ITEM_FRAME);
		registration.markAsClientFeature(Identifiers.MC_TNT_STABILITY);
		registration.markAsClientFeature(Identifiers.MC_NOTE_BLOCK);
		registration.markAsClientFeature(Identifiers.MC_ARMOR_STAND);
		registration.markAsClientFeature(Identifiers.MC_PAINTING);
		registration.markAsClientFeature(Identifiers.MC_HARVEST_TOOL);
		registration.markAsClientFeature(Identifiers.MC_ENCHANTMENT_POWER);
		registration.markAsClientFeature(Identifiers.MC_TOTAL_ENCHANTMENT_POWER);
		registration.markAsClientFeature(Identifiers.MC_PLAYER_HEAD);
		registration.markAsClientFeature(Identifiers.MC_VILLAGER_PROFESSION);
		registration.markAsClientFeature(Identifiers.MC_ITEM_TOOLTIP);
		registration.markAsClientFeature(Identifiers.MC_ANIMAL_OWNER);
		registration.markAsClientFeature(Identifiers.MC_ENTITY_HEALTH);
		registration.markAsClientFeature(Identifiers.MC_ENTITY_ARMOR);
		registration.markAsClientFeature(Identifiers.MC_CROP_PROGRESS);
		registration.markAsClientFeature(Identifiers.MC_MOB_SPAWNER);

		registration.hideTarget(EntityType.AREA_EFFECT_CLOUD);
		registration.hideTarget(EntityType.FIREWORK_ROCKET);
		registration.hideTarget(Blocks.BARRIER);
		registration.usePickedResult(EntityType.BOAT);
		registration.usePickedResult(EntityType.CHEST_BOAT);
	}

	private static final Cache<BlockState, BlockState> CHEST_CACHE = CacheBuilder.newBuilder().build();

	public static BlockState getCorrespondingNormalChest(BlockState state) {
		try {
			return CHEST_CACHE.get(state, () -> {
				ResourceLocation trappedName = PlatformProxy.getId(state.getBlock());
				if (trappedName.getPath().startsWith("trapped_")) {
					ResourceLocation chestName = new ResourceLocation(trappedName.getNamespace(), trappedName.getPath().substring(8));
					Block block = BuiltInRegistries.BLOCK.get(chestName);
					if (block != null) {
						return copyProperties(state, block.defaultBlockState());
					}
				}
				return state;
			});
		} catch (Exception e) {
			return state;
		}
	}

	@SuppressWarnings("unchecked")
	private static <T extends Comparable<T>> BlockState copyProperties(BlockState oldState, BlockState newState) {
		for (Map.Entry<Property<?>, Comparable<?>> entry : oldState.getValues().entrySet()) {
			Property<T> property = (Property<T>) entry.getKey();
			if (newState.hasProperty(property))
				newState = newState.setValue(property, property.getValueClass().cast(entry.getValue()));
		}
		return newState;
	}
}
