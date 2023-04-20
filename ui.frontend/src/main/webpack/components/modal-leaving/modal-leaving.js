import A11yDialog from 'a11y-dialog'
import { trapFocus } from "../../site/js/helper";
var dialogEl = document.getElementById('my-dialog')
var dialog = new A11yDialog(dialogEl)
dialog.on('show', function (dialogEl, event) {
})
class ModalLeaving {
  constructor(el) {
    this.el = el;
    this.intializeModal();
  }

  static init(el) {
    return new ModalLeaving(el);
  }
  intializeModal() {
    const exlusionExtlinks = ['https://www.facebook.com/edelmanfinancialengines/',
      'https://www.jobvite.com/edelmanfinancialengines',
      'https://twitter.com/edelmanfe',
      'https://instagram.com/edelmanfinancialengines',
      'https://linkedin.com/company/edelman-financial-engines',
      'https://event.on24.com/wcc/r/3650900/14658F6E878B575EFBB30EF0AA357733/3428297',
      'https://edelmandev.wpengine.com/',
    ]
    const modalEle = document.querySelector(".cmp-modal--leaving");
    let externalLinks = document.querySelectorAll("a");
    let currentExtUrl;
    externalLinks.forEach((extlink) => {
      let linkhn = extlink.hostname.split('.').reverse();
      let linkHref = linkhn[1] + "." + linkhn[0];
      let domainhn = window.location.hostname.split('.').reverse();
      let domainHref = domainhn[1] + "." + domainhn[0];
      const getLinkHref = extlink.getAttribute("href");
      const checkExlcusionLink = exlusionExtlinks.includes(getLinkHref)
      if (linkHref !== domainHref && !checkExlcusionLink &&
        !getLinkHref.match(/^tel\:/) && !getLinkHref.match(/^mailto\:/)) {
        extlink.addEventListener("click", (e) => {
          e.preventDefault();
          currentExtUrl = e.target.href;
          dialog.show();
          document.querySelector('.cmp-modal__button-secondary').focus();
          trapFocus(modalEle);
        });
      }
    })
    document.querySelector('.cmp-modal__button-primary').addEventListener("click", (ev) => {
      window.open(currentExtUrl);
      dialog.hide();
    });
    document.querySelector('.cmp-modal__button-secondary').addEventListener("click", (ev) => {
      dialog.hide();
    });
  }
}
export default ModalLeaving;
