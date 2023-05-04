export default class LocationMapDirections {
    constructor(el) {
        this.el = el;
        if (!el) return;
        this.mount(el);
    }

    static init(el) {
        return new LocationMapDirections(el);
    }

    mount(el) {
        const LAT = +el.dataset?.latitude;
        const LNG = +el.dataset?.longitude;
        const ZOOM = +el.dataset?.zoomlevel || 5;
        const SATURATION = -100;

        if (
            typeof google === "undefined" ||
            typeof google.maps === "undefined"
        ) {
            console.error(
                "Google maps API was not initialized. Please ensure that you are loading the scripts required to initialize google maps."
            );
            return;
        }

        const markerImg =
            "./icons/efe-marker.svg" || "https://www.edelmanfinancialengines.com/wp-content/themes/EFE_Divi_Child_Theme/images/efe-marker.svg";

        const myLatLng = new google.maps.LatLng(LAT, LNG);
        const styles = [
            {
                stylers: [{ saturation: SATURATION }],
            },
        ];
        const styledMap = new google.maps.StyledMapType(styles, {
            name: "Styled Map",
        });
        const options = {
            zoom: ZOOM,
            center: myLatLng,
            mapTypeControl: false,
            mapTypeControlOptions: {
                mapTypeIds: [google.maps.MapTypeId.TERRAIN, "map_style"],
            },
        };
        const map = new google.maps.Map(
            document.getElementById("map_canvas"),
            options
        );

        map.mapTypes.set("map_style", styledMap);
        map.setMapTypeId("map_style");

        new google.maps.Marker({
            position: myLatLng,
            icon: markerImg,
            map: map,
            title: "Middle of America PIN",
        });
    }
}
