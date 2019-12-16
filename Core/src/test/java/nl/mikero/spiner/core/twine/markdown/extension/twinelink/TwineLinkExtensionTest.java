package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class TwineLinkExtensionTest {
    @Test
    public void TestAll() {
        DataHolder options = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(TwineLinkExtension.create())).toImmutable();

        Parser parser = Parser.builder(options).build();
        HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        Document document = parser.parse("Hello World! [[climb down ladder|you_died]]");
        Assert.assertNotNull(document);

        String output = renderer.render(document);

        Assert.assertEquals("boozoo", output);
    }
}
