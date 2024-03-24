package layron.tms.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import layron.tms.model.Priority;

@Converter
public class CustomPriorityConverter implements AttributeConverter<Priority, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Priority attribute) {
        return attribute.getValue();
    }

    @Override
    public Priority convertToEntityAttribute(Integer dbData) {
        return Priority.valueOf(dbData);
    }
}
