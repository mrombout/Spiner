package nl.mikero.spiner.core.twine.markdown.extension.twinelink;

import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.LinkResolverFactory;
import com.vladsch.flexmark.html.renderer.LinkResolverContext;
import com.vladsch.flexmark.html.renderer.ResolvedLink;
import com.vladsch.flexmark.util.ast.Node;

import java.util.Set;

public class TwineLinkLinkResolver implements LinkResolver {
    private final TwineLinkOptions options;

    public TwineLinkLinkResolver(LinkResolverContext context) {
        this.options = new TwineLinkOptions(context.getOptions());
    }

    @Override
    public ResolvedLink resolveLink(Node node, LinkResolverContext context, ResolvedLink link) {
        return null;
    }

    public static class Factory implements LinkResolverFactory {
        @Override
        public Set<Class<? extends LinkResolverFactory>> getAfterDependents() {
            return null;
        }

        @Override
        public Set<Class<? extends LinkResolverFactory>> getBeforeDependents() {
            return null;
        }

        @Override
        public boolean affectsGlobalScope() {
            return false;
        }

        @Override
        public LinkResolver apply(LinkResolverContext context) {
            return new TwineLinkLinkResolver(context);
        }
    }
}
