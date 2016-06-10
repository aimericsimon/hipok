'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('medicalTypeRef', {
                parent: 'entity',
                url: '/medicalTypeRefs',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'MedicalTypeRefs'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/medicalTypeRef/medicalTypeRefs.html',
                        controller: 'MedicalTypeRefController'
                    }
                },
                resolve: {
                }
            })
            .state('medicalTypeRef.detail', {
                parent: 'entity',
                url: '/medicalTypeRef/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'MedicalTypeRef'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/medicalTypeRef/medicalTypeRef-detail.html',
                        controller: 'MedicalTypeRefDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'MedicalTypeRef', function($stateParams, MedicalTypeRef) {
                        return MedicalTypeRef.get({id : $stateParams.id});
                    }]
                }
            })
            .state('medicalTypeRef.new', {
                parent: 'medicalTypeRef',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/medicalTypeRef/medicalTypeRef-dialog.html',
                        controller: 'MedicalTypeRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    subtype: null,
                                    label: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('medicalTypeRef', null, { reload: true });
                    }, function() {
                        $state.go('medicalTypeRef');
                    })
                }]
            })
            .state('medicalTypeRef.edit', {
                parent: 'medicalTypeRef',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/medicalTypeRef/medicalTypeRef-dialog.html',
                        controller: 'MedicalTypeRefDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['MedicalTypeRef', function(MedicalTypeRef) {
                                return MedicalTypeRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('medicalTypeRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('medicalTypeRef.delete', {
                parent: 'medicalTypeRef',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/medicalTypeRef/medicalTypeRef-delete-dialog.html',
                        controller: 'MedicalTypeRefDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['MedicalTypeRef', function(MedicalTypeRef) {
                                return MedicalTypeRef.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('medicalTypeRef', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
