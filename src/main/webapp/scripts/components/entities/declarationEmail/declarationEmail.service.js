'use strict';

angular.module('hipokApp')
    .factory('DeclarationEmail', function ($resource, DateUtils) {
        return $resource('api/declarationEmails/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
