document.addEventListener("DOMContentLoaded", () => {
  const queryString = window.location.search;
  const urlParams = new URLSearchParams(queryString);
  function getCookie(name) {
    if (typeof document !== "undefined") {
      var value = "; " + document.cookie;
      var parts = value.split("; " + name + "=");
      if (parts.length === 2) {
        return parts.pop().split(";").shift();
      }
    }
  }
  var feValues = {};
  var env = {};
  var daVars = {};
  var feid = null;
  var poid = null;

  var daVarsStr = getCookie("daVars");
  if (daVarsStr) {
    try {
      daVars = JSON.parse(decodeURIComponent(daVarsStr));
    } catch (error) {
      console.log("No daVars found");
    }
  }
  if (daVars.length > 0) {
    env.da = daVars;
    feValues.env = env;
    feid = feValues.env.da?.userId;
    poid = feValues.env.da?.sponsorId;
  }

  var elements = document.getElementsByClassName("cmp-button");

  for (var i = 0; i < elements.length; i++) {
    var thisElement = elements[i];
    var thisRef = thisElement.getAttribute("href");

    if (thisRef.indexOf("{utm_params}") >= 0) {
      thisRef = thisRef.replace("{utm_params}", getUTMValues());
    }
    if (thisRef.indexOf("{feid}") >= 0 && feid !== null) {
      thisRef = thisRef.replace("{feid}", "feid=" + encodeURIComponent(feid));
    }
    if (thisRef.indexOf("{poid}") >= 0 && poid !== null) {
      thisRef = thisRef.replace("{poid}", "feid=" + encodeURIComponent(poid));
    }
    if (thisRef.indexOf("{voya}") >= 0) {
      thisRef = thisRef.replace("{voya}", getVoyaValues());
    }

    thisElement.href = thisRef;
  }

  function getUTMValues() {
    let utmParamArray = [
      "utm_source",
      "utm_medium",
      "utm_campaign",
      "utm_placement",
      "utm_device",
      "utm_term",
      "utm_content",
    ];

    // let attributeArray = ["utm_source","utm_medium","utm_campaign","utm_placement","utm_device","utm_term","utm_content",
    // "sem_account_id","sem_campaign_id","sem_ad_group_id","sem_matchtype","sem_ad_id","sem_network",
    // "sem_target_id","sem_feed_item_id","adgroup","keyword","sitelink","gclid","visitor_cid","pid","msclkid"];
    let utmArray = [];
    for (let i = 0; i < utmParamArray.length; i++) {
      if (urlParams.has(utmParamArray[i])) {
        utmArray.push(utmParamArray[i] + "=" + urlParams.get(utmParamArray[i]));
      } else if (getCookie(utmParamArray[i]) !== "") {
        utmArray.push(utmParamArray[i] + "=" + getCookie(utmParamArray[i]));
      }
    }
    var utmList = "";
    for (let j = 0; j < utmArray.length; j++) {
      utmList = utmList + utmArray[j] + "&";
    }
    utmList = utmList.trim("&");
    return utmList;
  }

  function getVoyaValues() {
    let voyaParamArray = [
      "clickthrough",
      "pagecode",
      "treatment",
      "alertDecision",
      "lastAlert",
      "explatWPI",
      "destination",
      "placement",
      "nextGenWPI",
      "service",
      "rkUrl",
      "shownDisclaimer",
      "deeplink",
    ];
    let voyaArray = [];
    for (let i = 0; i < voyaParamArray.length; i++) {
      if (urlParams.has(voyaParamArray[i])) {
        voyaArray.push(
          voyaParamArray[i] + "=" + urlParams.get(voyaParamArray[i]),
        );
      }
    }
    var voyaList = "";
    for (let j = 0; j < voyaArray.length; j++) {
      voyaList = voyaList + voyaArray[j] + "&";
    }
    voyaList = voyaList.trim("&");
    return voyaList;
  }
});
