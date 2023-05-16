/**
 * Header class
 */

export default class Header {
    constructor(el) {
        this.el = el;
        let minimalHeader = el.parentElement.classList.contains('minimal-header');
        this.subHeaderDesktop = el.querySelectorAll('.efe-nav-header')[0];
        this.subHeaderMobile = el.querySelectorAll('.efe-nav-header')[1];
        if(!minimalHeader) {
            this.handleSearchIconClick = el.querySelector('#nav-search-icon').addEventListener('click', this.handleSearchIconClick.bind(this))
            this.handleSearchCloseClick = el.querySelector('#search-close').addEventListener('click', this.handleSearchCloseClick.bind(this))
            this.handleSearchIconMobileClick = el.querySelector('#nav-search-iconT').addEventListener('click', this.handleSearchIconMobileClick.bind(this))
            this.handleSearchCloseMobileClick = el.querySelector('#search-closeT').addEventListener('click', this.handleSearchCloseMobileClick.bind(this))
        }
        clickFunction("#nav-hamburger", "nav-list-cta-group");
        clickNav(".cmp-navigation__item--level-0");
    }

    static init(el) {
        return new Header(el);
    }

    handleSearchIconClick() {
        const searchBar = this.el.querySelector('#efe-nav-search');
        searchBar.style.display = 'block';
        this.subHeaderDesktop.style.display = 'none';
    }

    handleSearchCloseClick() {
        const searchBar = this.el.querySelector('#efe-nav-search');
        searchBar.style.display = 'none';
        this.subHeaderDesktop.style.display = 'flex';
    }

    handleSearchIconMobileClick() {
        const searchBarMobile = this.el.querySelector('#efe-nav-searchT');
        searchBarMobile.style.display = 'block';
        this.subHeaderMobile.style.display = 'none';
    }

    handleSearchCloseMobileClick() {
        const searchBarMobile = this.el.querySelector('#efe-nav-searchT');
        searchBarMobile.style.display = 'none';
        this.subHeaderMobile.style.display = 'flex';
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
            let dropDownNav = element.children[2].children[0];
            dropDownNav.style.display = "none";
          })
          element.classList.toggle("act");
          let dropDownNav = element.children[2].children[0];
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
              let dropDownNav = element.children[2].children[0];
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
