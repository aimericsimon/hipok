'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('address', {
                parent: 'entity',
                url: '/addresss',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Addresss'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/address/addresss.html',
                        controller: 'AddressController'
                    }
                },
                resolve: {
                }
            })
            .state('address.detail', {
                parent: 'entity',
                url: '/address/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Address'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/address/address-detail.html',
                        controller: 'AddressDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Address', function($stateParams, Address) {
                        return Address.get({id : $stateParams.id});
                    }]
                }
            })
            .state('address.new', {
                parent: 'address',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/address/address-dialog.html',
                        controller: 'AddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    label: null,
                                    postalCode: null,
                                    city: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('address');
                    })
                }]
            })
            .state('address.edit', {
                parent: 'address',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/address/address-dialog.html',
                        controller: 'AddressDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Address', function(Address) {
                                return Address.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('address.delete', {
                parent: 'address',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/address/address-delete-dialog.html',
                        controller: 'AddressDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Address', function(Address) {
                                return Address.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('address', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
