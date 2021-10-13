package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.studyspot.StudySpot;

/**
 * Adds a StudySpot to the Study Tracker.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a study spot to the study tracker. \n"
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_RATING + "RATING "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_AMENITY + "AMENITY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "COM1 "
            + PREFIX_RATING + "5 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "SoC "
            + PREFIX_TAG + "cold "
            + PREFIX_TAG + "quiet"
            + PREFIX_AMENITY + "wifi";

    public static final String MESSAGE_SUCCESS = "New study spot added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDYSPOT = "This study spot already exists in the study tracker";

    private final StudySpot toAdd;

    /**
     * Creates an AddCommand to add the specified {@code StudySpot}
     */
    public AddCommand(StudySpot studySpot) {
        requireNonNull(studySpot);
        toAdd = studySpot;
    }

    /**
     * Creates a default AddCommand for {@code CommandList}
     */
    public AddCommand() {
        toAdd = null;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudySpot(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDYSPOT);
        }

        model.addStudySpot(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }

    @Override
    public String toString() {
        return "Add";
    }

    @Override
    public String getCommandUsage() {
        return MESSAGE_USAGE;
    }
}
