/// <reference types="cypress" />
describe("About Us", () => {
    it("page is found", () => {
        cy.visit("/about-us/")
    });
});