/**
 * Header class
 */

export default class Header {
  constructor(el) {
    this.el = el;

    clickFunction("#nav-search-icon a","efe-nav-search");
    clickFunction("#search-close", "efe-nav-search");
    clickFunction("#nav-hamburger", "nav-list-cta-group");
  }

  static init(el) {
    return new Header(el);
  }
}



const showElement = (e) => {
  const domElement = document.getElementById(e);
  let displayStyle = getComputedStyle(domElement, "display", null ).getPropertyValue("display");
  if (displayStyle == "none") {
    domElement.style.display = 'block';
  }
  else if (displayStyle == "block") {
    domElement.style.display = 'none';
  }
}

const clickFunction = (listenItem, elmToShow) => {
  document.querySelector(listenItem).addEventListener("click", function() {
    showElement(elmToShow);
  });
}