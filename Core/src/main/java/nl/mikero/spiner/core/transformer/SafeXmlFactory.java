package nl.mikero.spiner.core.transformer;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;

/**
 *
 *
 * See https://sonarcloud.io/organizations/mrombout-github/rules?open=java%3AS2755&rule_key=java%3AS2755
 */
public final class SafeXmlFactory {
    private SafeXmlFactory() {

    }

    public static final class InternalOnlyDocumentBuilderFactory {
        private InternalOnlyDocumentBuilderFactory() {

        }

        /**
         * Constructs a new DocumentBuilderFactory with external entities disabled
         *
         * @return a document builder factory with external entities disabled
         */
        public static DocumentBuilderFactory newInstance() {
            DocumentBuilderFactory df = DocumentBuilderFactory.newInstance();

            df.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            df.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");

            return df;
        }
    }

    public static final class InternalOnlyTransformerFactory {
        private InternalOnlyTransformerFactory() {

        }

        /**
         * Constructs a new transformer factory with external entities disabled.
         *
         * @return a transformer factory with external entities disabled
         */
        public static TransformerFactory newInstance() {
            TransformerFactory tf = TransformerFactory.newInstance();

            tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");

            return tf;
        }
    }
}
