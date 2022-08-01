package de.maxhenkel.miningdimension;

import de.maxhenkel.corelib.CommonRegistry;
import de.maxhenkel.miningdimension.block.BlockTeleporter;
import de.maxhenkel.miningdimension.config.ClientConfig;
import de.maxhenkel.miningdimension.config.ServerConfig;
import de.maxhenkel.miningdimension.tileentity.TileentityTeleporter;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Main.MODID)
public class Main {

    public static final String MODID = "mining_dimension";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    private static final DeferredRegister<Block> BLOCK_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);
    private static final DeferredRegister<Item> ITEM_REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public static final RegistryObject<BlockTeleporter> TELEPORTER = BLOCK_REGISTER.register("teleporter", BlockTeleporter::new);
    public static final RegistryObject<Item> TELEPORTER_ITEM = ITEM_REGISTER.register("teleporter", () -> TELEPORTER.get().toItem());

    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);
    public static final RegistryObject<BlockEntityType<TileentityTeleporter>> TELEPORTER_TILEENTITY = BLOCK_ENTITY_REGISTER.register("teleporter", () -> BlockEntityType.Builder.of(TileentityTeleporter::new, TELEPORTER.get()).build(null));

    public static ResourceKey<Level> MINING_DIMENSION;

    public static ServerConfig SERVER_CONFIG;
    public static ClientConfig CLIENT_CONFIG;

    public Main() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);

        SERVER_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.SERVER, ServerConfig.class, true);
        CLIENT_CONFIG = CommonRegistry.registerConfig(ModConfig.Type.CLIENT, ClientConfig.class);

        BLOCK_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        ITEM_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCK_ENTITY_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);

        MINING_DIMENSION = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(Main.MODID, "mining"));
    }


}