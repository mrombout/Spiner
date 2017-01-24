package nl.mikero.spiner.commandline.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(DefineOptions.class)
public @interface DefineOption {

    String opt() default "";
    String longOpt() default "";

    String description() default "";

    String argName() default "";
    boolean hasArg() default false;
    int numberOrArgs() default 0;
    boolean optionalArg() default false;
    char valueSeperator() default '\0';

    boolean required() default false;
}
