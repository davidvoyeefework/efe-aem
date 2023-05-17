export default class Analytics {
    constructor() {
        // TODO: Change to get the array dynamically
        const linksToTarget = [
            "connect with a Planner",
            "Breadcrumb",
            "Level 2",
            "(833) PLAN-EFE",
            "Call TO Action",
            "Do Not Share My Personal Information",
        ];
        this.linksToTarget = linksToTarget.map((str) => str.toLowerCase());
        this.handleLinksForAnalytics = document.addEventListener(
            "click",
            this.handleLinksForAnalytics.bind(this)
        );
    }

    handleLinksForAnalytics(event) {
        const region = this.findRegion(event.target);
        let linkActualText = event.target.closest("a")?.innerText;
        if (!linkActualText && typeof linkActualText !== "string") return;
        linkActualText = linkActualText.toLowerCase()?.trim();

        if (this.linksToTarget.includes(linkActualText)) {
            window.adobeDataLayer.push({
                event: "link_click",
                web: {
                    webInteraction: {
                        name: linkActualText,
                        region: region,
                        type: "other",
                        URL: window.location.href,
                        linkClicks: { value: 1 },
                    },
                },
            });
        }
    }

    findRegion(target) {
        const isHeader = target.closest(".header");
        const isFooter = target.closest("footer");

        if (isHeader) {
            return "header";
        }

        if (isFooter) {
            return "footer";
        }

        return "body";
    }
}
