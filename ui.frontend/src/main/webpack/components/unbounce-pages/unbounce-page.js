import A11yDialog from 'a11y-dialog'
import { getCookie, getFetch, handleLoader } from "../../site/js/helper";
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
            if (!document.querySelector('.sponsor-header')) {
                return
            }

            handleLoader(true);
            this.fetchSponsorData();
            this.fetchAggregateData();
            this.getAuthenticationStatus();
        })
    }
    // async getFetch(url, headers) {
    //     return fetch(url, {headers}).then(data=>{
    //         if(data.status === 200) {
    //             return data?.json();
    //         } else {
    //             console.log(data.statusText,"something went wrong");
    //             return false;
    //         }
    //     });
    // }
    async fetchSponsorData() {
        const apiHost = document.querySelector('#unbounce-properties')?.getAttribute('data-frame-api');//'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
        const headers = {
            'Accept': 'application/json, text/plain, */*',
            'X-Spa-Name': 'landing-flow',
        };
        await getFetch(apiHost, headers).then(data => {
            handleLoader(false);
            if (data) {
                this.changeHeaderValues(data);
                this.changeFooterValues(data);
            }
        });
    }
    async fetchAggregateData() {
        const daVarsStr = getCookie('daVars');
        const daVars = daVarsStr ? JSON.parse(decodeURIComponent(daVarsStr)) : null;
        if (!daVars || !daVars.sponsorId) {
            return
        }
        let apiUrl = document.querySelector('#unbounce-properties')?.getAttribute('data-aggregate-api');
        let apiHost = apiUrl.replace('{Recordkeeper}', daVars.sponsorId);
        const headers = {
            'Accept': 'application/json, text/plain, */*',
        }
        await getFetch(apiHost, headers).then(data => {
            console.log(data);
            handleLoader(false);
            // this.loader.style.display = 'none';
            // if(data) {
            //     this.changeHeaderValues(data);
            // }
        });
    }
    async getAuthenticationStatus() {
        const headers = {
            'Accept': 'application/json, text/plain, */*',
        }
        await getFetch('https://www.feitest.com/api/v1/userlogin/authenticationstatus', headers).then(data => {
            console.log(data);
            handleLoader(false);
            if (data.isSponsorIdentified) {
                document.querySelector('.unbounce-form').style.display = 'block';
            }
        });
    }
    changeHeaderValues(data) {
        if (data?.header?.sponsorName === 'AT&T') {
            document.querySelector('.sponsor-header')?.classList.add('sponsor--ATT');
        }
        document.querySelector('.sponsor-header')?.classList.add('sponsor--' + data?.header?.sponsorName);
        const headerDataVariables = document.querySelector('#unbounce-properties')?.getAttribute('data-variables');
        JSON.parse(headerDataVariables)?.forEach((item) => {
            console.log(Object.keys(item).length === 0);
            if (Object.keys(item).length === 0) {
                return
            }
            const elems = document.querySelectorAll('.' + Object.keys(item));
            elems?.forEach((ele) => {
                ele.classList.remove("sponsor-value-hide");
                ele.innerHTML = eval('data.' + item[Object.keys(item)]);
            })
        })
    }
    changeFooterValues(data) {
        const litUlElement = document.querySelector('.unbounce-footer-links .cmp-list');
        if (!litUlElement) {
            return
        }
        data?.footer?.footerLinks.forEach((item) => {
            let liElem = document.createElement('li');
            liElem.classList.add('cmp-list__item')
            if (item.target !== "modal") {
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
        document.querySelector('.unbounce-legal-doc-modal-btn').addEventListener("click", (e) => {
            dialog.show();
            const modalElem = dialogEl.querySelector('.cmp-modal__content');
            modalElem.innerHTML = " ";
            const modalElemBody = document.createElement('div');
            modalElem.appendChild(modalElemBody);
            const title = `<h4 class="legal-doc-title">${e.target.innerText}</h4>`;
            modalElemBody.innerHTML = title;
            data?.footer?.legalDocsLinks.forEach((link) => {
                let linkElem = document.createElement('p');
                linkElem.innerHTML = `<a href="${link.href}" target="${link.target}">${link.name}</a>`;
                modalElemBody.appendChild(linkElem);
            })
        });
    }
}