Cypress.on("uncaught:exception", (err, runnable) => {
    console.log("Uncaught Exception: " + err.message);
    return false;
});

// Prevent page view event
Cypress.Commands.add("preventPageViewRequest", () => {
    cy.intercept(
        {
            method: "POST",
            url: "**/interact*",
        },
        (req) => {
            if (
                JSON.parse(req.body).events[0].xdm.eventType ===
                "web.webpagedetails.pageViews"
            ) {
                req.destroy();
            }
        }
    ).as("checkForPageView");
});

Cypress.Commands.add("print", (message) => {
    cy.task("log", message);
});

// Pick a random location from locations.json
Cypress.Commands.add("randomLocation", () => {
    return cy
        .fixture("locations.json")
        .then((locations) => {
            let filtered_locations = locations.filter(
                (location) =>
                    location.externalName !== "" &&
                    location.externalName !== "Test" &&
                    location.externalName !== "National Advisor Center" &&
                    location.appointmentOnly === false
            );

            return filtered_locations[
                parseInt(Math.random() * filtered_locations.length)
            ];
        })
        .as("locationDetails");
});