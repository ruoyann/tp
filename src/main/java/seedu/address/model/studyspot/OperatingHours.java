package seedu.address.model.studyspot;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a StudySpot's operating hours in the study tracker.
 * Guarantees: immutable; is valid as declared in {@link #isValidOperatingHours(String)}
 */
public class OperatingHours {

    public static final String MESSAGE_CONSTRAINTS = "Operating hours are in the format: "
            + "[HHmm-HHmm, HHmm-HHmm], "
            + "where the first argument represents operating hours in the weekdays, "
            + "while the second argument represents operating hours in the weekends.";
    public static final String DEFAULT_OPERATING_HOURS = "-";

    /** Ensures the time provided is valid and in the format hh:mm. */
    public static final String TIME_VALIDATION_REGEX = "([01]?[0-9]|2[0-3])[0-5][0-9]";
    public static final int TIME_LENGTH = 4;

    public final String value;
    public final String weekdaysValue;
    public final String weekendsValue;

    /**
     * Constructs an {@code OperatingHours}.
     *
     * @param operatingHours A valid operating hours.
     */
    public OperatingHours(String operatingHours) {
        requireNonNull(operatingHours);
        checkArgument(isValidOperatingHours(operatingHours), MESSAGE_CONSTRAINTS);

        value = operatingHours;

        if (operatingHours.equals(DEFAULT_OPERATING_HOURS)) {
            weekdaysValue = DEFAULT_OPERATING_HOURS;
            weekendsValue = DEFAULT_OPERATING_HOURS;
        } else {
            String[] separatedOperatingHours = operatingHours.split(",", 2);
            weekdaysValue = separatedOperatingHours[0].trim();
            weekendsValue = separatedOperatingHours[1].trim();
        }

    }

    /**
     * Constructs an OperatingHours with no value.
     */
    private OperatingHours() {
        value = DEFAULT_OPERATING_HOURS;
        weekdaysValue = DEFAULT_OPERATING_HOURS;
        weekendsValue = DEFAULT_OPERATING_HOURS;
    }

    /**
     * Returns if a given string is a valid operating hours.
     */
    public static boolean isValidOperatingHours(String test) {
        if (test.equals(DEFAULT_OPERATING_HOURS)) {
            return true;
        }

        String[] separatedOperatingHours = test.split(",", 2);
        if (separatedOperatingHours.length == 1) {
            return false;
        }
        return isValidOperatingHour(separatedOperatingHours[0].trim())
                && isValidOperatingHour(separatedOperatingHours[1].trim());
    }

    private static boolean isValidOperatingHour(String test) {
        String[] separatedOperatingHour = test.split("-", 2);
        if (separatedOperatingHour.length == 1) {
            return false;
        }

        String openingHours = separatedOperatingHour[0].trim();
        String closingHours = separatedOperatingHour[1].trim();
        return openingHours.length() == TIME_LENGTH
                && openingHours.matches(TIME_VALIDATION_REGEX)
                && closingHours.length() == TIME_LENGTH
                && closingHours.matches(TIME_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        if (value.equals(DEFAULT_OPERATING_HOURS)) {
            return DEFAULT_OPERATING_HOURS;
        }
        return "Weekdays: " + weekdaysValue + ", Weekends: " + weekendsValue;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof OperatingHours // instanceof handles nulls
                && value.equals(((OperatingHours) other).value) // state check
                && weekdaysValue.equals(((OperatingHours) other).weekdaysValue)
                && weekendsValue.equals(((OperatingHours) other).weekendsValue));
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    public static OperatingHours emptyOperatingHours() {
        return new OperatingHours();
    }

}
