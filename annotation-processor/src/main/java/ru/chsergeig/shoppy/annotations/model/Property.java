package ru.chsergeig.shoppy.annotations.model;

import java.util.List;

public class Property {

    private boolean constructorBinding;
    private String propertyPath;
    private List<Field> fields;
    private List<Class> classes;

    public boolean isConstructorBinding() {
        return constructorBinding;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Class> getClasses() {
        return classes;
    }

    public static class Field {
        private String type;
        private String name;

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }

    public static class Class {
        private String name;
        private List<Field> fields;

        public String getName() {
            return name;
        }

        public List<Field> getFields() {
            return fields;
        }
    }
}
