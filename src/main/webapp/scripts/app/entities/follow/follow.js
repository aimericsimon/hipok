'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('follow', {
                parent: 'entity',
                url: '/follows',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Follows'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/follow/follows.html',
                        controller: 'FollowController'
                    }
                },
                resolve: {
                }
            })
            .state('follow.detail', {
                parent: 'entity',
                url: '/follow/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Follow'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/follow/follow-detail.html',
                        controller: 'FollowDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Follow', function($stateParams, Follow) {
                        return Follow.get({id : $stateParams.id});
                    }]
                }
            })
            .state('follow.new', {
                parent: 'follow',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/follow/follow-dialog.html',
                        controller: 'FollowDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    state: null,
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('follow', null, { reload: true });
                    }, function() {
                        $state.go('follow');
                    })
                }]
            })
            .state('follow.edit', {
                parent: 'follow',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/follow/follow-dialog.html',
                        controller: 'FollowDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Follow', function(Follow) {
                                return Follow.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('follow', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('follow.delete', {
                parent: 'follow',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/follow/follow-delete-dialog.html',
                        controller: 'FollowDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Follow', function(Follow) {
                                return Follow.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('follow', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
