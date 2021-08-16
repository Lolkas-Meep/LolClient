package me.lolkas.client.funStuff.modules.smallMods.alt;

import com.mojang.authlib.Agent;
import com.mojang.authlib.AuthenticationService;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Session;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Alt {
    private static UserAuthentication auth;
    private static Collection collection = new ArrayList();

    public static void onEnable(){
        UUID uuid = UUID.randomUUID();
        AuthenticationService authService = new YggdrasilAuthenticationService(MinecraftClient.getInstance().getNetworkProxy(), uuid.toString());
        auth = authService.createUserAuthentication(Agent.MINECRAFT);
        authService.createMinecraftSessionService();
    }

    public static void setUser(String name){
        auth.logOut();
        Session session = new Session(name, name, "0", "legacy");
        try {
            setSession(session);
        } catch (Exception e) {
        }
    }
    public static void setSession(Session s) throws Exception {
        Class<? extends MinecraftClient> mc = MinecraftClient.getInstance().getClass();
        try {
            Field session = null;

            for (Field f : mc.getDeclaredFields()) {
                if (f.getType().isInstance(s)) {
                    session = f;
                    System.out.println("Found field " + f.toString() + ", injecting...");
                }
            }

            if (session == null) {
                throw new IllegalStateException("No field of type " + Session.class.getCanonicalName() + " declared.");
            }

            session.setAccessible(true);
            session.set(MinecraftClient.getInstance(), s);
            session.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void saveAlt(String name){
        collection.add(name);
    }
}
