'use strict';

angular.module('hipokApp')
    .controller('EditoController', function ($scope, $state, $modal, Edito) {
      
        $scope.editos = [];
        $scope.loadAll = function() {
            Edito.query(function(result) {
               $scope.editos = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.edito = {
                label: null,
                description: null,
                id: null
            };
        };
    });
