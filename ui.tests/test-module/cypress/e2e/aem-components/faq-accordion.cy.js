/// <reference types="cypress" />
describe(`FAQ accordion: AEM Component - ${Cypress.config("baseUrl")}/ui-tests/faq-accordion/`, () => {
    before(() => {
        cy.visit("/ui-tests/faq-accordion/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("FAQ accordion", () => {
        cy.get("#ui-test-faq-accordion .cmp-accordion__item").as("accordionItems")
        cy.get(
            "#ui-test-faq-accordion script[type='application/ld+json']"
        ).then(($el) => {
            let faqs_info = JSON.parse($el.html()).mainEntity;
            console.log(faqs_info);
            cy.get("@accordionItems").first().then(($firstQuestion) => {
                cy.wrap($firstQuestion).contains(faqs_info[0].name, {matchCase: false}).should("be.visible")
                cy.wrap($firstQuestion).find(".cmp-accordion__panel").should("not.be.visible")
                cy.wrap($firstQuestion).find("button").click()
                cy.wrap($firstQuestion).find(".cmp-accordion__panel").should("be.visible")
                cy.wrap($firstQuestion).find("button").click()
                cy.wrap($firstQuestion).find(".cmp-accordion__panel").should("not.be.visible")
            });

            cy.get("@accordionItems").last().then(($lastQuestion) => {
                cy.wrap($lastQuestion).contains(faqs_info[1].name, {matchCase: false}).should("be.visible")
                cy.wrap($lastQuestion).find(".cmp-accordion__panel").should("not.be.visible")
                cy.wrap($lastQuestion).find("button").click()
                cy.wrap($lastQuestion).find(".cmp-accordion__panel").should("be.visible")
                cy.wrap($lastQuestion).find("button").click()
                cy.wrap($lastQuestion).find(".cmp-accordion__panel").should("not.be.visible")
            });
            
        });
    });
});

// {
//     "@type": "Question",
//     "name": "Can I choose which services I want?",
//     "acceptedAnswer": {
//         "@type": "Answer",
//         "text": "To help maximize your wealth, we believe in taking an integrated approach to wealth management. Your financial life – investments, taxes, insurance, retirement and estate planning – must be wired together. Your dedicated financial advisor can coordinate with a team of experts to connect those different pieces into a cohesive view so you can unlock new ways to help build, grow, protect and preserve your wealth.To get answers to some of your other services questions, see our Retirement Planning FAQ and Tax Planning FAQ. Coming soon: Financial Planning FAQ, Investment Management FAQ, Insurance Guidance FAQ and Estate Planning FAQ. Certain services provided on an educational and guidance basis only."
//     }
// }
