'use strict';

angular.module('hipokApp')
    .factory('Reporting', function ($resource, DateUtils) {
        return $resource('api/reportings/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.reportingDate = DateUtils.convertDateTimeFromServer(data.reportingDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
