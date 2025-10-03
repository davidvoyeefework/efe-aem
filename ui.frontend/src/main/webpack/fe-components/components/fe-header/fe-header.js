import * as utility from "../../site/js/utility";
import { debounce,fetchData, handleLoader } from "../../site/js/helper";

export default class FeHeader {
  constructor() {
    document.addEventListener("displayHeaderReplaced", this.executePageView);
    document.addEventListener("displayPropRetrieved", () => {
      this.attributeParameterElem = document.querySelector("#fe-properties");
      this.init();
    });
    document.addEventListener("messageFromfePage", () => {
      this.attributeParameterElem = document.querySelector("#fe-properties");
      this.init();
    });
    
    window.addEventListener('load', () => setTimeout(() => this.checkWrap(), 0));
    window.addEventListener('resize', debounce(() => this.checkWrap(), 100));

    const container = document.querySelector('#efe-nav-main .cmp-container--1920.cmp-container--26');
    if (!container) return;
      
    this.mo = new MutationObserver(() => this.checkWrap());
    this.mo.observe(container, {childList: true, subtree:true});
    
  }

  init() {
    if (window.aemfe.header) {
      console.log("Header init called");
      this.fetchHeaderDataVariables();
      this.checkWrap();
    }
  }

  fetchHeaderDataVariables() {
    console.log("Fetching header variables");
    handleLoader(true);
    const apiVal =
      this.attributeParameterElem?.getAttribute("data-api-dynamic");
    const headers = { Accept: "application/json, text/plain, */*" };
    fetchData(apiVal, headers).then((dataVariables) => {
      if (dataVariables.length > 0) {
        this.changeHeaderValues(dataVariables);
        handleLoader(false);
      }
    });
  }

  executePageView() {
    console.log("Firing pageview event");
    alloy("sendEvent", {
      xdm: {
        web: {
          webPageDetails: {
            pageViews: {
              value: 1,
            },
          },
        },
      },
    });
  }
  changeHeaderValues(headerDataVariables) {
    console.log("Changing header variables: " + headerDataVariables);
    const data = window.aemfe;
    //const headerDataVariables =  this.attributeParameterElem?.getAttribute('data-variables');
    headerDataVariables?.forEach((item) => {
      if (Object.keys(item).length === 0) {
        return;
      }
      const elems = document.querySelectorAll("." + Object.keys(item));
      elems?.forEach((ele) => {
        ele.classList.remove("sponsor-value-hide");
        // Two approaches to replace variables
        const activeVariableKey = Object?.keys(item)?.length
          ? Object.keys(item)[0]
          : ""; // "SUPPORT_PHONE"
          if (activeVariableKey == "availableThroughText"){
            return;
          }
        const activeKeyValue = item ? item[activeVariableKey] : ""; // header.supportPhone or custom
        const CUSTOM_VARIABLE_VALUE = "custom";
        // for custom scenario - manipulation before replacing variable
        if (activeKeyValue === CUSTOM_VARIABLE_VALUE) {
          ele.innerHTML = this.calculateCustomVariable(
            activeVariableKey,
            activeKeyValue,
          );
        } else {
          // Dynamic variables which have valid refrence in window obj data
          // get from window obj as per specified activeKeyValue
          let requiredReplaceableValue = "";

          // direct replacement
          if (data[activeKeyValue] !== undefined) {
            requiredReplaceableValue = data[activeKeyValue];
          } else {
            try {
              requiredReplaceableValue = eval(`data.${activeKeyValue}`)
                ? eval(`data.${activeKeyValue}`)
                : "";
            } catch (e) {
              console.log("invalid key", activeVariableKey);
            }
          }
          ele.innerHTML = requiredReplaceableValue;
        }
      });
    });
    window.addEventListener("click", (e) => {
      if (e.target.classList.contains("fe-program-fees-modal-toggle")) {
        document.querySelector(".fe-modal")?.classList.toggle("fe-show-modal");
      }
    });
    var sponsorLogo = document.querySelector(".sponsor-logo .cmp-image");
    if (sponsorLogo && data.context.isFeChannel && data.context.sponsorId) {
      var sponsorId = data.context.sponsorId;
      var firstLetter = sponsorId.charAt(0);
      var logo = `https://s7d9.scene7.com/is/image/financialengines/${sponsorId}?wid=200&hei=75&fit=constrain&fmt=png-alpha`;
      console.log("Updated Employer Logo Path :",logo);
      /* var logoFileName = data.context.sponsorId;
     // var logoPath = this.attributeParameterElem?.getAttribute(
        "data-sponsor-logo-path",
      );
      var logo = logoPath.replace("{sponsorid}", logoFileName); */

      var sponsorLogoEl =
        `<img src="` +
        logo +
        `" loading="lazy" class="cmp-image__image" itemprop="contentUrl" alt=" " title="Logo">`;
      sponsorLogo.insertAdjacentHTML("beforeend", sponsorLogoEl); 
      
    }
    document.dispatchEvent(
      new CustomEvent("displayHeaderReplaced", { bubbles: true }),
    );
  }
  calculateCustomVariable(key) {
    switch (key) {
      case "promotionBannerFeeText":
        return utility.promotionBannerFeeInfo()?.promotionBannerFeeText;
      case "PROMOTION_ANNOUNCED_END_DATE":
        return utility.PROMOTION_ANNOUNCED_END_DATE(key);
      case "promotionBannerFeeTextLink":
        return utility.promotionBannerFeeInfo()?.promotionBannerFeeTextLink;
      case "PROMOTION_EXPIRATION_DATE":
        return utility.getPromotionExpirationDate();
      case "promoDurationMonthsNumeric":
        return utility.getPromoDurationMonthsNumeric();
      case "programFees":
        return utility.getProgramFeesModalLink("program fees");
      case "seeFeesAndDisclosure":
        return utility.getProgramFeesModalLink("See fees and disclosure");
      case "learnMoreOALink":
        return utility.prepareOALearnMoreLink();
      case "learnMoreOAButton":
        return utility.prepareOALearnMoreLink("button");
      case "learnMorePALink":
        return utility.preparePALearnMoreLink();
      case "learnMorePAButton":
        return utility.preparePALearnMoreLink("button");
      case "product_choice_url":
        return utility.getProductChoiceUrl();
      case "product_choice_button":
        return utility.getProductChoiceButton();
      case "product_choice_button_prospects_only":
        return utility.getProductChoiceButtonProspectsOnly();
      case "otherWaysPromoMsgPa":
        return utility.getOtherWaysPromoMsgPa();
      case "dashboardLink":
        return utility.getDashboardLink();
      case "dashboardButton":
        return utility.getDashboardButton();
      case "loginDashboardButton":
        return utility.getLoginDashboardButton();
      case "pmAboutFeeText":
        return utility.getPmAboutFeeText();
      case "RK_LOGIN_URL":
        return utility.getLoginLink();
      default:
        return "";
    }
  }
  // inside FeHeader class
checkWrap = () => {
  const c = document.querySelector('#efe-nav-main .cmp-container--1920.cmp-container--26');
  if (!c) return;
  const line = c.querySelector('.minimal-header__vertical-line');
  if (!line) return;
  // Helpers
  const show = () => { line.classList.add('is-active');  line.style.visibility = 'visible'; };
  const hide = () => { line.classList.remove('is-active'); line.style.visibility = 'hidden'; };
  const q = (sel) => c.querySelector(sel);
  // Require explicit wrappers (add these classes / data-attrs in AEM)
  const primaryWrap   = q('.cmp-image--efe-logo-primary, [data-logo="primary"]');
  const secondaryWrap = q('.cmp-image--efe-logo-secondary, [data-logo="secondary"]');
  if (!primaryWrap || !secondaryWrap) { hide(); return; }
  // Find a real image inside each wrapper (handles AEM shapes)
  const getImg = (wrap) =>
    wrap.querySelector('img.cmp-image__image, picture img, img, svg, .cmp-image__image');
  const pImg = getImg(primaryWrap);
  const sImg = getImg(secondaryWrap);
  // Strict boolean: returns true/false only
  const hasRealImage = (img) => {
    if (!img) return false;                 // <— important: false, not null
    if (img.tagName === 'IMG') {
      return img.complete && img.naturalWidth > 0 && img.naturalHeight > 0;
    }
    // svg or other non-IMG graphic
    return true;
  };
  // If images not present/loaded yet, hide and re-check later
  if (!hasRealImage(pImg) || !hasRealImage(sImg)) {
    hide();
    // Re-run when the images load or when DOM changes add them
    [pImg, sImg].forEach(img => {
      if (img && img.tagName === 'IMG' && !img.complete) {
        img.addEventListener('load', () => this.checkWrap(), { once: true });
      }
    });
    // Watch wrappers for a late-arriving <img>
    [primaryWrap, secondaryWrap].forEach(wrap => {
      const mo = new MutationObserver(() => {
        const img = getImg(wrap);
        if (img) {
          if (img.tagName === 'IMG' && !img.complete) {
            img.addEventListener('load', () => this.checkWrap(), { once: true });
          }
          const keep = this.checkWrap();
          if (!keep){
            mo.disconnect();
            this.mo= null;
          }
        }
      });
      mo.observe(wrap, { childList: true, subtree: true });
    });
    return;
  }
  // Visible & substantial (avoid tiny icons)
  const isVisible = (el) => {
    const cs = getComputedStyle(el);
    if (cs.display === 'none' || cs.visibility === 'hidden' || cs.opacity === '0') return false;
    if (!el.offsetParent && cs.position !== 'fixed') return false;
    const r = el.getBoundingClientRect();
    return r.width >= 80 && r.height >= 20; // adjust thresholds if needed
  };
  if (!isVisible(primaryWrap) || !isVisible(secondaryWrap)) { hide(); return; }
  // Same-row check with tight tolerance (prevents 1px false positives)
  const sameRow = Math.abs(
    primaryWrap.getBoundingClientRect().top - secondaryWrap.getBoundingClientRect().top
  ) < 0.5;
    if (sameRow) show(); else hide();
  }
}
