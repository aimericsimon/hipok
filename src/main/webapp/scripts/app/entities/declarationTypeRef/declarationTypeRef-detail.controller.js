'use strict';

angular.module('hipokApp')
    .controller('DeclarationTypeRefDetailController', function ($scope, $rootScope, $stateParams, entity, DeclarationTypeRef, Declaration) {
        $scope.declarationTypeRef = entity;
        $scope.load = function (id) {
            DeclarationTypeRef.get({id: id}, function(result) {
                $scope.declarationTypeRef = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:declarationTypeRefUpdate', function(event, result) {
            $scope.declarationTypeRef = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
