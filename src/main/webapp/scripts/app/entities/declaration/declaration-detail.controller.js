'use strict';

angular.module('hipokApp')
    .controller('DeclarationDetailController', function ($scope, $rootScope, $stateParams, entity, Declaration, Profile, DeclarationTypeRef) {
        $scope.declaration = entity;
        $scope.load = function (id) {
            Declaration.get({id: id}, function(result) {
                $scope.declaration = result;
            });
        };
        var unsubscribe = $rootScope.$on('hipokApp:declarationUpdate', function(event, result) {
            $scope.declaration = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
