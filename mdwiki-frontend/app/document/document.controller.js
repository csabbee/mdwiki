import markdown from 'markdown';

class DocumentController {
    constructor(markdownDocument) {
        this.markdownDocument = markdownDocument;
        this.init();
    }

    init() {
        var content = markdown.parse(this.markdownDocument.content);
        angular.element(document.querySelector('#document-content'))
            .append(angular.element(content));
    }
}

DocumentController.$inject = ['markdownDocument'];

export { DocumentController };
