'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('notification', {
                parent: 'entity',
                url: '/notifications',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Notifications'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/notification/notifications.html',
                        controller: 'NotificationController'
                    }
                },
                resolve: {
                }
            })
            .state('notification.detail', {
                parent: 'entity',
                url: '/notification/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Notification'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/notification/notification-detail.html',
                        controller: 'NotificationDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Notification', function($stateParams, Notification) {
                        return Notification.get({id : $stateParams.id});
                    }]
                }
            })
            .state('notification.new', {
                parent: 'notification',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/notification/notification-dialog.html',
                        controller: 'NotificationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    creationDate: null,
                                    read: null,
                                    type: null,
                                    itemId: null,
                                    data: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('notification', null, { reload: true });
                    }, function() {
                        $state.go('notification');
                    })
                }]
            })
            .state('notification.edit', {
                parent: 'notification',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/notification/notification-dialog.html',
                        controller: 'NotificationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Notification', function(Notification) {
                                return Notification.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('notification', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('notification.delete', {
                parent: 'notification',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/notification/notification-delete-dialog.html',
                        controller: 'NotificationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Notification', function(Notification) {
                                return Notification.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('notification', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
