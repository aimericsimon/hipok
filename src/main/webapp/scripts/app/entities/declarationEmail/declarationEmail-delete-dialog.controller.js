'use strict';

angular.module('hipokApp')
	.controller('DeclarationEmailDeleteController', function($scope, $uibModalInstance, entity, DeclarationEmail) {

        $scope.declarationEmail = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DeclarationEmail.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
