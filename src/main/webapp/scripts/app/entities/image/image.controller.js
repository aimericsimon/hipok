'use strict';

angular.module('hipokApp')
    .controller('ImageController', function ($scope, $state, Image) {

        $scope.images = [];
        $scope.loadAll = function() {
            Image.query(function(result) {
               $scope.images = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.image = {
                imageUrl: null,
                imageThumbnailUrl: null,
                id: null
            };
        };
    });
