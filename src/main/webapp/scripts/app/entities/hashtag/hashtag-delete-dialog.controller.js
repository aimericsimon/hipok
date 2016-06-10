'use strict';

angular.module('hipokApp')
	.controller('HashtagDeleteController', function($scope, $modalInstance, entity, Hashtag) {

        $scope.hashtag = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Hashtag.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });