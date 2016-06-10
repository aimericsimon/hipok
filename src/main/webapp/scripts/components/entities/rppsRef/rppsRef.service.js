'use strict';

angular.module('hipokApp')
    .factory('RppsRef', function ($resource, DateUtils) {
        return $resource('api/rppsRefs/:id', {}, {
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
