/// <reference types="cypress" />
describe("Map Search Results - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/map-search-results/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Map Search Results", () => {
        cy.get("#ui-test-map-direction-hero").then(($map) => {
            cy.wrap($map).find("input#location").type("GA");
            cy.wrap($map)
                .find("[data-component=location-map-results]")
                .contains("find a planner", { matchCase: false })
                .click();

            cy.then(() => {
                cy.wrap($map)
                    .find(".cmp-location--results__items")
                    .contains("Atlanta", { matchCase: false });
            });
        });
    });
});
