import { inject } from 'aurelia-framework';
import { Router } from 'aurelia-router';

@inject(Router)
export class DocumentRouter {
    constructor(router) {
        this.router = router;
        this.router.configure(config=> {
            config.title = 'MarkDown Wiki';
            config.map([
                { route: '', moduleId: './document', nav: true, title: 'Documents'},
                { route: 'document/doc1', moduleId: './document', nav:true, title: 'Document' },
                { route: 'document/doc2', moduleId: './document', nav:true, title: 'Document' }
            ]);
        });
    };
}