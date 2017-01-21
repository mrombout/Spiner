# Spiner

<img src="http://twinespiner.github.io/images/logo.svg" alt="Spiner" title="Spiner" align="right" width="128" />

Spiner is a set of tools that allows you to easily author you own Choose Your Own Adventure books using [Twine 2](http://twinery.org/). This set consists of an application, which transforms your published Twine story into either an EPUB ebook, or a real book using [LaTeX](https://www.latex-project.org/), an awesome typesetting system.

[![Build Status](https://travis-ci.org/mrombout/Spiner.svg?branch=develop)](https://travis-ci.org/mrombout/Spiner)

## Features

The following features are currently supported:
<dl>
 <dt>Markdown</dt>
 <dd>Simple <a href="http://daringfireball.net/projects/markdown/">Markdown</a> syntax can be used to write and format your gamebook (EPUB only).</dd>
 <dt>LaTeX</dt>
 <dd>Turn you Twine story into a real book by transforming your story to a LaTeX, a high-quality typesetting system.</dd>
 <dt>Passage links</dt>
 <dd>Twine passage links (i.e. <code>[[displayed text|passage title]]</code>, <code>[[passage title]]</code>) are automatically converted to your chosen format, either <code>&lt;a&gt;</code> links for EPUB, or `\gbturn` for LaTeX.</dd>
 <dt>Story Stylesheet</dt>
 <dd>Your story stylesheet as defined in Twine will be add to every <code>.xhtml</code> file in your EPUB file.</dt>
 <dt>Images</dt>
 <dd>Both local (images on your harddrive) as remote images (images on the internet) can simply be embedded in the EPUB by using standard markdown syntax(e.g. <code>![alt](url)</code>).</dd>
 <dt>Metadata</dt>
 <dd>Any EPUB metadata can be set from inside a normal Twine passage named 'metadata' using XML.</dd>
</dl>

## Usage

### Graphical User Interface

By far the easiest way to get started is to let the GUI to do the work for you. Just download the correct version for your operating system and follow the instructions on the screen!

Operating System  | Download
----------------- | -------------
Windows           | _no download available yet_
Mac OS            | _no download available yet_
Linux             | _no download available yet_
Other             | _no download available yet_

### Commandline

There is also a separate command-line version that works like this:

```
usage: java -jar spiner-0.0.1.jar [-f <format>] [-help | -version] [-i <input>] [-o <file>]
 -f,--format <format>   output format, one of (epub|latex)
 -help                  display this help and exit
 -i,--file <input>      location of input HTML file
 -o,--output <file>     location of output file
 -version               output version information and exit
```

```
java -jar spiner-0.0.1.jar -f "epub" -i "My Cool Story.html" -o "My Cool Story.epub"
```

## Roadmap

The following features are currently not implemented but are to be expected somewhere in the indefinite future, in roughly the following order:

<dl>
  <dt>Annotation tag</dt>
  <dd>Passages tagged with `annotation` will not be included in the EPUB file.</dd>
  <dt>Unified markup</dt>
  <dd>It would be nice to be able to transform the same Twine story into both an EPUB and LaTeX file without having to fix up tiny details in either of them.</dd>
  <dt>Scripting? and other EPUB3 features</dt>
  <dd>I currently do not own an eReader that supports EPUB3 features. But support for EPUB3 features, especially
  scripting could prove interesting.</dd>
</dl>

## License

See the file [`COPYING`](https://raw.githubusercontent.com/TwineSpiner/Spiner/master/COPYING).
