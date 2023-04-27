import A11yDialog from 'a11y-dialog'
import { trapFocus } from "../../site/js/helper";
var dialogEl = document.getElementById('modal-promotional')
var dialog = new A11yDialog(dialogEl)
dialog.on('show', function (dialogEl, event) {
});

class ModalPromotional {
    constructor(el) {
      this.el = el;
      this.intializeModal();
    }
    static init(el) {
        return new ModalPromotional(el);
      }
    intializeModal() {
        setTimeout(()=>{
            this.showModal();
        }, 1000)
    }
    showModal() {
        let splitValue = document.cookie.split(`; pop_up_displayed=`);
        console.log(this.getCookie('pop_up_displayed'));
        if(splitValue.length < 2 || !splitValue[1].startsWith('true')) {
            dialog.show();
            let pop_disp = new Date();
            pop_disp = pop_disp.setHours(today.getHours()+4);
            let expires = 'expires=' + pop_disp.toUTCString();
            document.cookie = 'pop_up_displayed=true;' + expires + ';path=/';
        }
    }
    getCookie(cname) {
        let name = cname + "=";
        let decodedCookie = decodeURIComponent(document.cookie);
        let ca = decodedCookie.split(';');
        for(let i = 0; i <ca.length; i++) {
          let c = ca[i];
          while (c.charAt(0) == ' ') {
            c = c.substring(1);
          }
          if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
          }
        }
        return "";
      }
}
export default ModalPromotional;