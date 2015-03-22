'use strict';
import marked from 'marked';
import highlightjs from 'highlightjs';

function init() {
    marked.setOptions({
        renderer: new marked.Renderer(),
        gfm: true,
        tables: true,
        breaks: false,
        pedantic: false,
        sanitize: true,
        smartLists: true,
        smartypants: true
    });
}

class MarkdownParser {
    constructor() {
        init();
    }

    parse(markdownDocument) {
        var content = marked(markdownDocument);
        angular.element(document.querySelector('#document-content'))
            .append(angular.element(content));

        this.highlight();
    }

    highlight(htmlContent) {
        var codeBlocks = document.getElementsByTagName('pre');
        angular.forEach(codeBlocks, codeBlock => {
            angular.element(codeBlock).addClass('javascript');
            console.dir(codeBlock.className);
            highlightjs.highlightBlock(codeBlock);
        });
    }
}

MarkdownParser.$inject = [];

export { MarkdownParser };