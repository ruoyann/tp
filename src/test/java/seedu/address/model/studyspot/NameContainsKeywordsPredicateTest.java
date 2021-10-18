package seedu.address.model.studyspot;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.StudySpotBuilder;

public class NameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        NameContainsKeywordsPredicate firstPredicate = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        NameContainsKeywordsPredicate secondPredicate = new NameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NameContainsKeywordsPredicate firstPredicateCopy = new NameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different study spot -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.singletonList("COM2"));
        assertTrue(predicate.test(new StudySpotBuilder().withName("COM2 Hangout").build()));

        // Multiple keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("COM2", "Hangout"));
        assertTrue(predicate.test(new StudySpotBuilder().withName("COM2 Hangout").build()));

        // Only one matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("BIZ", "COM2"));
        assertTrue(predicate.test(new StudySpotBuilder().withName("BIZ Library").build()));

        // Mixed-case keywords
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("cOM2", "tEcH"));
        assertTrue(predicate.test(new StudySpotBuilder().withName("COM2 Hangout").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        NameContainsKeywordsPredicate predicate = new NameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new StudySpotBuilder().withName("Frontier").build()));

        // Non-matching keyword
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("Deck"));
        assertFalse(predicate.test(new StudySpotBuilder().withName("Frontier Canteen").build()));

        // Keywords match rating, operating hours and address, but does not match name
        predicate = new NameContainsKeywordsPredicate(Arrays.asList("4", "0900-2200", "0900-1800", "NUS", "Science"));
        assertFalse(predicate.test(new StudySpotBuilder().withName("Frontier").withRating("4")
                .withOperatingHours("0900-2200, 0900-1800").withAddress("NUS Science").build()));
    }
}
