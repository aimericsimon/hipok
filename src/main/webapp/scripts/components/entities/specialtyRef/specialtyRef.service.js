'use strict';

angular.module('hipokApp')
    .factory('SpecialtyRef', function ($resource, DateUtils) {
        return $resource('api/specialtyRefs/:id', {}, {
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
