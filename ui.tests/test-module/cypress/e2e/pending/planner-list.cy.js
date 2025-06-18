/// <reference types="cypress" />
describe("Planner List - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/planner-list.AZ.Phoenix/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Planner List", () => {
        cy.get("#ui-test-planner-list").then(($planner_list) => {
            cy.wrap($planner_list).contains("Phoenix, AZ", {
                matchCase: false,
            });

            cy.wrap($planner_list)
                .find("img")
                .then(($imgs) => {
                    cy.wrap($imgs)
                        .should("be.visible")
                        .invoke("outerHeight")
                        .should("be.gt", 100);
                    cy.wrap($imgs).invoke("outerWidth").should("be.gt", 100);
                });
        });
    });
});
