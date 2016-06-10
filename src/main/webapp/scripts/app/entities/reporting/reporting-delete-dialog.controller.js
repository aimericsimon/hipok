'use strict';

angular.module('hipokApp')
	.controller('ReportingDeleteController', function($scope, $modalInstance, entity, Reporting) {

        $scope.reporting = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Reporting.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });