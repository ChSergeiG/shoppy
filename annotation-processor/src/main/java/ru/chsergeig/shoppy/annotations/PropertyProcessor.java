package ru.chsergeig.shoppy.annotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import ru.chsergeig.shoppy.annotations.model.Property;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;


@SupportedAnnotationTypes({
        "ru.chsergeig.shoppy.annotations.PropertyAnnotation",
        "ru.chsergeig.shoppy.annotations.PropertyAnnotations",
})
@SupportedSourceVersion(SourceVersion.RELEASE_15)
public class PropertyProcessor extends AbstractProcessor {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Set<String> PROCESSED_PROPERTIES = new ConcurrentSkipListSet<>();

    private static final String SPACES = "    ";

    @Override
    public boolean process(
            Set<? extends TypeElement> annotations,
            RoundEnvironment roundEnv
    ) {
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

            annotatedElements.forEach(ae -> {
                if (ae.getKind() == ElementKind.INTERFACE) {
                    if (ae.getAnnotation(PropertyAnnotations.class) != null) {
                        for (PropertyAnnotation pa : ae.getAnnotation(PropertyAnnotations.class).value()) {
                            processA(pa);
                        }
                        return;
                    }
                    if (ae.getAnnotation(PropertyAnnotation.class) != null) {
                        processA(ae.getAnnotation(PropertyAnnotation.class));
                        return;
                    }
                }
            });
        }
        return true;
    }

    private void processA(PropertyAnnotation pa) {
        String fileName = pa.packageToGenerate() + "." + pa.filename().replace(".json", "");
        if (PROCESSED_PROPERTIES.contains(fileName)) {
            return;
        }
        try {
            PROCESSED_PROPERTIES.add(fileName);
            Property propertyModel = MAPPER.readValue(
                    Property.class.getClassLoader().getResourceAsStream(pa.filename()),
                    Property.class
            );
            JavaFileObject builderFile;
            try {
                builderFile = processingEnv
                        .getFiler()
                        .createSourceFile(fileName);
            } catch (IOException ioe) {
                processingEnv.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        "Не удалось создать javaFileObject: " + ioe.getLocalizedMessage(),
                        null
                );
                return;
            }
            try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
                printPackage(out, pa);
                printImports(out, propertyModel);
                printClassDefinition(out, fileName, propertyModel, (prefix) -> {
                    printFields(out, prefix, propertyModel.getFields());
                    printInnerClasses(out, prefix, propertyModel);
                });
            }
        } catch (IOException ignore) {
        }
    }


    private void printPackage(PrintWriter out, PropertyAnnotation pa) {
        out.println("package " + pa.packageToGenerate() + ";");
        out.println();
    }

    private void printImports(PrintWriter out, Property p) {
        out.printf("import org.springframework.boot.context.properties.ConfigurationProperties;\n");
        if (p.isConstructorBinding()) {
            out.printf("import org.springframework.boot.context.properties.ConstructorBinding;\n");
        }
        out.println();
    }

    private void printClassDefinition(PrintWriter out, String fn, Property p, Consumer<String> l) {
        out.printf("@ConfigurationProperties(prefix = \"%s\")\n", p.getPropertyPath());
        if (p.isConstructorBinding()) {
            out.printf("@ConstructorBinding\n");
        }
        String[] classChunk = fn.split("\\.");
        out.printf("public class %s {\n", classChunk[classChunk.length - 1]);
        l.accept(SPACES);
        out.printf("}\n");
    }

    private void printFields(PrintWriter out, String prefix, List<Property.Field> fields) {
        fields.forEach((f) -> {
            out.printf("%sprivate %s %s;\n", prefix, f.getType(), f.getName());
            out.println();
            out.printf("%spublic %s get%s() {\n", prefix, f.getType(), StringUtils.capitalize(f.getName()));
            out.printf("%sreturn %s;\n", prefix + SPACES, f.getName());
            out.printf("%s}\n", prefix);
            out.println();
            out.printf("%spublic void set%s(%s %s) {\n", prefix, StringUtils.capitalize(f.getName()), f.getType(), f.getName());
            out.printf("%sthis.%s = %s;\n", prefix + SPACES, f.getName(), f.getName());
            out.printf("%s}\n", prefix);
            out.println();
        });
        out.println();
    }

    private void printInnerClasses(PrintWriter out, String prefix, Property p) {
        p.getClasses().forEach((c) -> {
            out.printf("%spublic static class %s {\n", prefix, c.getName());
            printFields(out, prefix + SPACES, c.getFields());
            out.printf("%s}\n", prefix);
        });
    }
}
