'use strict';

angular.module('hipokApp')
    .controller('RppsRefController', function ($scope, $state, $modal, RppsRef, ParseLinks) {
      
        $scope.rppsRefs = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            RppsRef.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.rppsRefs = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.rppsRef = {
                number: null,
                id: null
            };
        };
    });
