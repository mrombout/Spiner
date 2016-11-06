package nl.mikero.spiner.core.transformer.latex.pegdown;

import nl.mikero.spiner.core.transformer.latex.model.LatexContainer;
import nl.mikero.spiner.core.transformer.latex.model.command.AbstractCommand;
import nl.mikero.spiner.core.transformer.latex.model.command.Environment;
import nl.mikero.spiner.core.transformer.latex.model.command.RawTexCommand;
import org.pegdown.LinkRenderer;
import org.pegdown.Printer;
import org.pegdown.ast.*;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayDeque;
import java.util.Deque;
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
        throw new NotImplementedException();
    }

    @Override
    public void visit(BulletListNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(CodeNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(DefinitionListNode node) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    @Override
    public void visit(ExpLinkNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(HeaderNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(HtmlBlockNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(InlineHtmlNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(ListItemNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(MailLinkNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(OrderedListNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(ParaNode node) {
        visitChildren(node);
    }

    @Override
    public void visit(QuotedNode node) {
        throw new NotImplementedException();
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
//        switch(node.getType()) {
//            case Apostrophe:
//                container.peek().addCommand(new AbstractCommand("textquotesingle"));
//                break;
//            case Ellipsis:
//                container.peek().addCommand(new AbstractCommand("ldots"));
//                break;
//            case Emdash:
//                container.peek().addCommand(new AbstractCommand("textemdash"));
//                break;
//            case Endash:
//                container.peek().addCommand(new AbstractCommand("textendash"));
//                break;
//            case HRule:
//                container.peek().addCommand(new AbstractCommand("rule").parameters().add("0.5\textwidth").add(".4pt").done());
//                break;
//            case Linebreak:
//                container.peek().addCommand(new AbstractCommand("linebreak"));
//                break;
//            case Nbsp:
//                container.peek().addCommand(new RawTexCommand("~"));
//                break;
//            default:
//                throw new IllegalStateException();
//        }
    }

    @Override
    public void visit(SpecialTextNode node) {
        throw new NotImplementedException();
    }

    @Override
    public void visit(StrikeNode node) {
        throw new NotImplementedException();
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
        throw new NotImplementedException();
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
        throw new NotImplementedException();
    }

    private void visitChildren(SuperNode node) {
        for(Node child : node.getChildren()) {
            child.accept(this);
        }
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
}
