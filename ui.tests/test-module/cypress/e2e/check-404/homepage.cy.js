/// <reference types="cypress" />
describe("Homepage", () => {
    it("page is found", () => {
        cy.visit("/")
    });
});