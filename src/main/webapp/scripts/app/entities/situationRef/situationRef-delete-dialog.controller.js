'use strict';

angular.module('hipokApp')
	.controller('SituationRefDeleteController', function($scope, $modalInstance, entity, SituationRef) {

        $scope.situationRef = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            SituationRef.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });