package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditStudySpotDescriptor;
import seedu.address.model.studyspot.Address;
import seedu.address.model.studyspot.Email;
import seedu.address.model.studyspot.Name;
import seedu.address.model.studyspot.Phone;
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
     * Returns an {@code EditStudySpotDescriptor} with fields containing {@code person}'s details
     */
    public EditStudySpotDescriptorBuilder(StudySpot person) {
        descriptor = new EditStudySpotDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditStudySpotDescriptor} that we are building.
     */
    public EditStudySpotDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditStudySpotDescriptor} that we are building.
     */
    public EditStudySpotDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditStudySpotDescriptor} that we are building.
     */
    public EditStudySpotDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
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
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditStudySpotDescriptor}
     * that we are building.
     */
    public EditStudySpotDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditStudySpotDescriptor build() {
        return descriptor;
    }
}
