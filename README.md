# TurnToPassage.Transformer

<img src="http://turntopassage.github.io/images/logo.svg" alt="TurnToPassage logo" title="TurnToPassage.Transformer" align="right" width="128" />

TurnToPassage is a set of tools that allows you to easily author you own Choose Your Own Adventure books in EPUB format
using [Twine 2](http://twinery.org/). This set consists of a Transformer, which transforms your regular published Twine story into an EPUB
file to be read on any modern e-reader.

## Features

The following features are currently supported:
<dl>
 <dt>Markdown</dt>
 <dd>Simple <a href="http://daringfireball.net/projects/markdown/">Markdown</a> syntax can be used to write and format your gamebook.</dd>
 <dt>Passage links</dt>
 <dd>Twine passage links (i.e. <code>[[displayed text|passage title]]</code>, <code>[[passage title]]</code>) are converted to simple <code>&lt;a&gt;</code> links in the epub which will automatically point to the correct <code>.xhtml</code> file.</dd>
 <dt>Story Stylesheet</dt>
 <dd>Your story stylesheet as defined in Twine will be add to every <code>.xhtml</code> file in your epub file.</dt>
 <dt>Images</dt>
 <dd>Both local (images on your harddrive) as remote images (images on the internet) can simply be embedded in the EPUB by using standard markdown syntax(e.g. <code>![alt](url)</code>).</dd>
 <dt>Metadata</dt>
 <dd>Any EPUB metadata can be set from inside a normal Twine passage named 'metadata' using XML.</dd>
</dl>

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
  <dt>Annotation tag</dt>
  <dd>Passages tagged with `annotation` will not be included in the EPUB file.</dd>
  <dt>Scripting? and other EPUB3 features</dt>
  <dd>I currently do not own an eReader that supports EPUB3 features. But support for EPUB3 features, especially
  scripting could prove interesting.</dd>
</dl>

## License

See the file [`COPYING`](https://raw.githubusercontent.com/TurnToPassage/TurnToPassage.Transformer/master/COPYING).
