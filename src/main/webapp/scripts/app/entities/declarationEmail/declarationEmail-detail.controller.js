'use strict';

angular.module('hipokApp')
    .controller('DeclarationEmailDetailController', function ($scope, $rootScope, $stateParams, entity, DeclarationEmail) {
        $scope.declarationEmail = entity;
        $scope.load = function (id) {
            DeclarationEmail.get({id: id}, function(result) {
                $scope.declarationEmail = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:declarationEmailUpdate', function(event, result) {
            $scope.declarationEmail = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
