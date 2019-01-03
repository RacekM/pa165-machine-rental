'use strict';

/* Defines application and its dependencies */

var pa165rentalApp = angular.module('pa165rentalApp', ['ngRoute', 'rentalControllers']);
var rentalControllers = angular.module('rentalControllers', []);

/* Configures URL fragment routing, e.g. #/machine/1  */
pa165rentalApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.when('/', {
            templateUrl: 'partials/main_page.html',
            controller: 'MainPageCtrl'
        }).when('/machines', {
            templateUrl: 'partials/machines.html',
            controller: 'MachinesCtrl'
        }).
        when('/admin/machines', {templateUrl: 'partials/admin_machines.html', controller: 'AdminMachinesCtrl'}).
        when('/admin/newmachine', {templateUrl: 'partials/admin_new_machine.html', controller: 'AdminNewMachineCtrl'}).
        when('/admin/users', {templateUrl: 'partials/admin_users.html', controller: 'AdminUsersCtrl'}).
        when('/admin/newuser', {templateUrl: 'partials/admin_new_user.html', controller: 'AdminNewUserCtrl'}).
        when('/admin/revisions', {templateUrl: 'partials/admin_revisions.html', controller: 'AdminRevisionCtrl'}).
        when('/admin/newrevision', {templateUrl: 'partials/admin_new_revision.html', controller: 'AdminNewRevisionCtrl'}).
        when('/admin/rentals', {templateUrl: 'partials/admin_rentals.html', controller: 'AdminRentalCtrl'}).
        when('/admin/newrental', {templateUrl: 'partials/admin_new_rental.html', controller: 'AdminNewRentalCtrl'}).
        otherwise({redirectTo: '/'});
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

function hideAlerts($rootScope){
    $rootScope.hideErrorAlert();
    $rootScope.hideSuccessAlert();
    $rootScope.hideWarningAlert();
}

/* Controllers */

rentalControllers.controller('MainPageCtrl', function ($scope, $rootScope, $http) {
    hideAlerts($rootScope);
    console.log("main page");
});

rentalControllers.controller('MachinesCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location) {
        hideAlerts($rootScope);
        //initial load of all machines
        loadMachines($http, $scope);
    });

rentalControllers.controller('MyRentalsCtrl', function ($scope, $rootScope, $http) {
    hideAlerts($rootScope);
    console.log("user's rentals page");
});

/*
 * Administration interface
 */

function loadMachines($http, $scope) {
    $http.get('/pa165/rest/machines').then(function (response) {
        $scope.machines = response.data.content;
        console.log('AJAX loaded all machines ');
    });
}

function loadUsers($http, $scope) {
    $http.get('/pa165/rest/users').then(function (response) {
        $scope.users = response.data.content;
        console.log('AJAX loaded all users ');
    });
}

function countAdmins(users) {
    var count = 0;
    for (var i = 0; i < users.length; i++) {
        console.log("userType=", users[i].userType);
        if (users[i].userType === "ADMIN") {
            count++;
        }
    }
    return count;
}

rentalControllers.controller('AdminMachinesCtrl',
    function ($scope, $rootScope, $routeParams, $http, $location) {
        hideAlerts($rootScope);
        //initial load of all machines
        loadMachines($http, $scope);
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
                    loadMachines($http, $scope);
                },
                function error(response) {
                    console.log('server returned error');
                    $rootScope.errorAlert = 'Cannot delete machine "' + machine.name;
                }
            );
        };
        $scope.createRevision = function (machine) {
            console.log("addRevision to machine " + machine.id + ' (' + machine.name + ')');
            $location.path("/admin/newrevision").search({machine: machine.id});
        };
        $scope.showRevisions = function (machine) {
            console.log("show revisions of machine " + machine.id + ' (' + machine.name + ')');
            $location.path("/admin/revisions").search({machine: machine.id});
        };
        $scope.showRentals = function (machine) {
            console.log("show rentals of machine " + machine.id + ' (' + machine.name + ')');
            $location.path("/admin/rentals").search({machine: machine.id});
        };
    });

rentalControllers.controller('AdminUsersCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        hideAlerts($rootScope);
        //initial load of all users
        loadUsers($http, $scope);
        // function called when Delete button is clicked
        $scope.deleteUser = function (user) {
            $http.get('/pa165/rest/users').then(function success(response) {
                    var users = response.data.content;
                    if (user.userType === "ADMIN" && countAdmins(users) <= 1) {
                        console.log('cannot delete last admin');
                        $rootScope.errorAlert = 'Cannot delete user "' + user.name + '" because he is last admin.';
                        return;
                    }

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
                            loadUsers($http, $scope);
                        },
                        function error(response) {
                            console.log('server returned error');
                            $rootScope.errorAlert = 'Cannot delete user "' + user.name;
                        }
                    );
                },
                function error(response) {
                    console.log('AJAX cannot get all users ');
                });
        };
    });


rentalControllers.controller('AdminNewMachineCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        hideAlerts($rootScope);
        //set object bound to form fields
        $scope.machine = {
            'name': ''
        };
        // function called when submit button is clicked, creates product on server
        $scope.create = function (machine) {
            $http({
                method: 'POST',
                url: '/pa165/rest/machines/create',
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
        hideAlerts($rootScope);
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
                url: '/pa165/rest/users/create',
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




function loadAdminRevisions($http, $scope, machine) {
    if (machine) {
        $http.get('/pa165/rest/revisions?machine=' + machine).then(function (response) {
            $scope.revisions = response.data.content;
            console.log('AJAX loaded all revisions for given machine');
        });
    } else {
        $http.get('/pa165/rest/revisions').then(function (response) {
            $scope.revisions = response.data.content;
            console.log('AJAX loaded all revisions ');
        });
    }
}

rentalControllers.controller('AdminRevisionCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        hideAlerts($rootScope);
        $scope.machine = $routeParams.machine;
        if ($routeParams.machine){
            $http.get('/pa165/rest/machines/' + $routeParams.machine).then(function (response) {
                $scope.machineName = response.data.name;
            });
        }
        //initial load of all machines
        loadAdminRevisions($http, $scope, $routeParams.machine);
        // function called when Delete button is clicked
        $scope.deleteRevision = function (revision) {
            console.log("deleting revision with id=" + revision.id + ' (machine: ' + revision.machine.name + ')');
            var deleteLink = revision.links.find(function (link) {
                return link.rel === "delete"
            });
            $http.delete(deleteLink.href).then(
                function success(response) {
                    console.log('deleted revision ' + revision.id + ' on server');
                    //display confirmation alert
                    $rootScope.successAlert = 'Deleted revision of"' + revision.machine.name + '"';
                    //load new list of all machines
                    loadAdminRevisions($http, $scope, $routeParams.machine);
                },
                function error(response) {
                    console.log('server returned error');
                    $rootScope.errorAlert = 'Cannot delete revision "' + revision.id;
                }
            );
        };
    });

rentalControllers.controller('AdminNewRevisionCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        hideAlerts($rootScope);
        //set object bound to form fields
        $scope.showMachines = !$routeParams.machine;
        $scope.revision = {
            'machine': $routeParams.machine,
            'result': true,
            'date': new Date()
        };
        if (!$routeParams.machine) {
            $http.get('/pa165/rest/machines').then(function (response) {
                $scope.machines = response.data.content;
            });
        } else {
            $http.get('/pa165/rest/machines/' + $routeParams.machine).then(function (response) {
                $scope.machine = response.data.name;
            });
            $http.get('/pa165/rest/revisions/last?machine=' + $routeParams.machine).then(function (response) {
                $scope.lastRevision = response.data.result;
            }).catch(function error(response) {
                $scope.lastRevision = 'no revision'
            });
        }
        // function called when submit button is clicked, creates product on server
        $scope.create = function (revision) {
            if (!revision.date){
                $rootScope.errorAlert = 'Date must be set !';
                return;
            }
            console.log("creating post request" + revision.id + " " + revision.machine + " " + revision.date + " " + revision.result);
            if (!revision.machine){
                $rootScope.errorAlert = 'empty machine !';
            }else if (revision.date > Date.now()){
                $rootScope.errorAlert = 'Date of revision can not be in future !';
            } else {
                $http({
                    method: 'POST',
                    url: '/pa165/rest/revisions/create',
                    data: revision
                }).then(function success(response) {
                    console.log('created revision');
                    var createdRevision = response.data;
                    //display confirmation alert
                    $rootScope.successAlert = 'A new revision "' + createdRevision.id + '" was created';
                    if (!$routeParams.machine) {
                        $location.path("/admin/revisions");
                    } else {
                        $location.path("/admin/revisions").search({machine: $routeParams.machine});
                    }
                }).catch(function error(response) {
                    //display error
                    $rootScope.errorAlert = 'Cannot create revision !';
                });
            }
        };
    });

function loadAdminRentals($http, $scope, machine) {
    if (machine){
        $http.get('/pa165/rest/rentals?machine=' + machine).then(function (response) {
            $scope.rentals = response.data.content;
            console.log('AJAX loaded all revisions for given machine');
        });
    }else {
        $http.get('/pa165/rest/rentals').then(function (response) {
            $scope.rentals = response.data.content;
            console.log('AJAX loaded all rentals ');
        });
    }
}

rentalControllers.controller('AdminRentalCtrl',
    function ($scope, $rootScope, $routeParams, $http) {
        hideAlerts($rootScope);
        $scope.machine = $routeParams.machine;
        if ($routeParams.machine){
            $http.get('/pa165/rest/machines/' + $routeParams.machine).then(function (response) {
                $scope.machineName = response.data.name;
            });
        }
        loadAdminRentals($http, $scope, $routeParams.machine);
        // function called when Delete button is clicked
        $scope.deleteRental = function (rental) {
            console.log("deleting rental with id=" + rental.id + '(machine: ' + rental.machine.name + ', user: ' + rental.user.name + ')');
            var deleteLink = rental.links.find(function (link) {
                return link.rel === "delete"
            });
            $http.delete(deleteLink.href).then(
                function success(response) {
                    console.log('deleted rental ' + rental.id + ' on server');
                    //display confirmation alert
                    $rootScope.successAlert = 'Deleted rental of"' + rental.machine.name + '"' + ' rented by ' + '"' + rental.user.name + '"';
                    //load new list of all machines
                    loadAdminRentals($http, $scope);
                },
                function error(response) {
                    console.log('server returned error');
                    $rootScope.errorAlert = 'Cannot delete rental "' + rental.id;
                }
            );
        };

    });

rentalControllers.controller('AdminNewRentalCtrl',
    function ($scope, $routeParams, $http, $location, $rootScope) {
        hideAlerts($rootScope);
        //set object bound to form fields
        $scope.rental = {
            'machine': $routeParams.machine,
            'user': $routeParams.user,
            'dateOfRental': new Date(),
            'returnDate': new Date(),
            'note': ''
        };

        if (!$routeParams.machine) {
            $http.get('/pa165/rest/machines').then(function (response) {
                $scope.machines = response.data.content;
            });
        } else {
            $http.get('/pa165/rest/machines/' + $routeParams.machine).then(function (response) {
                $scope.machine = response.data.name;
            });
        }

        if (!$routeParams.user) {
            $http.get('/pa165/rest/users').then(function (response) {
                $scope.users = response.data.content;
            });
        } else {
            $http.get('/pa165/rest/users/' + $routeParams.user).then(function (response) {
                $scope.user = response.data.name;
            });
        }

        // function called when submit button is clicked, creates product on server
        $scope.create = function (rental) {
            // so only date part + hours and minutes is used
            if (!rental.dateOfRental || !rental.returnDate){
                $rootScope.errorAlert = 'Date must be set !';
                return;
            }
            rental.dateOfRental.setHours(rental.dateOfRental.getHours(), rental.dateOfRental.getMinutes(), 0, 0);
            rental.returnDate.setHours(rental.returnDate.getHours(), rental.returnDate.getMinutes(), 0, 0);

            console.log("creating post request" + rental.id + " " + rental.machine + " " + rental.user + " " + rental.dateOfRental +
            " " + rental.returnDate + " " + rental.note);

            // take only date part and hours and minutes from actual date, Date.now return number of millisceonds since epocha
            var nowDatePart = new Date(Date.now());
            nowDatePart.setHours(nowDatePart.getHours(), nowDatePart.getMinutes(), 0, 0);

            if (!rental.machine){
                $rootScope.errorAlert = 'empty machine !';
            } else if (!rental.user) {
                $rootScope.errorAlert = 'empty user !';
            } else if (rental.dateOfRental < nowDatePart) {
                $rootScope.errorAlert = 'Date of renting can not be in past !';
            } else if (rental.returnDate < nowDatePart) {
                $rootScope.errorAlert = 'Date of return can not be in past !';
            } else if (rental.dateOfRental >= rental.returnDate) {
                $rootScope.errorAlert = 'Rental needs to be for at least 2 days !'
            } else {
                $http({
                    method: 'POST',
                    url: '/pa165/rest/rentals/create',
                    data: rental
                }).then(function success(response) {
                    console.log('created rental');
                    var createdRental = response.data;
                    //display confirmation alert
                    $rootScope.successAlert = 'A new rental "' + createdRental.id + '" was created';
                    $location.path("/admin/rentals");
                }).catch(function error(response) {
                    //display error
                    $rootScope.errorAlert = 'Cannot create rental, machine is already rented for this time';
                });
            }
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
