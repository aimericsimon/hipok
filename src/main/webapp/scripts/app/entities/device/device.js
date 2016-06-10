'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('device', {
                parent: 'entity',
                url: '/devices',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Devices'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/device/devices.html',
                        controller: 'DeviceController'
                    }
                },
                resolve: {
                }
            })
            .state('device.detail', {
                parent: 'entity',
                url: '/device/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Device'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/device/device-detail.html',
                        controller: 'DeviceDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Device', function($stateParams, Device) {
                        return Device.get({id : $stateParams.id});
                    }]
                }
            })
            .state('device.new', {
                parent: 'device',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/device/device-dialog.html',
                        controller: 'DeviceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    token: null,
                                    type: null,
                                    enabled: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('device', null, { reload: true });
                    }, function() {
                        $state.go('device');
                    })
                }]
            })
            .state('device.edit', {
                parent: 'device',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/device/device-dialog.html',
                        controller: 'DeviceDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Device', function(Device) {
                                return Device.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('device', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('device.delete', {
                parent: 'device',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/device/device-delete-dialog.html',
                        controller: 'DeviceDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Device', function(Device) {
                                return Device.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('device', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
