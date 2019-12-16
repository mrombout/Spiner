package nl.mikero.spiner.core.transformer.latex.pegdown;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.inject.Inject;
//import org.pegdown.LinkRenderer;
//import org.pegdown.VerbatimSerializer;
//import org.pegdown.ast.AbbreviationNode;
//import org.pegdown.ast.AnchorLinkNode;
//import org.pegdown.ast.AutoLinkNode;
//import org.pegdown.ast.BlockQuoteNode;
//import org.pegdown.ast.BulletListNode;
//import org.pegdown.ast.CodeNode;
//import org.pegdown.ast.DefinitionListNode;
//import org.pegdown.ast.DefinitionNode;
//import org.pegdown.ast.DefinitionTermNode;
//import org.pegdown.ast.ExpImageNode;
//import org.pegdown.ast.ExpLinkNode;
//import org.pegdown.ast.HeaderNode;
//import org.pegdown.ast.HtmlBlockNode;
//import org.pegdown.ast.InlineHtmlNode;
//import org.pegdown.ast.ListItemNode;
//import org.pegdown.ast.MailLinkNode;
//import org.pegdown.ast.Node;
//import org.pegdown.ast.OrderedListNode;
//import org.pegdown.ast.ParaNode;
//import org.pegdown.ast.QuotedNode;
//import org.pegdown.ast.RefImageNode;
//import org.pegdown.ast.RefLinkNode;
//import org.pegdown.ast.ReferenceNode;
//import org.pegdown.ast.RootNode;
//import org.pegdown.ast.SimpleNode;
//import org.pegdown.ast.SpecialTextNode;
//import org.pegdown.ast.StrikeNode;
//import org.pegdown.ast.StrongEmphSuperNode;
//import org.pegdown.ast.SuperNode;
//import org.pegdown.ast.TableBodyNode;
//import org.pegdown.ast.TableCaptionNode;
//import org.pegdown.ast.TableCellNode;
//import org.pegdown.ast.TableColumnNode;
//import org.pegdown.ast.TableHeaderNode;
//import org.pegdown.ast.TableNode;
//import org.pegdown.ast.TableRowNode;
//import org.pegdown.ast.TextNode;
//import org.pegdown.ast.VerbatimNode;
//import org.pegdown.ast.Visitor;
//import org.pegdown.ast.WikiLinkNode;

/**
 * Serializes Markdown to LaTeX.
 */
public class ToLatexSerializer /*implements Visitor*/ {
    private static final int HEADER_1 = 1;
    private static final int HEADER_2 = 2;
    private static final int HEADER_3 = 3;
    private static final int HEADER_4 = 4;
    private static final int HEADER_5 = 5;
    private static final int HEADER_6 = 6;

    private static final Map<Integer, String> SUPPORTED_LEVELS = Collections.unmodifiableMap(Stream.of(
            new AbstractMap.SimpleEntry<>(HEADER_1, "chapter"),
            new AbstractMap.SimpleEntry<>(HEADER_2, "section"),
            new AbstractMap.SimpleEntry<>(HEADER_3, "subsection"),
            new AbstractMap.SimpleEntry<>(HEADER_4, "subsubsection"),
            new AbstractMap.SimpleEntry<>(HEADER_5, "paragraph"),
            new AbstractMap.SimpleEntry<>(HEADER_6, "subparagraph")
    ).collect(Collectors.toMap(AbstractMap.SimpleEntry::getKey, AbstractMap.SimpleEntry::getValue)));

    private static final String MSG_INVALID_HEADER_LEVEL = "Header level '%d' is not supported, must be 1 to 6.";
    private static final String MSG_NODE_NOT_SUPPORTED = "Node '%s' not supported.";

    private static final int INDENT_SIZE = 2;

    private static final String TEX_ITEM = "item";

    private LatexPrinter printer;
//    private final LinkRenderer linkRenderer;

//    private final Map<String, ReferenceNode> references;
    private final Map<String, String> abbreviations;

//    private final Map<String, VerbatimSerializer> verbatimSerializers;

    /**
     * Constructs a new ToLatexSerializer.
     *
//     * @param linkRenderer LinkRenderer to render links with
//     * @param printer Printer to print to
     */
    @Inject
    public ToLatexSerializer(/*final LinkRenderer linkRenderer, */final LatexPrinter printer) {
//        this.linkRenderer = linkRenderer;
        this.printer = printer;

//        this.references = new HashMap<>();
        this.abbreviations = new HashMap<>();

//        this.verbatimSerializers = new HashMap<>();
//        this.verbatimSerializers.put(VerbatimSerializer.DEFAULT, LatexVerbatimSerializer.INSTANCE);
    }

    /**
     * Returns the RootNode as a String in the form of a LaTeX document.
     *
     * @param astRoot ast to print as LaTeX
     * @return LaTeX representation of the given astRoot
     */
//    public String toLatex(final RootNode astRoot) {
//        Objects.requireNonNull(astRoot);
//
//        printer = new LatexPrinter();
//        astRoot.accept(this);
//        return printer.getString();
//    }

    /**
     * Visits all children of the node and gradually build a LaTex document.
     *
     * @param node node to build LaTeX representation for
     */
//    @Override
//    public final void visit(final RootNode node) {
//        for(final ReferenceNode refNode : node.getReferences()) {
//            visitChildren(refNode);
//            references.put(normalize(printer.getString()), refNode);
//            printer.clear();
//        }
//
//        for(final AbbreviationNode abbrNode : node.getAbbreviations()) {
//            visitChildren(abbrNode);
//            String abbr = printer.getString();
//            printer.clear();
//            abbrNode.getExpansion().accept(this);
//            String expansion = printer.getString();
//            abbreviations.put(abbr, expansion);
//            printer.clear();
//        }
//
//        visitChildren(node);
//    }

    /**
     * AbbreviationNodes are currently ignored.
     *
     * @param node ignored
     */
//    @Override
//    public final void visit(final AbbreviationNode node) {
//        /* nop */
//    }

//    @Override
//    public final void visit(final AnchorLinkNode node) {
//        printLink(linkRenderer.render(node));
//    }

//    @Override
//    public final void visit(final AutoLinkNode node) {
//        printCommand(node, "url");
//    }

//    @Override
//    public final void visit(final BlockQuoteNode node) {
//        printIndentedEnvironment(node, "displayquote");
//    }

//    @Override
//    public final void visit(final BulletListNode node) {
//        printIndentedEnvironment(node, "enumerate");
//    }

//    @Override
//    public final void visit(final CodeNode node) {
//        printCommand(node, "lstinline");
//    }

//    @Override
//    public final void visit(final DefinitionListNode node) {
//        printIndentedEnvironment(node, "description");
//    }

//    @Override
//    public final void visit(final DefinitionNode node) {
//        printer.print(' ');
//        visitChildren(node);
//    }

//    @Override
//    public final void visit(final DefinitionTermNode node) {
//        printer.println().printCommand(TEX_ITEM).printOptionStart();
//        visitChildren(node);
//        printer.printOptionEnd();
//    }

//    @Override
//    public final void visit(final ExpImageNode node) {
//        throw new UnsupportedOperationException();
//    }

//    @Override
//    public final void visit(final ExpLinkNode node) {
//        String text = printChildrenToString(node);
//        printLink(linkRenderer.render(node, text));
//    }

//    @Override
//    public final void visit(final HeaderNode node) {
//        if(!SUPPORTED_LEVELS.containsKey(node.getLevel()))
//            throw new IllegalStateException(String.format(MSG_INVALID_HEADER_LEVEL, node.getLevel()));
//
//        printBreakBeforeCommand(node, SUPPORTED_LEVELS.get(node.getLevel()));
//    }

//    @Override
//    public final void visit(final HtmlBlockNode node) {
//        /* html is ignored */
//    }

//    @Override
//    public final void visit(final InlineHtmlNode node) {
//        printer.print(node.getText());
//    }
//
//    @Override
//    public final void visit(final ListItemNode node) {
//         TODO: Support for TaskListNode?
//        printConditionallyIndentedCommand(node, TEX_ITEM);
//    }
//
//    @Override
//    public final void visit(final MailLinkNode node) {
//        printLink(linkRenderer.render(node));
//    }
//
//    @Override
//    public final void visit(final OrderedListNode node) {
//        printIndentedEnvironment(node, "itemize");
//    }
//
//    @Override
//    public final void visit(final ParaNode node) {
//        boolean startWithNewLine = printer.endsWithNewLine();
//        printer.println();
//        visitChildren(node);
//        if(startWithNewLine) {
//            printer.println().println();
//        }
//    }

//    @Override
//    public final void visit(final QuotedNode node) {
//        switch(node.getType()) {
//            case DoubleAngle:
//                printer.printCommand("guillemotleft", true);
//                visitChildren(node);
//                printer.printCommand("guillemotright", true);
//                break;
//            case Double:
//                printer.print("‘‘");
//                visitChildren(node);
//                printer.print("’’");
//                break;
//            case Single:
//                printer.print("‘");
//                visitChildren(node);
//                printer.print("’");
//                break;
//            default:
//                assert false : String.format(MSG_NODE_NOT_SUPPORTED, node.getType());
//        }
//    }
//
//    @Override
//    public final void visit(final ReferenceNode node) {
//        /* reference nodes are not printed */
//    }
//
//    @Override
//    public final void visit(final RefImageNode node) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public final void visit(final RefLinkNode node) {
//        String text = printChildrenToString(node);
//        String key = node.referenceKey != null ? printChildrenToString(node.referenceKey) : text;
//        ReferenceNode refNode = references.get(normalize(key));
//        if(refNode == null) {
//            printer.printOption(text);
//            if(node.separatorSpace != null) {
//                printer.print(node.separatorSpace).printOptionStart();
//                if(node.referenceKey != null)
//                    printer.print(key);
//                printer.printOptionEnd();
//            }
//        } else {
//            printLink(linkRenderer.render(node, refNode.getUrl(), refNode.getTitle(), text));
//        }
//    }
//
//    @Override
//    public final void visit(final SimpleNode node) {
//        switch(node.getType()) {
//            case Apostrophe:
//                printer.print("’");
//                break;
//            case Ellipsis:
//                printer.printCommand("ldots");
//                break;
//            case Emdash:
//                printer.print("---");
//                break;
//            case Endash:
//                printer.print("--");
//                break;
//            case HRule:
//                printer.println();
//                printer.println().print("\\rule{0.5\\textwidth}{.4pt}");
//                break;
//            case Linebreak:
//                printer.printCommand("linebreak");
//                break;
//            case Nbsp:
//                printer.printCommand("~");
//                break;
//            default:
//                assert false : String.format(MSG_NODE_NOT_SUPPORTED, node.getType());
//        }
//    }

//    @Override
//    public final void visit(final SpecialTextNode node) {
//        String text = node.getText();
//        printer.print(LatexEncoder.encode(text));
//    }
//
//    @Override
//    public final void visit(final StrikeNode node) {
//        printCommand(node, "sout");
//    }
//
//    @Override
//    public final void visit(final StrongEmphSuperNode node) {
//        if(node.isClosed()) {
//            if(node.isStrong())
//                printCommand(node, "textbf");
//            else
//                printCommand(node, "emph");
//        } else {
//            printer.print(node.getChars());
//            visitChildren(node);
//        }
//    }
//
//    @Override
//    public final void visit(final TableBodyNode node) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public final void visit(final TableCaptionNode node) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public final void visit(final TableCellNode node) {
//        throw new UnsupportedOperationException();
//    }

//    @Override
//    public final void visit(final TableColumnNode node) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public final void visit(final TableHeaderNode node) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public final void visit(final TableNode node) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public final void visit(final TableRowNode node) {
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public final void visit(final VerbatimNode node) {
//        VerbatimSerializer serializer = lookupSerializer(node.getType());
//        serializer.serialize(node, printer);
//    }
//
//    /**
//     * Looks up and returns the {@link org.pegdown.VerbatimSerializer} associated with the given type.
//     *
//     * @param type type to lookup serializer for
//     * @return serializer associated with the given type
//     */
//    private VerbatimSerializer lookupSerializer(final String type) {
//        if(type != null && verbatimSerializers.containsKey(type))
//            return verbatimSerializers.get(type);
//        return verbatimSerializers.get(VerbatimSerializer.DEFAULT);
//    }

//    @Override
//    public final void visit(final WikiLinkNode node) {
//        printLink(linkRenderer.render(node));
//    }
//
//    @Override
//    public final void visit(final TextNode node) {
//        printer.print(node.getText());
//    }
//
//    @Override
//    public final void visit(final SuperNode node) {
//        visitChildren(node);
//    }
//
//    @Override
//    public final void visit(final Node node) {
//        /* do nothing */
//    }
//
//    /**
//     * Visits all children of the given node.
//     *
//     * @param node node to visit all children of
//     */
//    private void visitChildren(final SuperNode node) {
//        for(Node child : node.getChildren()) {
//            child.accept(this);
//        }
//    }
//
//    /**
//     * Prints a command and the `node`s content as a parameter.
//     *
//     * @param node text node to print as parameter
//     * @param command command to print
//     */
//    private void printCommand(final TextNode node, final String command) {
//        printer.printCommand(command).printParam(LatexEncoder.encode(node.getText()));
//    }

    /**
     * Prints a command and its children as parameters.
     *
     * @param node parent node of children to print
     * @param command command to print
     */
//    private void printCommand(final SuperNode node, final String command) {
//        printer.printCommand(command).printParamStart();
//        visitChildren(node);
//        printer.printParamEnd();
//    }

    /**
     * Prints an indented environment.
     *
     * @param node node containing the children of this environment
     * @param environmentName name of the environment to print
     */
//    private void printIndentedEnvironment(final SuperNode node, final String environmentName) {
//        printer.println().printCommand("begin").printParam(environmentName).indent(+INDENT_SIZE);
//        visitChildren(node);
//        printer.indent(-INDENT_SIZE).println().printCommand("end").printParam(environmentName);
//    }

//    private void printConditionallyIndentedCommand(final SuperNode node, final String command) {
//        if(node.getChildren().size() > 1) {
//            printer.println().printCommand(command).printParamStart().indent(+INDENT_SIZE);
//            visitChildren(node);
//            printer.indent(-INDENT_SIZE).println().printParamEnd();
//        } else {
//            boolean startWasNewLine = printer.endsWithNewLine();
//
//            printer.println().printCommand(command).printParamStart();
//            visitChildren(node);
//            printer.printParamEnd().printchkln(startWasNewLine);
//        }
//    }

    /**
     * Prints and returns all children as a string.
     *
     * @param node node to print
     * @return LaTeX representation of input node
     */
//    private String printChildrenToString(final SuperNode node) {
//        LatexPrinter priorPrinter = printer;
//        printer = new LatexPrinter();
//        visitChildren(node);
//        String result = printer.getString();
//        printer = priorPrinter;
//        return result;
//    }

//    private void printBreakBeforeCommand(final SuperNode node, final String tag) {
//        boolean starWasNewLine = printer.endsWithNewLine();
//        printer.println();
//        printCommand(node, tag);
//        if(starWasNewLine)
//            printer.println();
//    }

    /**
     * Prints a link.
     *
//     * @param rendering rendering to print link for
     */
//    private void printLink(final LinkRenderer.Rendering rendering) {
//        printer.printCommand("gbturn").printParam(rendering.text);
//    }

    private String normalize(final String input) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            switch(c) {
                case ' ':
                    // fallthrough
                case '\n':
                    // fallthrough
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
