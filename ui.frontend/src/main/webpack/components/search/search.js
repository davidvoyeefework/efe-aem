export default class Search {
  constructor(el) {
    this.el = el;
  }

  static init(el) {
    return new Search(el);
  }
}
