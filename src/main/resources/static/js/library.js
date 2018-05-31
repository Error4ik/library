base_url = 'http://localhost:8080/library';

var library = angular.module("library", ['ngRoute', 'angularUtils.directives.dirPagination']);

library.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: "client/index-content.html",
        controller: "IndexContentController"
    }).when('/addBooks', {
        templateUrl: 'client/add-book.html',
        controller: "AddBookController"
    }).when('/genre/:id', {
        templateUrl: 'client/index-content.html',
        controller: "BooksByGenreController"
    }).when('/author/:id', {
        templateUrl: "client/index-content.html",
        controller: "AuthorController"
    }).when('/book/:id', {
        templateUrl: "client/book.html",
        controller: "BookController"
    }).otherwise({
        templateUrl: "client/empty.html"
    });
});

library.controller("IndexContentController", function ($scope, $http) {
    doGet($http, base_url + "/book/books", function (data) {
        $scope.books = data;
    });

    doGet($http, base_url + "/book/genres", function (data) {
        $scope.genres = data;
    });
});

library.controller("AddBookController", function ($scope, $http) {
    doGet($http, base_url + "/book/genres", function (data) {
        $scope.genres = data;
    })
});

library.controller("BooksByGenreController", function ($scope, $http, $routeParams) {
    doGet($http, base_url + "/book/genre/" + $routeParams.id, function (data) {
        $scope.books = data;
    });
});

library.controller("AuthorController", function ($scope, $http, $routeParams) {
    doGet($http, base_url + "/book/author/" + $routeParams.id, function (data) {
        $scope.books = data;
    })
});

library.controller("BookController", function ($scope, $http, $routeParams) {
    doGet($http, base_url + "/book/book/" + $routeParams.id, function (data) {
        $scope.book = data;
    })
});

function doGet($http, url, action, error) {
    doHttp("GET", $http, url, null, action, error);
}

function doPost($http, url, data, action, error) {
    doHttp("POST", $http, url, data, action, error);
}

function doHttp(method, $http, url, data, action, error) {
    $http({
        method: method,
        url: url,
        data: data
    }).then(function successCallback(response) {
        action(response.data);
    }, function errorCallback(response) {
        if (error) {
            error(response)
        } else {
            console.log(response);
        }
    });
}

function addBook() {
    var data = new FormData($("#addNewBook")[0]);
    $.ajax({
        url: base_url + "/book/save",
        method: "POST",
        contentType: false,
        data: data,
        processData: false,
        success: function () {
            document.location.href = "./";
        },
        error: function (error) {
            console.log("ERROR: ", error);
        }
    });
}