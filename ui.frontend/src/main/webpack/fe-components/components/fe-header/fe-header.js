import * as utility from  "../../site/js/utility";
export default class FeHeader {
    constructor() {
        document.addEventListener("messageFromfePage", (e) =>{
            this.attributeParameterElem = document.querySelector('#fe-properties');
            this.init();
        });
    }
    init() {
        if(window.aemfe.header) {
            this.changeHeaderValues();
        }
    }
    changeHeaderValues() {
        const data = window.aemfe;
        const headerDataVariables =  this.attributeParameterElem?.getAttribute('data-variables');
        JSON.parse(headerDataVariables)?.forEach((item) => {
            if (Object.keys(item).length === 0) {
                return
            }
            const elems = document.querySelectorAll('.' + Object.keys(item));
            elems?.forEach((ele) => {
                ele.classList.remove("sponsor-value-hide");
                // Two approaches to replace variables
                const activeVariableKey  = Object?.keys(item)?.length ? Object.keys(item)[0] : ""; // "SUPPORT_PHONE"
                const activeKeyValue = item ? item[activeVariableKey] : ""; // header.supportPhone or custom
                const CUSTOM_VARIABLE_VALUE = "custom";
                // for custom scenario - manipulation before replacing variable
                if(activeKeyValue === CUSTOM_VARIABLE_VALUE) {
                    ele.innerHTML = this.calculateCustomVariable(activeVariableKey, activeKeyValue);
                } else {
                    // Dynamic variables which have valid refrence in window obj data
                    // get from window obj as per specified activeKeyValue
                    let requiredReplaceableValue  = "";

                    // direct replacement
                    if(data[activeKeyValue] !== undefined) {
                        requiredReplaceableValue = data[activeKeyValue];
                    } else {
                        try {
                            requiredReplaceableValue = eval(`data.${activeKeyValue}`)? eval(`data.${activeKeyValue}`) : "";
                        } catch (e) {
                            console.log('invalid key', activeVariableKey);
                        }
                    }
                    ele.innerHTML = requiredReplaceableValue;
                }
            })
        })
        document.querySelector('.fe-modal-toggle-fe-program-fees')?.addEventListener('click', (e)=>{
            e.preventDefault();
            document.querySelector('.fe-aem-form .fe-modal-toggle-fe-program-fees')?.click();
        });
        var sponsorLogo = document.querySelector(".sponsor-logo .cmp-image");
        if (sponsorLogo && data.context.isFeChannel && data.context.sponsorId) {
            var logoFileName = data.context.sponsorId;
            var logoPath = this.attributeParameterElem?.getAttribute('data-sponsor-logo-path')
            var logo = logoPath.replace("{sponsorid}", logoFileName);
            var sponsorLogoEl = `<img src="`+ logo + `" loading="lazy" class="cmp-image__image" itemprop="contentUrl" alt=" " title="Logo">`;
            sponsorLogo.insertAdjacentHTML('beforeend', sponsorLogoEl);
        }
    }
    calculateCustomVariable(key) {
        switch(key) {
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
            case "learnMorePALink":
                return utility.preparePALearnMoreLink();
            case "product_choice_url":
                return utility.getProductChoiceUrl();
            case "otherWaysPromoMsgPa":
                return utility.getOtherWaysPromoMsgPa();
            case "dashboardLink":
                return utility.getDashboardLink();
            case "pmAboutFeeText":
                return utility.getPmAboutFeeText();
            default:
                return ""

        }
    }

}