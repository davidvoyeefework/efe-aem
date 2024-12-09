/* eslint-disable @typescript-eslint/no-unused-vars, @typescript-eslint/no-this-alias */
/**
 * @author Subash Selvaraj <sselvara@adobe.com>
 * Initializer class
 *
 * Responsible for instantiating and map JS Class on components if [data-component=`component`] is present
 * Takes care of intantiation on DOM mutation
 */
import CONSTANTS from "../site/js/const";

class Initializer {
  constructor() {
    var self = this;
    this.selectors = {
      component: "[data-component]",
      body: "body",
    };

    // initialize the components on DOM ready
    if (document.readyState !== "loading") {
      self.onDocumentReady();
    } else {
      document.addEventListener(
        "DOMContentLoaded",
        self.onDocumentReady.bind(this),
        { once: true }
      );
    }
  }

  initMutation() {
    /*------------------------------------------------------------------
     * MutationObserver is used to listen for DOM changes
     * DOC: https://developer.mozilla.org/en-US/docs/Web/API/MutationObserver#Instance_methods
     * Performance related article :
     * https://hacks.mozilla.org/2012/05/dom-mutationobserver-reacting-to-dom-changes-without-killing-browser-performance
     *------------------------------------------------------------------
     */

    // observe body element for mutations change
    var targetNode = document.querySelector(this.selectors.body);

    // Options for the observer (which mutations to observe)
    const config = { attributes: false, childList: true, subtree: true };

    if (targetNode) {
      const observer = new MutationObserver(this.handleMutation.bind(this));
      // Start observing on body element for configured mutations
      observer.observe(targetNode, config);
    }
  }

  // Callback function to execute when mutations are observed
  handleMutation(mutationsList) {
    for (var mutation of mutationsList) {
      if (mutation.type == "childList") {
        const newNodes = mutation.addedNodes;
        // if new nodes are added to the DOM run through initialize component code
        if (newNodes.length) {
          // console.debug("Initializer: New Nodes -> ", newNodes);
          newNodes.forEach((element) => {
            if (
              element.dataset &&
              element.dataset.component &&
              !element.dataset.initialized
            ) {
              this.initComponent(element);
              element.dataset.initialized = true;
              console.debug(
                "Initializer: Root component is UI component -> ",
                element.dataset.component
              );
            }

            element.querySelector &&
              element.querySelectorAll(this.selectors.component).length &&
              element
                .querySelectorAll(this.selectors.component)
                .forEach((el) => {
                  if (
                    el.dataset &&
                    el.dataset.component &&
                    !el.dataset.initialized
                  ) {
                    this.initComponent(el);
                    el.dataset.initialized = true;
                    console.error(
                      "Initializer: Child component is UI component ->",
                      el.dataset.component
                    );
                  }
                });
          });
        }
      }
    }
  }

  initComponent(el) {
    const dataset = el.dataset;
    const componentName = dataset.component;
    const componentType = dataset.cmpType;
    const isReactComponent =
      CONSTANTS.COMPONENT_TYPE ===
      (componentType && componentType.toLowerCase());
    const pathExtension = isReactComponent
      ? CONSTANTS.REACT_COMPONENTS_PATH
      : ``;

    // const path = `../${pathExtension}components/${componentName}/${componentName}.js`;
    import(
      /* webpackExclude: /\.stories\.js$/ */
      `../${pathExtension}components/${componentName}/${componentName}.js`
    )
      .then((component) => {
        component.default.init(el);
      })
      .catch((error) => {
        console.debug(
          `../${pathExtension}components/${componentName}/${componentName}.js not found`
        );
      });
  }

  onDocumentReady() {
  replaceLocationPlaceholder();
  replaceLocationPlaceholderWithDefaultText();
    const elements = document.querySelectorAll(this.selectors.component);
    for (let i = 0; i < elements.length; i++) {
      this.initComponent(elements[i]);
    }
    this.initMutation();
  }
}

function replaceLocationPlaceholder() {
    const ldJsonScript = document.querySelector('script[type="application/ld+json"]');
    if (ldJsonScript != null) {
        const jsonData = JSON.parse(ldJsonScript.innerHTML);
        if (jsonData.address != null) {
            const addressLocality = jsonData.address.addressLocality;
            const addressRegion = jsonData.address.addressRegion;
            const replacementText = `${addressLocality}, ${addressRegion}`;
            replacePlaceholder(replacementText);
        }
    }
}

function replaceLocationPlaceholderWithDefaultText() {
    const ldJsonScript = document.querySelector('script[type="application/ld+json"]');
    const pattern = /\{planner\.location default=”([^”]*)”\}/g;
    const pageContent = document.body.innerHTML;

    if ((ldJsonScript == null || JSON.parse(ldJsonScript.innerHTML).address == null)) {
        replacePlaceholdersWithCorrespondingValues(pageContent, pattern);
    }
}

function replacePlaceholdersWithCorrespondingValues(pageContent, pattern) {
    let match;
    let placeholders = [];

    while ((match = pattern.exec(pageContent)) !== null) {
        placeholders.push({
            placeholder: match[0],
            value: match[1]
        });
    }

    placeholders.forEach(item => {
        replacePlaceholderByPlaceholder(item.placeholder, item.value);
    });
}

function replacePlaceholderByPlaceholder(placeholder, replacementText) {
    const elements = document.querySelectorAll('p, span');
    elements.forEach(element => {
        let htmlContent = element.innerHTML;
        htmlContent = htmlContent.replace(placeholder, replacementText);
        element.innerHTML = htmlContent;
    });
}


function replacePlaceholder(replacementText) {
    const paragraphs = document.querySelectorAll('p');
    paragraphs.forEach(paragraph => {
        let htmlContent = paragraph.innerHTML;
        htmlContent = htmlContent.replace(/{planner\.location default=”[^”]*”}/g, replacementText);
        paragraph.innerHTML = htmlContent;
    });
    const spans = document.querySelectorAll('span');
    spans.forEach(span => {
        let htmlContent = span.innerHTML;
        htmlContent = htmlContent.replace(/{planner\.location default=”[^”]*”}/g, replacementText);
        span.innerHTML = htmlContent;
    });
}

export { Initializer };
/* eslint-enable */


// Cornish Tabs 
const tabsCornish = () => {
  const cornishTabEl = document.querySelector(".tabs-cornish .cmp-tabs__tablist");
  const triggerEl = document.querySelector(".tabs-cornish"); 

  if (cornishTabEl != null) {
    cornishTabEl.setAttribute("id", "cornish-tab-nav");
    triggerEl.setAttribute("id", "trigger");   

    // cornishTabEl is element to be wrapped
    const createWrapper = () => {
      var wrapperCreated = document.getElementById("wrapper");
      if (wrapperCreated == null) {
        const wrapper = document.createElement('div');
        cornishTabEl.parentNode.insertBefore(wrapper, cornishTabEl);
        wrapper.appendChild(cornishTabEl);
        const childNode = document.querySelector(".tabs-cornish .cmp-tabs > div");
        childNode.setAttribute("id", "wrapper"); 
      }
    }
    createWrapper();
 
    const scrollWidth = cornishTabEl.scrollWidth;
    const viewportWidth = cornishTabEl.offsetWidth;
      if (scrollWidth <= viewportWidth) {
        triggerEl.classList.add("no-icon");
      }
      if (scrollWidth > viewportWidth) {
        triggerEl.classList.remove("no-icon");
      } 
  }
 
 }
 tabsCornish();

window.addEventListener("resize", tabsCornish);

//GSAP Animation Platform for Cornish Tabs
document.addEventListener("DOMContentLoaded", (event) => {
  const cornishTabEl = document.querySelector(".tabs-cornish .cmp-tabs__tablist");
  if (cornishTabEl != null) {
    gsap.registerPlugin(ScrollTrigger,ScrollToPlugin);
    const scrollEl = document.getElementById("cornish-tab-nav");    
    const imgTag = document.createElement("div");
    const imgTagBack = document.createElement("div");
    imgTag.classList.add("scrollIcon");
    imgTagBack.classList.add("scrollIconBack");    
    wrapper.appendChild(imgTag);
    wrapper.appendChild(imgTagBack);
    const scrollIcon = document.querySelector("#wrapper > .scrollIcon");
    const scrollIconBack = document.querySelector("#wrapper > .scrollIconBack");

    scrollIcon.addEventListener("click", () => {
      let scrollWidth = scrollEl.scrollWidth;
      let viewPortWidth = scrollEl.offsetWidth;
      let overFlowWidth = scrollWidth - viewPortWidth -.5;
      let tabOlElement = document.querySelectorAll("#cornish-tab-nav li");
      let tabNumber = tabOlElement.length;
      let widthTally = 0;
      for (let i = 0; i <= tabNumber; i++) {
        let tabElWidth = tabOlElement[i].clientWidth + 56;
        widthTally = widthTally + tabElWidth;
        if (widthTally >= viewPortWidth) {
          widthTally = widthTally - tabOlElement[i].clientWidth -56; 
          scrollEl.scrollLeft += widthTally -30;    
          return;
        }
      }
    });
  
    scrollIconBack.addEventListener("click", () => {
      let viewPortWidth = scrollEl.offsetWidth;
      let tabOlElement = document.querySelectorAll("#cornish-tab-nav li");
      let tabNumber = tabOlElement.length;
      let widthTally = 0;
      for (let i = tabNumber -1; i >= 0; i--) {
        let tabElWidth = tabOlElement[i].clientWidth + 56;
        widthTally = widthTally + tabElWidth;
        if (widthTally >= viewPortWidth) {
          widthTally = widthTally - tabOlElement[i].clientWidth - 56;
          scrollEl.scrollLeft -= widthTally - 30;
          return;
        }
      }
    });

    // SCROLL ICON BEHAVIOR
    scrollEl.addEventListener("scroll", (event) => {
      let scroll = scrollEl.scrollLeft;
      let scrollWidth = scrollEl.scrollWidth;
      let viewPortWidth = scrollEl.offsetWidth;
      let scrollEnd = scrollWidth - viewPortWidth -.5;
      const wrapper = document.getElementById("wrapper");

      if (scroll >= scrollEnd ) {
        wrapper.classList.add("no-icon");
        wrapper.classList.add("reverse-icon");
      }
      else if (scroll == 0) {
        wrapper.classList.remove("no-icon");
        wrapper.classList.remove("reverse-icon");
      }

      else if (scroll > 0 && scroll < scrollEnd) {
        wrapper.classList.remove("no-icon");
        wrapper.classList.add("reverse-icon");
      }

    });

    // STICKY SECTION ON SCROLL BEHAVIOR
    const tl = gsap.timeline({
      scrollTrigger: {
          trigger: "#trigger",
          start: "top top",
          scrub: true,
          pinSpacing: false,
          pin: "#wrapper",
          toggleClass: {targets: "#wrapper", className: "drop-shadow"},
          end: () => `+=${$("#trigger").height()}`,
      }
  })
  tabsCornish();    
  }
 });

 // Close functionality for Offer Alert Banner
document.addEventListener("DOMContentLoaded", (event) => {
  const floatingOfferBanner = document.querySelector("#alert-offer-floating");
  if (floatingOfferBanner != null) {
      floatingOfferBanner.addEventListener("click",  () => {
        floatingOfferBanner.style.display = "none";
      })
  }
  });







