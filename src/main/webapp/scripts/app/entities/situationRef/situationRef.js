'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('situationRef', {
                parent: 'entity',
                url: '/situationRefs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SituationRefs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/situationRef/situationRefs.html',
                        controller: 'SituationRefController'
                    }
                },
                resolve: {
                }
            })
            .state('situationRef.detail', {
                parent: 'entity',
                url: '/situationRef/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SituationRef'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/situationRef/situationRef-detail.html',
                        controller: 'SituationRefDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SituationRef', function($stateParams, SituationRef) {
                        return SituationRef.get({id : $stateParams.id});
                    }]
                }
            })
            .state('situationRef.new', {
                parent: 'situationRef',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/situationRef/situationRef-dialog.html',
                        controller: 'SituationRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    label: null,
                                    code: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('situationRef', null, { reload: true });
                    }, function() {
                        $state.go('situationRef');
                    })
                }]
            })
            .state('situationRef.edit', {
                parent: 'situationRef',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/situationRef/situationRef-dialog.html',
                        controller: 'SituationRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SituationRef', function(SituationRef) {
                                return SituationRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('situationRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('situationRef.delete', {
                parent: 'situationRef',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/situationRef/situationRef-delete-dialog.html',
                        controller: 'SituationRefDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SituationRef', function(SituationRef) {
                                return SituationRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('situationRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
