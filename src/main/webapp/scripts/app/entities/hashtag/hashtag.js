'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('hashtag', {
                parent: 'entity',
                url: '/hashtags',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Hashtags'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hashtag/hashtags.html',
                        controller: 'HashtagController'
                    }
                },
                resolve: {
                }
            })
            .state('hashtag.detail', {
                parent: 'entity',
                url: '/hashtag/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Hashtag'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/hashtag/hashtag-detail.html',
                        controller: 'HashtagDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Hashtag', function($stateParams, Hashtag) {
                        return Hashtag.get({id : $stateParams.id});
                    }]
                }
            })
            .state('hashtag.new', {
                parent: 'hashtag',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hashtag/hashtag-dialog.html',
                        controller: 'HashtagDialogController',
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
                        $state.go('hashtag', null, { reload: true });
                    }, function() {
                        $state.go('hashtag');
                    })
                }]
            })
            .state('hashtag.edit', {
                parent: 'hashtag',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hashtag/hashtag-dialog.html',
                        controller: 'HashtagDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Hashtag', function(Hashtag) {
                                return Hashtag.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hashtag', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('hashtag.delete', {
                parent: 'hashtag',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/hashtag/hashtag-delete-dialog.html',
                        controller: 'HashtagDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Hashtag', function(Hashtag) {
                                return Hashtag.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('hashtag', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
