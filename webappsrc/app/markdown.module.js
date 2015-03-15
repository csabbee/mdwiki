'use strict';

import { MainController } from './main/main.controller';
import { NavigationController } from './navigation/navigation.controller';
import { DocumentController } from './document/document.controller';
import { DocumentRest } from './document/document-rest.service';
import { urls } from './config/url-configs';

export default angular.module('mdwiki.intangibles', [])
    .controller('MainController', MainController)
    .controller('NavigationController', NavigationController)
    .controller('DocumentController', DocumentController)
    .value('RestUrls', urls)
    .service('DocumentRest', DocumentRest);
