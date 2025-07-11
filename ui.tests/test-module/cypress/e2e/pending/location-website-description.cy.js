/// <reference types="cypress" />
describe("Location Website Description - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/location-website-description.AZ.Phoenix/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Location Website Description", () => {
        cy.get("#ui-test-location-website-description")
            .contains("Phoenix", { matchCase: false })
            .should("be.visible");
    });
});
