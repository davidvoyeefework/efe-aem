import { getCookie, getFetch, handleLoader, fetchData, postJSONforKeys, pushToWindowObject } from "../../site/js/helper";
import {forKeysPayload} from './forkeys-payload';
export default class FePage {
    constructor() {
        document.addEventListener(
            "DOMContentLoaded",
            () => {
                window.aemfe = {};
                this.init();
            },
            { once: true }
        );
    }
    init() {
        const el = document.querySelector('.fe-load-data');
        if (!el) {
            return
        }
        handleLoader(true);
        this.getAuthenticationStatus();
        this.fetchAggregateData();
        this.getKeys();
    }
    async fetchSponsorData(userLoggedIn) {
        let pageFrameApiFlow = userLoggedIn?'wpilanding':'landing-flow';
        handleLoader(true)
        const apiHost = document.querySelector('#fe-properties')?.getAttribute('data-frame-api');//'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
        const headers = {
            'Accept': 'application/json, text/plain, */*',
            'X-Spa-Name': pageFrameApiFlow,
        };
        await fetchData(apiHost+pageFrameApiFlow, headers).then(data => {
            handleLoader(false);
            if (data) {
                data = {
                    "footer" : {
                      "sponsorName" : null,
                      "rkName" : null,
                      "aboutUsPageTitle" : "About Edelman Financial Engines",
                      "aboutUsPhotoDesc" : "Nobel Prize-winning economist Bill Sharpe co-founded Edelman Financial Engines in 1996.",
                      "aboutUsParagraph1" : "After careful consideration, your employer hired Edelman Financial Engines to provide Professional Management to participants in your retirement plan.&nbsp;Nobel Prize-winning economist Bill Sharpe co-founded Edelman Financial Engines in 1996 after spending decades helping the country's largest pension funds invest billions of dollars of retirement money. He believed that technology could make it possible to offer the same sophisticated retirement help to individuals, regardless of wealth or investment experience. Today, Bill's revolutionary vision&#8212to give everyone access to independent, high-quality investment advice&#8212is a reality.",
                      "aboutUsPreParagraph1" : "After careful consideration, your employer hired Edelman Financial Engines to provide Professional Management to participants in your retirement plan.&nbsp;",
                      "aboutUsParagraph2" : "Today Edelman Financial Engines works with more than 700 large employers (including over 140 of the FORTUNE 500 as of December 31, 2017)&nbsp;and eight of the largest 401(k) providers, including Alight Solutions, Fidelity, ING, JPMorgan, Mercer, T. Rowe Price, Vanguard and Conduent.",
                      "aboutUsPostParagraph2" : "&nbsp;and eight of the largest 401(k) providers, including Alight Solutions, Fidelity, ING, JPMorgan, Mercer, T. Rowe Price, Vanguard and Conduent",
                      "aboutUsLink" : "<a href=\"http://www.financialengines.com\" target=\"_blank\">Visit the Edelman Financial Engines Web site for more information ></a>",
                      "rkAltText" : "Edelman Financial Engines",
                      "poweredByHeaderAltText" : null,
                      "aboutProviderP1" : "",
                      "aboutProviderP2" : "",
                      "aboutProviderP3" : "",
                      "aboutProviderTitle" : null,
                      "footerLinks" : [ {
                        "href" : "https://www.edelmanfinancialengines.com/about-us/edelman-financial-engines",
                        "id" : null,
                        "name" : "About Edelman Financial Engines",
                        "target" : "window"
                      }, {
                        "href" : "legalDocLink",
                        "id" : null,
                        "name" : "Legal Information",
                        "target" : "modal"
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/FEA_PrivacyPolicy_MA.pdf",
                        "id" : null,
                        "name" : "Privacy Policy",
                        "target" : "window"
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/OnlinePrivacyStatement.pdf",
                        "id" : null,
                        "name" : "Online Privacy Statement",
                        "target" : "window"
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/Chat_Usage_terms.pdf",
                        "id" : null,
                        "name" : "Chat Usage Terms",
                        "target" : "window"
                      }, {
                        "href" : "https://pages.financialengines.com/privacy/#how-to-exercise-your-rights",
                        "id" : "doc.dnspi",
                        "name" : "Do Not Share My Personal Information",
                        "target" : "window"
                      } ],
                      "isPhotoDisplayed" : true,
                      "subadvisedPADisclosures" : [ ],
                      "legalDocsLinks" : [ {
                        "href" : "https://legaldocs.feitest.io/Master/FEA_ProfManagement_Terms.pdf",
                        "id" : "doc.ma-terms",
                        "name" : "Terms of Service",
                        "target" : null
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/FEA_PrivacyPolicy_MA.pdf",
                        "id" : "doc.ma-privacy",
                        "name" : "Privacy Policy",
                        "target" : null
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/WebsiteTermsofService.pdf",
                        "id" : "doc.webterms",
                        "name" : "Website Terms of Service",
                        "target" : null
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/SecurityStatement.pdf",
                        "id" : "doc.security",
                        "name" : "Security Statement",
                        "target" : null
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/FEA_ADV2A_B.pdf",
                        "id" : "doc.ma-part2adv",
                        "name" : "Disclosure Brochure",
                        "target" : null
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/TotalRetirementAdvice_Supplement.pdf",
                        "id" : "doc.ma-ttl-ret-adv",
                        "name" : "Advice on Outside Accounts",
                        "target" : null
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/Chat_Usage_terms.pdf",
                        "id" : "doc.chat-usage",
                        "name" : "Chat Usage Terms",
                        "target" : null
                      }, {
                        "href" : "https://legaldocs.feitest.io/Master/OnlinePrivacyStatement.pdf",
                        "id" : "doc.online-privacy",
                        "name" : "Online Privacy Statement",
                        "target" : null
                      }, {
                        "href" : "https://pages.financialengines.com/privacy/#how-to-exercise-your-rights",
                        "id" : "doc.dnspi",
                        "name" : "Do Not Share My Personal Information",
                        "target" : "window"
                      } ],
                      "copyright" : "&#169; 2023 Edelman Financial Engines, LLC. Edelman Financial Engines<sup>&#174;</sup> is a registered trademark of Edelman Financial Engines, LLC. All advisory services provided by Financial Engines Advisors L.L.C., a federally registered investment advisor. Results are not guaranteed.",
                      "patentText" : "See %1% for patent information.",
                      "patentLink" : "https://edelmanfinancialengines.com/patent-information"
                    },
                    "context" : {
                      "userId" : null,
                      "t" : null,
                      "isFeChannel" : true,
                      "rkId" : null,
                      "sponsorId" : null,
                      "isQuovoEnabled" : false,
                      "isMember" : false,
                      "isRebranding" : false,
                      "isRebrandingEfe" : true,
                      "s" : "tstx0p0mallcf8f5h0xp9"
                    },
                    "header" : {
                      "sponsorName" : null,
                      "supportPhone" : "(800) 601-5957",
                      "supportHour" : "Business days from 9 a.m. to 9 p.m. ET",
                      "supportPhoneText" : "We're here to help.{{programFee}}",
                      "supportPhonePrefix" : "Call",
                      "supportPhoneSuffix" : "",
                      "aboveHeaderOfferLink" : null,
                      "offerExpiryDate" : null,
                      "showLongPhoneNum" : false
                    }
                  };
                pushToWindowObject(data);
                const dataFromApi = new CustomEvent("messageFromfePage", {
                    messageFromParent: {
                    success: "true",
                    },
                });
                document.dispatchEvent(dataFromApi);
                handleLoader(false);
            }
        }).catch(error => {
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async fetchAggregateData() {
        const daVarsStr = getCookie('daVars');
        const daVars = daVarsStr ? JSON.parse(decodeURIComponent(daVarsStr)) : null;
        if (!daVars || !daVars.sponsorId) {
            return
        }
        let apiUrl = document.querySelector('#fe-properties')?.getAttribute('data-aggregate-api');
        let apiHost = apiUrl.replace('{Recordkeeper}', daVars.sponsorId);
        const headers = {
            'Accept': 'application/json, text/plain, */*',
        }
        await fetchData(apiHost, headers).then(data => {
            console.log(data);
            pushToWindowObject(data);
            handleLoader(false);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async getKeys() {
        let apiUrl = document.querySelector('#fe-properties')?.getAttribute('data-keys-api');
        const keys = forKeysPayload;
        await postJSONforKeys('https://www.feitest.com/api/v1/texts/forKeys', keys).then(data => {
            pushToWindowObject(data);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async getAuthenticationStatus() {
        const headers = {
            'Accept': 'application/json, text/plain, */*',
        }
        await fetchData('https://www.feitest.com/api/v1/userlogin/authenticationstatus', headers).then(data => {
            console.log(data);
            pushToWindowObject(data);
            handleLoader(false);
            const userLoggedIn = data.userLoggedIn;
            this.fetchSponsorData(userLoggedIn);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
}