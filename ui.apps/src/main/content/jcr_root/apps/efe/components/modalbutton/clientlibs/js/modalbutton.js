const modalButtons = document.querySelectorAll('button.cmp-modal--button');
const modalCloseButtons = document.querySelectorAll('button.cmp-button-modal-content__close');

modalButtons.forEach(el => el.addEventListener('click', event => {
    var modalComponent = document.getElementById(event.currentTarget.dataset.modalId);
    modalComponent.classList.remove("d-none");
}));

modalCloseButtons.forEach(el => el.addEventListener('click', event => {
    var modalComponent = document.getElementById(event.currentTarget.dataset.modalId);
    modalComponent.classList.add("d-none");
}));