'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('declaration', {
                parent: 'entity',
                url: '/declarations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Declarations'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/declaration/declarations.html',
                        controller: 'DeclarationController'
                    }
                },
                resolve: {
                }
            })
            .state('declaration.detail', {
                parent: 'entity',
                url: '/declaration/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Declaration'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/declaration/declaration-detail.html',
                        controller: 'DeclarationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Declaration', function($stateParams, Declaration) {
                        return Declaration.get({id : $stateParams.id});
                    }]
                }
            })
            .state('declaration.new', {
                parent: 'declaration',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declaration/declaration-dialog.html',
                        controller: 'DeclarationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    description: null,
                                    declarationDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('declaration', null, { reload: true });
                    }, function() {
                        $state.go('declaration');
                    })
                }]
            })
            .state('declaration.edit', {
                parent: 'declaration',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declaration/declaration-dialog.html',
                        controller: 'DeclarationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Declaration', function(Declaration) {
                                return Declaration.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('declaration', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('declaration.delete', {
                parent: 'declaration',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declaration/declaration-delete-dialog.html',
                        controller: 'DeclarationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Declaration', function(Declaration) {
                                return Declaration.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('declaration', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
