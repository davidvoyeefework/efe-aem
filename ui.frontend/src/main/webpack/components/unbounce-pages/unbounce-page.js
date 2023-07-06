export default class UnbouncePage {
    constructor() {
        // const target = new EventTarget();
        // target.addEventListener('sponsorTargetEvent', e=>this.fetchSponsorData(e.detail.name));
        // target.dispatchEvent(new CustomEvent("sponsorTargetEvent", {
        //     detail: {
        //       name: "Boeing",
        //     },
        //   }));
        window.addEventListener("load", (event) => {
            this.fetchSponsorData();
        })
    }
    async fetchSponsorData() {
        console.log('loading');
        //const apiHost = window.location.hostname+':3000';
        // https://www.feitest.com/api/v1/pageframe/?namespace=landing-flow
        const apiHost = document.querySelector('#unbounce-properties').getAttribute('data-frame-api');//'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
        const response = await fetch(`${apiHost}`,{
            method: 'GET',
            mod: "no-cors",
            credentials: "include",
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'X-Spa-Name':'landing-flow',
            },
        }).catch((err)=>console.log(err));

        const sponsorData = await response?.json();
        console.log('loading complated');
        this.changeHeaderValues(sponsorData?.header);
        console.log(sponsorData);
    }
    changeHeaderValues(data) {
        document.querySelector('.sponsor-header').classList.add('sponsor--'+data?.sponsorName)
        const headerDataVariables = document.querySelector('#unbounce-properties').getAttribute('data-variables');
        JSON.parse(headerDataVariables).forEach((item)=>{
            const elem = document.querySelector('.'+Object.keys(item))
            if(elem) {
                elem.classList.remove("sponsor-value-hide");
                elem.innerHTML = data[Object.keys(item)];
            }
        })
        let supportPhoneText = document.querySelector('#efe-global-nav-header .cmp-button__text');
        let supportPhoneLink = document.querySelector('#efe-global-nav-header .button a');
        //let supportPhoneSuffix = document.querySelector('#efe-global-nav-header .cmp-button__text');
        if(data) {
            supportPhoneLink.setAttribute("href", 'tel:'+data?.supportPhone);
            supportPhoneText.innerHTML = data?.supportHour+'<br>'+data?.supportPhonePrefix+' '+data?.supportPhone+'<br>'+data?.supportPhoneSuffix;
        }
    }
}