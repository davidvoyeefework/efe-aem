import A11yDialog from 'a11y-dialog'
var dialogEl = document.querySelector('.modal-promotional');
var dialog = new A11yDialog(dialogEl);
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
        if(splitValue.length < 1 || !splitValue[0]?.includes('pop_up_displayed=true')) {
            dialog.show();
            let pop_disp = new Date();
            pop_disp.setTime(pop_disp.getTime() + (4 * 60 * 60 * 1000));
            let expires = 'expires=' + pop_disp.toUTCString();
            document.cookie = 'pop_up_displayed=true;' + expires + ';path=/';
        }
    }
}
export default ModalPromotional;