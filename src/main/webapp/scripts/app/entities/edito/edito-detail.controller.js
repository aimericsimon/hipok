'use strict';

angular.module('hipokApp')
    .controller('EditoDetailController', function ($scope, $rootScope, $stateParams, entity, Edito) {
        $scope.edito = entity;
        $scope.load = function (id) {
            Edito.get({id: id}, function(result) {
                $scope.edito = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:editoUpdate', function(event, result) {
            $scope.edito = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
