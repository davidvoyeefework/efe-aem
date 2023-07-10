import A11yDialog from 'a11y-dialog'
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
           if(!document.querySelector('.sponsor-header')) {
            return 
           }
            this.fetchSponsorData();
            this.fetchAggregateData();
        })
    }
    async fetchSponsorData() {
        console.log('loading');
        const apiHost = document.querySelector('#unbounce-properties')?.getAttribute('data-frame-api');//'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
        const response = await fetch(`${apiHost}`,{
            method: 'GET',
            mod: "no-cors",
            credentials: "include",
            headers: {
                'Accept': 'application/json, text/plain, */*',
                'X-Spa-Name':'landing-flow',
            },
        });
        const sponsorData =  response;
        if(sponsorData.status === 200) {
            const Data = await sponsorData?.json();
            console.log('loading complated');
            this.changeHeaderValues(Data);
            this.changeFooterValues(Data);
            console.log(Data);
        } else {
            console.log(sponsorData.statusText,"something went wrong")
        }
    }
    async fetchAggregateData() {
        console.log('loading');
        const apiHost = document.querySelector('#unbounce-properties')?.getAttribute('data-aggregate-api');//'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
        const response = await fetch(`${apiHost}`,{
            method: 'GET',
            mod: "no-cors",
            credentials: "include",
            headers: {
                'Accept': 'application/json, text/plain, */*',
            },
        });
        const sponsorData =  response;
        if(sponsorData.status === 200) {
            const Data = await sponsorData?.json();
            console.log('loading complated');
            console.log(Data);
        } else {
            console.log(sponsorData.statusText,"something went wrong")
        }
    }
    changeHeaderValues(data) {
        document.querySelector('.sponsor-header')?.classList.add('sponsor--'+data?.header?.sponsorName)
        const headerDataVariables = document.querySelector('#unbounce-properties')?.getAttribute('data-variables');
        JSON.parse(headerDataVariables).forEach((item)=>{
            const elems = document.querySelectorAll('.'+Object.keys(item));
            elems?.forEach((ele)=>{
                ele.classList.remove("sponsor-value-hide");
                ele.innerHTML = eval('data.'+item[Object.keys(item)]);
            })
        })
    }
    changeFooterValues(data) {
        const litUlElement = document.querySelector('.unbounce-footer-links .cmp-list');
        if(!litUlElement) {
            return
        }
        //litUlElement.innerHTML = " ";
        data?.footer?.footerLinks.forEach((item)=>{
            let liElem = document.createElement('li');
            liElem.classList.add('cmp-list__item')
            if(item.target !== "modal") {
                liElem.innerHTML = `<a href="${item.href}" target="${item.target}" class="cmp-list__item-link">
                <span class="cmp-list__item-title">${item.name}</span></a>`;
            } else {
                liElem.innerHTML = `<a href="javascript:void(0)" class="cmp-list__item-link unbounce-legal-doc-modal-btn">
                <span class="cmp-list__item-title">${item.name}</span></a>`;
            }
            litUlElement.appendChild(liElem);
        });
            const dialogEl = document.querySelector('.unbounce-footer-modal');
            const dialog = new A11yDialog(dialogEl);
            document.querySelector('.unbounce-legal-doc-modal-btn').addEventListener("click", (e)=>{
                dialog.show();
                const modalElem = dialogEl.querySelector('.cmp-modal__content');
                modalElem.innerHTML = " ";
                const modalElemBody = document.createElement('div');
                modalElem.appendChild(modalElemBody);
                const title = `<h4 class="legal-doc-title">${e.target.innerText}</h4>`;
                modalElemBody.innerHTML = title;
                data?.footer?.legalDocsLinks.forEach((link)=>{
                    let linkElem = document.createElement('p');
                    linkElem.innerHTML = `<a href="${link.href}" target="${link.target}">${link.name}</a>`;
                    modalElemBody.appendChild(linkElem);
                })
            });
    }
}