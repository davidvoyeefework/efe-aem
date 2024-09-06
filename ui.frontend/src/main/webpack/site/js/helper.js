const formToJSON = (elements) =>
  [].reduce.call(
    elements,
    (data, element) => {
      if (element.type != "submit") {
        data[element.name] = element.value;
      }
      return data;
    },
    {},
  );

export { formToJSON };

export const camelToDashed = (str) => {
  return str.replace(/[A-Z]/g, (m) => "-" + m.toLowerCase());
};

export const throttle = (callback, limit) => {
  let wait = false; // Initially, we're not waiting
  return function () {
    // We return a throttled function
    if (!wait) {
      // If we're not waiting
      callback.call(); // Execute users function
      wait = true; // Prevent future invocations
      setTimeout(function () {
        // After a period of time
        wait = false; // And allow future invocations
      }, limit);
    }
  };
};

export const debounce = (func, wait) => {
  let timeout;

  return function executedFunction(...args) {
    const later = () => {
      clearTimeout(timeout);
      func(...args);
    };

    clearTimeout(timeout);
    timeout = setTimeout(later, wait);
  };
};

// The helper function to use with dangerouslySetInnerHTML inside React component
export const setInnerHtml = (htmlStr) => {
  return {
    __html: htmlStr,
  };
};
export const trapFocus = (element) => {
  var focusableEls = element.querySelectorAll(
    'a[href]:not([disabled]), button:not([disabled]), textarea:not([disabled]), input[type="text"]:not([disabled]), input[type="radio"]:not([disabled]), input[type="checkbox"]:not([disabled]), select:not([disabled])',
  );
  var firstFocusableEl = focusableEls[0];
  var lastFocusableEl = focusableEls[focusableEls.length - 1];
  var KEYCODE_TAB = 9;

  element.addEventListener("keydown", function (e) {
    var isTabPressed = e.key === "Tab" || e.keyCode === KEYCODE_TAB;

    if (!isTabPressed) {
      return;
    }

    if (e.shiftKey) {
      /* shift + tab */ if (document.activeElement === firstFocusableEl) {
        lastFocusableEl.focus();
        e.preventDefault();
      }
    } /* tab */ else {
      if (document.activeElement === lastFocusableEl) {
        firstFocusableEl.focus();
        e.preventDefault();
      }
    }
  });
};
export const getCookie = (name) => {
  if (typeof document !== "undefined") {
    var value = "; " + document.cookie;
    var parts = value.split("; " + name + "=");
    if (parts.length === 2) {
      return parts.pop().split(";").shift();
    }
  }
};

export const getFetch = async (url, headers) => {
  return fetch(url, { headers, credentials: "include" }).then((data) => {
    if (data.status === 200) {
      return data?.json();
    } else {
      console.log(data.statusText, "something went wrong");
      return false;
    }
  });
};

export const fetchData = async (url, headers) => {
  try {
    const response = await fetch(url, { headers, credentials: "include" });
    if (!response.ok) {
      throw new Error("Request failed");
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error:", error.message);
    // You can handle the error here or rethrow it to be caught elsewhere
    throw error;
  }
};

export const handleLoader = (isShow) => {
  const loaderclass = ".loader";
  if (!document.querySelector(loaderclass)) {
    return;
  }

  var loader = document.querySelector(loaderclass);
  if (isShow) {
    loader.style.display = "block";
  } else {
    loader.style.display = "none";
  }
};

const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);

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

  if (thisRef.indexOf("%7Butm_params%7D") >= 0) {
    thisRef = thisRef.replace("%7Butm_params%7D", getUTMValues());
  }
  if (thisRef.indexOf("%7Bfeid%7D") >= 0 && feid !== null) {
    thisRef = thisRef.replace("%7Bfeid%7D", "feid=" + encodeURIComponent(feid));
  }
  if (thisRef.indexOf("%7Bpoid%7D") >= 0 && poid !== null) {
    thisRef = thisRef.replace("%7Bpoid%7D", "feid=" + encodeURIComponent(poid));
  }
  if (thisRef.indexOf("%7Bvoya%7D") >= 0) {
    thisRef = thisRef.replace("%7Bvoya%7D", getVoyaValues());
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
