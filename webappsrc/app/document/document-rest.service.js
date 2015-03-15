'use strict';

const baseUrl = new WeakMap();

class DocumentRest {
    constructor($http, $q, RestUrls, $log) {
        this.$http = $http;
        this.$q = $q;
        this.$log = $log;
        baseUrl.set(this, RestUrls.base+'/markdown/');
    }

    // TODO: server side or client side rendering?
    getDocument(name) {
        var deferResult = this.$q.defer();
        var self = this;

        this.$http({
            method: 'JSONP',
            url: baseUrl.get(this) + name +'.json'
        }).success(document => {
            self.$log.info(name);
            deferResult.resolve(document);
        }).error((data, status) => {
            self.$log.error(data);
            self.$log.error(status);
        });

        return deferResult.promise;
    }
}

DocumentRest.$inject = ['$http', '$q', 'RestUrls', '$log'];

export { DocumentRest };