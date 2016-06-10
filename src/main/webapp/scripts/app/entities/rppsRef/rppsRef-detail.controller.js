'use strict';

angular.module('hipokApp')
    .controller('RppsRefDetailController', function ($scope, $rootScope, $stateParams, entity, RppsRef) {
        $scope.rppsRef = entity;
        $scope.load = function (id) {
            RppsRef.get({id: id}, function(result) {
                $scope.rppsRef = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:rppsRefUpdate', function(event, result) {
            $scope.rppsRef = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
