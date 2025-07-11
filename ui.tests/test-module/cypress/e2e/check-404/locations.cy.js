/// <reference types="cypress" />
describe("our Locations", () => {
    it("page is found", () => {
        cy.visit("/locations")
    });
});