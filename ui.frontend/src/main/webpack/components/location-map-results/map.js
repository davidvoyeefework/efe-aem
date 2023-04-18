// to be reused later
class LocationMapResults {
    constructor() {
        this.init();
    }

    init() {
        const LAT = 39.828175,
            LNG = -98.5795,
            SATURATION = -100,
            ZOOM = 5;
        let myLatLng = new google.maps.LatLng(LAT, LNG);
        let styles = [
            {
                stylers: [{ saturation: SATURATION }],
            },
        ];
        let styledMap = new google.maps.StyledMapType(styles, {
            name: "Styled Map",
        });
        let options = {
            zoom: ZOOM,
            center: myLatLng,
            mapTypeControl: false,
            mapTypeControlOptions: {
                mapTypeIds: [google.maps.MapTypeId.TERRAIN, "map_style"],
            },
        };
        let map = new google.maps.Map(
            document.getElementById("map_canvas"),
            options
        );
        map.mapTypes.set("map_style", styledMap);
        map.setMapTypeId("map_style");
        let marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            title: "Middle of America PIN",
        });
    }
}

new LocationMapResults();
