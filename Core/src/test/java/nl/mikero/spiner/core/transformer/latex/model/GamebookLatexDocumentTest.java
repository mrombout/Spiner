package nl.mikero.spiner.core.transformer.latex.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GamebookLatexDocumentTest {
    @Test
    public void construct_Default_ValidDocumentClass() {
        // Arrange

        // Act
        GamebookLatexDocument result = new GamebookLatexDocument();

        // Assert
        assertEquals(5, result.getCommands().size());
        assertEquals("\\documentclass[b5paper]{article}", result.getCommands().get(0).toString());
    }
}
