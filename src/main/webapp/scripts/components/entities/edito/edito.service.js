'use strict';

angular.module('hipokApp')
    .factory('Edito', function ($resource, DateUtils) {
        return $resource('api/editos/:id', {}, {
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
