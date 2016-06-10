'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('specialtyRef', {
                parent: 'entity',
                url: '/specialtyRefs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SpecialtyRefs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/specialtyRef/specialtyRefs.html',
                        controller: 'SpecialtyRefController'
                    }
                },
                resolve: {
                }
            })
            .state('specialtyRef.detail', {
                parent: 'entity',
                url: '/specialtyRef/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'SpecialtyRef'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/specialtyRef/specialtyRef-detail.html',
                        controller: 'SpecialtyRefDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'SpecialtyRef', function($stateParams, SpecialtyRef) {
                        return SpecialtyRef.get({id : $stateParams.id});
                    }]
                }
            })
            .state('specialtyRef.new', {
                parent: 'specialtyRef',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/specialtyRef/specialtyRef-dialog.html',
                        controller: 'SpecialtyRefDialogController',
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
                        $state.go('specialtyRef', null, { reload: true });
                    }, function() {
                        $state.go('specialtyRef');
                    })
                }]
            })
            .state('specialtyRef.edit', {
                parent: 'specialtyRef',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/specialtyRef/specialtyRef-dialog.html',
                        controller: 'SpecialtyRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['SpecialtyRef', function(SpecialtyRef) {
                                return SpecialtyRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('specialtyRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('specialtyRef.delete', {
                parent: 'specialtyRef',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/specialtyRef/specialtyRef-delete-dialog.html',
                        controller: 'SpecialtyRefDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['SpecialtyRef', function(SpecialtyRef) {
                                return SpecialtyRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('specialtyRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
