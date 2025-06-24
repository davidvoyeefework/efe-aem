/// <reference types="cypress" />
describe("Map Direction - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/map-direction.AZ.Phoenix/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Map Direction", () => {
        cy.get("#ui-test-map-direction-hero").then(($map_hero) => {
            cy.wrap($map_hero)
                .find("script[type='application/ld+json']")
                .then(($schema) => {
                    let map_direction_info = JSON.parse($schema.html());

                    cy.wrap($map_hero)
                        .contains(
                            `${map_direction_info.address.addressLocality}, ${map_direction_info.address.addressRegion} ${map_direction_info.address.postalCode}`
                        )
                        .should("be.visible");

                    cy.wrap($map_hero)
                        .find("[data-component=location-map-directions]")
                        .should(
                            "have.attr",
                            "data-latitude",
                            map_direction_info.geo.latitude
                        )
                        .and(
                            "have.attr",
                            "data-longitude",
                            map_direction_info.geo.longitude
                        );
                });

            cy.wrap($map_hero)
                .find("#map_canvas")
                .invoke("outerWidth")
                .should("be.gt", 300);
            cy.wrap($map_hero)
                .find("#map_canvas")
                .invoke("outerHeight")
                .should("be.gt", 300);

            cy.wrap($map_hero)
                .contains("Driving Directions", { matchCase: false })
                .should("be.visible");
        });
    });
});
