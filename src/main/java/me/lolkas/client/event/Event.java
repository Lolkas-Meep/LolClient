package me.lolkas.client.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {
    public static class Packet{
        static Map<PacketEvents, List<Handler>> HANDLERS = new HashMap<>();
        public static void registerEventHandler(PacketEvents event, Handler handler){
            if(!HANDLERS.containsKey(event)) HANDLERS.put(event, new ArrayList<>());
            HANDLERS.get(event).add(handler);
        }

        public static boolean fireEvent(PacketEvents event, PacketEvent arg){
            if(HANDLERS.containsKey(event)){
                for (Handler handler : HANDLERS.get(event)){
                    handler.onFired(arg);
                }
            }
            return arg.isCancelled();
        }
        public interface Handler {
            void onFired(PacketEvent event);
        }
    }
}
