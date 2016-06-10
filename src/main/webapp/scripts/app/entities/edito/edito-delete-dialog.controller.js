'use strict';

angular.module('hipokApp')
	.controller('EditoDeleteController', function($scope, $modalInstance, entity, Edito) {

        $scope.edito = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Edito.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });