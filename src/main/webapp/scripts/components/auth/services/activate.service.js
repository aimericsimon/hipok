'use strict';

angular.module('hipokApp')
    .factory('Activate', function ($resource) {
        return $resource('app/activate', {}, {
            'get': { method: 'GET', params: {}, isArray: false}
        });
    });


