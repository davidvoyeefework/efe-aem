/// <reference types="cypress" />
describe("Services - Financial Planning", () => {
    it("page is found", () => {
        cy.visit("/services/planning/")
    });
});