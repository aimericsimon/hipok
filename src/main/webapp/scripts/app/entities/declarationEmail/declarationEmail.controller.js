'use strict';

angular.module('hipokApp')
    .controller('DeclarationEmailController', function ($scope, $state, DeclarationEmail) {

        $scope.declarationEmails = [];
        $scope.loadAll = function() {
            DeclarationEmail.query(function(result) {
               $scope.declarationEmails = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.declarationEmail = {
                email: null,
                id: null
            };
        };
    });
