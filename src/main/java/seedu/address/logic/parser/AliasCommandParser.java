package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_ALIAS_ARGUMENTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ALIAS_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FLAG;
import static seedu.address.logic.parser.ParserUtil.arePrefixesPresent;

import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.AliasCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.Alias;

/**
 * Parses input arguments and creates a new AliasCommand object
 */
public class AliasCommandParser implements Parser<AliasCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AliasCommand
     * and returns a AliasCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AliasCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_ALIAS, PREFIX_ALIAS_COMMAND, PREFIX_FLAG);
        List<String> flagsList = argMultimap.getAllValues(PREFIX_FLAG);
        boolean isShowFlagPresent = ParserUtil.isFlagPresent(flagsList, AliasCommand.FLAG_SHOW);

        if (isShowFlagPresent) {
            return new AliasCommand(true);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_ALIAS, PREFIX_ALIAS_COMMAND)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE));
        }

        String userAlias = argMultimap.getValue(PREFIX_ALIAS).get();
        String aliasCommandPhrase = argMultimap.getValue(PREFIX_ALIAS_COMMAND).get();
        String aliasPhraseFlags = flagsList.isEmpty()
                ? ""
                : flagsList.stream().collect(Collectors.joining(" -", " -", ""));
        String aliasCommandPhraseWithFlags = aliasCommandPhrase + aliasPhraseFlags;

        Alias aliasToAdd;

        try {
            aliasToAdd = new Alias(userAlias, aliasCommandPhraseWithFlags);
        } catch (IllegalArgumentException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_ALIAS_ARGUMENTS, e.getMessage()));
        }

        return new AliasCommand(false, aliasToAdd);
    }
}
