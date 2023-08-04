import A11yDialog from 'a11y-dialog'
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
            liElem.classList.add('cmp-list__item');
            if(item.id === 'doc.dnspi')  {
                liElem.innerHTML = `<button class="cmp-list__item-link ot-sdk-show-settings">
                ${item.name}
                </button>`
            } 
            else if(item.target === "modal") {
                liElem.innerHTML = `<a href="javascript:void(0)" class="cmp-list__item-link">
                <span class="cmp-list__item-title fe-modal-btn ${item.href}">${item.name}</span></a>`;
            } else {
                liElem.innerHTML = `<a href="${item.href}" target="${item.target}" class="cmp-list__item-link">
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
        window.addEventListener('click', e=>{
            if (e.target.classList.contains('legalDocLink')) {
                            this.renderLegalDocModal(e);
                }
                if (e.target.classList.contains('aboutProviderLink')) {
                                this.renderAboutUsModal(e);
                    }
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
        const title = `<h4 class="cmp-modal__fe-title">${data?.footer?.aboutProviderTitle}</h4>`;
        modalElemBody.innerHTML = title;
        let content = document.createElement('div');
        content.classList.add('cmp-modal__about-us-text');
        content.innerHTML = `<p>${data?.footer?.aboutProviderP1}</p>
                            <p>${data?.footer?.aboutProviderP2}</p>
                            <p>${data?.footer?.aboutProviderP3}</p>`
        modalElemBody.appendChild(content);
        const elCloseBtn = document.createElement('div');
        elCloseBtn.classList.add('cmp-modal__close-btn-div');
        elCloseBtn.innerHTML = "<button>Close</button>";
        modalElemBody.appendChild(elCloseBtn);
        elCloseBtn.addEventListener("click", (ev) => {
            document.querySelector('body').classList.toggle("modal-open");
            dialog.hide();
          });
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
        const title = `<h4 class="cmp-modal__fe-title">${e.target.innerText}</h4>`;
        modalElemBody.innerHTML = title;
        const elUl = document.createElement('ul')
        data?.footer?.legalDocsLinks.forEach((link) => {
            let linkElem = document.createElement('li');
            linkElem.innerHTML = `<a href="${link.href}" target="${link.target}">${link.name}</a>`;
            elUl.appendChild(linkElem);
        });
        modalElemBody.appendChild(elUl);
        const elCloseBtn = document.createElement('div');
        elCloseBtn.classList.add('cmp-modal__close-btn-div');
        elCloseBtn.innerHTML = "<button>Close</button>";
        modalElemBody.appendChild(elCloseBtn);
        elCloseBtn.addEventListener("click", (ev) => {
            document.querySelector('body').classList.toggle("modal-open");
            dialog.hide();
          });
    }
}