'use strict';

angular.module('hipokApp')
	.controller('FollowDeleteController', function($scope, $uibModalInstance, entity, Follow) {

        $scope.follow = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Follow.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
