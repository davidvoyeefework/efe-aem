export default class FeMembershipDashboardButton {
    constructor() {
        document.addEventListener("messageFromfePage", (e) => {
            this.attributeParameterElem = document.querySelectorAll('.fe-membership-dashboard-button');
            this.envUrl = document.querySelector('#fe-properties')?.getAttribute('data-api-domain');
            this.windowObject = window.aemfe;
            this.init();
        });
    }
    init() {
        if (this.attributeParameterElem) {
            this.buildFeMembershipDashboardButton(this.attributeParameterElem);
        }
    }
    buildFeMembershipDashboardButton(containerEl) {
        let buttonHeading = '';
        let oaButtonTitle;
        if (
            (this.windowObject?.isUserFullyAuth === true &&
                this.windowObject?.context?.userTier &&
                this.windowObject?.context?.userTier !== "PROSPECT") ||
            this.windowObject?.isUserLightAuth === true
        ) {
            buttonHeading = "Already using Online Advice?";

            if (this.windowObject?.context?.isMember) {
                oaButtonTitle = "MEMBER DASHBOARD";
                buttonHeading = "Already using Professional Management?";
            }
            this.homePageUrl =
                this.envUrl +
                "onlineadvice/start.act?t=" +
                encodeURIComponent(this.windowObject?.context?.t) +
                "&s=" +
                encodeURIComponent(this.windowObject?.context?.s) +
                "&removeAdviceLandingPage=true";
                containerEl.forEach(ele => {
                var buttonEl = document.createElement("a");
                buttonEl.classList.add("fe-form__button");
                buttonEl.classList.add("fe-form__button--dashboard");
                buttonEl.setAttribute("href", this.homePageUrl);
                buttonEl.innerText = oaButtonTitle;

                var buttonHeadingEl = document.createElement("p");
                buttonHeadingEl.classList.add("fe-dashboard-button__heading");
                buttonHeadingEl.innerText = buttonHeading;
                ele.appendChild(buttonHeadingEl);
                ele.appendChild(buttonEl);
            });
        }
    }
}