import {inject} from 'aurelia-framework';
import {Router} from 'aurelia-router';
import 'bootstrap';
import 'bootstrap/css/bootstrap.css!';

@inject(Router)
export class App{
    constructor(router) {
        this.router = router;
        this.router.configure(config=> {
            config.title = 'MarkDown Wiki';
            config.map([
                { route: ['','welcome'], moduleId: './welcome', nav: true, title: 'Welcome' },
                { route: 'document-router', moduleId: './document/document-router', nav:true, title: 'Document Router' }
            ]);
        });
    }
}