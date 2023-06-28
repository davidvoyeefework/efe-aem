export default class SkipToMainMenu {
    constructor(el) {
        el.style.display = 'block';
    }

    static init(el) {
        return new SkipToMainMenu(el);
    }
}
