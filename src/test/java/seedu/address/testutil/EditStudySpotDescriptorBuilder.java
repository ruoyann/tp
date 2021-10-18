package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditStudySpotDescriptor;
import seedu.address.model.amenity.Amenity;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.OperatingHours;
import seedu.address.model.studyspot.Rating;
import seedu.address.model.studyspot.StudySpot;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditStudySpotDescriptor objects.
 */
public class EditStudySpotDescriptorBuilder {

    private EditStudySpotDescriptor descriptor;

    public EditStudySpotDescriptorBuilder() {
        descriptor = new EditStudySpotDescriptor();
    }

    public EditStudySpotDescriptorBuilder(EditStudySpotDescriptor descriptor) {
        this.descriptor = new EditStudySpotDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditStudySpotDescriptor} with fields containing {@code studySpot}'s details
     */
    public EditStudySpotDescriptorBuilder(StudySpot studySpot) {
        descriptor = new EditStudySpotDescriptor();
        descriptor.setName(studySpot.getName());
        descriptor.setRating(studySpot.getRating());
        descriptor.setOperatingHours(studySpot.getOperatingHours());
        descriptor.setAddress(studySpot.getAddress());
        descriptor.setAddedTags(studySpot.getTags());
        descriptor.setAddedAmenities(studySpot.getAmenities());
    }

    /**
     * Sets the {@code Name} of the {@code EditStudySpotDescriptor} that we are building.
     */
    public EditStudySpotDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Rating} of the {@code EditStudySpotDescriptor} that we are building.
     */
    public EditStudySpotDescriptorBuilder withRating(String rating) {
        descriptor.setRating(new Rating(rating));
        return this;
    }

    /**
     * Sets the {@code OperatingHours} of the {@code EditStudySpotDescriptor} that we are building.
     */
    public EditStudySpotDescriptorBuilder withOperatingHours(String operatingHours) {
        descriptor.setOperatingHours(new OperatingHours(operatingHours));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditStudySpotDescriptor} that we are building.
     */
    public EditStudySpotDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code addedTags} into a {@code Set<Tag>} and set it to the {@code EditStudySpotDescriptor}
     * that we are building.
     */
    public EditStudySpotDescriptorBuilder withAddedTags(String... addedTags) {
        Set<Tag> tagSet = Stream.of(addedTags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setAddedTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code removedTags} into a {@code Set<Tag>} and set it to the {@code EditStudySpotDescriptor}
     * that we are building.
     */
    public EditStudySpotDescriptorBuilder withRemovedTags(String... removedTags) {
        Set<Tag> tagSet = Stream.of(removedTags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setRemovedTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code addedAmenities} into a {@code Set<Amenity>} and set it to the {@code EditStudySpotDescriptor}
     * that we are building.
     */
    public EditStudySpotDescriptorBuilder withAddedAmenities(String... addedAmenities) {
        Set<Amenity> amenitySet = Stream.of(addedAmenities).map(Amenity::new).collect(Collectors.toSet());
        descriptor.setAddedAmenities(amenitySet);
        return this;
    }

    /**
     * Parses the {@code removedAmenities} into a {@code Set<Amenity>} and set it to the {@code EditStudySpotDescriptor}
     * that we are building.
     */
    public EditStudySpotDescriptorBuilder withRemovedAmenities(String... removedAmenities) {
        Set<Amenity> amenitySet = Stream.of(removedAmenities).map(Amenity::new).collect(Collectors.toSet());
        descriptor.setRemovedAmenities(amenitySet);
        return this;
    }

    public EditStudySpotDescriptor build() {
        return descriptor;
    }
}
