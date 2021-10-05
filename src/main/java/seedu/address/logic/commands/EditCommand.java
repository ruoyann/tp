package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EDIT_SPOT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_AMENITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_STUDYSPOTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Email;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;


/**
 * Edits the details of an existing study spot in the study tracker.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the study spot identified "
            + "by its name. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_EDIT_SPOT + "NAME] (non-case sensitive) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_RATING + "RATING] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_TAG + "TAG]..."
            + "[" + PREFIX_AMENITY + "NEW AMENITY]..."
            + "[" + PREFIX_REMOVE_AMENITY + "OLD AMENITY]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_RATING + "4"
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_STUDYSPOT_SUCCESS = "Edited study spot: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_STUDYSPOT = "This study spot already exists in the study tracker.";
    public static final String MESSAGE_REPEATED_COMMANDS =
            "There can only be one addition or removal instruction in each command.";

    private Index index;
    private Name name;
    private final EditStudySpotDescriptor editStudySpotDescriptor;

    /**
     * @param index of the study spot in the filtered study spot list to edit
     * @param editStudySpotDescriptor details to edit the study spot with
     */
    public EditCommand(Index index, EditStudySpotDescriptor editStudySpotDescriptor) {
        requireNonNull(index);
        requireNonNull(editStudySpotDescriptor);

        this.index = index;
        this.editStudySpotDescriptor = new EditStudySpotDescriptor(editStudySpotDescriptor);
    }

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
        requireNonNull(model);
        List<StudySpot> lastShownList = model.getFullList();

        boolean isPresent = false;
        StudySpot studySpotToEdit = null;
        for (StudySpot current: lastShownList) {
            if (current.isSameName(name)) {
                studySpotToEdit = current;
                isPresent = true;
                break;
            }
        }

        if (!isPresent) {
            throw new CommandException(Messages.MESSAGE_INVALID_EDIT_NAME);
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
        Email updatedEmail = editStudySpotDescriptor.getEmail().orElse(studySpotToEdit.getEmail());
        Address updatedAddress = editStudySpotDescriptor.getAddress().orElse(studySpotToEdit.getAddress());
        Set<Tag> updatedTags = editStudySpotDescriptor.getTags().orElse(studySpotToEdit.getTags());
        Set<Amenity> updatedAmenities = editStudySpotDescriptor.updateAmenities(studySpotToEdit.getAmenities())
                .getAmenities().orElse(studySpotToEdit.getAmenities());

        return new StudySpot(updatedName, updatedRating, updatedEmail, updatedAddress, updatedTags, updatedAmenities);
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
        private Email email;
        private Address address;
        private Set<Tag> tags;
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
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setAmenities(toCopy.amenities);
            setAddedAmenities(toCopy.addedAmenities);
            setRemovedAmenities(toCopy.removedAmenities);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, rating, email, address, tags,
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

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
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
         * Returns amenities added in an edit command.
         */
        public Set<Amenity> getAmenitiesAdded() {
            return addedAmenities;
        }

        /**
         * Returns amenities removed in an edit command.
         */
        public Set<Amenity> getAmenitiesRemoved() {
            return removedAmenities;
        }

        /**
         * Returns a new EditStudySpotDescriptor, which updates the existing amenities according to
         * the edit command provided.
         */
        public EditStudySpotDescriptor updateAmenities(Set<Amenity> existingAmenities) throws CommandException {
            boolean repeatedAmenityPresent = checkRepeatedAmenity(addedAmenities, removedAmenities);
            if (repeatedAmenityPresent) {
                throw new CommandException(MESSAGE_REPEATED_COMMANDS);
            }

            Set<Amenity> updatedAmenities = new HashSet<>();
            if (addedAmenities != null) {
                updatedAmenities = combineAmenitiesAdded(addedAmenities, existingAmenities);
            }
            if (removedAmenities != null) {
                updatedAmenities = combineAmenitiesRemoved(removedAmenities, existingAmenities);
            }
            setAmenities(updatedAmenities);
            return this;
        }

        /**
         * Returns an amenity set which has {@code addedAmenities} added to {@code existingAmenities}.
         */
        public Set<Amenity> combineAmenitiesAdded(Set<Amenity> addedAmenities, Set<Amenity> existingAmenities) {
            Set<Amenity> result = existingAmenities;
            for (Amenity amenity: addedAmenities) {
                if (!existingAmenities.contains(amenity)) {
                    result = addAmenity(result, amenity);
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
                    throw new CommandException(String.format(Amenity.MESSAGE_REMOVAL_CONSTRAINTS,
                            Amenity.listAllAmenityTypes(Amenity.VALID_TYPES)));
                } else {
                    result = removedAmenity(result, amenity);
                }
            }
            return result;
        }

        private boolean checkRepeatedAmenity(Set<Amenity> addedAmenities, Set<Amenity> removedAmenities) {
            if (addedAmenities == null || removedAmenities == null) {
                return false;
            }
            for (Amenity amenity : addedAmenities) {
                if (containAmenityType(removedAmenities, amenity.amenityType)) {
                    return true;
                }
            }
            return false;
        }

        private boolean containAmenityType(Set<Amenity> amenities, String amenityType) {
            for (Amenity amenity : amenities) {
                if (amenity.amenityType.equals(amenityType)) {
                    return true;
                }
            }
            return false;
        }

        private Set<Amenity> addAmenity(Set<Amenity> existingAmenities, Amenity amenityAdded) {
            Set<Amenity> result = new HashSet<>();
            result.addAll(existingAmenities);
            result.add(amenityAdded);
            return result;
        }

        private Set<Amenity> removedAmenity(Set<Amenity> existingAmenities, Amenity amenityRemoved) {
            Set<Amenity> result = new HashSet<>();
            for (Amenity amenity : existingAmenities) {
                if (!amenity.equals(amenityRemoved)) {
                    result.add(amenity);
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
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags())
                    && getAmenities().equals(e.getAmenities());
        }
    }
}
