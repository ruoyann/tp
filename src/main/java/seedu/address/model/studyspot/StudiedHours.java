package seedu.address.model.studyspot;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class StudiedHours {
    public static final String MESSAGE_CONSTRAINTS = "Logged hours should only be an integer!";

    //Regex should allow any integer value
    public static final String VALIDATION_REGEX = "^[0-9]\\d*$";
    public static final String DEFAULT_VALUE = "0";

    public final String value;

    private final int loggedHours;


    /**
     * Returns the number of studied hours based on the given {@code string}.
     */
    public StudiedHours(String loggedHours) {
        requireNonNull(loggedHours);
        checkArgument(isValidLoggedHours(loggedHours), MESSAGE_CONSTRAINTS);
        this.value = loggedHours;
        this.loggedHours = Integer.parseInt(loggedHours);
    }

    /**
     * Private constructor for purpose of addition with int.
     */
    private StudiedHours(int loggedHours) {
        this.loggedHours = loggedHours;
        this.value = Integer.toString(loggedHours);
    }

    /**
     * Checks if the given str is a valid input.
     */
    public static boolean isValidLoggedHours(String str) {
        requireNonNull(str);
        return str.matches(VALIDATION_REGEX);
    }

    /**
     * Adds hours of the given StudiedHours to the current object and returns a new StudiedHours object
     */
    public StudiedHours addHours(StudiedHours hours) {
        int totalHours = this.loggedHours + hours.loggedHours;
        return new StudiedHours(totalHours);
    }

    @Override
    public String toString() {
        return String.valueOf(loggedHours);
    }
}
