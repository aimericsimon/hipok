'use strict';

angular.module('hipokApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reporting', {
                parent: 'entity',
                url: '/reportings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Reportings'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reporting/reportings.html',
                        controller: 'ReportingController'
                    }
                },
                resolve: {
                }
            })
            .state('reporting.detail', {
                parent: 'entity',
                url: '/reporting/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Reporting'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reporting/reporting-detail.html',
                        controller: 'ReportingDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Reporting', function($stateParams, Reporting) {
                        return Reporting.get({id : $stateParams.id});
                    }]
                }
            })
            .state('reporting.new', {
                parent: 'reporting',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/reporting/reporting-dialog.html',
                        controller: 'ReportingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    reportingDate: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('reporting', null, { reload: true });
                    }, function() {
                        $state.go('reporting');
                    })
                }]
            })
            .state('reporting.edit', {
                parent: 'reporting',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/reporting/reporting-dialog.html',
                        controller: 'ReportingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Reporting', function(Reporting) {
                                return Reporting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reporting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('reporting.delete', {
                parent: 'reporting',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/reporting/reporting-delete-dialog.html',
                        controller: 'ReportingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Reporting', function(Reporting) {
                                return Reporting.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reporting', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
