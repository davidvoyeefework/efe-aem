import A11yDialog from 'a11y-dialog'
var dialogEl = document.querySelector('.modal-promotional');
var dialog = new A11yDialog(dialogEl);
class ModalPromotional {
    constructor(el) {
      this.el = el;
      this.intializeModal(el);
    }
    static init(el) {
        return new ModalPromotional(el);
      }
    intializeModal(el) {
        const startDate = new Date(el.getAttribute('startDate'));
        const endDate = new Date(el.getAttribute('endDate'));
        const currentDate = new Date();
        if(currentDate > startDate && currentDate<endDate) {
            setTimeout(()=>{
                this.showModal();
            }, 1000)
        }
    }
    showModal() {
        let popupDisplayed=(document.cookie.match('(^|;) *'+'pop_up_displayed'+'=([^;]*)')||[])[2];
        if(!popupDisplayed) {
            dialog.show();
            let pop_disp = new Date();
            pop_disp.setTime(pop_disp.getTime() + (4 * 60 * 60 * 1000));
            let expires = 'expires=' + pop_disp.toUTCString();
            document.cookie = 'pop_up_displayed=true;' + expires + ';path=/';
        }
    }
}
export default ModalPromotional;