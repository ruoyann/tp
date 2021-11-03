package seedu.address.logic.commands.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CommandListTest {

    @Test
    void getCommandToUsageMapping() {
        HashMap<String, String> commands = CommandList.getCommandToUsageMapping();

        assertTrue(commands.containsKey("list"));
        assertTrue(commands.containsKey("log"));

        assertFalse(commands.containsKey("unknown"));
    }
}
