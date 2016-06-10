'use strict';

angular.module('hipokApp')
	.controller('DeclarationDeleteController', function($scope, $uibModalInstance, entity, Declaration) {

        $scope.declaration = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Declaration.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
