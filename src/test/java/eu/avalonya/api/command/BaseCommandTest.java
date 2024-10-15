package eu.avalonya.api.command;

import be.seeseemelk.mockbukkit.MockBukkit;
import eu.avalonya.api.AvalonyaAPI;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BaseCommandTest {

    private Server server;
    private JavaPlugin plugin;

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(AvalonyaAPI.class);

        new TestCommand().register(plugin);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void testBasicCommandExist() {
        assertNotNull(server.getCommandMap().getCommand("test"));
    }

    public static class TestCommand extends BaseCommand<Player> {

        /**
         * Constructors
         */
        public TestCommand() {
            super("test");
        }

        @Override
        public void run(Player sender, ArgumentCollection args) {
            sender.sendMessage("Hello");
        }
    }

}