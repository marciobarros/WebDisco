"use strict";

(function(){
  let mdlTfFix = () => {
    return {
      restrict: "C",
      require: "ngModel",
      link: ($scope, $element, $attrs, ngModelCtrl) => {
        $scope.$watch(() => {
          return ngModelCtrl.$modelValue;
        }, (newVal, oldVal) =>{

          if(typeof newVal !== "undefined" && newVal !== "" && newVal !== oldVal){
            $element.parent().addClass("is-dirty");
          }
          else{
            $element.parent().removeClass("is-dirty");
          }
        });
      }
    };
  };

  mdlTfFix.$inject = [];
  App.directive("mdlTextfieldInput", mdlTfFix);

})();