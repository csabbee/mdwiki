import angular from 'angular';
import 'angular-ui-router';
import { caching } from './htmltemplates';
import * as MarkdownModule from './markdown.module';

/**
 * We load the templates into angular by calling the exported function
 */
caching();

var mdwiki = angular.module('mdwiki',[
    'ui.router',
    'htmlTemplates'
]).config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    'use strict';

    $urlRouterProvider.otherwise('/main');

    $stateProvider.state('main', {
        url: '/main',
        templateUrl: 'main/main.html',
        controller: MarkdownModule.ctrl,
        controllerAs: 'MainController'
    })
}]);