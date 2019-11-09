angular.module('iw3')

.controller('productos', function($scope, $rootScope, productosService,SweetAlert){
	$scope.titulo="Productos";
	$scope.busqueda={text:""};
	
	$scope.data=[];
	$scope.refresh=function() {
		productosService.list().then(
			function(resp){
				$scope.data=resp.data;
			},
			function(err){}
		);
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
					
				}
			});
	};
	
}); //End main controller