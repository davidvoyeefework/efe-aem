import { captureScIdFromUrl } from "./helper";

export const availableThroughText = (key) => {
  const windowDataObj = window.aemfe;
  let topFee = 0.0;
  if (windowDataObj?.sponsoredFees?.feeScheduleTiers) {
    windowDataObj.sponsoredFees.feeScheduleTiers.forEach((elem) => {
      if (elem.feeRate > topFee) {
        topFee = elem.feeRate;
      }
    });
  }
  let availableThroughText =
    windowDataObj["publicEnrollment.products.option.pm.availableThrough"];
  if (availableThroughText) {
    availableThroughText = availableThroughText.replace(
      "{{ sponsor }}",
      windowDataObj?.preferredName,
    );
    availableThroughText = availableThroughText.replace(
      "{{ industryPercent }}",
      lowerThanIndustryPercentValue(),
    );
    availableThroughText = availableThroughText.replace(
      "{{ feePercent }}",
      (topFee * 0.01).toPrecision(2) + "%",
    );
  }
  if (availableThroughText.slice(-2) === "**") {
    availableThroughText = availableThroughText.slice(0, -2);
  }
  return availableThroughText;
};
export const lowerThanIndustryPercentValue = () => {
  return window.aemfe?.sponsoredFees[0]?.lowerThanIndustryPercentValue;
};

/**
 * 
 * @returns Object {
    promotionBannerFeeText,
    promotionBannerFeeTextLink
  } 
 */
export const promotionBannerFeeInfo = () => {
  let promotionBannerFeeText = "";
  let promotionBannerFeeTextLink = "";

  const windowDataObj = window?.aemfe;
  const promotionBannerFeeTextPaSponsor =
    windowDataObj[`maoachoice.promotionNew.banner.feeText.pa`] || "";

  // set data that is dependent on isPaSponsor flag
  if (checkIfPaSponsored()) {
    // use banner fee text key if it returns a value
    if (
      promotionBannerFeeTextPaSponsor &&
      promotionBannerFeeTextPaSponsor.indexOf(
        "windowDataObj.maoachoice.promotionNew.banner.feeText.pa",
      ) === -1
    ) {
      promotionBannerFeeText = promotionBannerFeeTextPaSponsor;
    }
    // otherwise use default
    else {
      promotionBannerFeeText =
        "See if Management is right for you. Sign up, and if you're not satisfied just cancel by {{promoEndDate}} and we'll waive the program fee.*";
    }

    promotionBannerFeeTextLink =
      "*Or stay, and {{programFees}} will apply after {{promoExpirationDate}} if you remain enrolled.";
  } else {
    promotionBannerFeeText =
      windowDataObj[`maoachoice.promotionNew.banner.feeText`] || "";
    promotionBannerFeeTextLink =
      windowDataObj[`maoachoice.promotionNew.banner.feeTextLink`] || "";
  }

  // before final return check for data fields if any
  if (getActiveDataFields(promotionBannerFeeText)?.length) {
    //saasds {{asdasd}} sadasd {{wsqada}}
    promotionBannerFeeText = getDataFieldsWithValueInStr(
      promotionBannerFeeText,
    );
  }

  if (getActiveDataFields(promotionBannerFeeTextLink)?.length) {
    promotionBannerFeeTextLink = getDataFieldsWithValueInStr(
      promotionBannerFeeTextLink,
    );
  }

  return {
    promotionBannerFeeText,
    promotionBannerFeeTextLink,
  };
};

export const PROMOTION_ANNOUNCED_END_DATE = (key) => {
  const windowDataObj = window?.aemfe;
  let value = windowDataObj?.promotion?.announcedDeadlineDate;
  let announcedDeadlineDate = value;
  let PROMOTION_ANNOUNCED_END_DATE = "";
  var monthNames = [
    "Jan.",
    "Feb.",
    "March",
    "April",
    "May",
    "June",
    "July",
    "Aug.",
    "Sept.",
    "Oct.",
    "Nov.",
    "Dec.",
  ];
  if (announcedDeadlineDate != null) {
    var announcedDeadlineDateArray = announcedDeadlineDate.split("-");

    PROMOTION_ANNOUNCED_END_DATE = new Date(announcedDeadlineDate);
  }
  if (PROMOTION_ANNOUNCED_END_DATE == "") {
    return PROMOTION_ANNOUNCED_END_DATE;
  } else {
    return `${
      monthNames[PROMOTION_ANNOUNCED_END_DATE.getMonth()]
    } ${PROMOTION_ANNOUNCED_END_DATE.getDate()}, ${PROMOTION_ANNOUNCED_END_DATE.getFullYear()}`;
  }
};

// get PROMOTION_EXPIRATION_DATE
export const getPromotionExpirationDate = () => {
  const windowDataObj = window?.aemfe;
  let expiryDateRefvalue = windowDataObj?.promotion?.expirationDate;
  let promotionExpirationDate = "";
  var monthNames = [
    "Jan.",
    "Feb.",
    "March",
    "April",
    "May",
    "June",
    "July",
    "Aug.",
    "Sept.",
    "Oct.",
    "Nov.",
    "Dec.",
  ];
  if (expiryDateRefvalue != null) {
    let expirationDateArray = expiryDateRefvalue.split("" - "");
    promotionExpirationDate = new Date(expiryDateRefvalue);
  }
  if (promotionExpirationDate == "") {
    return promotionExpirationDate;
  } else {
    return `${
      monthNames[promotionExpirationDate.getMonth()]
    } ${promotionExpirationDate.getDate()}, ${promotionExpirationDate.getFullYear()}`;
  }
};

//promoDurationMonthsNumeric
export const getPromoDurationMonthsNumeric = () => {
  const windowDataObj = window?.aemfe;
  let promoDurationMonthsNumericRef =
    windowDataObj?.promotion?.offerDetails || "";
  let promoDurationMonthsNumeric = "";

  if (promoDurationMonthsNumericRef) {
    if (promoDurationMonthsNumericRef === "3 mo Fee Waiver") {
      promoDurationMonthsNumeric = "3";
    } else if (promoDurationMonthsNumericRef === "6 mo Fee Waiver") {
      promoDurationMonthsNumeric = "6";
    }
  }
  return promoDurationMonthsNumeric;
};

/**
 * returns a final replaced string which has active values for data fields
 */
export const getDataFieldsWithValueInStr = (str) => {
  const dataFieldArr = getActiveDataFields(str); // ["{{wdds}}", {{sadada}}, "{{asdadas}}"]
  let replacedStr = str;
  if (dataFieldArr?.length) {
    dataFieldArr.forEach((item) => {
      const activeFieldName = item
        .replace("{{", "")
        .replace("%7B%7B", "")
        .replace("}}", "")
        .replace("%7D%7D", "");
      replacedStr = replacedStr.replace(
        item,
        getDataFieldValue(activeFieldName), // programFees
      );
    });
  }
  return replacedStr;
};

const getDataFieldValue = (activeFieldName) => {
  // all data fields logic here - Please add cases for new variables
  switch (activeFieldName) {
    case "programFees":
      return getProgramFeesModalLink("program fees");
    case "seeFeesAndDisclosure":
      return getProgramFeesModalLink("See fees and disclosure");
    case "promoExpirationDate":
      return getPromotionExpirationDate();
    default:
      return " --datafield default value here-- ";
  }
};

export const getActiveDataFields = (str) => {
  const activeDataFields = str.match(/(?:%7B%7B|\{\{)(.*?)(?:}}|%7D%7D)+/g);
  return activeDataFields;
};

// programFees
export const getProgramFeesModalLink = (str) => {
  return `<a href="javascript:void(0)" class="fe-program-fees-modal-toggle">${str}</a>`;
};

/**
 *
 * @returns learnMoreOALink as string
 */
export const prepareOALearnMoreLink = (str) => {
  const windowDataObj = window?.aemfe;
  let url = "";
  let type;
  const userLoggedIn = Boolean(windowDataObj?.userLoggedIn);
  const apiBaseUrl = getApiBaseUrl();
  const threeTierChoiceCallVersion =
    windowDataObj[`maoachoice.three_tier.productChoice.version`];
  const session = windowDataObj?.context?.s || "";
  const ticket = windowDataObj?.context?.t || "";
  if (userLoggedIn) {
    url =
      apiBaseUrl +
      "maoachoice/start.act?t=" +
      ticket +
      "&s=" +
      session +
      "&br=558&targetPoint=PRODUCT_CHOICES&targetTab=OA&fromPoint=";

    //now remove https:// from the url since Unbounce already prefix the button link url with https://.
    //this value will be the dynamic replacement for button link url
    if (str) {
      return `<div class="button cmp-button--button-primary" style="padding-top:30px">
              <a class="cmp-button" href=${url}>
                  <span class="cmp-button__text">Get Started</span>
              </a>
              </div>`;
    }
    return `<a href=${url} target="_self" class="fe-learn-more-link">GET STARTED</a>`;
  } else {
    type = threeTierChoiceCallVersion;
    if (type && type === "SIDE_BY_SIDE_THREE_TIER_CHOICE_CALL") {
      url =
        apiBaseUrl +
        "app/productchoices/#/originalOaDetailed?fromPoint=MA_PUBLIC_ENROLL";
    } else {
      url =
        apiBaseUrl +
        "app/productchoices/#/oaDetailed?fromPoint=MA_PUBLIC_ENROLL";
    }
    if (str) {
      return `<div class="button cmp-button--button-primary" style="padding-top:30px">
                <a class="cmp-button" href=${url}>
                    <span class="cmp-button__text">Get Started</span>
                </a>
                </div>`;
    }
    return `<a href=${url} target="_self" class="fe-learn-more-link">GET STARTED</a>`;
  }
};

/**
 *
 * @returns learnMorePALink as string
 */
export const preparePALearnMoreLink = (str) => {
  var url =
    getApiBaseUrl() + "app/productchoices/#/paDetailedRouter?viaRouter=true";
  if (str) {
    return `<div class="button cmp-button--button-primary" style="padding-top:30px">
                  <a class="cmp-button" href=${url}>
                      <span class="cmp-button__text">Get Started</span>
                  </a>
                  </div>`;
  }
  return `<a href=${url} target="_self" class="fe-learn-more-link">GET STARTED</a>`;
};

export const getApiBaseUrl = () => {
  return document
    .querySelector("#fe-properties")
    ?.getAttribute("data-api-domain");
};

export const checkIfPaSponsored = () => {
  const windowDataObj = window?.aemfe;
  return Boolean(windowDataObj?.financialPlanningEnabled);
};

export const getProductChoiceUrl = () => {
  const windowDataObj = window?.aemfe;
  let url = "";
  let type;
  const userLoggedIn = Boolean(windowDataObj?.userLoggedIn);
  const apiBaseUrl = getApiBaseUrl();
  const choiceFlowVersionForPA =
    windowDataObj[
      `maoachoice.pilot.tokenlogin.alternatechoiceflow.test.versions`
    ];
  const choiceFlowVersionForNonPA =
    windowDataObj[`publicEnrollment.alternatechoiceflow.limit.versions`];
  const session = windowDataObj?.context?.s || "";
  const ticket = windowDataObj?.context?.t || "";
  const isMember = windowDataObj?.context.isMember;

  if (userLoggedIn) {
    if (isMember && checkIfPaSponsored()) {
      url = apiBaseUrl + "app/productchoices/#/paDetailedRouter?fromPoint=";
    } else {
      url =
        apiBaseUrl +
        "maoachoice/start.act?t=" +
        ticket +
        "&s=" +
        session +
        "&br=558&targetPoint=PRODUCT_CHOICES&showMoreInfo=false&fromPoint=";
    }
  } else {
    //user is anonymous but we know the sponsor
    if (checkIfPaSponsored()) {
      //PA Sponsor
      type = choiceFlowVersionForPA;
      if (type && type === "SIMPLE_SIDE_BY_SIDE") {
        url =
          apiBaseUrl +
          "app/productchoices/#/simpleSideBySide?fromPoint=MA_PUBLIC_ENROLL";
      } else {
        url =
          apiBaseUrl +
          "app/productchoices/#/threeTierProductChoiceRouter?fromPoint=MA_PUBLIC_ENROLL";
      }
    } else {
      type = choiceFlowVersionForNonPA;
      if (type && type === "PM_ONLY") {
        url =
          apiBaseUrl +
          "app/productchoices/#/pmDetailed?fromPoint=MA_PUBLIC_ENROLL";
      } else {
        url =
          apiBaseUrl +
          "app/productchoices/#/simpleSideBySide?fromPoint=MA_PUBLIC_ENROLL";
      }
    }
  }
  var scId = captureScIdFromUrl();
  if (scId) {
    url = url + "&s_cid=" + encodeURIComponent(scId);
  }
  //now remove https:// from the url since Unbounce already prefix the button link url with https://.
  //this value will be the dynamic replacement for button link url
  return `<a href=${url} target="_self" class="fe-learn-more-link">LEARN MORE</a>`;
};

export const getProductChoiceButton = () => {
  const windowDataObj = window?.aemfe;
  let url = "";
  let type;
  const userLoggedIn = Boolean(windowDataObj?.userLoggedIn);
  const apiBaseUrl = getApiBaseUrl();
  const choiceFlowVersionForPA =
    windowDataObj[
      `maoachoice.pilot.tokenlogin.alternatechoiceflow.test.versions`
    ];
  const choiceFlowVersionForNonPA =
    windowDataObj[`publicEnrollment.alternatechoiceflow.limit.versions`];
  const session = windowDataObj?.context?.s || "";
  const ticket = windowDataObj?.context?.t || "";
  const isMember = windowDataObj?.context.isMember;
  if (!userLoggedIn || !isMember) {
    if (userLoggedIn) {
      //      if (isMember && checkIfPaSponsored()) {
      //        url = apiBaseUrl + "app/productchoices/#/paDetailedRouter?fromPoint=";
      //      } else {
      url =
        apiBaseUrl +
        "maoachoice/start.act?t=" +
        ticket +
        "&s=" +
        session +
        "&br=558&targetPoint=PRODUCT_CHOICES&showMoreInfo=false&fromPoint=";
      //      }
    } else {
      //user is anonymous but we know the sponsor
      if (checkIfPaSponsored()) {
        //PA Sponsor
        type = choiceFlowVersionForPA;
        if (type && type === "SIMPLE_SIDE_BY_SIDE") {
          url =
            apiBaseUrl +
            "app/productchoices/#/simpleSideBySide?fromPoint=MA_PUBLIC_ENROLL";
        } else {
          url =
            apiBaseUrl +
            "app/productchoices/#/threeTierProductChoiceRouter?fromPoint=MA_PUBLIC_ENROLL";
        }
      } else {
        type = choiceFlowVersionForNonPA;
        if (type && type === "PM_ONLY") {
          url =
            apiBaseUrl +
            "app/productchoices/#/pmDetailed?fromPoint=MA_PUBLIC_ENROLL";
        } else {
          url =
            apiBaseUrl +
            "app/productchoices/#/simpleSideBySide?fromPoint=MA_PUBLIC_ENROLL";
        }
      }
    }
    var scId = captureScIdFromUrl();
    if (scId) {
      url = url + "&s_cid=" + encodeURIComponent(scId);
    }
    //now remove https:// from the url since Unbounce already prefix the button link url with https://.
    //this value will be the dynamic replacement for button link url
    return `<div class="button cmp-button--button-primary" style="padding-top:30px">
                             <a class="cmp-button" href=${url}>
                                 <span class="cmp-button__text">Get Started</span>
                             </a>
                             </div>`;
    //return `<a href=${url} target="_self" class="fe-learn-more-link">LEARN MORE</a>`;
  } else return ``;
};

export const getProductChoiceButtonProspectsOnly = () => {
  const windowDataObj = window?.aemfe;
  let url = "";
  let type;
  const userLoggedIn = Boolean(windowDataObj?.userLoggedIn);
  const apiBaseUrl = getApiBaseUrl();
  const choiceFlowVersionForPA =
    windowDataObj[
      `maoachoice.pilot.tokenlogin.alternatechoiceflow.test.versions`
    ];
  const choiceFlowVersionForNonPA =
    windowDataObj[`publicEnrollment.alternatechoiceflow.limit.versions`];
  const session = windowDataObj?.context?.s || "";
  const ticket = windowDataObj?.context?.t || "";
  // version of the product choice button that only shows for prospects (not OA users and not members).

  const context = windowDataObj?.context;
  const isMember = context?.isMember;
  if (context?.userTier && context.userTier === "PROSPECT" && !isMember) {
    if (userLoggedIn) {
      url =
        apiBaseUrl +
        "maoachoice/start.act?t=" +
        ticket +
        "&s=" +
        session +
        "&br=558&targetPoint=PRODUCT_CHOICES&showMoreInfo=false&fromPoint=";
    } else {
      //user is anonymous but we know the sponsor
      if (checkIfPaSponsored()) {
        //PA Sponsor
        type = choiceFlowVersionForPA;
        if (type && type === "SIMPLE_SIDE_BY_SIDE") {
          url =
            apiBaseUrl +
            "app/productchoices/#/simpleSideBySide?fromPoint=MA_PUBLIC_ENROLL";
        } else {
          url =
            apiBaseUrl +
            "app/productchoices/#/threeTierProductChoiceRouter?fromPoint=MA_PUBLIC_ENROLL";
        }
      } else {
        type = choiceFlowVersionForNonPA;
        if (type && type === "PM_ONLY") {
          url =
            apiBaseUrl +
            "app/productchoices/#/pmDetailed?fromPoint=MA_PUBLIC_ENROLL";
        } else {
          url =
            apiBaseUrl +
            "app/productchoices/#/simpleSideBySide?fromPoint=MA_PUBLIC_ENROLL";
        }
      }
    }
    var scId = captureScIdFromUrl();
    if (scId) {
      url = url + "&s_cid=" + encodeURIComponent(scId);
    }
    //now remove https:// from the url since Unbounce already prefix the button link url with https://.
    //this value will be the dynamic replacement for button link url
    return `<div class="button cmp-button--button-primary" style="padding-top:30px">
                             <a class="cmp-button" href=${url}>
                                 <span class="cmp-button__text">Get Started</span>
                             </a>
                             </div>`;
    //return `<a href=${url} target="_self" class="fe-learn-more-link">LEARN MORE</a>`;
  } else return ``;
};

export const getOtherWaysPromoMsgPa = () => {
  const windowDataObj = window?.aemfe;
  let otherWaysPromoMsgPa = "";
  // use key text
  let otherWaysPromoMsgPaTemp =
    windowDataObj[`maoachoice.promotionNew.otherWays.pa.promoMsg`];
  // use default if key text not set
  if (
    otherWaysPromoMsgPaTemp &&
    otherWaysPromoMsgPaTemp.indexOf(
      "maoachoice.promotionNew.otherWays.pa.promoMsg",
    ) !== -1
  ) {
    otherWaysPromoMsgPaTemp =
      "Available with a Special Offer on program fees for a limited time.";
  }
  otherWaysPromoMsgPa = otherWaysPromoMsgPaTemp;
  return otherWaysPromoMsgPa;
};

export const getDashboardLink = () => {
  const windowDataObj = window?.aemfe;
  const {
    isUserFullyAuth,
    context,
    userLoggedIn,
    isUserLightAuth,
    providerInfo,
  } = windowDataObj;
  const apiBaseUrl = getApiBaseUrl();
  let url = "";
  let linkLabel = "LOGIN TO ONLINE ADVICE";
  if (context?.userTier) {
    linkLabel =
      context?.userTier === "OA"
        ? "LOGIN TO ONLINE ADVICE"
        : context?.userTier === "MA"
          ? "MEMBER DASHBOARD"
          : linkLabel;
  }
  if (
    (isUserFullyAuth && context?.userTier && context.userTier !== "PROSPECT") ||
    isUserLightAuth === true
  ) {
    url =
      apiBaseUrl +
      "onlineadvice/start.act?t=" +
      encodeURIComponent(context.t) +
      "&s=" +
      encodeURIComponent(context.s) +
      "&removeAdviceLandingPage=true";
    url;
  } else if (userLoggedIn === true && isUserLightAuth === true) {
    url = providerInfo?.rkLoginUrl;
    url.replace(/^https?:\/\//i, "");
  }
  return url
    ? `<a href="https://www.financialengines.com/app/cx/#/overview" target="_blank" class="fe-learn-more-link">${linkLabel}</a>`
    : "";
};

export const checkIfPureProspect = () => {
  const windowDataObj = window?.aemfe;
  const { isUserFullyAuth, context } = windowDataObj;
  const apiBaseUrl = getApiBaseUrl();
  if (context?.userTier) {
    console.log("context.userTier " + context.userTier);
    if (context.userTier === "PROSPECT") {
      console.log("checkIfPureProspect: true");
      return true;
    }
  }
  return false;
};

export const getDashboardButton = () => {
  const windowDataObj = window?.aemfe;
  const {
    isUserFullyAuth,
    context,
    userLoggedIn,
    isUserLightAuth,
    providerInfo,
  } = windowDataObj;
  const apiBaseUrl = getApiBaseUrl();
  let url = "";

  let linkLabel = "LOGIN TO ONLINE ADVICE";
  if (context?.userTier) {
    if (context.userTier === "OA") {
      console.log("context.userTier: OA");
      return "";
    }
    linkLabel =
      context?.userTier === "OA"
        ? "LOGIN TO ONLINE ADVICE"
        : context?.userTier === "MA"
          ? "MEMBER DASHBOARD"
          : linkLabel;
  }
  if (
    (isUserFullyAuth && context?.userTier && context.userTier !== "PROSPECT") ||
    isUserLightAuth === true
  ) {
    url =
      apiBaseUrl +
      "onlineadvice/start.act?t=" +
      encodeURIComponent(context.t) +
      "&s=" +
      encodeURIComponent(context.s) +
      "&removeAdviceLandingPage=true";
    url;
  } else if (userLoggedIn === true && isUserLightAuth === true) {
    url = providerInfo?.rkLoginUrl;
    url.replace(/^https?:\/\//i, "");
  }
  return url
    ? `<div class="button cmp-button--button-primary" style="padding-top:30px">
                      <a class="cmp-button" href=${url}>
                          <span class="cmp-button__text">Get Started</span>
                      </a>
                      </div>`
    : //    ? `<a href="${url}" target="_blank" class="fe-learn-more-link">${linkLabel}</a>`
      "";
};

export const getLoginDashboardButton = () => {
  const windowDataObj = window?.aemfe;
  const {
    isUserFullyAuth,
    context,
    userLoggedIn,
    isUserLightAuth,
    providerInfo,
  } = windowDataObj;
  if (context?.userTier) {
    if (context.userTier === "OA") {
      console.log("getLoginDashboardButton context.userTier: OA");
      return `<div class="button cmp-button--button-primary" style="padding-top:30px">
                                       <a class="cmp-button" href="https://www.financialengines.com/app/cx/#/overview">
                                           <span class="cmp-button__text">Log in</span>
                                       </a>
                                       </div>`;
    }
  }
  return "";
};

export const getLoginLink = () => {
  const windowDataObj = window?.aemfe;
  let url = windowDataObj?.providerInfo?.rkLoginUrl;
  return `<a href="${url}" target="_blank" class="fe-learn-more-link">LOGIN</a>`;
};
export const getPmAboutFeeText = () => {
  const windowDataObj = window?.aemfe;
  const FeeAndPervalue = monthlyFeeAndPervalue(windowDataObj?.sponsoredFees);
  let pmAboutFeeText =
    "About {{ monthlyFee }} per month for each {{ perValue }} in your account";
  if (
    windowDataObj?.sponsoredFees[0]?.feeScheduleTiers[0]?.samplePeriod &&
    windowDataObj?.sponsoredFees[0]?.feeScheduleTiers[0]?.samplePeriod ==
      "Annually"
  ) {
    pmAboutFeeText = pmAboutFeeText.replace("per month", "per year");
  }
  pmAboutFeeText = pmAboutFeeText.replace(
    "{{ monthlyFee }}",
    FeeAndPervalue?.feePeriod,
  );
  pmAboutFeeText = pmAboutFeeText.replace(
    "{{ perValue }}",
    FeeAndPervalue?.sampleAum,
  );
  return pmAboutFeeText;
};
export const monthlyFeeAndPervalue = (sponsoredFees) => {
  var FeeAndPervalue = {};
  var pmFeesInfo = {};
  sponsoredFees.forEach((prgFee) => {
    if (
      prgFee &&
      prgFee.feature === "PROFESSIONAL_MANAGEMENT" &&
      prgFee.accountTypes.includes("SPONSORED") > -1 &&
      prgFee.feeScheduleTiers &&
      prgFee.feeScheduleTiers.length > 0
    ) {
      pmFeesInfo = prgFee;
      var feesInfo = pmFeesInfo.feeScheduleTiers
        .sort(function (a, b) {
          return a.feeRate > b.feeRate ? 1 : -1;
        })
        .reverse(); // var feesInfo = _.sortBy(pmFeesInfo.feeScheduleTiers, 'feeRate').reverse();
      if (feesInfo[0]) {
        FeeAndPervalue.feePeriod = formatMoney(feesInfo[0]?.sampleFeePerPeriod);
        FeeAndPervalue.sampleAum = formatMoneyNoCentsWithComma(
          feesInfo[0]?.sampleAum,
        );
      }
    }
  });
  return FeeAndPervalue;
};
export const formatMoney = (amount) => {
  if (typeof amount === "number") {
    // All money is dollars with two fractional digits.
    return "$" + amount.toFixed(2);
  }
};
export const formatMoneyNoCentsWithComma = (amount) => {
  if (typeof amount === "number") {
    // All money is dollars with 0 fractional digits. Include commas
    return "$" + Number(parseFloat(amount).toFixed(0)).toLocaleString("en");
  }
};
