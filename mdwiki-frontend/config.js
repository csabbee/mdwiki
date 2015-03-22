System.config({
  "baseURL": "./",
  "transpiler": "babel",
  "paths": {
    "*": "*.js",
    "github:*": "jspm_packages/github/*.js",
    "npm:*": "jspm_packages/npm/*.js"
  },
  "bundles": {
    "build": [
      "github:angular/bower-angular@1.3.14/angular",
      "github:angular-ui/ui-router@0.2.13/angular-ui-router",
      "github:angular/bower-angular@1.3.14",
      "github:angular-ui/ui-router@0.2.13",
      "app/user.module",
      "app/init"
    ]
  }
});

System.config({
  "map": {
    "angular": "github:angular/bower-angular@1.3.14",
    "angular-ui-router": "github:angular-ui/ui-router@0.2.13",
    "highlightjs": "github:isagalaev/highlight.js@7.0.1",
    "marked": "github:chjj/marked@0.3.3",
    "github:angular-ui/ui-router@0.2.13": {
      "angular": "github:angular/bower-angular@1.3.14"
    }
  }
});

