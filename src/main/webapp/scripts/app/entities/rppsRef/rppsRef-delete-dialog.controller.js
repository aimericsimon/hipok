'use strict';

angular.module('hipokApp')
	.controller('RppsRefDeleteController', function($scope, $modalInstance, entity, RppsRef) {

        $scope.rppsRef = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            RppsRef.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });