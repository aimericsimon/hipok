'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('edito', {
                parent: 'entity',
                url: '/editos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Editos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/edito/editos.html',
                        controller: 'EditoController'
                    }
                },
                resolve: {
                }
            })
            .state('edito.detail', {
                parent: 'entity',
                url: '/edito/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Edito'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/edito/edito-detail.html',
                        controller: 'EditoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Edito', function($stateParams, Edito) {
                        return Edito.get({id : $stateParams.id});
                    }]
                }
            })
            .state('edito.new', {
                parent: 'edito',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/edito/edito-dialog.html',
                        controller: 'EditoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    label: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('edito', null, { reload: true });
                    }, function() {
                        $state.go('edito');
                    })
                }]
            })
            .state('edito.edit', {
                parent: 'edito',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/edito/edito-dialog.html',
                        controller: 'EditoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Edito', function(Edito) {
                                return Edito.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('edito', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('edito.delete', {
                parent: 'edito',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/edito/edito-delete-dialog.html',
                        controller: 'EditoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Edito', function(Edito) {
                                return Edito.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('edito', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
