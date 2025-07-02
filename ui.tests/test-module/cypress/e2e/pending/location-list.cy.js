/// <reference types="cypress" />
describe("Location List - AEM Component", () => {
    before(() => {
        cy.visit("/ui-tests/location-list/");
        cy.preventPageViewRequest(); // Prevent page view request.
    });

    it("Location List", () => {
        cy.get(
            "#ui-test-locaion-list li.cmp-languagenavigation__item--level-0"
        ).then(($city_lists) => {
            expect(
                $city_lists,
                "Verify there are locations displayed."
            ).to.have.length.greaterThan(0);
            let city =
                $city_lists[parseInt(Math.random() * $city_lists.length)];

            cy.wrap(city)
                .find("span")
                .first()
                .should("be.visible")
                .and("not.be.empty");

            cy.wrap(city)
                .find("ul li.cmp-languagenavigation__item--level-1")
                .each(($location) => {
                    let location_name = $location.text().trim();
                    cy.wrap($location)
                        .find("a")
                        .contains(location_name)
                        .should("be.visible")
                        .and("have.attr", "href")
                        .and(
                            "include",
                            location_name
                                .replaceAll(" ", "-")
                                .replaceAll("/", "-")
                                .replaceAll(".", "-")
                        );
                });
        });
    });
});
