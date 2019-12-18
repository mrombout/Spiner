Feature: Plain markdown to EPUB
  Scenario: Paragraphs
    Given I have the Twine story "stories/paragraphs.html"
    When I transform it to "EPUB"
    Then page "First" should look like "First" in "epub/paragraphs.epub"

  Scenario: Headers
    Given I have the Twine story "stories/headers.html"
    When I transform it to "EPUB"
    Then page "First" should look like "First" in "epub/headers.epub"

  Scenario: Emphasis
    Given I have the Twine story "stories/emphasis.html"
    When I transform it to "EPUB"
    Then page "First" should look like "First" in "epub/emphasis.epub"

  Scenario: Lists
    Given I have the Twine story "stories/lists.html"
    When I transform it to "EPUB"
    Then page "First" should look like "First" in "epub/lists.epub"

  Scenario: Links
    Given I have the Twine story "stories/links.html"
    When I transform it to "EPUB"
    Then page "First" should look like "First" in "epub/links.epub"
