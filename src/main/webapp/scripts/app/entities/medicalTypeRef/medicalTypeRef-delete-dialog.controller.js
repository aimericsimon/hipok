'use strict';

angular.module('hipokApp')
	.controller('MedicalTypeRefDeleteController', function($scope, $modalInstance, entity, MedicalTypeRef) {

        $scope.medicalTypeRef = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            MedicalTypeRef.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });