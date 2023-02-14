/**
 * Header class
 */

export default class Header {
  constructor(el) {
    this.el = el;
    document.querySelector("#nav-search-icon a").addEventListener("click", function() {
      showElement("efe-nav-search");
    });
    document.querySelector("#search-close").addEventListener("click", function() {
      showElement("efe-nav-search");
    });
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