'use strict';

angular.module('hipokApp')
    .factory('Notification', function ($resource, DateUtils) {
        return $resource('api/notifications/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.creationDate = DateUtils.convertDateTimeFromServer(data.creationDate);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
