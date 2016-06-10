'use strict';

angular.module('hipokApp')
    .controller('DeclarationTypeRefController', function ($scope, $state, DeclarationTypeRef) {

        $scope.declarationTypeRefs = [];
        $scope.loadAll = function() {
            DeclarationTypeRef.query(function(result) {
               $scope.declarationTypeRefs = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.declarationTypeRef = {
                label: null,
                id: null
            };
        };
    });
