package nl.mikero.spiner.core.transformer.latex.model;

public class Parameter {
    private final String value;

    public Parameter(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("{%s}", value);
    }
}
