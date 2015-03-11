# mdwiki
Simple wiki based on locally stored markdown files.

## Features

- retrieve markdown files in json with GET

## Planned Features

- create new markdown document with POST
- add embedded Jetty server to make standalone app
- view markdown documents rendered as html
- version control md documents with git
- integrate solr to provide search capabilities
- integrate Spring Security

## Usage

GET `mdwiki/markdown/<document>.json` where `document` is without extension.

### Project Defaults

- `WIKI_ROOT=~/wiki/markdown`
  directory the app looks for markdown files. Flat, subirecties are not scanned
- `DEFAULT_ENCODING=UTF-8`
  encoding of markdown documents
- `LINE_SEPARATOR=\n`
- `MARKDOWN_EXTENSION=md`
- `DEFAULT_AUTHOR=default-author`

override with `-D<parameter>=<value>` at startup
