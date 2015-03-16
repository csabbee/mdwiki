'use strict';

class NavigationController {
    constructor($location) {
        this.$location = $location;
        this.init();
    }

    init() {
        this.links = {
            doc1: 'doc1',
            doc2: 'doc2',
            doc3: 'doc3',
            doc4: 'doc4'
        }
    }
}

NavigationController.$inject = ['$location'];

export { NavigationController };
