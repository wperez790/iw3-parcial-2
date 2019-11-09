angular.module('iw3')
.factory('graphService',
		['$http','$q','URL_API_BASE',
		function($http, $q, URL_API_BASE) {
			return {
				requestPushData: function() {
					$http.get(URL_API_BASE+"graph/push");
				}
		} 
	}
]);