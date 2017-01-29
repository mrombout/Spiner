Feature: Plain markdown to EPUB
  Scenario: Headers
    Given I have the Twine story "stories/headers.html"
    When I transform it to "EPUB"
    Then page "First" should look like "First" in "epub/headers.epub"

  Scenario: Emphasis
    Given I have the Twine story "stories/emphasis.html"
    When I transform it to "EPUB"
    Then page "First" should look like "First" in "epub/emphasis.epub"

  Scenario: Paragraphs
    Given I have the Twine story "stories/paragraphs.html"
    When I transform it to "EPUB"
    Then page "First" should look like "First" in "epub/paragraphs.epub"
