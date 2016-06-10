'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('extendedUser', {
                parent: 'entity',
                url: '/extendedUsers',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ExtendedUsers'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/extendedUser/extendedUsers.html',
                        controller: 'ExtendedUserController'
                    }
                },
                resolve: {
                }
            })
            .state('extendedUser.detail', {
                parent: 'entity',
                url: '/extendedUser/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'ExtendedUser'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/extendedUser/extendedUser-detail.html',
                        controller: 'ExtendedUserDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'ExtendedUser', function($stateParams, ExtendedUser) {
                        return ExtendedUser.get({id : $stateParams.id});
                    }]
                }
            })
            .state('extendedUser.new', {
                parent: 'extendedUser',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/extendedUser/extendedUser-dialog.html',
                        controller: 'ExtendedUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    birthDate: null,
                                    sex: null,
                                    practiceLocation: null,
                                    adeliNumber: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('extendedUser', null, { reload: true });
                    }, function() {
                        $state.go('extendedUser');
                    })
                }]
            })
            .state('extendedUser.edit', {
                parent: 'extendedUser',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/extendedUser/extendedUser-dialog.html',
                        controller: 'ExtendedUserDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ExtendedUser', function(ExtendedUser) {
                                return ExtendedUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('extendedUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('extendedUser.delete', {
                parent: 'extendedUser',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/extendedUser/extendedUser-delete-dialog.html',
                        controller: 'ExtendedUserDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ExtendedUser', function(ExtendedUser) {
                                return ExtendedUser.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('extendedUser', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
