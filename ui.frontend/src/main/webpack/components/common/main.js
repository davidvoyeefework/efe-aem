export default class Analytics {
    constructor() {
        let linksToTarget = document.querySelector('[data-tracking-list]')?.dataset.trackingList;
        if (!linksToTarget) return;

        linksToTarget = JSON.parse(linksToTarget);
        this.linksToTarget = linksToTarget.map((str) => str.toLowerCase());
        this.handleLinksForAnalytics = document.addEventListener(
            "click",
            this.handleLinksForAnalytics.bind(this)
        );
    }
    handleLinksForAnalytics(event) {
        const region = this.findRegion(event.target);
        let linkActualText = event.target.closest("a")?.innerText;
        const getLinkHref = event.target.closest("a")?.getAttribute("href");
        if (!linkActualText && typeof linkActualText !== "string") return;
        linkActualText = linkActualText.toLowerCase()?.trim();
        if(getLinkHref?.match(/^tel\:/) || getLinkHref?.match(/^mailto\:/)) {
            window.adobeDataLayer.push({
                event: "link_click",
                web: {
                    webInteraction: {
                        name: getLinkHref,
                        region: region,
                        type: "other",
                        linkClicks: { value: 1 },
                    },
                },
            });
        }
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
