angular.module('iw3')

.controller(
		'main',
		function($scope, $rootScope, $log, graphService, wsService,
				Notification) {
			$scope.titulo = "Main";

			$scope.graphOptions = {
				demo : {
					options : {},
					data : {}
				}
			};

			$scope.procesaDatosGraph = function(datos) {
				var labels = [];
				var data = [];
				datos.forEach(function(o, i) {
					labels.push(o.label);
					data.push(o.value);
				});
				$scope.graphOptions.demo.data = {
					data : data,
					labels : labels
				}
			};
			$scope.iniciaWS = function() {
				$log.log("iniciandoWS");
				wsService.initStompClient('/iw3/data', function(payload,
						headers, res) {
					$log.log(payload);
					if (payload.type == 'GRAPH_DATA') {
						$scope.procesaDatosGraph(payload.payload);
					}
					if (payload.type == 'NOTIFICA') {
						$scope.notificar(payload.payload.label,payload.payload.value);
					}
					$scope.$apply();
				});
			}

			$scope.requestRefresh = function() {
				graphService.requestPushData();
			};
			$rootScope.authInfo($scope.iniciaWS());

			$scope.$on("$destroy", function() {
				wsService.stopStompClient();
			});
			
			
			$scope.notificar = function(msg, tipo) {
				obj = {
					message : msg,
					templateUrl : 'notifica.html',
					delay : 8000
				};
				if (tipo == "0") {
					Notification.primary(obj);
				}
				if (tipo == "1") {
					Notification.success(obj);
				}
				if (tipo == "2") {
					Notification.error(obj);
				}
			};

		}); // End main controller
