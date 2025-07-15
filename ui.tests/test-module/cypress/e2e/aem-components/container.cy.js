/// <reference types="cypress" />
describe(`Container: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/container/`, () => {
    before(() => {
        cy.visit("/ui-tests/container/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Container", () => {
        cy.get("#ui-test-container-dark-gray")
            .parent()
            .then(($el) => {
                cy.wrap($el)
                    .should(
                        "have.css",
                        "background-color",
                        "rgb(221, 221, 222)"
                    )
                    .contains("container", { matchCase: false })
                    .should("be.visible");
                cy.wrap($el)
                    .find("button")
                    .should("have.attr", "type", "button")
                    .contains("Primary CTA", { matchCase: false })
                    .should("be.visible");
            });

        cy.get("#ui-test-container-white")
            .parent()
            .then(($el) => {
                cy.wrap($el)
                    .should(
                        "have.css",
                        "background-color",
                        "rgb(255, 255, 255)"
                    )
                    .contains("container", { matchCase: false })
                    .should("be.visible");
                cy.wrap($el)
                    .find("button")
                    .should("have.attr", "type", "button")
                    .contains("Primary CTA", { matchCase: false })
                    .should("be.visible");
            });

        cy.get("#ui-test-container-light-gray")
            .parent()
            .then(($el) => {
                cy.wrap($el)
                    .should(
                        "have.css",
                        "background-color",
                        "rgb(242, 242, 242)"
                    )
                    .contains("container", { matchCase: false })
                    .should("be.visible");
                cy.wrap($el)
                    .find("button")
                    .should("have.attr", "type", "button")
                    .contains("Primary CTA", { matchCase: false })
                    .should("be.visible");
            });

        cy.get("#ui-test-container-image").then(($el) => {
            cy.wrap($el)
                .invoke("css", "background-image")
                .then((bgImage) => {
                    expect(bgImage).to.include(
                        "/ui-tests-assets/images/WEB_ED-great-resignation-hero-new.jpg"
                    );
                });
            cy.wrap($el)
                .contains("container", { matchCase: false })
                .should("be.visible");
            cy.wrap($el)
                .find("button")
                .should("have.attr", "type", "button")
                .contains("Primary CTA", { matchCase: false })
                .should("be.visible");
        });

        // ui-test-container-light-gray
    });
});
