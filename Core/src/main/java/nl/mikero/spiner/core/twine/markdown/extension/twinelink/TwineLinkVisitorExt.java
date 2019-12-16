package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class TwineLinkVisitorExt {
    public static <V extends TwineLinkVisitor> VisitHandler<?>[] VISIT_HANDLER(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(TwineLink.class, visitor::visit),
        };
    }
}
