'use strict';

class MainController {
    constructor($scope) {
        this.$scope = $scope;
        this.init();
    }

    init() {
        this.$scope.message = 'Using Angular, with ES6 modules!';
    }
}

MainController.$inject = ['$scope'];

export { MainController };
