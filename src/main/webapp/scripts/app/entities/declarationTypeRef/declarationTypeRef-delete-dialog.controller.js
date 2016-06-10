'use strict';

angular.module('hipokApp')
	.controller('DeclarationTypeRefDeleteController', function($scope, $uibModalInstance, entity, DeclarationTypeRef) {

        $scope.declarationTypeRef = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            DeclarationTypeRef.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
