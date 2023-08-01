export default class FeHeader {
    constructor() {
        this.attributeParameterElem = document.querySelector('#fe-properties');
        this.init();
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
                //ele.innerHTML = eval('data.' + item[Object.keys(item)]);//we need this if data attribute coming data.a....
                let ackey = item[Object.keys(item)].split('.')
                ele.innerHTML = data[ackey[0]][ackey[1]];
            })
        })

        var sponsorLogo = document.querySelector(".sponsor-logo .cmp-image");
        if (sponsorLogo && data.context.isFeChannel && data.context.sponsorId) {
            var logoFileName = data.context.sponsorId;
            var logoPath = this.attributeParameterElem?.getAttribute('data-sponsor-logo-path')
            var logo = logoPath.replace("{sponsorid}", logoFileName);
            var sponsorLogoEl = `<img src="`+ logo + `" loading="lazy" class="cmp-image__image" itemprop="contentUrl" alt=" " title="Logo">`;
            sponsorLogo.insertAdjacentHTML('beforeend', sponsorLogoEl);
        }
    }
}