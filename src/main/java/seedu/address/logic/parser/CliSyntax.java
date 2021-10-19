package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_RATING = new Prefix("r/");
    public static final Prefix PREFIX_OPERATING_HOURS = new Prefix("o/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMOVE_TAG = new Prefix("rt/");
    public static final Prefix PREFIX_AMENITY = new Prefix("m/");
    public static final Prefix PREFIX_REMOVE_AMENITY = new Prefix("rm/");
    public static final Prefix PREFIX_EDIT_SPOT = new Prefix("spot/");
    public static final Prefix PREFIX_FLAG = new Prefix("-");
    public static final Prefix PREFIX_DELETE_SPOT = new Prefix("n/");
    public static final Prefix PREFIX_ALIAS = new Prefix("al/");
    public static final Prefix PREFIX_ALIAS_COMMAND = new Prefix("cmd/");
    public static final Prefix PREFIX_HOURS = new Prefix("hr/");

}
