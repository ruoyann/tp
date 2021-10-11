package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.alias.Alias;

/**
 * Jackson-friendly version of {@link Alias}.
 */
public class JsonAdaptedAlias {

    private final String userAlias;
    private final String aliasCommandWord;

    /**
     * Constructs a {@code JsonAdaptedAlias} with the given {@code userAlias} and {@code aliasCommandWord}.
     */
    @JsonCreator
    public JsonAdaptedAlias(@JsonProperty("userAlias") String userAlias,
                            @JsonProperty("aliasCommandWord") String aliasCommandWord) {
        this.userAlias = userAlias;
        this.aliasCommandWord = aliasCommandWord;
    }

    /**
     * Converts a given {@code Amenity} into this class for Jackson use.
     */
    public JsonAdaptedAlias(Alias source) {
        userAlias = source.userAlias;
        aliasCommandWord = source.aliasCommandWord;
    }

    @JsonValue
    public String getUserAlias() {
        return userAlias;
    }

    @JsonValue
    public String getAliasCommandWord() {
        return aliasCommandWord;
    }

    /**
     * Converts this Jackson-friendly adapted amenity object into the model's {@code Amenity} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted amenity.
     */
    public Alias toModelType() throws IllegalValueException {
        if (!Alias.isValidUserAlias(userAlias)) {
            throw new IllegalValueException(Alias.MESSAGE_ALIAS_CONSTRAINTS);
        } else if (!Alias.isValidCommandWord(aliasCommandWord)) {
            throw new IllegalValueException(Alias.MESSAGE_COMMAND_CONSTRAINTS);
        }
        return new Alias(userAlias, aliasCommandWord);
    }

}
