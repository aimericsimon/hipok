'use strict';

angular.module('hipokApp')
	.controller('DeviceDeleteController', function($scope, $uibModalInstance, entity, Device) {

        $scope.device = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Device.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
