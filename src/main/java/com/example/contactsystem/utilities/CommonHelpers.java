package com.example.contactsystem.utilities;

import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
public class CommonHelpers<T> {
    public Boolean isFieldNullable(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column columnAnnotation = field.getAnnotation(Column.class);
            return columnAnnotation.nullable();
        } else {
            return true;
        }
    }

    public T performPartialUpdate(T foundObj, T updateObj, String uniqueField) {
        // Retrieve fields of T class
        Class<?> tClass = foundObj.getClass();
        Field[] contactClassFields = tClass.getDeclaredFields();

        // Iterate over the fields
        for (Field field : contactClassFields) {
            try {
                // Exclude primary key
                if (!field.getName().equals(uniqueField)) {

                    // Make private methods accessible
                    field.setAccessible(true);

                    // Retrieve value from updatedObj else retrieve from foundObj
                    Object value;

                    Object newValue = field.get(updateObj);
                    if (newValue == null) {
                        value = field.get(foundObj);
                    } else {
                        value = newValue;
                    }

                    // Check if field is null
                    boolean fieldExists = (value != null);

                    // Set values based on whether it carries @column(nullable = false)
                    if (!fieldExists && this.isFieldNullable(field)) {
                        field.set(foundObj, null);
                    } else {
                        field.set(foundObj, value);
                    }

                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return foundObj;
    }
}
