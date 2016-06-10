'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('rppsRef', {
                parent: 'entity',
                url: '/rppsRefs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'RppsRefs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rppsRef/rppsRefs.html',
                        controller: 'RppsRefController'
                    }
                },
                resolve: {
                }
            })
            .state('rppsRef.detail', {
                parent: 'entity',
                url: '/rppsRef/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'RppsRef'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/rppsRef/rppsRef-detail.html',
                        controller: 'RppsRefDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'RppsRef', function($stateParams, RppsRef) {
                        return RppsRef.get({id : $stateParams.id});
                    }]
                }
            })
            .state('rppsRef.new', {
                parent: 'rppsRef',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/rppsRef/rppsRef-dialog.html',
                        controller: 'RppsRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    number: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('rppsRef', null, { reload: true });
                    }, function() {
                        $state.go('rppsRef');
                    })
                }]
            })
            .state('rppsRef.edit', {
                parent: 'rppsRef',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/rppsRef/rppsRef-dialog.html',
                        controller: 'RppsRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['RppsRef', function(RppsRef) {
                                return RppsRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rppsRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('rppsRef.delete', {
                parent: 'rppsRef',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/rppsRef/rppsRef-delete-dialog.html',
                        controller: 'RppsRefDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['RppsRef', function(RppsRef) {
                                return RppsRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('rppsRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
