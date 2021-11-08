package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT_SPOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPERATING_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Favourite;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudiedHours;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;


/**
 * Edits the details of an existing study spot in the study tracker.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the specified study spot (case-insensitive).\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Note that study hours cannot be changed with edit, use log command instead.\n"
            + "Parameters: "
            + PREFIX_EDIT_SPOT + "NAME* "
            + PREFIX_NAME + "NAME "
            + PREFIX_RATING + "RATING "
            + PREFIX_OPERATING_HOURS + "OPERATING HOURS "
            + PREFIX_ADDRESS + "ADDRESS "
            + "" + PREFIX_TAG + "TAG..."
            + "" + PREFIX_AMENITY + "NEW AMENITY... "
            + "" + PREFIX_REMOVE_TAG + "OLD TAG... "
            + "" + PREFIX_REMOVE_AMENITY + "OLD AMENITY... \n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_EDIT_SPOT + "tr3 "
            + PREFIX_RATING + "5 ";

    public static final String MESSAGE_EDIT_STUDYSPOT_SUCCESS = "Edited study spot: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDYSPOT = "This study spot already exists in the study tracker.";
    public static final String MESSAGE_REPEATED_COMMANDS =
            "There can only be one addition or removal instruction in each command.";
    public static final String MESSAGE_REMOVAL_CONSTRAINTS =
            "Only tags or amenities currently present can be removed.";
    public static final String MESSAGE_MISSING_REMOVAL_INPUT =
            "Please enter a existing tag or amenity to remove. E.g. rt/cold OR rm/wifi.";

    public static final String FIELD_TAG = "tag";
    public static final String FIELD_AMENITY = "amenity";

    private static final Logger logger = LogsCenter.getLogger(EditCommand.class);

    private final Name name;
    private final EditStudySpotDescriptor editStudySpotDescriptor;

    /**
     * @param name of the study spot in the study spot list to edit
     * @param editStudySpotDescriptor details to edit the study spot with
     */
    public EditCommand(Name name, EditStudySpotDescriptor editStudySpotDescriptor) {
        requireNonNull(name);
        requireNonNull(editStudySpotDescriptor);

        this.name = name;
        this.editStudySpotDescriptor = editStudySpotDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        logger.info("Executing Edit Command...");
        requireNonNull(model);

        StudySpot studySpotToEdit = model.findStudySpot(name);
        if (studySpotToEdit == null) {
            throw new CommandException(MESSAGE_INVALID_NAME);
        }

        StudySpot editedStudySpot = createEditedStudySpot(studySpotToEdit, editStudySpotDescriptor);

        if (!studySpotToEdit.isSameStudySpot(editedStudySpot) && model.hasStudySpot(editedStudySpot)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDYSPOT);
        }

        model.setStudySpot(studySpotToEdit, editedStudySpot);
        model.updateFilteredStudySpotList(PREDICATE_SHOW_ALL_STUDYSPOTS);
        return new CommandResult(String.format(MESSAGE_EDIT_STUDYSPOT_SUCCESS, editedStudySpot));
    }

    /**
     * Creates and returns a {@code StudySpot} with the details of {@code studySpotToEdit}
     * edited with {@code editStudySpotDescriptor}.
     */
    private static StudySpot createEditedStudySpot(StudySpot studySpotToEdit,
                                                   EditStudySpotDescriptor editStudySpotDescriptor)
            throws CommandException {
        assert studySpotToEdit != null;

        Name updatedName = editStudySpotDescriptor.getName().orElse(studySpotToEdit.getName());
        Rating updatedRating = editStudySpotDescriptor.getRating().orElse(studySpotToEdit.getRating());
        OperatingHours updatedOperatingHours = editStudySpotDescriptor.getOperatingHours()
                .orElse(studySpotToEdit.getOperatingHours());
        Address updatedAddress = editStudySpotDescriptor.getAddress().orElse(studySpotToEdit.getAddress());
        //Studied hours should not be changeable via Edit, so return same value
        StudiedHours studiedHours = studySpotToEdit.getStudiedHours();
        Set<Tag> updatedTags = editStudySpotDescriptor.updateTags(studySpotToEdit.getTags())
                .getTags().orElse(studySpotToEdit.getTags());
        Set<Amenity> updatedAmenities = editStudySpotDescriptor.updateAmenities(studySpotToEdit.getAmenities())
                .getAmenities().orElse(studySpotToEdit.getAmenities());
        Favourite updatedFavourite = editStudySpotDescriptor.getFavourite().orElse(studySpotToEdit.getFavourite());

        return new StudySpot(updatedName, updatedRating, updatedOperatingHours, updatedAddress, studiedHours,
                updatedFavourite, updatedTags, updatedAmenities);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return name.isSameNameCaseInsensitive(e.name)
                && editStudySpotDescriptor.equals(e.editStudySpotDescriptor);
    }

    /**
     * Stores the details to edit the study spot with. Each non-empty field value will replace the
     * corresponding field value of the study spot.
     */
    public static class EditStudySpotDescriptor {
        private Name name;
        private Rating rating;
        private OperatingHours operatingHours;
        private Address address;
        private Favourite favourite;
        private Set<Tag> tags;
        private Set<Tag> addedTags;
        private Set<Tag> removedTags;
        private Set<Amenity> amenities;
        private Set<Amenity> addedAmenities;
        private Set<Amenity> removedAmenities;

        public EditStudySpotDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} and {@code amenities} are used internally.
         */
        public EditStudySpotDescriptor(EditStudySpotDescriptor toCopy) {
            setName(toCopy.name);
            setRating(toCopy.rating);
            setOperatingHours(toCopy.operatingHours);
            setAddress(toCopy.address);
            setFavourite(toCopy.favourite);
            setTags(toCopy.tags);
            setAddedTags(toCopy.addedTags);
            setRemovedTags(toCopy.removedTags);
            setAmenities(toCopy.amenities);
            setAddedAmenities(toCopy.addedAmenities);
            setRemovedAmenities(toCopy.removedAmenities);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, rating, operatingHours, address, tags, addedTags, removedTags,
                    amenities, addedAmenities, removedAmenities);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public Optional<Rating> getRating() {
            return Optional.ofNullable(rating);
        }

        public void setOperatingHours(OperatingHours operatingHours) {
            this.operatingHours = operatingHours;
        }

        public Optional<OperatingHours> getOperatingHours() {
            return Optional.ofNullable(operatingHours);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setFavourite(Favourite favourite) {
            this.favourite = favourite;
        }

        public Optional<Favourite> getFavourite() {
            return Optional.ofNullable(favourite);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Sets {@code addedTags} to this object's {@code addedTags}.
         */
        public void setAddedTags(Set<Tag> addedTags) {
            this.addedTags = (addedTags != null) ? new HashSet<>(addedTags) : null;
        }

        /**
         * Sets {@code removedTags} to this object's {@code removedTags}.
         */
        public void setRemovedTags(Set<Tag> removedTags) {
            this.removedTags = (removedTags != null) ? new HashSet<>(removedTags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable tag set of added tags, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code addedTags} is null.
         */
        public Optional<Set<Tag>> getAddedTags() {
            return (addedTags != null) ? Optional.of(Collections.unmodifiableSet(addedTags)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable tag set of removed tags, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code removedTags} is null.
         */
        public Optional<Set<Tag>> getRemovedTags() {
            return (removedTags != null) ? Optional.of(Collections.unmodifiableSet(removedTags)) : Optional.empty();
        }

        /**
         * Sets {@code amenities} to this object's {@code amenities}.
         * A defensive copy of {@code amenities} is used internally.
         */
        public void setAmenities(Set<Amenity> amenities) {
            this.amenities = (amenities != null) ? new HashSet<>(amenities) : null;
        }

        /**
         * Sets {@code addedAmenities} to this object's {@code addedAmenities}.
         */
        public void setAddedAmenities(Set<Amenity> addedAmenities) {
            this.addedAmenities = (addedAmenities != null) ? new HashSet<>(addedAmenities) : null;
        }

        /**
         * Sets {@code removedAmenities} to this object's {@code removedAmenities}.
         */
        public void setRemovedAmenities(Set<Amenity> removedAmenities) {
            this.removedAmenities = (removedAmenities != null) ? new HashSet<>(removedAmenities) : null;
        }

        /**
         * Returns an unmodifiable amenity set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code amenities} is null.
         */
        public Optional<Set<Amenity>> getAmenities() {
            return (amenities != null) ? Optional.of(Collections.unmodifiableSet(amenities)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable amenity set of added amenities, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code addedAmenities} is null.
         */
        public Optional<Set<Amenity>> getAddedAmenities() {
            return (addedAmenities != null)
                    ? Optional.of(Collections.unmodifiableSet(addedAmenities)) : Optional.empty();
        }

        /**
         * Returns an unmodifiable amenity set of removed amenities, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code amenities} is null.
         */
        public Optional<Set<Amenity>> getRemovedAmenities() {
            return (removedAmenities != null)
                    ? Optional.of(Collections.unmodifiableSet(removedAmenities)) : Optional.empty();
        }

        /**
         * Returns a new EditStudySpotDescriptor, which updates the existing tags according to
         * the edit command provided.
         */
        public EditStudySpotDescriptor updateTags(Set<Tag> existingTags) throws CommandException {
            boolean repeatedTagPresent = checkRepeatedField(addedTags, removedTags, FIELD_TAG);
            if (repeatedTagPresent) {
                throw new CommandException(MESSAGE_REPEATED_COMMANDS);
            }

            // case where all tags are cleared
            if (addedTags != null && addedTags.isEmpty()) {
                setTags(addedTags);
                return this;
            }

            // case where input to rt/ is empty
            if (removedTags != null && removedTags.isEmpty()) {
                throw new CommandException(MESSAGE_MISSING_REMOVAL_INPUT);
            }

            // case where setTags() is already used without setAddedTags() and setRemovedTags()
            if (addedTags == null && removedTags == null && tags != null) {
                return this;
            }

            Set<Tag> updatedTags = existingTags;
            if (addedTags != null) {
                updatedTags = combineTagsAdded(addedTags, updatedTags);
            }
            if (removedTags != null) {
                updatedTags = combineTagsRemoved(removedTags, updatedTags);
            }
            setTags(updatedTags);
            return this;
        }

        /**
         * Returns a new EditStudySpotDescriptor, which updates the existing amenities according to
         * the edit command provided.
         */
        public EditStudySpotDescriptor updateAmenities(Set<Amenity> existingAmenities) throws CommandException {
            boolean repeatedAmenityPresent = checkRepeatedField(addedAmenities, removedAmenities, FIELD_AMENITY);
            if (repeatedAmenityPresent) {
                throw new CommandException(MESSAGE_REPEATED_COMMANDS);
            }

            // case where all amenities are cleared
            if (addedAmenities != null && addedAmenities.isEmpty()) {
                setAmenities(addedAmenities);
                return this;
            }

            // case where input to rm/ is empty
            if (removedAmenities != null && removedAmenities.isEmpty()) {
                throw new CommandException(MESSAGE_MISSING_REMOVAL_INPUT);
            }

            Set<Amenity> updatedAmenities = existingAmenities;
            if (addedAmenities != null) {
                updatedAmenities = combineAmenitiesAdded(addedAmenities, updatedAmenities);
            }
            if (removedAmenities != null) {
                updatedAmenities = combineAmenitiesRemoved(removedAmenities, updatedAmenities);
            }
            setAmenities(updatedAmenities);
            return this;
        }

        /**
         * Returns an tag set which has {@code addedTags} added to {@code existingTags}.
         */
        public Set<Tag> combineTagsAdded(Set<Tag> addedTags, Set<Tag> existingTags) {
            Set<Tag> result = existingTags;
            for (Tag tag: addedTags) {
                if (!existingTags.contains(tag)) {
                    result = addField(result, tag);
                }
            }
            return result;
        }

        /**
         * Returns an tag set which has {@code tagsRemoved} removed from {@code existingTags}.
         */
        public Set<Tag> combineTagsRemoved(Set<Tag> tagsRemoved, Set<Tag> existingTags)
                throws CommandException {
            Set<Tag> result = existingTags;
            for (Tag tag: tagsRemoved) {
                if (!existingTags.contains(tag)) {
                    throw new CommandException(MESSAGE_REMOVAL_CONSTRAINTS);
                } else {
                    result = removeField(result, tag);
                }
            }
            return result;
        }

        /**
         * Returns an amenity set which has {@code addedAmenities} added to {@code existingAmenities}.
         */
        public Set<Amenity> combineAmenitiesAdded(Set<Amenity> addedAmenities, Set<Amenity> existingAmenities) {
            Set<Amenity> result = existingAmenities;
            for (Amenity amenity: addedAmenities) {
                if (!existingAmenities.contains(amenity)) {
                    result = addField(result, amenity);
                }
            }
            return result;
        }

        /**
         * Returns an amenity set which has {@code amenitiesRemoved} removed from {@code existingAmenities}.
         */
        public Set<Amenity> combineAmenitiesRemoved(Set<Amenity> amenitiesRemoved, Set<Amenity> existingAmenities)
                throws CommandException {
            Set<Amenity> result = existingAmenities;
            for (Amenity amenity: amenitiesRemoved) {
                if (!existingAmenities.contains(amenity)) {
                    throw new CommandException(MESSAGE_REMOVAL_CONSTRAINTS);
                } else {
                    result = removeField(result, amenity);
                }
            }
            return result;
        }

        private <T> boolean checkRepeatedField(Set<T> addedSet, Set<T> removedSet,
                                           String fieldType) {
            if (addedSet == null || removedSet == null) {
                return false;
            }

            switch (fieldType) {
            case FIELD_TAG:
                // It is safe to cast 'Set<Tag>' to 'Set<T>' as the method only goes to this case block
                // when addedSet is of type Set<Tag>.
                @SuppressWarnings("unchecked")
                Set<Tag> addedTags = (Set<Tag>) addedSet;
                for (Tag tag : addedTags) {
                    if (containField(removedSet, tag.tagName, FIELD_TAG)) {
                        return true;
                    }
                }
                break;
            case FIELD_AMENITY:
                // It is safe to cast 'Set<Amenity>' to 'Set<T>' as the method only goes to this case block
                // when addedSet is of type Set<Amenity>.
                @SuppressWarnings("unchecked")
                Set<Amenity> addedAmenities = (Set<Amenity>) addedSet;
                for (Amenity amenity : addedAmenities) {
                    if (containField(removedSet, amenity.amenityType, FIELD_AMENITY)) {
                        return true;
                    }
                }
                break;
            default:
                throw new AssertionError("error occured");
            }
            return false;
        }

        private <T> boolean containField(Set<T> set, String fieldTested, String fieldType) {
            switch (fieldType) {
            case FIELD_TAG:
                // It is safe to cast 'Set<Tag>' to 'Set<T>' as the method only goes to this case block
                // when set is of type Set<Tag>.
                @SuppressWarnings("unchecked")
                Set<Tag> tagSet = (Set<Tag>) set;
                for (Tag tag : tagSet) {
                    if (tag.tagName.equals(fieldTested)) {
                        return true;
                    }
                }
                break;
            case FIELD_AMENITY:
                // It is safe to cast 'Set<Amenity>' to 'Set<T>' as the method only goes to this case block
                // when set is of type Set<Amenity>.
                @SuppressWarnings("unchecked")
                Set<Amenity> amenitySet = (Set<Amenity>) set;
                for (Amenity amenity : amenitySet) {
                    if (amenity.amenityType.equals(fieldTested)) {
                        return true;
                    }
                }
                break;
            default:
                throw new AssertionError("error occured");
            }
            return false;
        }

        private <T> Set<T> addField(Set<T> existingFields, T addedField) {
            Set<T> result = new HashSet<>(existingFields);
            result.add(addedField);
            return result;
        }

        private <T> Set<T> removeField(Set<T> existingFields, T removedField) {
            Set<T> result = new HashSet<>();
            for (T field : existingFields) {
                if (!field.equals(removedField)) {
                    result.add(field);
                }
            }
            return result;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditStudySpotDescriptor)) {
                return false;
            }

            // state check
            EditStudySpotDescriptor e = (EditStudySpotDescriptor) other;
            return getName().equals(e.getName())
                    && getRating().equals(e.getRating())
                    && getOperatingHours().equals(e.getOperatingHours())
                    && getAddress().equals(e.getAddress())
                    && getFavourite().equals(e.getFavourite())
                    && getTags().equals(e.getTags())
                    && getAddedTags().equals(e.getAddedTags())
                    && getRemovedTags().equals(e.getRemovedTags())
                    && getAmenities().equals(e.getAmenities())
                    && getAddedAmenities().equals(e.getAddedAmenities())
                    && getRemovedAmenities().equals(e.getRemovedAmenities());
        }

    }
}
