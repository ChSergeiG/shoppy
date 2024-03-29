package ru.chsergeig.shoppy.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Repeatable(PropertyAnnotations.class)
public @interface PropertyAnnotation {
    String filename();

    String packageToGenerate();
}
