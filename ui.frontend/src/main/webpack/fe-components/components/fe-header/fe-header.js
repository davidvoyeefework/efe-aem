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
            // this.getDataFieldIds();
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
                if(data[item[Object.keys(item)]]) {
                    ele.innerHTML = data[item[Object.keys(item)]];
                } else {
                    try {
                        ele.innerHTML = eval('data.' + item[Object?.keys(item)]);
                    } catch (e) {
                        console.log('invalid key', Object.keys(item));
                    }
                }
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