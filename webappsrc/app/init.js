import angular from 'angular';
import 'angular-ui-router';
import { caching } from './htmltemplates';
import './markdown.module';

/**
 * We load the templates into angular by calling the exported function
 */
caching();

angular.module('mdwiki', [
    'ui.router',
    'htmlTemplates',
    'mdwiki.intangibles'
]).config(['$stateProvider', '$urlRouterProvider', ($stateProvider, $urlRouterProvider) => {
    'use strict';

    $urlRouterProvider.otherwise('/main');

    $stateProvider.state('main', {
        url: '/main',
        templateUrl: 'main/main.html',
        controller: 'MainController',
        controllerAs: 'MainController'
    })
}]);