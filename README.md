# TurnToPassage.Transformer

<img src="http://turntopassage.github.io/images/logo.svg" alt="TurnToPassage logo" title="TurnToPassage.Transformer" align="right" width="128" />

TurnToPassage is a set of tools that allows you to easily author you own Choose Your Own Adventure books in EPUB format
using [Twine 2](http://twinery.org/). This set consists of a Transformer, which transforms your regular published Twine story into an EPUB
file to be read on any modern e-reader.

## Supported Twine Features

The following Twine features are currently supported:

* Markdown syntax for passages
* Standard passage links (e.g. [[displayed text|passage title]], [[passage title]])

## Usage

### Graphical User Interface

By far the easiest way to get starting is to use the GUI to do the work for you. Just download the right version for
your operating system and follow the instructions on the screen!

Operating System  | Download
----------------- | -------------
Windows           | _no download available yet_
Mac OS            | _no download available yet_
Linux             | _no download available yet_
Other             | _no download available yet_

### Commandline

There is also a separate command-line version that works like this:

```
usage: java -jar ttp-transformer-0.0.1.jar [-help | -version] [-f <file>] [-o <file>]
 -f,--file <file>     location of input HTML file
 -help                display this help and exit
 -o,--output <file>   location of output EPUB file
 -version             output version information and exit
```

```
java -jar ttp-transformer-0.0.1.jar -f "My Cool Story.html" -o "My Cool Story.epub"
```

## Roadmap

The following features are currently not implemented but are to be expected somewhere in the indefinite future, in
roughly:

<dl>
  <dt>Metadata</dt>
  <dd>The ability to set any metadata from inside a private Twine passage.</dd>
  <dt>Annotation tag</dt>
  <dd>Passages tagged with `annotation` will not be included in the EPUB file.</dd>
  <dt>Images</dt>
  <dd>The ability to add (local and remote) images to a Twine story using standard markdown syntax. Images will be
  downloaded or copied to the EPUB file.</dd>
  <dt>CSS</dt>
  <dd>The ability to add (local) stylesheets. Stylesheets will be copied to the EPUB file and included in the XHTML
  files.</dd>
  <dt>External Links</dt>
  <dd>The ability to add links to external websites.</dd>
  <dt>Static Passage Choice Magic (tm)</dt>
  <dd>The ability to use really trivial switches and conditionals and have TurnToPassage calculate all possible paths to
  put into a static EPUB file. Don't expect this any time soon, or ever. Just an idea.</dd>
  <dt>Scripting? and other EPUB3 features</dt>
  <dd>I currently do not own an eReader that supports EPUB3 features. But support for EPUB3 features, especially
  scripting could prove interesting.</dd>
</dl>

## License

See the file [`COPYING`](#).
