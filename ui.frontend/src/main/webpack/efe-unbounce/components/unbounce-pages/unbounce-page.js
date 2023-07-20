//import {handleLoader} from '../../site/js/helper'
import A11yDialog from 'a11y-dialog'
import { getCookie, getFetch, handleLoader, fetchData, postJSON, pushToWindowObject } from "../../site/js/helper";
export default class UnbouncePage {
    constructor() {
        this.init()
    }
    init() {
        window.addEventListener("load", (event) => {
            const el = document.querySelector('.unbounce-promotions-page');
            if(!el) {
                return 
            }
            window.fe={};
            handleLoader(true)
            this.fetchSponsorData();
            this.fetchAggregateData();
            this.getKeys();
        });
    }
    async fetchSponsorData() {
        const apiHost = document.querySelector('#unbounce-properties')?.getAttribute('data-frame-api');//'http://localhost:3000';//document.querySelector('.sponsor-header').getAttribute('data-page-frame-api');
        const headers = {
            'Accept': 'application/json, text/plain, */*',
            'X-Spa-Name': 'landing-flow',
        };
        await fetchData(apiHost, headers).then(data => {
            handleLoader(false);
            if (data) {
                  pushToWindowObject(data);
                  this.changeHeaderValues();
                  this.changeFooterValues();
                  handleLoader(false);
            }
        }).catch(error => {
            console.error('An error occurred:', error);
            handleLoader(false);
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
        await fetchData(apiHost, headers).then(data => {
            console.log(data);
            pushToWindowObject(data);
            handleLoader(false);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    async getKeys() {
        let apiUrl = document.querySelector('#unbounce-properties')?.getAttribute('data-keys-api');
        const keys= ["publicEnrollment.hero.heading","publicEnrollment.hero.description"];
        await postJSON(apiUrl, keys).then(data=>{
            pushToWindowObject(data);
        }).catch(error => {
            // Handle any errors that occurred during the fetch request
            console.error('An error occurred:', error);
            handleLoader(false);
        });
    }
    changeHeaderValues() {
        const data = window.fe;
        const headerDataVariables = document.querySelector('#unbounce-properties')?.getAttribute('data-variables');
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

        var sponsorLogo = document.querySelector(".sponsor-logo img");
        if(sponsorLogo && data.context.isFeChannel && data.context.sponsorId){
            var logoFileName = data.context.sponsorId;
            var logo = "/content/dam/fe/logos/sponsors/"+logoFileName+"-small.png";
            sponsorLogo.src = logo;
        }
    }
    changeFooterValues() {
        const data = window.fe;
        const litUlElement = document.querySelector('.cmp-list--footer .cmp-list');
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

        // copy right code
        if(data && data.footer && data.footer.copyright){
            const copyrightEle = document.querySelector('#disclosure-copy');
            if(!copyrightEle){
                return
            }

            var patentSection = '';
            if(data.footer.patentLink && data.footer.patentText){
                var patentLinkHtml = `<a href="`+data.footer.patentLink+`" target="_blank">`+data.footer.patentLink+`</a>`
                patentSection = ' '+ data.footer.patentText.replace("%1%", patentLinkHtml);
            }

            var copyrightHTML = `<span class="cmp-text--caption">` + data.footer.copyright + patentSection + `</span>`
            copyrightEle.innerHTML = copyrightHTML;
        }

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