package me.kall.clientchatclean;

import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.event.ClientChatEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Main.MOD_ID)
public final class Main {
    public static final String MOD_ID = "clientchatclean";

    public Main(IEventBus modEventBus, Dist dist, ModContainer container) {
        if (!FMLLoader.getDist().isClient()) return;
        container.registerConfig(ModConfig.Type.CLIENT, CONFIG);
        NeoForge.EVENT_BUS.addListener((ClientChatEvent event) -> {
            if (event.getMessage().equals(DELETION.get())) {
                event.setCanceled(true);
                Minecraft.getInstance().gui.getChat().clearMessages(true);
            }
        });
    }

    public static final ModConfigSpec CONFIG;
    public static final ModConfigSpec.ConfigValue<? extends String> DELETION;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        builder.push("ClientChatClean");
        DELETION = builder.define("DeletionMessage", "123");
        builder.pop();
        CONFIG = builder.build();
    }
}
