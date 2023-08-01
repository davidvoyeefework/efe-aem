import A11yDialog from 'a11y-dialog'
import { getCookie, getFetch, handleLoader, fetchData, postJSON, pushToWindowObject } from "../../site/js/helper";
export default class FeFooter {
    constructor() {
        document.addEventListener("messageFromfePage", (e) =>{
            this.attributeParameterElem = document.querySelector('#fe-properties'); 
            this.init();
        });
    }
    init() {
        if(window.aemfe.header) {
            this.changeFooterValues();
        }
    }
    changeFooterValues() {
        const data = window.aemfe;
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
                liElem.innerHTML = `<a href="javascript:void(0)" class="cmp-list__item-link fe-modal-btn ${item.href}">
                <span class="cmp-list__item-title">${item.name}</span></a>`;
            }
            litUlElement.appendChild(liElem);
        });

        // copy right code
        if (data && data.footer && data.footer.copyright) {
            const copyrightEle = document.querySelector('#disclosure-copy');
            if (!copyrightEle) {
                return
            }

            var patentSection = '';
            if (data.footer.patentLink && data.footer.patentText) {
                var patentLinkHtml = `<a href="` + data.footer.patentLink + `" target="_blank">` + data.footer.patentLink + `</a>`
                patentSection = ' ' + data.footer.patentText.replace("%1%", patentLinkHtml);
            }

            var copyrightHTML = `<span class="cmp-text--caption">` + data.footer.copyright + patentSection + `</span>`
            copyrightEle.innerHTML = copyrightHTML;
        }
        document.querySelectorAll('.fe-modal-btn').forEach(item => {
            item.addEventListener("click", (ev) => {
                if (item.classList.contains('legalDocLink')) {
                    this.renderLegalDocModal(ev);
                }
                if (item.classList.contains('aboutProviderLink')) {
                    this.renderAboutUsModal(ev);
                }
            })
        });
    }
    renderAboutUsModal(e) {
        const data = window.aemfe;
        const dialogEl = document.querySelector('.fe-footer-modal');
        const dialog = new A11yDialog(dialogEl);
        dialog.show();
        const modalElem = dialogEl.querySelector('.cmp-modal__content');
        modalElem.innerHTML = " ";
        const modalElemBody = document.createElement('div');
        modalElem.appendChild(modalElemBody);
        const title = `<h4 class="legal-doc-title">${data?.footer?.aboutProviderTitle}</h4>`;
        modalElemBody.innerHTML = title;
        let content = document.createElement('div');
        content.innerHTML = `<p>${data?.footer?.aboutProviderP1}</p>
                            <p>${data?.footer?.aboutProviderP2}</p>
                            <p>${data?.footer?.aboutProviderP3}</p>`
        modalElemBody.appendChild(content);
    }
    renderLegalDocModal(e) {
        const data = window.aemfe;
        const dialogEl = document.querySelector('.fe-footer-modal');
        const dialog = new A11yDialog(dialogEl);
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
    }
}