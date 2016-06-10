(function() {
    'use strict';

    angular
        .module('hipokApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('auth-etat', {
            parent: 'entity',
            url: '/auth-etat',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AuthEtats'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auth-etat/auth-etats.html',
                    controller: 'AuthEtatController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('auth-etat-detail', {
            parent: 'entity',
            url: '/auth-etat/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'AuthEtat'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/auth-etat/auth-etat-detail.html',
                    controller: 'AuthEtatDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'AuthEtat', function($stateParams, AuthEtat) {
                    return AuthEtat.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('auth-etat.new', {
            parent: 'auth-etat',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auth-etat/auth-etat-dialog.html',
                    controller: 'AuthEtatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                idProfile: null,
                                idImageAuth: null,
                                etat: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('auth-etat', null, { reload: true });
                }, function() {
                    $state.go('auth-etat');
                });
            }]
        })
        .state('auth-etat.edit', {
            parent: 'auth-etat',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auth-etat/auth-etat-dialog.html',
                    controller: 'AuthEtatDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['AuthEtat', function(AuthEtat) {
                            return AuthEtat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auth-etat', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('auth-etat.delete', {
            parent: 'auth-etat',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/auth-etat/auth-etat-delete-dialog.html',
                    controller: 'AuthEtatDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['AuthEtat', function(AuthEtat) {
                            return AuthEtat.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('auth-etat', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
