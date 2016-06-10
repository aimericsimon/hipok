'use strict';

angular.module('hipokApp')
    .factory('Account', function Account($resource) {
        return $resource('app/account', {}, {
            'get': { method: 'GET', params: {}, isArray: false,
                interceptor: {
                    response: function(response) {
                        // expose response
                        return response;
                    }
                }
            }
        });
    });
