# mdwiki
rudimentary markdown wiki

Simple wiki based on locally stored markdown files.

## Running app from command line

    mvn tomcat:run

This will install every dependencies both for the backend and frontend.

Afterwards you can reach the app in the browser on [localhost:8080/kicsikrumpli](http://localhost:8080/kicsikrumpli)

## Developing the frontend
The following commands should be given out in the **webappsrc** folder

    npm install
    gulp watch

Upon changing/adding any javascript, html file in the **app** folder it will bundle them together into the **build.js** file.

## Reading materials
* [Angular 1.x + ES6 modules](http://engineering.iconnect360.com/angularjs/)
