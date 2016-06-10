'use strict';

angular.module('hipokApp')
    .controller('DeviceDetailController', function ($scope, $rootScope, $stateParams, entity, Device, Profile) {
        $scope.device = entity;
        $scope.load = function (id) {
            Device.get({id: id}, function(result) {
                $scope.device = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:deviceUpdate', function(event, result) {
            $scope.device = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
