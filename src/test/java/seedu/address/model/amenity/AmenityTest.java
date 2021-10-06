package seedu.address.model.amenity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AmenityTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Amenity(null));
    }

    @Test
    public void constructor_invalidAmenityType_throwsIllegalArgumentException() {
        String invalidAmenityType = "";
        assertThrows(IllegalArgumentException.class, () -> new Amenity(invalidAmenityType));
    }

    @Test
    public void isValidAmenityType() {
        // null amenity type
        assertThrows(NullPointerException.class, () -> Amenity.isValidAmenityType(null));

        // invalid amenity type
        assertFalse(Amenity.isValidAmenityType("carpark"));
    }

    @Test
    public void listAllAmenityTypes() {
        String expectedOutcome = "";

        // one amenity type
        String[] singleAmenityType = {"wifi"};
        expectedOutcome = "wifi";

        assertEquals(expectedOutcome, Amenity.listAllAmenityTypes(singleAmenityType));

        // multiple amenity types
        String[] multipleAmenityTypes = {"wifi", "charger", "food", "aircon"};
        expectedOutcome = "wifi, charger, food, aircon";

        assertEquals(expectedOutcome, Amenity.listAllAmenityTypes(multipleAmenityTypes));
    }

}
