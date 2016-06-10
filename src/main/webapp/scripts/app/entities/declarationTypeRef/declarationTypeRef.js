'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('declarationTypeRef', {
                parent: 'entity',
                url: '/declarationTypeRefs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DeclarationTypeRefs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/declarationTypeRef/declarationTypeRefs.html',
                        controller: 'DeclarationTypeRefController'
                    }
                },
                resolve: {
                }
            })
            .state('declarationTypeRef.detail', {
                parent: 'entity',
                url: '/declarationTypeRef/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DeclarationTypeRef'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/declarationTypeRef/declarationTypeRef-detail.html',
                        controller: 'DeclarationTypeRefDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DeclarationTypeRef', function($stateParams, DeclarationTypeRef) {
                        return DeclarationTypeRef.get({id : $stateParams.id});
                    }]
                }
            })
            .state('declarationTypeRef.new', {
                parent: 'declarationTypeRef',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declarationTypeRef/declarationTypeRef-dialog.html',
                        controller: 'DeclarationTypeRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    label: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('declarationTypeRef', null, { reload: true });
                    }, function() {
                        $state.go('declarationTypeRef');
                    })
                }]
            })
            .state('declarationTypeRef.edit', {
                parent: 'declarationTypeRef',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declarationTypeRef/declarationTypeRef-dialog.html',
                        controller: 'DeclarationTypeRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DeclarationTypeRef', function(DeclarationTypeRef) {
                                return DeclarationTypeRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('declarationTypeRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('declarationTypeRef.delete', {
                parent: 'declarationTypeRef',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declarationTypeRef/declarationTypeRef-delete-dialog.html',
                        controller: 'DeclarationTypeRefDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DeclarationTypeRef', function(DeclarationTypeRef) {
                                return DeclarationTypeRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('declarationTypeRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
