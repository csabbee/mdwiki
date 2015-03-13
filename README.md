# mdwiki
Simple wiki based on locally stored markdown files.

## Features

- retrieve markdown files in json with GET
- create new markdown document with POST

## Planned Features

- add embedded Jetty server to make standalone app
- view markdown documents rendered as html
- version control md documents with git
- integrate ~~solr~~ Lucene to provide search capabilities
- integrate Spring Security

## Usage

- GET `mdwiki/markdown/<document>.json` where `document` is without extension.
- POST `mdwiki/markdown` with body

        {
          "author" : <author>,
          "name" : <filename>,
          "content" : <file-content>
        }

    curl

        curl -X POST -H "Content-Type: application/json" -d '{"name":"post-test","author":"post-author","content":"post-content"}' http://localhost:8080/mdwiki/markdown

### Project Defaults

- `WIKI_ROOT=~/wiki/markdown`
  directory the app looks for markdown files. Flat, subirecties are not scanned
- `DEFAULT_ENCODING=UTF-8`
  encoding of markdown documents
- `LINE_SEPARATOR=\n`
- `MARKDOWN_EXTENSION=md`
- `DEFAULT_AUTHOR=default-author`

override with `-D<parameter>=<value>` at startup

## Notes

- [Spring Data Solr](http://docs.spring.io/spring-data/solr/docs/1.4.0.RC1/reference/html/)
- [JGit cookbook](https://github.com/centic9/jgit-cookbook)
