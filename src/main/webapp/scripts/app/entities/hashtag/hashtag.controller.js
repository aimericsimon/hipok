'use strict';

angular.module('hipokApp')
    .controller('HashtagController', function ($scope, $state, $modal, Hashtag) {
      
        $scope.hashtags = [];
        $scope.loadAll = function() {
            Hashtag.query(function(result) {
               $scope.hashtags = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.hashtag = {
                label: null,
                id: null
            };
        };
    });
