'use strict';

angular.module('hipokApp')
    .factory('MedicalTypeRef', function ($resource, DateUtils) {
        return $resource('api/medicalTypeRefs/:id', {}, {
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
