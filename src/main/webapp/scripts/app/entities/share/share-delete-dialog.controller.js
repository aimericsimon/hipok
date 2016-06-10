'use strict';

angular.module('hipokApp')
	.controller('ShareDeleteController', function($scope, $modalInstance, entity, Share) {

        $scope.share = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Share.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });