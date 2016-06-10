'use strict';

angular.module('hipokApp')
    .factory('Register', function ($resource) {
        return $resource('app/register', {}, {
        });
    });


