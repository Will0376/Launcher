package pro.gravit.launchserver.components;

import pro.gravit.launchserver.LaunchServer;
import pro.gravit.utils.command.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandRemoverComponent extends Component implements AutoCloseable {
    public String[] removeList = new String[]{};
    public transient Map<String, Command> commandsList = new HashMap<>();

    @Override
    public void preInit(LaunchServer launchServer) {

    }

    @Override
    public void init(LaunchServer launchServer) {

    }

    @Override
    public void postInit(LaunchServer launchServer) {
        for (String cmd : removeList) {
            Command removedCmd = launchServer.commandHandler.unregisterCommand(cmd);
            if (removedCmd != null)
                commandsList.put(cmd, removedCmd);
        }
    }

    @Override
    public void close() {
        for (Map.Entry<String, Command> e : commandsList.entrySet()) {
            LaunchServer.server.commandHandler.registerCommand(e.getKey(), e.getValue());
        }
    }
}