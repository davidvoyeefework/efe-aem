export default class Search {
  constructor(el) {
    this.el = el;
    this.el
      .querySelector(".cmp-search__cta")
      .addEventListener("click", this.handleSearchButtonClick.bind(this));
  }

  static init(el) {
    return new Search(el);
  }

  handleSearchButtonClick(event) {
    event.preventDefault();

    const searchInputValue = this.el.querySelector(".cmp-search__input")?.value;
    if (!searchInputValue) {
      return;
    }

    /**
     * Check if there's a match for the searched term, if it exists go there.
     */

    const results = document.querySelector(".cmp-search__results");
    if (results) {
      results.querySelectorAll(".cmp-search__item-title")?.forEach((result) => {
        if (result.innerText() === searchInputValue) {
          result.parentElement?.click();
        }
      });
    }
  }
}
