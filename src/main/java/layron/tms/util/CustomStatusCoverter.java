package layron.tms.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import layron.tms.model.Status;

@Converter
public class CustomStatusCoverter implements AttributeConverter<Status, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Status attribute) {
        return attribute.getValue();
    }

    @Override
    public Status convertToEntityAttribute(Integer dbData) {
        return Status.valueOf(dbData);
    }
}
