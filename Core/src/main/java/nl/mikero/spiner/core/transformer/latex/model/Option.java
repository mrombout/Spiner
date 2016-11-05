package nl.mikero.spiner.core.transformer.latex.model;

public class Option {
    private final String name;
    private final String value;

    public Option(String name, String value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        if(value != null)
            return String.format("%s=%s", name, value);
        return name;
    }
}
