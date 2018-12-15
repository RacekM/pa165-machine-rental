'use strict';

/* Defines application and its dependencies */

var pa165rentalApp = angular.module('pa165rentalApp', ['ngRoute', 'rentalControllers']);
var rentalControllers = angular.module('rentalControllers', []);

/* Configures URL fragment routing, e.g. #/machine/1  */
pa165rentalApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/renting', {templateUrl: 'partials/main_page.html', controller: 'MainPageCtrl'}).
        when('/admin/machines', {templateUrl: 'partials/admin_machines.html', controller: 'AdminMachinesCtrl'}).
        when('/admin/newmachine', {templateUrl: 'partials/admin_new_machine.html', controller: 'AdminNewMachineCtrl'}).
        when('/admin/users', {templateUrl: 'partials/admin_users.html', controller: 'AdminUsersCtrl'}).
        when('/admin/newuser', {templateUrl: 'partials/admin_new_user.html', controller: 'AdminNewUserCtrl'}).
        otherwise({redirectTo: '/renting'});
    }]);

/*
 * alert closing functions defined in root scope to be available in every template
 */
pa165rentalApp.run(function ($rootScope) {
    $rootScope.hideSuccessAlert = function () {
        $rootScope.successAlert = undefined;
    };
    $rootScope.hideWarningAlert = function () {
        $rootScope.warningAlert = undefined;
    };
    $rootScope.hideErrorAlert = function () {
        $rootScope.errorAlert = undefined;
    };
});


/* Controllers */

rentalControllers.controller('MainPageCtrl', function ($scope, $http) {
    console.log("main page");
});


/*
 * Administration interface
 */

function loadAdminMachines($http, $scope) {
    $http.get('/pa165/api/v1/machines').then(function (response) {
        $scope.machines = response.data.content;
        console.log('AJAX loaded all machines ');
    });
}

function loadAdminUsers($http, $scope) {
    $http.get('/pa165/api/v1/users').then(function (response) {
        $scope.users = response.data.content;
        console.log('AJAX loaded all users ');
    });
}

rentalControllers.controller('AdminMachinesCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        //initial load of all machines
        loadAdminMachines($http, $scope);
        // function called when Delete button is clicked
        $scope.deleteMachine = function (machine) {
            console.log("deleting machine with id=" + machine.id + ' (' + machine.name + ')');
            var deleteLink = machine.links.find(function (link) {
                return link.rel === "delete"
            });
            $http.delete(deleteLink.href).then(
                function success(response) {
                    console.log('deleted machine ' + machine.id + ' on server');
                    //display confirmation alert
                    $rootScope.successAlert = 'Deleted machine "' + machine.name + '"';
                    //load new list of all machines
                    loadAdminMachines($http, $scope);
                },
                function error(response) {
                    console.log('server returned error');
                    $rootScope.errorAlert = 'Cannot delete machine "' + machine.name;
                }
            );
        };
    });

rentalControllers.controller('AdminUsersCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        //initial load of all users
        loadAdminUsers($http, $scope);
        // function called when Delete button is clicked
        $scope.deleteUser = function (user) {
            console.log("deleting user with id=" + user.id + ' (' + user.name + ')');
            var deleteLink = user.links.find(function (link) {
                return link.rel === "delete"
            });
            $http.delete(deleteLink.href).then(
                function success(response) {
                    console.log('deleted user ' + user.id + ' on server');
                    //display confirmation alert
                    $rootScope.successAlert = 'Deleted user "' + user.name + '"';
                    //load new list of all users
                    loadAdminUsers($http, $scope);
                },
                function error(response) {
                    console.log('server returned error');
                    $rootScope.errorAlert = 'Cannot delete user "' + user.name;
                }
            );
        };
    });


rentalControllers.controller('AdminNewMachineCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        //set object bound to form fields
        $scope.machine = {
            'name': ''
        };
        // function called when submit button is clicked, creates product on server
        $scope.create = function (machine) {
            $http({
                method: 'POST',
                url: '/pa165/api/v1/machines/create',
                data: machine
            }).then(function success(response) {
                console.log('created machine');
                var createdMachine = response.data;
                //display confirmation alert
                $rootScope.successAlert = 'A new machine "' + createdMachine.name + '" was created';
                //change view to list of machines
                $location.path("/admin/machines");
            }, function error(response) {
                //display error
                $scope.errorAlert = 'Cannot create machine !';
            });
        };
    });

rentalControllers.controller('AdminNewUserCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        //prepare data for selection lists
        $scope.userTypes = ['ADMIN', 'LEGAL_PERSON', 'INDIVIDUAL'];
        //set object bound to form fields
        $scope.user = {
            'name': '',
            'username': '',
            'passwordHash': '',
            'userType': $scope.userTypes[0]
        };
        // function called when submit button is clicked, creates product on server
        $scope.create = function (user) {
            $http({
                method: 'POST',
                url: '/pa165/api/v1/users/create',
                data: user
            }).then(function success(response) {
                console.log('created user');
                var createdUser = response.data;
                //display confirmation alert
                $rootScope.successAlert = 'A new user "' + createdUser.name + '" was created';
                //change view to list of users
                $location.path("/admin/users");
            }, function error(response) {
                //display error
                $scope.errorAlert = 'Cannot create user !';
            });
        };
    });



/* Utilities */

// defines new directive (HTML attribute "convert-to-int") for conversion between string and int
// of the value of a selection list in a form
// without this, the value of the selected option is always a string, not an integer
rentalControllers.directive('convertToInt', function () {
    return {
        require: 'ngModel',
        link: function (scope, element, attrs, ngModel) {
            ngModel.$parsers.push(function (val) {
                return parseInt(val, 10);
            });
            ngModel.$formatters.push(function (val) {
                return '' + val;
            });
        }
    };
});
