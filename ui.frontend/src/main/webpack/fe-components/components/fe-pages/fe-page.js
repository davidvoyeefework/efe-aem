import {
  getCookie,
  handleLoader,
  fetchData,
  postJSONforKeys,
  pushToWindowObject,
} from "../../site/js/helper";
import { forKeysPayload } from "./forkeys-payload";

//removed getFetch from import to resolve linting error - 2023/12/05 JR

export default class FePage {
  constructor() {
    // FPID Code
    console.log("Setting alloy variables.");
    const displayPropEvent = new CustomEvent("displayPropRetrieved");
    window.__alloyMonitors = window.__alloyMonitors || [];
    window.fpidCompleted = false;
    window.__alloyMonitors.push({
      onBeforeNetworkRequest(data) {
        if (
          window.fpidCompleted &&
          data.payload.events[0].xdm.eventType == "displayProp"
        ) {
          window.DisplayPropReqID = data.requestId;
          console.log(data);
        }
      },
      onNetworkResponse(data) {
        if (
          window.fpidCompleted &&
          window.DisplayPropReqID &&
          data.requestId == window.DisplayPropReqID
        ) {
          window.DisplayPropReqID = null;
          console.log(data);
          document.dispatchEvent(displayPropEvent);
          console.log("Display Properties Retrieved");
        }
      },
    });
    console.log("Registering event listeners");
    // Trying to keep this in the head code to see if that triggers correctly. 2023/12/05 JR
    // document.addEventListener("displayPropRetrieved", function () {});
    // document.addEventListener("displayHeaderReplaced", function () {});
    document.addEventListener("fpidComplete", function () {
      console.log(
        "fpidComplete event triggered.  Calling personalization request.",
      );
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
    });
    console.log(
      "Custom events created. Beginning check for FPID library and Adobe datalayer",
    );

    // End FPID

    document.addEventListener(
      "DOMContentLoaded",
      () => {
        window.aemfe = {};
        window.aemform = {};
        this.init();
      },
      { once: true },
    );
  }
  init() {
    const el = document.querySelector(".fe-load-data");
    if (!el) {
      return;
    }
    handleLoader(true);
    this.getAuthenticationStatus();
    this.getKeys();
  }

  async checkFPIDReadyState() {
    if (!window.adobeDataLayer || !window.efeAdobeWebSdkWrapperModule) {
      console.log(
        "Missing required FPID objects, setting interval for re-check.",
      );
      setTimeout(this.checkFPIDReadyState(), 50);
    } else {
      console.log("Required FPID objects found, initializing library.");
      const EfeAdobeWebSdkWrapper =
        window.efeAdobeWebSdkWrapperModule.EfeAdobeWebSdkWrapper;
      const efeAdobeWebSdk =
        EfeAdobeWebSdkWrapper && new EfeAdobeWebSdkWrapper();
      efeAdobeWebSdk.initialize();
      console.log("FPID Library Init Called");
    }
  }

  async fetchSponsorData(userLoggedIn) {
    let pageFrameApiFlow = userLoggedIn ? "wpilanding" : "landing-flow";
    handleLoader(true);
    const apiHost = document
      .querySelector("#fe-properties")
      ?.getAttribute("data-frame-api"); //'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
    const headers = {
      Accept: "application/json, text/plain, */*",
      "X-Spa-Name": pageFrameApiFlow,
    };
    await fetchData(apiHost + pageFrameApiFlow, headers)
      .then((data) => {
        if (data) {
          if (data?.context?.isFeChannel) {
            document.querySelector("body").classList.add("fe-direct-sponsor");
          } else {
            document
              .querySelector("body")
              .classList.add("fe-subadvised-sponsor");
          }

          document
            .querySelector("body")
            .classList.add("theme-" + daVars.providerId);

          pushToWindowObject(data);
          window.aemform.pageFrameData = data;
          this.fetchAggregateData();
        }
      })
      .catch((error) => {
        console.error("An error occurred:", error);
        handleLoader(false);
      });
  }
  async fetchAggregateData() {
    const daVarsStr = getCookie("daVars");
    const daVars = daVarsStr ? JSON.parse(decodeURIComponent(daVarsStr)) : null;
    if (!daVars || !daVars.sponsorId) {
      handleLoader(false);
      return;
    }
    let apiUrl = document
      .querySelector("#fe-properties")
      ?.getAttribute("data-aggregate-api");
    let apiHost = apiUrl.replace("{Recordkeeper}", daVars.sponsorId);
    const headers = {
      Accept: "application/json, text/plain, */*",
    };
    await fetchData(apiHost, headers)
      .then((data) => {
        pushToWindowObject(data);
        window.aemform.planownerData = data;
        handleLoader(false);
        const dataFromApi = new CustomEvent("messageFromfePage", {
          messageFromParent: {
            success: "true",
          },
        });
        document.dispatchEvent(dataFromApi);
      })
      .catch((error) => {
        // Handle any errors that occurred during the fetch request
        console.error("An error occurred:", error);
        handleLoader(false);
      });
  }
  async getKeys() {
    let apiUrl = document
      .querySelector("#fe-properties")
      ?.getAttribute("data-keys-api");
    const keys = forKeysPayload;
    //https://www.feitest.com/api/v1/texts/forKeys
    await postJSONforKeys(apiUrl, keys)
      .then((data) => {
        pushToWindowObject(data);
        window.aemform.keyText = data;
      })
      .catch((error) => {
        // Handle any errors that occurred during the fetch request
        console.error("An error occurred:", error);
        handleLoader(false);
      });
  }
  async getAuthenticationStatus() {
    let apiUrl = document
      .querySelector("#fe-properties")
      ?.getAttribute("data-authenticate-api");
    const headers = {
      Accept: "application/json, text/plain, */*",
    };
    await fetchData(apiUrl, headers)
      .then((data) => {
        pushToWindowObject(data);
        window.aemform.userAuthStatus = data;
        const userLoggedIn = data.userLoggedIn;
        this.fetchSponsorData(userLoggedIn);
      })
      .catch((error) => {
        // Handle any errors that occurred during the fetch request
        console.error("An error occurred:", error);
        handleLoader(false);
      });
  }
}
