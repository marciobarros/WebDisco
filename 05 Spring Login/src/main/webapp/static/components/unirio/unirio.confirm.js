angular.module('unirio.dsw', []).directive('ngConfirmClick', [function() {
    return {
        link: function (scope, element, attr) {
            var msg = attr.ngConfirmClick || "Confirmação";
            var clickAction = attr.confirmedClick;
            
            element.bind('click',function (event) {
                if (window.confirm(msg)) {
                    scope.$eval(clickAction);
                }
            });
        }
    };
}]);

