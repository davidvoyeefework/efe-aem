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
    
    window.addEventListener('load', this.checkWrap);
    window.addEventListener('resize', debounce(() => this.checkWrap(), 100));
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
    const container = document.querySelector('#efe-nav-main .cmp-container--26');
      if (!container) return;
    const line = container.querySelector('.minimal-header__vertical-line');
      if (!line) return;
      // 1) Prefer explicit logo classes (add these classes in AEM if not already)
      const getLogoWrapper = (sel) => {
      const el = container.querySelector(sel);
      return el ? (el.closest('.cmp-image') || el) : null;
      }
    let primary   = getLogoWrapper('.cmp-image--efe-logo-primary, [data-logo="primary"]');
    let secondary = getLogoWrapper('.cmp-image--efe-logo-secondary, [data-logo="secondary"]');
    // 2) Fallback: use the line's left/right siblings, but ignore CTAs and tiny icons
    if (!primary || !secondary) {
      const isLogoLike = (el) => {
        if (!el || el.closest?.('#nav-list-cta-group')) return false; // ignore CTA area
        const img = el.matches('.cmp-image')
          ? el.querySelector('.cmp-image__image, img, picture img')
          : el.querySelector?.('.cmp-image__image, img, picture img');
        // treat as a logo only if it's visually substantial
        if (!img) return false;
        const r = img.getBoundingClientRect();
        return r.width >= 80 && r.height >= 20; // tune thresholds as needed
    }
      const left  = line.previousElementSibling;
      const right = line.nextElementSibling;
        if (!primary && isLogoLike(left))  primary = left.closest('.cmp-image') || left;
        if (!secondary && isLogoLike(right)) secondary = right.closest('.cmp-image') || right;
    }
      // 3) If we still don't have BOTH, hide the line and bail
    if (!primary || !secondary) {
      line.classList.add('hidden');
      line.style.visibility = 'hidden';
      return;
    }
    // 4) Same-row check (reliable across offset parents)
    const aTop = primary.getBoundingClientRect().top;
    const bTop = secondary.getBoundingClientRect().top;
    const sameRow = Math.abs(aTop - bTop) < 1.5;
    // Show only if both logos AND on the same row
    line.classList.toggle('hidden', !sameRow);
    line.style.visibility = sameRow ? 'visible' : 'hidden';
  }
}
