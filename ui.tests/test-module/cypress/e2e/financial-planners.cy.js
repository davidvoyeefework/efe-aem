/// <reference types="cypress" />
describe("Financial Planners", () => {
    it("page is found", () => {
        cy.visit("/financial-planners/")
    });
});