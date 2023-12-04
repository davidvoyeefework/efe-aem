import * as utility from "../../site/js/utility";
import { fetchData, handleLoader } from "../../site/js/helper";

export default class FeHeader {
  constructor() {
    document.addEventListener("displayPropRetrieved", this.init);
    document.addEventListener("displayHeaderReplaced", this.executePageView);

    window.__alloyMonitors = window.__alloyMonitors || [];
    window.__alloyMonitors.push({
      onBeforeNetworkRequest(data) {
        if (
          this.completeFPID &&
          data.payload.events[0].xdm.eventType == "displayProp"
        ) {
          this.DisplayPropReqID = data.requestId;
          console.log(data);
        }
      },
      onNetworkResponse(data) {
        if (
          this.completeFPID &&
          DisplayPropReqID &&
          data.requestId == this.DisplayPropReqID
        ) {
          this.DisplayPropReqID = null;
          console.log(data);
          document.dispatchEvent(
            new CustomEvent("displayPropRetrieved", { bubbles: true }),
          );
          console.log("Display Properties Retrieved");
        }
      },
    });
  }

  /*
    document.addEventListener("messageFromfePage", (e) => {
      this.attributeParameterElem = document.querySelector("#fe-properties");
      this.init();
    });*/

  LibCheckIntervalID = setInterval(this.CheckFPIDReadyState, 50);
  DisplayPropReqID;
  completeFPID = false;
  CheckFPIDReadyState() {
    if (!window.adobeDataLayer && !window.efeAdobeWebSdkWrapperModule) {
      console.log("FPid Library Ready");
      const EfeAdobeWebSdkWrapper =
        window.efeAdobeWebSdkWrapperModule.EfeAdobeWebSdkWrapper;
      const efeAdobeWebSdk =
        EfeAdobeWebSdkWrapper && new EfeAdobeWebSdkWrapper();
      document.addEventListener(
        "fpidComplete",
        this.callPersonalizationRequest,
      );
      efeAdobeWebSdk.initialize();
      console.log("FPid Library Init Called");
      clearInterval(LibCheckIntervalID);
    } else {
      // Wait
    }
  }

  init() {
    if (window.aemfe.header) {
      console.log("Header init called");
      this.fetchHeaderDataVariables();
    }
  }

  callPersonalizationRequest() {
    console.log("Calling personalization request");
    this.completeFPID = true;
    let daVarsStr = getCookie("daVars");
    let daVars = daVarsStr ? JSON.parse(decodeURIComponent(daVarsStr)) : null;
    if (daVars && daVars.sponsorId && daVars.providerId) {
      var unbounceLoadObj = {
        event: "personalization.request",
        _financialengines: {
          recordKeeperDetails: {
            rkId: daVars.providerId,
          },
          sponsorDetails: {
            sponsorId: daVars.sponsorId,
          },
        },
      };
      adobeDataLayer.push(unbounceLoadObj);
      console.log("Personalization event pushed.");
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
      var logoFileName = data.context.sponsorId;
      var logoPath = this.attributeParameterElem?.getAttribute(
        "data-sponsor-logo-path",
      );
      var logo = logoPath.replace("{sponsorid}", logoFileName);
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
      case "availableThroughText":
        return utility.availableThroughText(key);
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
      case "otherWaysPromoMsgPa":
        return utility.getOtherWaysPromoMsgPa();
      case "dashboardLink":
        return utility.getDashboardLink();
      case "pmAboutFeeText":
        return utility.getPmAboutFeeText();
      case "RK_LOGIN_URL":
        return utility.getLoginLink();
      default:
        return "";
    }
  }
}
