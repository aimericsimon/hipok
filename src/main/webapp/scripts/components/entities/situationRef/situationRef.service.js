'use strict';

angular.module('hipokApp')
    .factory('SituationRef', function ($resource, DateUtils) {
        return $resource('api/situationRefs/:id', {}, {
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
