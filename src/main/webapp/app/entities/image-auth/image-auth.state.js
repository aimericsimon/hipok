(function() {
    'use strict';

    angular
        .module('hipokApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('image-auth', {
            parent: 'entity',
            url: '/image-auth',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ImageAuths'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/image-auth/image-auths.html',
                    controller: 'ImageAuthController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('image-auth-detail', {
            parent: 'entity',
            url: '/image-auth/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ImageAuth'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/image-auth/image-auth-detail.html',
                    controller: 'ImageAuthDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ImageAuth', function($stateParams, ImageAuth) {
                    return ImageAuth.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('image-auth.new', {
            parent: 'image-auth',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/image-auth/image-auth-dialog.html',
                    controller: 'ImageAuthDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imageAuth_url: null,
                                imageAuth_thumbnail_url: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('image-auth', null, { reload: true });
                }, function() {
                    $state.go('image-auth');
                });
            }]
        })
        .state('image-auth.edit', {
            parent: 'image-auth',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/image-auth/image-auth-dialog.html',
                    controller: 'ImageAuthDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ImageAuth', function(ImageAuth) {
                            return ImageAuth.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('image-auth', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('image-auth.delete', {
            parent: 'image-auth',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/image-auth/image-auth-delete-dialog.html',
                    controller: 'ImageAuthDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ImageAuth', function(ImageAuth) {
                            return ImageAuth.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('image-auth', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
