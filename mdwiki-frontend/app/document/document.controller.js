class DocumentController {
    constructor(markdownDocument, MarkdownParser) {
        this.markdownDocument = markdownDocument;
        this.MarkdownParser = MarkdownParser;
        this.init();
    }

    init() {
        this.MarkdownParser.parse(this.markdownDocument.content);
    }
}

DocumentController.$inject = ['markdownDocument', 'MarkdownParser'];

export { DocumentController };
