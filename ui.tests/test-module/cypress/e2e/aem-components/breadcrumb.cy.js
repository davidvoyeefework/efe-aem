/// <reference types="cypress" />
describe("Breadcrumb - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/breadcrumb/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Breadcrumb", () => {
        cy.get("#ui-test-breadcrum script[type='application/ld+json']").then(
            ($el) => {
                let breadcrum_info = JSON.parse($el.html());
                breadcrum_info.itemListElement.forEach(
                    (breadcrum_item, ind) => {
                        cy.get(
                            `#ui-test-breadcrum .cmp-breadcrumb__item:nth-child(${
                                ind + 1
                            })`
                        )
                            .contains(breadcrum_item.name)
                            .should("be.visible");
                    }
                );
            }
        );
    });
});
