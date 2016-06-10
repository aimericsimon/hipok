'use strict';

angular.module('hipokApp')
    .factory('errorHandlerInterceptor', function ($q, $rootScope) {
        return {
            'responseError': function (response) {
                if (!(response.status == 401 && response.data.path.indexOf("/app/account") == 0 )){
	                $rootScope.$emit('hipokApp.httpError', response);
	            }
                return $q.reject(response);
            }
        };
    });
