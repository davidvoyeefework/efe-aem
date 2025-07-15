/// <reference types="cypress" />
describe(`Accordion: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/accordion/`, () => {
    before(() => {
        cy.visit("/ui-tests/accordion/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Accordion", () => {
        cy.get("[id^=ui-test-accordion-item-].cmp-accordion__item").as(
            "accordionItems"
        );

        // Testing first item
        cy.get("@accordionItems")
            .first()
            .contains("Item #1", { matchCase: false })
            .should("be.visible");
        cy.get("@accordionItems")
            .first()
            .contains("Details for item #1", { matchCase: false })
            .should("not.be.visible");
        cy.get("@accordionItems")
            .first()
            .find("[id^=ui-test-accordion-item-].cmp-accordion__button")
            .click();
        cy.get("@accordionItems")
            .first()
            .contains("Details for item #1", { matchCase: false })
            .should("be.visible");
        cy.get("@accordionItems")
            .first()
            .find("[id^=ui-test-accordion-item-].cmp-accordion__button")
            .click();
        cy.get("@accordionItems")
            .first()
            .contains("Details for item #1", { matchCase: false })
            .should("not.be.visible");

        // Testing second item
        cy.get("@accordionItems")
            .last()
            .contains("Item #2", { matchCase: false })
            .should("be.visible");
        cy.get("@accordionItems")
            .last()
            .contains("Details for item #2", { matchCase: false })
            .should("not.be.visible");
        cy.get("@accordionItems")
            .last()
            .find("[id^=ui-test-accordion-item-].cmp-accordion__button")
            .click();
        cy.get("@accordionItems")
            .last()
            .contains("Details for item #2", { matchCase: false })
            .should("be.visible");
        cy.get("@accordionItems")
            .last()
            .find("[id^=ui-test-accordion-item-].cmp-accordion__button")
            .click();
        cy.get("@accordionItems")
            .last()
            .contains("Details for item #2", { matchCase: false })
            .should("not.be.visible");
    });
});
