package me.kall.clientchatclean;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(Main.MOD_ID)
public final class Main {
    public static final String MOD_ID = "clientchatclean";

    public Main(FMLJavaModLoadingContext context) {
        if (!FMLLoader.getDist().isClient()) return;
        context.registerConfig(ModConfig.Type.CLIENT, CONFIG);
        MinecraftForge.EVENT_BUS.addListener((ClientChatEvent event) -> {
            if (event.getMessage().equals(DELETION.get())) {
                event.setCanceled(true);
                Minecraft.getInstance().gui.getChat().clearMessages(true);
            }
        });
    }

    public static final ForgeConfigSpec CONFIG;
    public static final ForgeConfigSpec.ConfigValue<? extends String> DELETION;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("ClientChatClean");
        DELETION = builder.define("DeletionMessage", "123");
        builder.pop();
        CONFIG = builder.build();
    }
}
