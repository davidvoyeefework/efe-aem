const modalButtons = document.querySelectorAll('.cmp-modal--button');
const modalCloseButtons = document.querySelectorAll('.cmp-button-modal__close');

modalButtons.forEach(el => el.addEventListener('click', event => {
  var modalComponent = document.getElementById(event.target.dataset.modalId);
    modalComponent.classList.remove("d-none");
}));

modalCloseButtons.forEach(el => el.addEventListener('click', event => {
  var modalComponent = document.getElementById(event.target.dataset.modalId);
    modalComponent.classList.add("d-none");
}));