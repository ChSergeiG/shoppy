package ru.chsergeig.shoppy;

import ru.chsergeig.shoppy.annotations.PropertyAnnotation;

@PropertyAnnotation(
        filename = "SecurityProperties.json",
        packageToGenerate = "ru.chsergeig.shoppy.properties"
)
public interface WithProperties {
}
