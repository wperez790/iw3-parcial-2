angular.module('iw3')

.config(function($locationProvider, $routeProvider, $localStorageProvider, $httpProvider) {
	$locationProvider.hashPrefix('!');
	
	$localStorageProvider.setKeyPrefix('iw3');
	
	$httpProvider.defaults.withCredentials = true;
	
	$httpProvider.interceptors.push('APIInterceptor');
	
	
	$routeProvider
	
	.when('/',{
		templateUrl: 'views/main.html',
		controller:'main'
	})
	.when('/productos',{
		templateUrl: 'views/productos.html',
		controller:'productos'
	})
	
	.otherwise({
		redirectTo: '/'
	})
});

