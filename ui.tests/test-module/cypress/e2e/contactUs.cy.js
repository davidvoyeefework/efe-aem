/// <reference types="cypress" />
describe("Contact Us", () => {
    it("page is found", () => {
        cy.visit("/contact-us/")
    });
});