package nl.mikero.turntopassage.frontend.wizard;

import javafx.beans.property.*;
import javafx.collections.ObservableMap;
import javafx.scene.paint.Color;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.dialog.Wizard;
import org.controlsfx.property.BeanPropertyUtils;
import org.controlsfx.tools.Borders;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Allows the user to change the metadata to be included in the EPUB.
 */
public class MetadataWizardPane extends AbstractWizardPane {

    private PropertySheet propertySheet;

    private Metadata metadata;

    public MetadataWizardPane() {
        this.metadata = new Metadata();

        setHeaderTitle("General Information");
        setHeaderText("Please provide some general information about your story");
        setContent(Borders.wrap(createPropertySheet()).lineBorder().color(Color.LIGHTGRAY).innerPadding(0).build().build());
    }

    private PropertySheet createPropertySheet() {
        propertySheet = new PropertySheet();
        propertySheet.getItems().setAll(BeanPropertyUtils.getProperties(metadata));
        propertySheet.setPrefWidth(640);

        return propertySheet;
    }

    @Override
    public void onExitingPage(Wizard wizard) {
        ObservableMap<String, Object> settings = wizard.getSettings();
        for(PropertySheet.Item item : propertySheet.getItems()) {
            settings.put(item.getName(), item.getValue());
        }
    }

    public static class Metadata implements Serializable {

        private final StringProperty title;
        private final StringProperty author;
        private final StringProperty language;
        private final StringProperty identifier;

        private final StringProperty creator;
        private final StringProperty contributor;
        private final StringProperty publisher;
        private final StringProperty subject;
        private final StringProperty description;
        private final ObjectProperty<LocalDate> date;
        private final StringProperty type;
        private final StringProperty format;
        private final StringProperty source;
        private final StringProperty relation;
        private final StringProperty coverage;
        private final StringProperty rights;

        public Metadata() {
            this.title = new SimpleStringProperty();
            this.author = new SimpleStringProperty();
            this.language = new SimpleStringProperty();
            this.identifier = new SimpleStringProperty();

            this.creator = new SimpleStringProperty();
            this.contributor = new SimpleStringProperty();
            this.publisher = new SimpleStringProperty();
            this.subject = new SimpleStringProperty();
            this.description = new SimpleStringProperty("");
            this.date = new SimpleObjectProperty<>(LocalDate.now());
            this.type = new SimpleStringProperty();
            this.format = new SimpleStringProperty();
            this.source = new SimpleStringProperty();
            this.relation = new SimpleStringProperty();
            this.coverage = new SimpleStringProperty();
            this.rights = new SimpleStringProperty();
        }

        public StringProperty titleProperty() {
            return title;
        }
        public String getTitle() {
            return title.get();
        }
        public void setTitle(String title) {
            this.title.set(title);
        }

        public StringProperty authorProperty() {
            return author;
        }
        public String getAuthor() {
            return author.get();
        }
        public void setAuthor(String author) {
            this.author.set(author);
        }

        public StringProperty languageProperty() {
            return language;
        }
        public String getLanguage() {
            return language.get();
        }
        public void setLanguage(String language) {
            this.language.set(language);
        }

        public StringProperty identifierProperty() {
            return identifier;
        }
        public String getIdentifier() {
            return identifier.get();
        }
        public void setIdentifier(String identifier) {
            this.identifier.set(identifier);
        }

        public StringProperty creatorProperty() {
            return creator;
        }
        public String getCreator() {
            return creator.get();
        }
        public void setCreator(String creator) {
            this.creator.set(creator);
        }

        public StringProperty contributorProperty() {
            return contributor;
        }
        public String getContributor() {
            return contributor.get();
        }
        public void setContributor(String contributor) {
            this.contributor.set(contributor);
        }

        public StringProperty publisherProperty() {
            return publisher;
        }
        public String getPublisher() {
            return publisher.get();
        }
        public void setPublisher(String publisher) {
            this.publisher.set(publisher);
        }

        public StringProperty subjectProperty() {
            return subject;
        }
        public String getSubject() {
            return subject.get();
        }
        public void setSubject(String subject) {
            this.subject.set(subject);
        }

        public StringProperty descriptionProperty() {
            return description;
        }
        public String getDescription() {
            return description.get();
        }
        public void setDescription(String description) {
            this.description.set(description);
        }

        public ObjectProperty<LocalDate> dateProperty() {
            return date;
        }
        public LocalDate getDate() {
            return date.get();
        }
        public void setDate(LocalDate date) {
            this.date.set(date);
        }

        public StringProperty typeProperty() {
            return type;
        }
        public String getType() {
            return type.get();
        }
        public void setType(String type) {
            this.type.set(type);
        }

        public StringProperty formatProperty() {
            return format;
        }
        public String getFormat() {
            return format.get();
        }
        public void setFormat(String format) {
            this.format.set(format);
        }

        public StringProperty sourceProperty() {
            return source;
        }
        public String getSource() {
            return source.get();
        }
        public void setSource(String source) {
            this.source.set(source);
        }

        public StringProperty relationProperty() {
            return relation;
        }
        public String getRelation() {
            return relation.get();
        }
        public void setRelation(String relation) {
            this.relation.set(relation);
        }

        public StringProperty coverageProperty() {
            return coverage;
        }
        public String getCoverage() {
            return coverage.get();
        }
        public void setCoverage(String coverage) {
            this.coverage.set(coverage);
        }

        public StringProperty rightsProperty() {
            return rights;
        }
        public String getRights() {
            return rights.get();
        }
        public void setRights(String rights) {
            this.rights.set(rights);
        }
    }

}
