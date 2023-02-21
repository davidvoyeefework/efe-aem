/**
 * Header class
 */


// import $ from 'jquery';
export default class Header {
  constructor(el) {
    this.el = el;

    clickFunction("#nav-search-icon a","efe-nav-search");
    clickFunction("#nav-search-iconT a","efe-nav-searchT");
    clickFunction("#search-close", "efe-nav-search");
    clickFunction("#search-closeT", "efe-nav-searchT");
    clickFunction("#nav-hamburger", "nav-list-cta-group");
    clickNav(".cmp-navigation__item--level-0 ");
    // $('body').style('background,' )
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

const clickNav = (listenItem) => {
  let NavItems = document.querySelectorAll(listenItem);
  
  NavItems.forEach(element => 
      element.addEventListener("click", function() {

        NavItems.forEach( el => {
          if(el.classList.contains("cmp-navigation__item--active")) {
            el.classList.remove("cmp-navigation__item--active");
            let dropDownNav = el.children[2];
            dropDownNav.style.display = 'none';
          }
        }
        );
   
        element.classList.add("cmp-navigation__item--active");
        let dropDownNav = element.children[2];
        let displayStyle = getComputedStyle(dropDownNav, "display", null ).getPropertyValue("display");
        if (displayStyle == "none") {
          dropDownNav.style.display = 'block';
        }
        else if (displayStyle == "block") {
          dropDownNav.style.display = 'none';
        }

      })
    );
}