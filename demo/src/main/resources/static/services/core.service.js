angular.module('iw3').factory('coreService',function($http,URL_BASE){
	return {
		
		authInfo: function() {
			 return $http.get(URL_BASE+"authinfo");
		},
		version: function() {
			 return $http.get(URL_BASE+"version");
		},
		login: function(user) {
			var req = {
				method: 'POST',
				url: URL_BASE+'dologin',
				headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
				data: 'username='+user.name+'&password='+user.password
			};
			return $http(req);
		},
		
		loginJwt: function(user) {
			var req = {
				method: 'POST',
				url: URL_BASE+'loginJwt',
				headers : { 'Content-Type': 'application/json' },
				data: user
				//data: 'name='+user.name+'&password='+user.password
			};
			return $http(req);
		}
	}
});