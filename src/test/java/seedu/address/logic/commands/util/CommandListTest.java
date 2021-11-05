package seedu.address.logic.commands.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

class CommandListTest {

    @Test
    void getCommandToUsageMapping() {
        // test constructor
        CommandList cl = new CommandList();
        assertNotNull(cl);

        HashMap<String, String> commands = CommandList.getCommandToUsageMapping();

        assertTrue(commands.containsKey("list"));
        assertTrue(commands.containsKey("log"));

        assertFalse(commands.containsKey("unknown"));
    }
}
