angular.module('iw3')
.controller('insertProductos', function($scope, $rootScope, productosService,Notification,$uibModalInstance){	
	
	$scope.prodFormTitulo = $scope.SuccessBtnText ="Insertar";
	
	
	$scope.success=function() {		
		productosService.insert($scope.prod).then(
			function(resp){
				if(resp.status===201){
					Notification.success("Se inserto con exito");
				}else{
					Notification.error("No se pudo insertar");
				}
			},
			function(err){}
		);
		$scope.closeModal();
	}	
	
	$scope.closeModal = function(){
		if($rootScope.InsertProdOpen){
			$uibModalInstance.dismiss(true);
			$rootScope.InsertProdOpen = false;
		}
	}
});