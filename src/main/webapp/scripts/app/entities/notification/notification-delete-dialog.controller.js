'use strict';

angular.module('hipokApp')
	.controller('NotificationDeleteController', function($scope, $uibModalInstance, entity, Notification) {

        $scope.notification = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Notification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
