'use strict';

angular.module('hipokApp')
    .factory('Declaration', function ($resource, DateUtils) {
        return $resource('api/declarations/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.declarationDate = DateUtils.convertDateTimeFromServer(data.declarationDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
