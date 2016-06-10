'use strict';

angular.module('hipokApp')
    .controller('DeviceController', function ($scope, $state, Device) {

        $scope.devices = [];
        $scope.loadAll = function() {
            Device.query(function(result) {
               $scope.devices = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.device = {
                token: null,
                type: null,
                enabled: false,
                id: null
            };
        };
    });
