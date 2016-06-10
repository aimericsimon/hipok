'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('titleRef', {
                parent: 'entity',
                url: '/titleRefs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TitleRefs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/titleRef/titleRefs.html',
                        controller: 'TitleRefController'
                    }
                },
                resolve: {
                }
            })
            .state('titleRef.detail', {
                parent: 'entity',
                url: '/titleRef/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'TitleRef'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/titleRef/titleRef-detail.html',
                        controller: 'TitleRefDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TitleRef', function($stateParams, TitleRef) {
                        return TitleRef.get({id : $stateParams.id});
                    }]
                }
            })
            .state('titleRef.new', {
                parent: 'titleRef',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/titleRef/titleRef-dialog.html',
                        controller: 'TitleRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    label: null,
                                    abbreviation: null,
                                    code: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('titleRef', null, { reload: true });
                    }, function() {
                        $state.go('titleRef');
                    })
                }]
            })
            .state('titleRef.edit', {
                parent: 'titleRef',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/titleRef/titleRef-dialog.html',
                        controller: 'TitleRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TitleRef', function(TitleRef) {
                                return TitleRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('titleRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('titleRef.delete', {
                parent: 'titleRef',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/titleRef/titleRef-delete-dialog.html',
                        controller: 'TitleRefDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TitleRef', function(TitleRef) {
                                return TitleRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('titleRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
