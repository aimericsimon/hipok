'use strict';

angular.module('hipokApp')
	.controller('PublicationDeleteController', function($scope, $uibModalInstance, entity, Publication) {

        $scope.publication = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Publication.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
