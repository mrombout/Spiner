package nl.mikero.spiner.core.pegdown.plugin;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.pegdown.Printer;
import org.pegdown.ast.VerbatimNode;

public class LatexVerbatimSerializerTest {
    private LatexVerbatimSerializer serializer;
    private Printer printer;

    @Before
    public void setUp() {
        serializer = new LatexVerbatimSerializer();
        printer = new Printer();
    }

    @Test(expected = NullPointerException.class)
    public void serialize_NullNode_ThrowsNullPointerException() {
        // Arrange
        Printer printer = new Printer();

        // Act
        serializer.serialize(null, printer);

        // Assert
    }

    @Test(expected = NullPointerException.class)
    public void serialize_NullPrinter_ThrowsNullPointerException() {
        // Arrange
        VerbatimNode node = new VerbatimNode("Foo");

        // Act
        serializer.serialize(node, null);

        // Assert
    }

    @Test
    public void serialize_EmptyVerbatim_Unknown() {
        // Arrange
        VerbatimNode node = new VerbatimNode("");

        // Act
        serializer.serialize(node, printer);

        // Assert
        Assert.assertEquals("\\begin{verbatim}\n\n\\end{verbatim}", printer.getString());
    }

    @Test
    public void serialize_PopulatedVerbatimg_Unknown() {
        // Arrange
        VerbatimNode node = new VerbatimNode("Foo");

        // Act
        serializer.serialize(node, printer);

        // Assert
        Assert.assertEquals("\\begin{verbatim}\n  Foo\n\\end{verbatim}", printer.getString());
    }

    @Test
    public void serialize_NewlineVerbatim_Unknown() {
        // Arrange
        VerbatimNode node = new VerbatimNode("\n\nFoo");

        // Act
        serializer.serialize(node, printer);

        // Assert
        Assert.assertEquals("\\begin{verbatim}\n  \\\\\n  \\\\\n  Foo\n\\end{verbatim}", printer.getString());
    }
}
