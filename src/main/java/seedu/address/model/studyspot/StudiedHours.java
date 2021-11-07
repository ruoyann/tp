package seedu.address.model.studyspot;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.commons.exceptions.IllegalValueException;

public class StudiedHours {
    public static final String MESSAGE_CONSTRAINTS = "Logged hours should only be a positive integer!";
    public static final String MESSAGE_INTEGER_OVERFLOW = "Given hours exceed integer limit!";
    public static final String MESSAGE_HOURS_IS_FULL = "The Study Spot has reached integer limit, please "
            + "reset or override!";

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
    public StudiedHours addHours(StudiedHours hours) throws IllegalValueException {
        if (hours.loggedHours == 0) {
            throw new IllegalValueException(MESSAGE_CONSTRAINTS);
        }
        long totalHours = (long) this.loggedHours + (long) hours.loggedHours;
        if (totalHours > Integer.MAX_VALUE) {
            throw new IllegalValueException(MESSAGE_HOURS_IS_FULL);
        }
        return new StudiedHours((int) totalHours);
    }

    /**
     * Returns integer that represents the number of studied hours
     * @return int
     */

    public int getHours() {
        return this.loggedHours;
    }

    @Override
    public String toString() {
        return String.valueOf(loggedHours);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StudiedHours // instanceof handles nulls
                && loggedHours == (((StudiedHours) other).loggedHours)); // state check
    }
}
