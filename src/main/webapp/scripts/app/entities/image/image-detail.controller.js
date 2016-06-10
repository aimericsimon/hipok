'use strict';

angular.module('hipokApp')
    .controller('ImageDetailController', function ($scope, $rootScope, $stateParams, entity, Image) {
        $scope.image = entity;
        $scope.load = function (id) {
            Image.get({id: id}, function(result) {
                $scope.image = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:imageUpdate', function(event, result) {
            $scope.image = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
