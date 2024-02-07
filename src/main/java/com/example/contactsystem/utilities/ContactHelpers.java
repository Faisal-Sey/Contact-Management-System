package com.example.contactsystem.utilities;

import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class ContactHelpers {
    public Boolean isFieldNullable(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            return columnAnnotation.nullable();
        } else {
            return true;
        }
    }
}
