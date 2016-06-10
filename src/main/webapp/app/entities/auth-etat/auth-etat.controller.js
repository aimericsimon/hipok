(function() {
    'use strict';

    angular
        .module('hipokApp')
        .controller('AuthEtatController', AuthEtatController);

    AuthEtatController.$inject = ['$scope', '$state', 'AuthEtat'];

    function AuthEtatController ($scope, $state, AuthEtat) {
        var vm = this;
        
        vm.authEtats = [];

        loadAll();

        function loadAll() {
            AuthEtat.query(function(result) {
                vm.authEtats = result;
            });
        }
    }
})();
