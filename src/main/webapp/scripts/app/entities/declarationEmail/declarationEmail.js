'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('declarationEmail', {
                parent: 'entity',
                url: '/declarationEmails',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DeclarationEmails'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/declarationEmail/declarationEmails.html',
                        controller: 'DeclarationEmailController'
                    }
                },
                resolve: {
                }
            })
            .state('declarationEmail.detail', {
                parent: 'entity',
                url: '/declarationEmail/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'DeclarationEmail'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/declarationEmail/declarationEmail-detail.html',
                        controller: 'DeclarationEmailDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'DeclarationEmail', function($stateParams, DeclarationEmail) {
                        return DeclarationEmail.get({id : $stateParams.id});
                    }]
                }
            })
            .state('declarationEmail.new', {
                parent: 'declarationEmail',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declarationEmail/declarationEmail-dialog.html',
                        controller: 'DeclarationEmailDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    email: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('declarationEmail', null, { reload: true });
                    }, function() {
                        $state.go('declarationEmail');
                    })
                }]
            })
            .state('declarationEmail.edit', {
                parent: 'declarationEmail',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declarationEmail/declarationEmail-dialog.html',
                        controller: 'DeclarationEmailDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['DeclarationEmail', function(DeclarationEmail) {
                                return DeclarationEmail.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('declarationEmail', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('declarationEmail.delete', {
                parent: 'declarationEmail',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/declarationEmail/declarationEmail-delete-dialog.html',
                        controller: 'DeclarationEmailDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['DeclarationEmail', function(DeclarationEmail) {
                                return DeclarationEmail.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('declarationEmail', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
