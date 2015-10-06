angular.module("app")
    .factory("Ship", ShipService);

function ShipService() {

    return function (coordinates) {

        var ship = {
            coordinates: {},
            coordinateList : coordinates,
            sunk: false,
            attack: function (coordinate) {
                coordinates[coordinate] = 1
            }
        };

        coordinates.forEach(function (coordinate) {
            coordinates[coordinate] = 0
        });

        return ship;
    }
}