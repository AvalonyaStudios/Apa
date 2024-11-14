package eu.avalonya.api.command;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import eu.avalonya.api.AvalonyaAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BaseCommandTest {

    private ServerMock server;
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

    @ParameterizedTest
    @ValueSource(ints = {42, 10, 56, 65645})
    void testSetCooldown(int time) {
        BaseCommand<Player> command = new TestCommand();

        command.setCooldown(time);

        assertEquals(time, command.getCooldown());
    }

    @Test
    void testCancelCooldown() {
        BaseCommand<Player> command = new TestCommand();
        Player player = server.addPlayer();

        command.setCooldown(10);
        command.saveCooldown(player);

        assertTrue(command.inCooldown(player));

        command.cancelCooldown(player);

        assertFalse(command.inCooldown(player));
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