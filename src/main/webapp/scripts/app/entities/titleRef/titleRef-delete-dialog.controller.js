'use strict';

angular.module('hipokApp')
	.controller('TitleRefDeleteController', function($scope, $modalInstance, entity, TitleRef) {

        $scope.titleRef = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TitleRef.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });