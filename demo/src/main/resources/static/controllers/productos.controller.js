angular.module('iw3')

.controller('productos', function($scope, $rootScope, productosService,SweetAlert,Notification){
	$scope.titulo="Productos";
	$scope.busqueda={text:""};
	
	$scope.data=[];
	$scope.refresh=function() {
		productosService.list().then(
			function(resp){
				$scope.data=resp.data;
			},
			function(err){
				Notification.error("No se pudo cargar la lista de productos");
			}
		);
	}
	
	$scope.openInsertForm=function(){
		$scope.prod={};
		$rootScope.openProductForm(true);
	}
	
	$scope.openUpdateForm=function(prod) {
		$rootScope.selectedProd = prod;
		$rootScope.openProductForm(false);
	}
		
	$scope.init=function() {
		$scope.refresh();
	}
		
	$rootScope.authInfo($scope.init,false,false);
	
	$scope.eliminar=function(prod) {
		SweetAlert.swal({
			  title: "Eliminar producto",
			  text: "Está seguro que desea eliminar el producto <strong>"+prod.descripcion+"</strong>?",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Si, eliminar producto!",
			  cancelButtonText: "No",
			  closeOnConfirm: true,
			  html: true
			}, function(confirm){
				if(confirm) {
					productosService.delete(prod.id).then(
						function(resp){
							if(resp.status===200){
								Notification.success("Se elimino correctamente");
								$scope.refresh();
							}else{
								Notification.error("No se pudo eliminar");
							}
						}
					);
				}
			});
	};
	
}); //End main controller