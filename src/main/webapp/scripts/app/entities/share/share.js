'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('share', {
                parent: 'entity',
                url: '/shares',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Shares'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/share/shares.html',
                        controller: 'ShareController'
                    }
                },
                resolve: {
                }
            })
            .state('share.detail', {
                parent: 'entity',
                url: '/share/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Share'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/share/share-detail.html',
                        controller: 'ShareDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Share', function($stateParams, Share) {
                        return Share.get({id : $stateParams.id});
                    }]
                }
            })
            .state('share.new', {
                parent: 'share',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/share/share-dialog.html',
                        controller: 'ShareDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('share', null, { reload: true });
                    }, function() {
                        $state.go('share');
                    })
                }]
            })
            .state('share.edit', {
                parent: 'share',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/share/share-dialog.html',
                        controller: 'ShareDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Share', function(Share) {
                                return Share.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('share', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('share.delete', {
                parent: 'share',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/share/share-delete-dialog.html',
                        controller: 'ShareDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Share', function(Share) {
                                return Share.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('share', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
