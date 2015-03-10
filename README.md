# mdwiki
Simple wiki based on locally stored markdown files.

## features

- retrieve markdown files in json with GET

## planned features

- create new markdown document with POST
- add embedded Jetty server to make standalone app
- version control md documents with git
- view markdown documents rendered as html
- integrate Spring Security

## Usage

GET `mdwiki/markdown/<document>.json` where `document` is without extension.

### Project defaults

- `WIKI_ROOT=~/wiki/markdown`
  directory the app looks for markdown files. Flat, subirecties are not scanned
- `DEFAULT_ENCODING=UTF-8`
  encoding of markdown documents
- `LINE_SEPARATOR=\n`
- `MARKDOWN_EXTENSION=md`
- `DEFAULT_AUTHOR=default-author`

override with `-D<parameter>=<value>` at startup
