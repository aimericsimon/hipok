'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('anatomicZoneRef', {
                parent: 'entity',
                url: '/anatomicZoneRefs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'AnatomicZoneRefs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/anatomicZoneRef/anatomicZoneRefs.html',
                        controller: 'AnatomicZoneRefController'
                    }
                },
                resolve: {
                }
            })
            .state('anatomicZoneRef.detail', {
                parent: 'entity',
                url: '/anatomicZoneRef/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'AnatomicZoneRef'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/anatomicZoneRef/anatomicZoneRef-detail.html',
                        controller: 'AnatomicZoneRefDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'AnatomicZoneRef', function($stateParams, AnatomicZoneRef) {
                        return AnatomicZoneRef.get({id : $stateParams.id});
                    }]
                }
            })
            .state('anatomicZoneRef.new', {
                parent: 'anatomicZoneRef',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/anatomicZoneRef/anatomicZoneRef-dialog.html',
                        controller: 'AnatomicZoneRefDialogController',
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
                        $state.go('anatomicZoneRef', null, { reload: true });
                    }, function() {
                        $state.go('anatomicZoneRef');
                    })
                }]
            })
            .state('anatomicZoneRef.edit', {
                parent: 'anatomicZoneRef',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/anatomicZoneRef/anatomicZoneRef-dialog.html',
                        controller: 'AnatomicZoneRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['AnatomicZoneRef', function(AnatomicZoneRef) {
                                return AnatomicZoneRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('anatomicZoneRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('anatomicZoneRef.delete', {
                parent: 'anatomicZoneRef',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/anatomicZoneRef/anatomicZoneRef-delete-dialog.html',
                        controller: 'AnatomicZoneRefDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['AnatomicZoneRef', function(AnatomicZoneRef) {
                                return AnatomicZoneRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('anatomicZoneRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
