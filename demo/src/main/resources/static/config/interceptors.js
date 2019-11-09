angular.module('iw3')

.factory('APIInterceptor', function($q, $rootScope, $localStorage, $location) {

	return {

		request : function(config) {
			if ($localStorage.logged && $localStorage.userdata) {
				userdata = $localStorage.userdata;
				config.headers['X-AUTH-TOKEN'] = userdata.authtoken;
				$rootScope.autenticado = true;
			} else {
				//console.log($location)
				$rootScope.autenticado = false;

				//$rootScope.openLoginForm();
			}
			return config || $q.when(config);
		},

		responseError : function(response) {
			if (response.status == 401) {
				$rootScope.autenticado = false;

				$rootScope.openLoginForm();
			}
			return $q.reject(response);
		}
	};
})