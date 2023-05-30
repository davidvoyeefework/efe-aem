export default class LocationMapResults {
    constructor(el) {
        this.el = el;
        if (!el) return;
        this.markerImg = "/etc.clientlibs/efe/clientlibs/clientlib-site/resources/images/icons/efe-marker.svg";
        this.searchBtn = el.querySelector(".search-location-btn");
        this.searchInput = el.querySelector("#location");
        this.handleLocationSearch = this.searchBtn.addEventListener(
            "click",
            this.handleLocationSearch.bind(this)
        );
        this.handleLocationEnter = this.searchInput.addEventListener(
            "keyup",
            this.handleLocationEnter.bind(this)
        );
        this.offices = JSON.parse(el.dataset?.offices);
        this.EXPLORE_LINK_LABEL = el.dataset?.explorelinkLabel;
        this.PLANNER_BTN_LABEL = el.dataset?.plannerBtnLabel;
        this.defaultLatitude = 39.828175;
        this.defaultLongitude = -98.5795;
        this.furthestOffice = 48280;
        this.initMap(this.offices, {
            lat: this.defaultLatitude,
            lng: this.defaultLongitude,
            firstLoad: true,
        });
    }

    static init(el) {
        return new LocationMapResults(el);
    }

    handleLocationEnter(event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            this.searchBtn.click();
        }
    }

    getCurrentSearchedLatAndLng() {
        let searchInput = this.el.querySelector("#location")?.value;
        this.geocodeAddress(searchInput)
            .then((results) => {
                return {
                    latitude: results.latitude,
                    longitude: results.longitude,
                }
            })
            .catch((error) => {
                console.error(`Geocode failed with error: ${error}`);
            });
    }

    handleEmptyResults(res) {
        let latitude, longitude;
        if (!res) {
            const coordinates = this.getCurrentSearchedLatAndLng();
            latitude = coordinates.latitude;
            longitude = coordinates.longitude;
        } else {
            latitude = res.latitude;
            longitude = res.longitude;
        }
        this.showSearchResultsContainer(null, {
            showNationalAdvisor: true,
            lat: latitude,
            lng: longitude,
            hidePin: true,
        });
    }

    handleLocationSearch(event) {
        event.preventDefault();
        let searchInput = this.el.querySelector("#location")?.value;
        if(this.searchBtn.textContent === "Find a Planner") {
            window.adobeDataLayer?.push({
                event: "find_planner_click",
                web: { 
                    webInteraction: {
                    name:"Find a Planner",
                    region: "Body",
                    type: "other",
                    locationValue: searchInput,
                    findPlanner: {
                        value: 1
                    }
                    }
                }
            })
        } else {
            window.adobeDataLayer?.push({
                event: "find_planner_click",
                web: { 
                    webInteraction: {
                    name:"Search Location",
                    region: "Body",
                    type: "other",
                    locationValue: searchInput,
                    searchLocation: {
                        value: 1
                    }
                    }
                }
            })
        }
        this.searchBtn.textContent = this.el.dataset?.searchBtnLabel || "Search Destinations";
        if (searchInput === "") {
            this.showSearchResultsContainer(null, {
                showNationalAdvisor: true,
                lat: this.defaultLatitude,
                lng: this.defaultLongitude,
                hidePin: true,
            });
        } else {
            // trim the string of whitespaces and convert to lowercase
            searchInput = searchInput.trim().toLowerCase();
            const data = this.filterArray(searchInput);
            if (data.length === 0) {
                this.geocodeAddress(searchInput)
                    .then((results) => {
                        const nearbyLocations =
                            this.getLocationsWithin30MileRadius(
                                results.latitude,
                                results.longitude
                            );
                        if (nearbyLocations.length === 0) {
                            this.handleEmptyResults(results);
                        } else {
                            this.furthestOffice =  48280 / 1.8;
                            this.showSearchResultsContainer(nearbyLocations, {
                                showNationalAdvisor: false,
                                lat: results.latitude,
                                lng: results.longitude,
                                showBounds: true,
                            });
                        }
                    })
                    .catch((error) => {
                        this.showSearchResultsContainer(null, {
                            showNationalAdvisor: true,
                            lat: this.defaultLatitude,
                            lng: this.defaultLongitude,
                            hidePin: true,
                        });
                        console.error(`Geocode failed with error: ${error}`);
                    });
            } else {
                this.geocodeAddress(searchInput)
                    .then((results) => {
                        this.furthestOffice =
                            this.getDynamicRadiusForFurthestOffice(
                                results.latitude,
                                results.longitude,
                                data,
                            );

                        this.showSearchResultsContainer(data, {
                            showNationalAdvisor: false,
                            lat: results.latitude,
                            lng: results.longitude,
                            showBounds: true,
                        });
                    })
                    .catch((error) => {
                        console.error(`Geocode failed with error: ${error}`);
                    });
            }
        }
        this.searchInput = searchInput;
    }

    geocodeAddress(address) {
        const geocoder = new google.maps.Geocoder();
        const componentRestrictions = { country: "US" };

        return new Promise((resolve, reject) => {
            geocoder.geocode(
                { address, componentRestrictions },
                (results, status) => {
                    if (status === "OK") {
                        const latitude = results[0].geometry.location.lat();
                        const longitude = results[0].geometry.location.lng();
                        console.log(latitude, longitude);

                        resolve({ latitude, longitude });
                    } else {
                        console.error(
                            `Geocode was not successful for the following reason: ${status}`
                        );
                        reject(status);
                    }
                }
            );
        });
    }

    showSearchResultsContainer(results, obj) {
        this.el.classList.remove("cmp-location--results-default");
        this.clearCurrentResults();
        if (obj && obj.showNationalAdvisor) {
            this.constructEmptyResults();
        } else {
            results.forEach((result) => {
                this.constructSearchResults(result);
            });
        }
        this.initMap(results, obj);
    }

    clearCurrentResults() {
        const resultsContainer = this.el.querySelector(
            ".cmp-location--results__items"
        );
        if (resultsContainer.classList.contains("no-results")) {
            resultsContainer.classList.remove("no-results");
        }
        resultsContainer.innerHTML = "";
    }

    constructEmptyResults() {
        console.log(this);
        const nationalTitle = this.el.dataset?.nationalTitle;
        const nationalDetails = this.el.dataset?.nationalDetails;
        const nationalCallback = this.el.dataset?.nationalCallback;
        const nationalCallbackLabel = this.el.dataset?.nationalCallbackLabel;
        const nationalLink = this.el.dataset?.nationalLink;

        const resultsContainer = this.el.querySelector(
            ".cmp-location--results__items"
        );
        resultsContainer.classList.add("no-results");
        const item = document.createElement("div");
        item.classList.add("cmp-location--results__items--item");

        const h2 = document.createElement("h2");
        h2.textContent = nationalTitle;
        item.appendChild(h2);

        const telLink = document.createElement("a");
        telLink.classList.add('cmp-tel-link');
        telLink.href = "tel:" + nationalCallback;
        telLink.innerText = nationalCallbackLabel;
        item.appendChild(telLink);

        const link = document.createElement("a");
        link.href = nationalLink;
        link.textContent = this.EXPLORE_LINK_LABEL;
        item.appendChild(link);

        const p = document.createElement("p");
        p.textContent = nationalDetails;
        item.appendChild(p);

        resultsContainer.appendChild(item);
    }

    constructSearchResults(result) {
        const resultsContainer = this.el.querySelector(
            ".cmp-location--results__items"
        );
        const item = document.createElement("div");
        item.classList.add("cmp-location--results__items--item");

        const title = document.createElement("h2");
        title.textContent = result.externalName;

        const street1 = document.createElement("p");
        street1.textContent = result.address1;
        const street2 = document.createElement("p");
        street2.textContent = result.address2;

        const locationInfo = document.createElement("p");
        const city = document.createElement("span");
        city.textContent = result.city;
        const state = document.createElement("span");
        state.textContent = result.state;
        const zip = document.createElement("span");
        zip.textContent = result.zip;

        locationInfo.appendChild(city);
        locationInfo.appendChild(document.createTextNode(" "));
        locationInfo.appendChild(state);
        locationInfo.appendChild(document.createTextNode(" "));
        locationInfo.appendChild(zip);

        const link = document.createElement("a");
        link.href = result.link;
        link.textContent = this.EXPLORE_LINK_LABEL;

        item.appendChild(title);
        item.appendChild(street1);
        item.appendChild(street2);
        item.appendChild(locationInfo);
        item.appendChild(link);
        resultsContainer.appendChild(item);
    }

    getLocationsWithin30MileRadius(lat, lng) {
        const searchLat = lat;
        const searchLng = lng;
        const searchRadius = 30; // 30 mile radius
        let nearbyLocations = [];

        this.offices.forEach((location) => {
            const locationLat = parseFloat(location.latitude);
            const locationLng = parseFloat(location.longitude);
            const distance =
                google.maps.geometry.spherical.computeDistanceBetween(
                    new google.maps.LatLng(searchLat, searchLng),
                    new google.maps.LatLng(locationLat, locationLng)
                ) * 0.000621371; // Convert meters to miles
            if (distance <= searchRadius) {
                nearbyLocations.push({
                    ...location,
                    distance,
                });
            }
        });

        return nearbyLocations;
    }

    getDynamicRadiusForFurthestOffice(lat, lng, locations) {
        let furthestLocation = 0;
        locations.forEach(location => {
            const locationLat = parseFloat(location.latitude);
            const locationLng = parseFloat(location.longitude);
            const distance =
                google.maps.geometry.spherical.computeDistanceBetween(
                    new google.maps.LatLng(lat, lng),
                    new google.maps.LatLng(locationLat, locationLng)
                ) * 0.000621371; // Convert meters to miles
            if (!isNaN(distance)) {
                furthestLocation = Math.max(furthestLocation, distance);
            }
        })
        return (furthestLocation * 1609.34) / 1.8;
    }

    filterArray(value) {
        return this.offices.filter(function (obj) {
            return Object.entries(obj).some(function ([key, val]) {
                return (
                    (key === "state" || key === "stateCode" || key === "zip") &&
                    val.toString().toLowerCase() === value.toLowerCase()
                );
            });
        });
    }

    initMap(data, obj) {
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

        const myLatLng = new google.maps.LatLng(obj.lat, obj.lng);
        const styles = [
            {
                stylers: [{ saturation: SATURATION }],
            },
        ];
        const styledMap = new google.maps.StyledMapType(styles, {
            name: "Styled Map",
        });
        const options = {
            zoom: 5,
            center: myLatLng,
            mapTypeId: google.maps.MapTypeId.TERRAIN,
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

        if (!obj.hidePin && data) {
            data.forEach((office) => {
                new google.maps.Marker({
                    position: new google.maps.LatLng(
                        office.latitude,
                        office.longitude
                    ),
                    map: map,
                    icon: this.markerImg,
                    title: office.city,
                });
            });
        }

        if (!obj.firstLoad && obj.showBounds) {
            const circleOptions = {
                center: myLatLng,
                fillOpacity: 0,
                strokeOpacity: 0,
                map: map,
                radius: this.furthestOffice,
            };

            const myCircle = new google.maps.Circle(circleOptions);
            map.fitBounds(myCircle.getBounds());
        }

        this.map = map;
    }
}
