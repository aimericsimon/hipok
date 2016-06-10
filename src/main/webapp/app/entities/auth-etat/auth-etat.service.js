(function() {
    'use strict';
    angular
        .module('hipokApp')
        .factory('AuthEtat', AuthEtat);

    AuthEtat.$inject = ['$resource'];

    function AuthEtat ($resource) {
        var resourceUrl =  'api/auth-etats/:id';

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
