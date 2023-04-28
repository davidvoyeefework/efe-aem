import A11yDialog from 'a11y-dialog'
import { trapFocus } from "../../site/js/helper";
class ModalLeaving {
  constructor(el) {
    var dialogEl = document.querySelector(".cmp-modal--leaving");
    this.dialog = new A11yDialog(dialogEl);
    this.el = el;
    this.intializeModal(el);
  }

  static init(el) {
    return new ModalLeaving(el);
  }
  intializeModal(el) {
    const exclusionLinksList = JSON.parse(el.getAttribute('modal-list'));
    const exlusionExtlinks =  Object.keys(exclusionLinksList).map((m)=>exclusionLinksList[m]);
    const modalEle = document.querySelector(".cmp-modal--leaving");
    let externalLinks = document.querySelectorAll("a");
    let currentExtUrl;
    externalLinks.forEach((extlink) => {
      let linkhn = extlink.hostname.split('.').reverse();
      let linkHref = linkhn[1] + "." + linkhn[0];
      let domainhn = window.location.hostname.split('.').reverse();
      let domainHref = domainhn[1] + "." + domainhn[0];
      const getLinkHref = extlink.getAttribute("href")?extlink.getAttribute("href"):'#';
      const checkExlcusionLink = exlusionExtlinks.includes(getLinkHref);
      if (linkHref !== domainHref && !checkExlcusionLink &&
        !getLinkHref.match(/^tel\:/) && !getLinkHref.match(/^mailto\:/) && getLinkHref !=='javascript:void(0)') {
        extlink.addEventListener("click", (e) => {
          e.preventDefault();
          currentExtUrl = extlink.getAttribute("href");
          this.dialog.show();
          document.querySelector('.cmp-modal__button-secondary').focus();
          trapFocus(modalEle);
        });
      }
    })
    document.querySelector('.cmp-modal__button-primary').addEventListener("click", (ev) => {
      window.open(currentExtUrl);
      this.dialog.hide();
    });
    document.querySelector('.cmp-modal__button-secondary').addEventListener("click", (ev) => {
      this.dialog.hide();
    });
  }
}
export default ModalLeaving;
