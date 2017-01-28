package nl.mikero.spiner.core.transformer.latex.pegdown;

import com.google.inject.Inject;
import nl.mikero.spiner.core.pegdown.plugin.LatexVerbatimSerializer;
import org.pegdown.LinkRenderer;
import org.pegdown.Printer;
import org.pegdown.VerbatimSerializer;
import org.pegdown.ast.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToLatexSerializer implements Visitor {
    private static final Map<Integer, String> SUPPORTED_LEVELS = Collections.unmodifiableMap(Stream.of(
            new AbstractMap.SimpleEntry<>(1, "chapter"),
            new AbstractMap.SimpleEntry<>(2, "section"),
            new AbstractMap.SimpleEntry<>(3, "subsection"),
            new AbstractMap.SimpleEntry<>(4, "subsubsection"),
            new AbstractMap.SimpleEntry<>(5, "paragraph"),
            new AbstractMap.SimpleEntry<>(6, "subparagraph")
    ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));

    private Printer printer;
    private final LinkRenderer linkRenderer;

    private final Map<String, ReferenceNode> references;
    private final Map<String, String> abbreviations;

    private Map<String, VerbatimSerializer> verbatimSerializers;

    @Inject
    public ToLatexSerializer(final LinkRenderer linkRenderer, final Printer printer) {
        this.linkRenderer = linkRenderer;
        this.printer = printer;

        this.references = new HashMap<>();
        this.abbreviations = new HashMap<>();

        this.verbatimSerializers = new HashMap<>();
        this.verbatimSerializers.put(VerbatimSerializer.DEFAULT, LatexVerbatimSerializer.INSTANCE);
    }

    public String toLatex(final RootNode astRoot) {
        Objects.requireNonNull(astRoot);

        printer = new Printer();
        astRoot.accept(this);
        return printer.getString();
    }

    @Override
    public void visit(final RootNode node) {
        for(ReferenceNode refNode : node.getReferences()) {
            visitChildren(refNode);
            references.put(normalize(printer.getString()), refNode);
            printer.clear();
        }

        for(AbbreviationNode abbrNode : node.getAbbreviations()) {
            visitChildren(abbrNode);
            String abbr = printer.getString();
            printer.clear();
            abbrNode.getExpansion().accept(this);
            String expansion = printer.getString();
            abbreviations.put(abbr, expansion);
            printer.clear();
        }

        visitChildren(node);
    }

    @Override
    public void visit(final AbbreviationNode node) {
        /* nop */
    }

    @Override
    public void visit(final AnchorLinkNode node) {
        printLink(linkRenderer.render(node));
    }

    @Override
    public void visit(final AutoLinkNode node) {
        printCommand(node, "url");
    }

    @Override
    public void visit(final BlockQuoteNode node) {
        printIndentedEnvironment(node, "displayquote");
    }

    @Override
    public void visit(final BulletListNode node) {
        printIndentedEnvironment(node, "enumerate");
    }

    @Override
    public void visit(final CodeNode node) {
        printCommand(node, "lstinline");
    }

    @Override
    public void visit(final DefinitionListNode node) {
        printIndentedEnvironment(node, "description");
    }

    @Override
    public void visit(final DefinitionNode node) {
        printer.print(' ');
        visitChildren(node);
    }

    @Override
    public void visit(final DefinitionTermNode node) {
        printer.println().print("\\").print("item").print("[");
        visitChildren(node);
        printer.print("]");
    }

    @Override
    public void visit(final ExpImageNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final ExpLinkNode node) {
        String text = printChildrenToString(node);
        printLink(linkRenderer.render(node, text));
    }

    @Override
    public void visit(final HeaderNode node) {
        if(!SUPPORTED_LEVELS.containsKey(node.getLevel()))
            throw new IllegalStateException(String.format("Header level '%d' is not supported, must be 1 to 6.", node.getLevel()));

        printBreakBeforeCommand(node, SUPPORTED_LEVELS.get(node.getLevel()));
    }

    @Override
    public void visit(final HtmlBlockNode node) {
        /* html is ignored */
    }

    @Override
    public void visit(final InlineHtmlNode node) {
        printer.print(node.getText());
    }
    
    @Override
    public void visit(final ListItemNode node) {
        // TODO: Support for TaskListNode?
        printConditionallyIndentedCommand(node, "item");
    }

    @Override
    public void visit(final MailLinkNode node) {
        printLink(linkRenderer.render(node));
    }

    @Override
    public void visit(final OrderedListNode node) {
        printIndentedEnvironment(node, "itemize");
    }

    @Override
    public void visit(final ParaNode node) {
        boolean startWithNewLine = printer.endsWithNewLine();
        printer.println();
        visitChildren(node);
        if(startWithNewLine) {
            printer.println();
            printer.println();
        }
    }

    @Override
    public void visit(final QuotedNode node) {
        switch(node.getType()) {
            case DoubleAngle:
                printer.print("\\guillemotleft{}");
                visitChildren(node);
                printer.print("\\guillemotright{}");
                break;
            case Double:
                printer.print("‘‘");
                visitChildren(node);
                printer.print("’’");
                break;
            case Single:
                printer.print("‘");
                visitChildren(node);
                printer.print("’");
                break;
        }
    }

    @Override
    public void visit(final ReferenceNode node) {
        /* reference nodes are not printed */
    }

    @Override
    public void visit(final RefImageNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final RefLinkNode node) {
        String text = printChildrenToString(node);
        String key = node.referenceKey != null ? printChildrenToString(node.referenceKey) : text;
        ReferenceNode refNode = references.get(normalize(key));
        if(refNode == null) {
            printer.print('[').print(text).print(']');
            if(node.separatorSpace != null) {
                printer.print(node.separatorSpace).print('[');
                if(node.referenceKey != null)
                    printer.print(key);
                printer.print(']');
            }
        } else {
            printLink(linkRenderer.render(node, refNode.getUrl(), refNode.getTitle(), text));
        }
    }

    @Override
    public void visit(final SimpleNode node) {
        switch(node.getType()) {
            case Apostrophe:
                printer.print("’");
                break;
            case Ellipsis:
                printCommand("ldots");
                break;
            case Emdash:
                printer.print("---");
                break;
            case Endash:
                printer.print("--");
                break;
            case HRule:
                printer.println();
                printer.println().print("\\rule{0.5\\textwidth}{.4pt}");
                break;
            case Linebreak:
                printCommand("linebreak");
                break;
            case Nbsp:
                printCommand("~");
                break;
        }
    }

    @Override
    public void visit(final SpecialTextNode node) {
        String text = node.getText();
        printer.print(LatexEncoder.encode(text));
    }

    @Override
    public void visit(final StrikeNode node) {
        printCommand(node, "sout");
    }

    @Override
    public void visit(final StrongEmphSuperNode node) {
        if(node.isClosed()) {
            if(node.isStrong())
                printCommand(node, "textbf");
            else
                printCommand(node, "emph");
        } else {
            printer.print(node.getChars());
            visitChildren(node);
        }
    }

    @Override
    public void visit(final TableBodyNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableCaptionNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableCellNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableColumnNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableHeaderNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final TableRowNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void visit(final VerbatimNode node) {
        VerbatimSerializer serializer = lookupSerializer(node.getType());
        serializer.serialize(node, printer);
    }

    private VerbatimSerializer lookupSerializer(final String type) {
        if(type != null && verbatimSerializers.containsKey(type)) {
            return verbatimSerializers.get(type);
        } else {
            return verbatimSerializers.get(VerbatimSerializer.DEFAULT);
        }
    }

    @Override
    public void visit(final WikiLinkNode node) {
        printLink(linkRenderer.render(node));
    }

    @Override
    public void visit(final TextNode node) {
        printer.print(node.getText());
    }

    @Override
    public void visit(final SuperNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(final Node node) {
        /* do nothing */
    }

    private void visitChildren(final SuperNode node) {
        for(Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    private void printCommand(final String command) {
        printer.print("\\").print(command);
    }

    private void printCommand(final TextNode node, final String command) {
        printer.print("\\").print(command).print("{");
        printer.print(LatexEncoder.encode(node.getText()));
        printer.print("}");
    }

    private void printCommand(final SuperNode node, final String command) {
        printer.print("\\").print(command).print("{");
        visitChildren(node);
        printer.print("}");
    }

    private void printIndentedEnvironment(final SuperNode node, final String environment) {
        printer.println().print("\\begin{").print(environment).print("}").indent(+2);
        visitChildren(node);
        printer.indent(-2).println().print("\\end{").print(environment).print("}");
    }

    private void printConditionallyIndentedCommand(final SuperNode node, final String command) {
        if(node.getChildren().size() > 1) {
            printer.println().print("\\").print(command).print("{").indent(+2);
            visitChildren(node);
            printer.indent(-2).println().print("}");
        } else {
            boolean startWasNewLine = printer.endsWithNewLine();

            printer.println().print("\\").print(command).print("{");
            visitChildren(node);
            printer.print("}").printchkln(startWasNewLine);
        }
    }

    private String printChildrenToString(final SuperNode node) {
        Printer priorPrinter = printer;
        printer = new Printer();
        visitChildren(node);
        String result = printer.getString();
        printer = priorPrinter;
        return result;
    }

    private void printBreakBeforeCommand(final SuperNode node, String tag) {
        boolean starWasNewLine = printer.endsWithNewLine();
        printer.println();
        printCommand(node, tag);
        if(starWasNewLine)
            printer.println();
    }

    private void printLink(final LinkRenderer.Rendering rendering) {
        printer.print("\\gbturn{").print(rendering.text).print("}");
    }

    private String normalize(final String input) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch(c) {
                case ' ':
                case '\n':
                case '\t':
                    continue;
                default:
                    break;
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }
}
