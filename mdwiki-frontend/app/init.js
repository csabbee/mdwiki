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

    $urlRouterProvider.otherwise('/');

    $stateProvider.state('home', {
        url: '/',
        views: {
            "navigationPane": {
                templateUrl: "navigation/navigationPanel.html",
                controller: 'NavigationController',
                controllerAs: 'NavigationController'
            },
            "content": {
                templateUrl: 'main/main.html',
                controller: 'MainController',
                controllerAs: 'MainController'
            }
        }
    }).state('home.document', {
        url: 'document/:document',
        templateUrl: 'document/document.html',
        controller: 'DocumentController',
        controllerAs: 'DocumentController',
        resolve: {
            markdownDocument: ['$stateParams', 'DocumentRest',
                ($stateParams, DocumentRest) => {
                    return DocumentRest.getDocument($stateParams.document);
                }]
        }
    });
}]);