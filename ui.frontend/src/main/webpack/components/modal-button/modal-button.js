import { trapFocus } from "../../site/js/helper";
class ModalButton {
  constructor(el) {
    this.el = el;
    this.intializeModal(el);
  }

  static init(el) {
    return new ModalButton(el);
  }
  intializeModal(el) {
    el.querySelector('.cmp-modal--button').addEventListener("click", (event) => {
      el.getElementById(event.target.dataset.modalId).classList.remove("d-none");
    });
    el.querySelector('.cmp-modal__button-secondary').addEventListener("click", (event) => {
      el.getElementById(event.target.dataset.modalId).classList.add("d-none");
    });
  }
}
export default ModalButton;