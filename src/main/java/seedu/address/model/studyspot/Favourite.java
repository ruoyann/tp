package seedu.address.model.studyspot;

import static java.util.Objects.requireNonNull;

/**
 * Represents if a StudySpot is a favourite in the study tracker.
 * Guarantees: immutable;
 */
public class Favourite {

    public static final String MESSAGE_CONSTRAINTS =
            "Favourites should only be true or false, and it should not be blank";

    public final boolean isFavourite;

    /**
     * Constructs a {@code Favourite}.
     *
     * @param isFavourite represents if a Study Spot is a favourite.
     */
    public Favourite(boolean isFavourite) {
        requireNonNull(isFavourite);
        this.isFavourite = isFavourite;
    }

    /**
     * Returns boolean that represents if a Study Spot is a favourite.
     * @return boolean
     */
    public boolean isFavourite() {
        return isFavourite;
    }

    @Override
    public String toString() {
        return String.valueOf(isFavourite);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite // instanceof handles nulls
                && isFavourite == ((Favourite) other).isFavourite); // state check
    }

}
