'use strict';

class DocumentController {
    constructor(compiledMarkdownDocument) {
        this.compiledMarkdownDocument = compiledMarkdownDocument;
    }
}

DocumentController.$inject = ['compiledMarkdownDocument'];

export { DocumentController };
