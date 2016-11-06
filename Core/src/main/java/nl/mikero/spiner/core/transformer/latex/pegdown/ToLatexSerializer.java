package nl.mikero.spiner.core.transformer.latex.pegdown;

import org.pegdown.LinkRenderer;
import org.pegdown.Printer;
import org.pegdown.ast.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Objects;

public class ToLatexSerializer implements Visitor {
    private Printer printer;
    private LinkRenderer linkRenderer;

    public ToLatexSerializer(LinkRenderer linkRenderer) {
        this.linkRenderer = linkRenderer;
        this.printer = new Printer();
    }

    public String toLatex(RootNode astRoot) {
        Objects.requireNonNull(astRoot);

        printer = new Printer();
        astRoot.accept(this);
        return printer.getString();
    }

    @Override
    public void visit(RootNode node) {
        // TODO: Support for references
        // TODO: Support for abbreviations

        visitChildren(node);
    }

    @Override
    public void visit(AbbreviationNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(AnchorLinkNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(AutoLinkNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(BlockQuoteNode node) {
        // TODO: Needs csquotes package
        printIndentedEnvironment(node, "displayquote");
    }

    @Override
    public void visit(BulletListNode node) {
        printIndentedEnvironment(node, "enumerate");
    }

    @Override
    public void visit(CodeNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(DefinitionListNode node) {
        printIndentedEnvironment(node, "description");
    }

    @Override
    public void visit(DefinitionNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(DefinitionTermNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(ExpImageNode node) {
        String text = printChildrenToString(node);
        printLink(linkRenderer.render(node, text));
    }

    @Override
    public void visit(ExpLinkNode node) {
        String text = printChildrenToString(node);
        printLink(linkRenderer.render(node, text));
    }

    @Override
    public void visit(HeaderNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(HtmlBlockNode node) {
        String text = node.getText();
        if(text.length() > 0) printer.println();
        printer.print(text);
    }

    @Override
    public void visit(InlineHtmlNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(ListItemNode node) {
        // TODO: Support for TaskListNode?
        printConditionallyIndentedCommand(node, "item");
    }

    @Override
    public void visit(MailLinkNode node) {
        printLink(linkRenderer.render(node));
    }

    @Override
    public void visit(OrderedListNode node) {
        printIndentedEnvironment(node, "itemize");
    }

    @Override
    public void visit(ParaNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(QuotedNode node) {
        switch(node.getType()) {
            case DoubleAngle:
                printer.print("\\guillemotleft");
                visitChildren(node);
                printer.print("\\guillemotright");
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
    public void visit(ReferenceNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(RefImageNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(RefLinkNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(SimpleNode node) {
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
                printer.println().print("\\rule{0.5\\textwidth}{.4pt}");
                break;
            case Linebreak:
                printCommand("linebreak");
                break;
            case Nbsp:
                printCommand("~");
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public void visit(SpecialTextNode node) {
        printer.printEncoded(node.getText());
    }

    @Override
    public void visit(StrikeNode node) {
        // TODO: Needs \\usepackage[normalem]{ulem}
        printCommand(node, "sout");
    }

    @Override
    public void visit(StrongEmphSuperNode node) {
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
    public void visit(TableBodyNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(TableCaptionNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(TableCellNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(TableColumnNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(TableHeaderNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(TableNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(TableRowNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(VerbatimNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(WikiLinkNode node) {
        printLink(linkRenderer.render(node));
    }

    @Override
    public void visit(TextNode node) {
        printer.print(node.getText());
    }

    @Override
    public void visit(SuperNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(Node node) {
//        for(ToLatexSerializerPlugin plugin : plugins) {
//            if(plugin.visit(node, this, printer))
//                return
//        }
    }

    private void visitChildren(SuperNode node) {
        for(Node child : node.getChildren()) {
            child.accept(this);
        }
    }

    private void printCommand(String command) {
        printer.print("\\").print(command);
    }

    private void printCommand(TextNode node, String command) {
        printer.print("\\").print(command).print("{");
        printer.printEncoded(node.getText());
        printer.print("}");
    }

    private void printCommand(SuperNode node, String command) {
        printer.print("\\").print(command).print("{");
        visitChildren(node);
        printer.print("}");
    }

    private void printIndentedEnvironment(SuperNode node, String environment) {
        printer.println().print("\\begin{").print(environment).print("}").indent(+2);
        visitChildren(node);
        printer.indent(-2).println().print("\\end{").print(environment).print("}");
    }

    private void printConditionallyIndentedCommand(SuperNode node, String command) {
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

    private String printChildrenToString(SuperNode node) {
        Printer priorPrinter = printer;
        printer = new Printer();
        visitChildren(node);
        String result = printer.getString();
        printer = priorPrinter;
        return result;
    }

    private void printLink(LinkRenderer.Rendering rendering) {
        printer.print("\\gbturn{").print(rendering.text).print("}");
    }
}
