export default class FeHeader {
    constructor() {
        this.init();
    }
    init() {
        if(window.aemfe.header) {
            this.changeHeaderValues();
        }
    }
    changeHeaderValues() {
        const data = window.aemfe;
        const headerDataVariables = document.querySelector('#fe-properties')?.getAttribute('data-variables');
        JSON.parse(headerDataVariables)?.forEach((item) => {

            if (Object.keys(item).length === 0) {
                return
            }
            const elems = document.querySelectorAll('.' + Object.keys(item));
            elems?.forEach((ele) => {
                ele.classList.remove("sponsor-value-hide");
                ele.innerHTML = eval('data.' + item[Object.keys(item)]);
            })
        })

        var sponsorLogo = document.querySelector(".sponsor-logo .cmp-image");
        if (sponsorLogo && data.context.isFeChannel && data.context.sponsorId) {
            var logoFileName = data.context.sponsorId;
            var logo = "/content/dam/fe/logos/sponsors/" + logoFileName + "-small.png";
            var sponsorLogoEl = `<a class="cmp-image__link" target="#">
                                    <img src="`+ logo + `" loading="lazy" class="cmp-image__image" itemprop="contentUrl" alt=" " title="Logo">
                                </a>`;


            sponsorLogo.insertAdjacentHTML('beforeend', sponsorLogoEl);
        }
    }
}