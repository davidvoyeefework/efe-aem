import { captureScIdFromUrl } from "./helper";


export const availableThroughText = (key) => {
    const windowDataObj = window.aemfe;
    let availableThroughText = windowDataObj['publicEnrollment.products.option.pm.availableThrough'];
    if(availableThroughText) {
        availableThroughText = availableThroughText.replace(
            "{{ sponsor }}",
            windowDataObj?.preferredName
          );
        availableThroughText = availableThroughText.replace(
            "{{ industryPercent }}",
            lowerThanIndustryPercentValue()
          );
          availableThroughText = availableThroughText.replace(
            "{{ feePercent }}",
            (windowDataObj?.sponsoredFees[0]?.feeScheduleTiers[0].feeRate * 0.01).toPrecision(2) + "%"
          );
    }
    if (availableThroughText.slice(-2) === "**") {
        availableThroughText = availableThroughText.slice(0, -2);
      }
    return availableThroughText;
}
export const lowerThanIndustryPercentValue = ()=>{
    return window.aemfe?.sponsoredFees[0]?.lowerThanIndustryPercentValue;
}


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
  const promotionBannerFeeTextPaSponsor = windowDataObj[`maoachoice.promotionNew.banner.feeText.pa`] || "";
  
   // set data that is dependent on isPaSponsor flag
   if (checkIfPaSponsored()) {
    // use banner fee text key if it returns a value
    if (
      promotionBannerFeeTextPaSponsor &&
      promotionBannerFeeTextPaSponsor.indexOf(
        "windowDataObj.maoachoice.promotionNew.banner.feeText.pa"
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
    promotionBannerFeeText = windowDataObj[`maoachoice.promotionNew.banner.feeText`] || "";
    promotionBannerFeeTextLink = windowDataObj[`maoachoice.promotionNew.banner.feeTextLink`] ||  "";
  }

  // before final return check for data fields if any
  if(getActiveDataFields(promotionBannerFeeText)?.length) { //saasds {{asdasd}} sadasd {{wsqada}}
    promotionBannerFeeText = getDataFieldsWithValueInStr(promotionBannerFeeText)
  }

  if(getActiveDataFields(promotionBannerFeeTextLink)?.length) {
    promotionBannerFeeTextLink = getDataFieldsWithValueInStr(promotionBannerFeeTextLink)
  }

  return {
    promotionBannerFeeText,
    promotionBannerFeeTextLink
  }
  
}




export const PROMOTION_ANNOUNCED_END_DATE = (key)=>{
    const windowDataObj = window?.aemfe;
    let value = windowDataObj?.promotion?.announcedDeadlineDate || "";
    let announcedDeadlineDate = value;
    let PROMOTION_ANNOUNCED_END_DATE;
    if (announcedDeadlineDate != null) {
        var announcedDeadlineDateArray = announcedDeadlineDate.split(
          "-"
        );
        if (
          announcedDeadlineDateArray.length > 0 &&
          Number(announcedDeadlineDateArray[1]) >= 3 &&
          Number(announcedDeadlineDateArray[1]) <= 7
        ) {
            PROMOTION_ANNOUNCED_END_DATE = new Date(
            announcedDeadlineDate
          ).toLocaleString("en-us", {
            month: "long",
            day: "numeric",
            year: "numeric"
          });
        } else {
            PROMOTION_ANNOUNCED_END_DATE = new Date(
            announcedDeadlineDate
          )
            .toLocaleString("en-us", {
              month: "short",
              day: "numeric",
              year: "numeric"
            })
            .replace(" ", ". ");
        }
    }
    return PROMOTION_ANNOUNCED_END_DATE;
}


// get PROMOTION_EXPIRATION_DATE 
export const getPromotionExpirationDate = () => {
  const windowDataObj = window?.aemfe;
  let expiryDateRefvalue = windowDataObj?.promotion?.expirationDate || "";
  let promotionExpirationDate = "";

    if (expiryDateRefvalue != null) {
      let expirationDateArray = expiryDateRefvalue.split(
        ""-""
      );
      if (
        expirationDateArray.length > 0 &&
        Number(expirationDateArray[1]) >= 3 &&
        Number(expirationDateArray[1]) <= 7
      ) {
        promotionExpirationDate = new Date(
          expiryDateRefvalue
        ).toLocaleString("en-us", {
          month: "long",
          day: "numeric",
          year: "numeric"
        });
      } else {
        promotionExpirationDate = new Date(
          expiryDateRefvalue
        )
          .toLocaleString("en-us", {
            month: "short",
            day: "numeric",
            year: "numeric"
          })
          .replace(" ", ". ");
      }
    }
  return promotionExpirationDate;
}

//promoDurationMonthsNumeric
export const getPromoDurationMonthsNumeric = () => {
  const windowDataObj = window?.aemfe;
  let promoDurationMonthsNumericRef = windowDataObj?.promotion?.offerDetails || "";
  let promoDurationMonthsNumeric = "";

  if (promoDurationMonthsNumericRef) {
    if (promoDurationMonthsNumericRef === "3 mo Fee Waiver") {
      promoDurationMonthsNumeric = "3";
    } else if (
      promoDurationMonthsNumericRef === "6 mo Fee Waiver"
    ) {
      promoDurationMonthsNumeric = "6";
    }
  }
  return promoDurationMonthsNumeric;
}


/**
 * returns a final replaced string which has active values for data fields
 */
export const getDataFieldsWithValueInStr  = (str) => {

  const dataFieldArr = getActiveDataFields(str); // ["{{wdds}}", {{sadada}}, "{{asdadas}}"]
  let replacedStr = str;
  if(dataFieldArr?.length) {
    dataFieldArr.forEach((item) => {
      const activeFieldName = item.replace("{{", "")
                              .replace("%7B%7B", "")
                              .replace("}}", "")
                              .replace("%7D%7D", "");                 
      replacedStr = replacedStr.replace(
        item,
        getDataFieldValue(activeFieldName) // programFees
      );
    });
  }
  return replacedStr

}

const getDataFieldValue = (activeFieldName) => {
  // all data fields logic here - Please add cases for new variables     
  switch(activeFieldName) {
    case "programFees":
      return getProgramFeesModalLink("program fees");
    case "seeFeesAndDisclosure":
      return getProgramFeesModalLink("See fees and disclosure");
    case "promoExpirationDate":
      return getPromotionExpirationDate();
    default:
      return " --datafield default value here-- "

  }
}


export const getActiveDataFields = (str) => {
  const activeDataFields = str.match(
    /(?:%7B%7B|\{\{)(.*?)(?:}}|%7D%7D)+/g
  );
  return activeDataFields;
}




// programFees
export const getProgramFeesModalLink = (str) => {
  return `<a href="#" target="_blank" class="fe-modal-toggle-fe-program-fees">${str}</a>`
}


/**
 * 
 * @returns learnMoreOALink as string
 */
export const prepareOALearnMoreLink = () => {
  const windowDataObj = window?.aemfe;
  let url = "";
  let type;
  const userLoggedIn = Boolean(windowDataObj?.userLoggedIn);
  const apiBaseUrl = getApiBaseUrl();
  const threeTierChoiceCallVersion =  windowDataObj[`maoachoice.three_tier.productChoice.version`];
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
    return `<a href=${url.substring(8, url.length)} target="_self" class="fe-learn-more-link">Learn More</a>`;
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
    return `<a href=${url.substring(8, url.length)} target="_self" class="fe-learn-more-link">Learn More</a>`;
  }
}

/**
 * 
 * @returns learnMorePALink as string
 */
export const preparePALearnMoreLink = () => {
  var url =
   getApiBaseUrl() +
    "app/productchoices/#/paDetailedRouter?viaRouter=true";

  return url.substring(8, url.length);
}


export const getApiBaseUrl  = () =>{
  return  document.querySelector('#fe-properties')?.getAttribute('data-api-domain');
}

export const checkIfPaSponsored = () => {
  const windowDataObj = window?.aemfe;
  return Boolean(windowDataObj?.financialPlanningEnabled)
}

export const getProductChoiceUrl = () => {
  const windowDataObj = window?.aemfe;
  let url = "";
  let type;
  const userLoggedIn = Boolean(windowDataObj?.userLoggedIn);
  const apiBaseUrl = getApiBaseUrl();
  const choiceFlowVersionForPA = windowDataObj[`maoachoice.pilot.tokenlogin.alternatechoiceflow.test.versions`];
  const choiceFlowVersionForNonPA = windowDataObj[`publicEnrollment.alternatechoiceflow.limit.versions`];
  const session = windowDataObj?.context?.s || "";
  const ticket = windowDataObj?.context?.t || "";
  const isMember = windowDataObj?.context.isMember;

    if (userLoggedIn) { 
      if (isMember && checkIfPaSponsored()) { 
        url =
          apiBaseUrl +
          "app/productchoices/#/paDetailedRouter?fromPoint=";
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
    return url.substring(8, url.length);
}

export const getOtherWaysPromoMsgPa = () => {
  const windowDataObj = window?.aemfe;
  let otherWaysPromoMsgPa = "";
    // use key text
    let otherWaysPromoMsgPaTemp = windowDataObj[`maoachoice.promotionNew.otherWays.pa.promoMsg`];
    // use default if key text not set
    if (
      otherWaysPromoMsgPaTemp &&
      otherWaysPromoMsgPaTemp.indexOf(
        "maoachoice.promotionNew.otherWays.pa.promoMsg"
      ) !== -1
    ) {
      otherWaysPromoMsgPaTemp =
        "Available with a Special Offer on program fees for a limited time.";
    }
    otherWaysPromoMsgPa = otherWaysPromoMsgPaTemp;
    return otherWaysPromoMsgPa
}

export const getDashboardLink = () => {
  const windowDataObj = window?.aemfe;
  const {isUserFullyAuth, context, userLoggedIn, isUserLightAuth, providerInfo} = windowDataObj; 
  const apiBaseUrl = getApiBaseUrl();
  let url = "";
    if (
      isUserFullyAuth &&
      context?.userTier &&
      context.userTier !== "PROSPECT"
    ) {
      url =
        apiBaseUrl +
        "onlineadvice/start.act?t=" +
        encodeURIComponent(context.t) +
        "&s=" +
        encodeURIComponent(context.s) +
        "&removeAdviceLandingPage=true";
       url.substring(8, url.length);
    } else if (
      userLoggedIn === true &&
      isUserLightAuth === true
    ) {
      url = providerInfo?.rkLoginUrl;
       url.replace(/^https?:\/\//i, "");
    } 
    return `<a href="${url}" target="_blank" >${url}</a>`;
}

export const getPmAboutFeeText = () => {
  const windowDataObj = window?.aemfe;
  const FeeAndPervalue = monthlyFeeAndPervalue(windowDataObj?.sponsoredFees);
  let pmAboutFeeText =
      "About {{ monthlyFee }} per month for each {{ perValue }} in your account";
    if (
      windowDataObj?.sponsoredFees[0]?.feeScheduleTiers[0]?.samplePeriod &&
      windowDataObj?.sponsoredFees[0]?.feeScheduleTiers[0]?.samplePeriod == "Annually"
    ) {
      pmAboutFeeText = pmAboutFeeText.replace("per month", "per year");
    }
    pmAboutFeeText = pmAboutFeeText.replace(
      "{{ monthlyFee }}",
      FeeAndPervalue?.feePeriod
    );
    pmAboutFeeText = pmAboutFeeText.replace(
      "{{ perValue }}",
      FeeAndPervalue?.sampleAum
    );
  return pmAboutFeeText
}
export const monthlyFeeAndPervalue = (sponsoredFees)=> {
    var FeeAndPervalue = {};
    var pmFeesInfo = {};
    sponsoredFees.forEach((prgFee)=> {
        if (prgFee && prgFee.feature === "PROFESSIONAL_MANAGEMENT" && prgFee.accountTypes.includes("SPONSORED") > -1 && prgFee.feeScheduleTiers && prgFee.feeScheduleTiers.length > 0) {
            pmFeesInfo = prgFee;
            var feesInfo = pmFeesInfo.feeScheduleTiers.sort(function (a, b) {
              return a.feeRate > b.feeRate ? 1 : -1;
            }).reverse(); // var feesInfo = _.sortBy(pmFeesInfo.feeScheduleTiers, 'feeRate').reverse();
            if (feesInfo[0]) {
                FeeAndPervalue.feePeriod = formatMoney(feesInfo[0]?.sampleFeePerPeriod);
                FeeAndPervalue.sampleAum = formatMoneyNoCentsWithComma(feesInfo[0]?.sampleAum);
            }
        }
    });
    return FeeAndPervalue;
}
export const formatMoney = (amount)=> {
    if (typeof amount === "number") {
        // All money is dollars with two fractional digits.
        return "$" + amount.toFixed(2);
      }
}
export const formatMoneyNoCentsWithComma = (amount)=>{
    if (typeof amount === "number") {
        // All money is dollars with 0 fractional digits. Include commas
        return Number(parseFloat(amount).toFixed(0)).toLocaleString("en");
      }
}



