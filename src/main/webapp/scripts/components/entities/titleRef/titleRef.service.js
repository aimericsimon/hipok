'use strict';

angular.module('hipokApp')
    .factory('TitleRef', function ($resource, DateUtils) {
        return $resource('api/titleRefs/:id', {}, {
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
