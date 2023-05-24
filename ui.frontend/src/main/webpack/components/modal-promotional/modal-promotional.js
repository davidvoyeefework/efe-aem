import A11yDialog from 'a11y-dialog'
var dialogEl = document.querySelector('.modal-promotional');
var dialog = new A11yDialog(dialogEl);
class ModalPromotional {
    constructor(el) {
      this.el = el;
      this.intializeModal(el);
      this.modalName='';
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
        const modalName = this.el.getAttribute('id')
        let popupDisplayed=this.getCookie(modalName);
        if(!popupDisplayed) {
            dialog.show();
            let pop_disp = new Date();
            pop_disp.setTime(pop_disp.getTime() + (4 * 60 * 60 * 1000));
            let expires = 'expires=' + pop_disp.toUTCString();
            document.cookie = modalName+'=true;' + expires + ';path=/';
        }
    }
    getCookie(name) {
        let cookie = {};
        document.cookie.split(';').forEach(function(el) {
          let [k,v] = el.split('=');
          cookie[k.trim()] = v;
        })
        return cookie[name];
    }
}
export default ModalPromotional;