angular.module('iw3')
.controller('updateProductos', function($scope, $rootScope, productosService,Notification,$uibModalInstance){	
	
	$scope.prodFormTitulo = $scope.SuccessBtnText ="Modificar";
	
	$scope.prod = JSON.parse(JSON.stringify($rootScope.selectedProd));
	//$scope.prod = $rootScope.selectedProd;
	
	$scope.success=function() {		
		productosService.update($scope.prod).then(
			function(resp){
				if(resp.status===200){
					Notification.success("Se actualizó con exito");
				}else{
					Notification.error("No se pudo actualizar");					
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