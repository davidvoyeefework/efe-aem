describe("template spec", () => {
    it("passed", () => {
        cy.task("log", Cypress.env());
        cy.visit("https://example.cypress.io/")
    });
});