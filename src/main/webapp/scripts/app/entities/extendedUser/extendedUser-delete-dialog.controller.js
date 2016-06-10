'use strict';

angular.module('hipokApp')
	.controller('ExtendedUserDeleteController', function($scope, $modalInstance, entity, ExtendedUser) {

        $scope.extendedUser = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ExtendedUser.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });