'use strict';

angular.module('hipokApp')
	.controller('AnatomicZoneRefDeleteController', function($scope, $modalInstance, entity, AnatomicZoneRef) {

        $scope.anatomicZoneRef = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            AnatomicZoneRef.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });