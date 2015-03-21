'use strict';

class DocumentController {
    constructor(compiledMarkdownDocument) {
        this.compiledMarkdownDocument = compiledMarkdownDocument;
        this.init();
    }

    init() {
        angular.element(document.querySelector('#document-content'))
            .append(angular.element(this.compiledMarkdownDocument.content));
    }
}

DocumentController.$inject = ['compiledMarkdownDocument'];

export { DocumentController };
