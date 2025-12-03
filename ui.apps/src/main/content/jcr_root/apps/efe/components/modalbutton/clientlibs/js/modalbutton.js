function showButtonModal(e) {
    var modalComponent = document.getElementById(e.target.dataset.modalId);
    modalComponent.classList.remove("d-none");
}

function hideButtonModal(e) {
    var modalComponent = document.getElementById(e.target.dataset.modalId);
}

