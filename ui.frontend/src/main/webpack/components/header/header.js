/**
 * Header class
 */

export default class Header {
  constructor(el) {
    this.el = el;
    clickFunction("#nav-search-icon a","efe-nav-search");
    clickFunction("#nav-search-iconT a","efe-nav-searchT");
    clickFunction("#search-close", "efe-nav-search");
    clickFunction("#search-closeT", "efe-nav-searchT");
    clickFunction("#nav-hamburger", "nav-list-cta-group");
    clickNav(".cmp-navigation__item--level-0");
  }

  static init(el) {
    return new Header(el);
  }
}

let windowWidth = document.documentElement.clientWidth || document.body.clientWidth || window.innerWidth;
const resize = () => {
  let ww = document.documentElement.clientWidth || document.body.clientWidth || window.innerWidth;
  windowWidth = ww;
}

const NavBreakpoint = 1290;
window.onresize = resize;

const showElement = (e) => {
  const domElement = document.getElementById(e);
  let displayStyle = getComputedStyle(domElement, "display", null ).getPropertyValue("display");
  if (displayStyle == "none") {
    domElement.style.display = "block";
    if (e == "nav-list-cta-group") {
      animateHamburger('showClose');
    }
  }
  else if (displayStyle == "block") {
    domElement.style.display = 'none';
    if (e == "nav-list-cta-group") {
      animateHamburger('hideClose');
    }
  }
};

const clickFunction = (listenItem, elmToShow) => {
  document.querySelector(listenItem).addEventListener("click", function() {
    showElement(elmToShow);
  });
};
const clickNav = (listenItem) => {
  const NavItems = document.querySelectorAll(listenItem);
 
  NavItems.forEach( (element) => {
      element.addEventListener("click", function() {
        if(!element.classList.contains("act")) {
       
          NavItems.forEach((element) => {
            element.classList.remove("act");
            let dropDownNav = element.children[2];
            dropDownNav.style.display = "none";
          })
          element.classList.toggle("act");
          let dropDownNav = element.children[2];
          let displayStyle = getComputedStyle(dropDownNav, "display", null ).getPropertyValue("display");
          if (displayStyle === "none") {
            dropDownNav.style.display = "block";
          }
          if (displayStyle === "block") {
            dropDownNav.style.display = "none";
          }
        }
        else {
              element.classList.remove("act");
              let dropDownNav = element.children[2];
              let displayStyle = getComputedStyle(dropDownNav, "display", null ).getPropertyValue("display");
              if (displayStyle == "block") {
                dropDownNav.style.display = "none";
              }
        }
      })
     });
};

const animateHamburger = (state) => {
  if (state == "showClose") {
    
    let topBar = document.getElementById("top-hamburger-line");
    let middleBar = document.getElementById("middle-hamburger-line");
    let bottomBar = document.getElementById("bottom-hamburger-line");
    middleBar.style.opacity = 0;
    topBar.style.transform = 'rotate(225deg)';
    topBar.style["-webkit-transform"] = 'rotate(225deg)';
    topBar.style["-ms-transform"] = 'rotate(225deg)';
    topBar.style.top = '9px';
    bottomBar.style.transform = 'rotate(-225deg)';
    bottomBar.style["-webkit-transform"] = 'rotate(-225deg)';
    bottomBar.style["-ms-transform"] = 'rotate(-225deg)';
    bottomBar.style.top = '9px';
  }

  if (state == "hideClose") {
    let topBar = document.getElementById("top-hamburger-line");
    let middleBar = document.getElementById("middle-hamburger-line");
    let bottomBar = document.getElementById("bottom-hamburger-line");
    middleBar.style.opacity = 1;
    topBar.style.transform = 'rotate(0deg)';
    topBar.style.top = '0px';
    bottomBar.style.transform = 'rotate(0deg)';
    bottomBar.style.top = '20px';
  }
}