angular.module('iw3')
.controller('LoginFormController', 
		function LoginFormController(
				$rootScope, $scope, $localStorage,
				$uibModalInstance, 
				coreService,$log) {
			$scope.title="Ingreso";
			
			$scope.user={name:"",password:""};
			
			
			$scope.login = function () {
				coreService.login($scope.user).then(
					function(resp){ 
						if(resp.status===200) {
							$localStorage.userdata=resp.data;
							$localStorage.logged=true;
							$rootScope.autenticado=true;	
							$rootScope.loginOpen = false;
							$uibModalInstance.dismiss(true);
						}else{
							$rootScope.autenticado=false;	
							delete $localStorage.userdata;
							$localStorage.logged=false;
						}
					},
					function(respErr){
						$log.log(respErr);
					}
				);
			  };  
		}); //End LoginFormController




