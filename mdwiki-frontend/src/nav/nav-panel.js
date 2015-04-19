import {bindable} from 'aurelia-framework';
import {customElement} from 'aurelia-framework';

@customElement('nav-panel')
export class NavPanel {
    @bindable router = null;
}