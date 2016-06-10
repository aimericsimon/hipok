'use strict';

angular.module('hipokApp')
    .factory('Hashtag', function ($resource, DateUtils) {
        return $resource('api/hashtags/:id', {}, {
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
