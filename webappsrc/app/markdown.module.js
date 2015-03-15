'use strict';

import { MainController } from './main/main.controller';
import { NavigationController } from './navigation/navigation.controller';

export default angular.module('mdwiki.intangibles', [])
    .controller('MainController', MainController)
    .controller('NavigationController', NavigationController)
