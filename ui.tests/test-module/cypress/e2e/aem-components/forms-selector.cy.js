/// <reference types="cypress" />
describe("Forms Selector - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/forms-selector/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Forms Selector", () => {
        cy.get("#ui-test-contact-us-form input#first_name").type("Mark");
        cy.get("#ui-test-contact-us-form input#last_name").type("Smith");
        cy.get("#ui-test-contact-us-form input#email_address").type(
            "marksmith@hotmail.com"
        );
        cy.get("#ui-test-contact-us-form input#phone_number").type(
            "8007771111"
        );
        cy.get("#ui-test-contact-us-form input#zip_code").type("50111");

        cy.get(
            "#ui-test-contact-us-form select#investableAssets > option"
        ).then((options) => {
            let random_option = parseInt(Math.random() * options.length);
            cy.get("#ui-test-contact-us-form select#investableAssets").select(
                options[random_option != 0 ? random_option : random_option + 1]
                    .value
            );
        });

        cy.get("#ui-test-contact-us-form #form-builder").contains(
            "get started",
            { matchCase: false }
        );
    });
});
