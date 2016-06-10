'use strict';

angular.module('hipokApp')
	.controller('SpecialtyRefDeleteController', function($scope, $modalInstance, entity, SpecialtyRef) {

        $scope.specialtyRef = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SpecialtyRef.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });