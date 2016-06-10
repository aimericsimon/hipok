'use strict';

angular.module('hipokApp')
    .factory('Password', function ($resource) {
        return $resource('app/account/change_password', {}, {
        });
    });

angular.module('hipokApp')
    .factory('PasswordResetInit', function ($resource) {
        return $resource('app/account/reset_password/init', {}, {
        })
    });

angular.module('hipokApp')
    .factory('PasswordResetFinish', function ($resource) {
        return $resource('app/account/reset_password/finish', {}, {
        })
    });
