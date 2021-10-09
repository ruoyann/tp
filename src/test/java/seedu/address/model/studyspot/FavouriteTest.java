package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class FavouriteTest {

    @Test
    public void isValidFavourite() {
        // null favourite
        assertThrows(NullPointerException.class, () -> Favourite.isValidFavourite(null));

        // invalid favourite
        assertFalse(Favourite.isValidFavourite("false1"));
        assertFalse(Favourite.isValidFavourite("null"));
        assertFalse(Favourite.isValidFavourite("1"));
        assertFalse(Favourite.isValidFavourite("0"));

        // valid favourite
        assertTrue(Favourite.isValidFavourite("true"));
        assertTrue(Favourite.isValidFavourite("false"));
    }

    @Test
    public void equals() {
        Favourite favouriteTrue = new Favourite(true);
        Favourite favouriteFalse = new Favourite(false);

        // same object -> returns true
        assertTrue(favouriteTrue.equals(favouriteTrue));

        // same values -> returns true
        Favourite favouriteTrueCopy = new Favourite(true);
        assertTrue(favouriteTrue.equals(favouriteTrueCopy));

        // different types -> returns false
        assertFalse(favouriteTrue.equals(1));

        // null -> returns false
        assertFalse(favouriteTrue.equals(null));

        // different favourite -> returns false
        assertFalse(favouriteTrue.equals(favouriteFalse));
    }
}
