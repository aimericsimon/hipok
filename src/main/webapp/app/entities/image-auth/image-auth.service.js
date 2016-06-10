(function() {
    'use strict';
    angular
        .module('hipokApp')
        .factory('ImageAuth', ImageAuth);

    ImageAuth.$inject = ['$resource'];

    function ImageAuth ($resource) {
        var resourceUrl =  'api/image-auths/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
