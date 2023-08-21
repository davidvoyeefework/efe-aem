
export default class FeReplaceDataWithPlaceholder {
  constructor() {
    document.addEventListener("messageFromfePage", (e) => {
      this.attributeParameterElem = document.querySelector('#fe-properties');
      this.dataPlacholders = [];
      this.bodyContent = document.querySelector("body div:first-child").innerHTML;
      this.init();
    });
  }
  init() {
    this.getDataFieldIds();
  }
  getDataFieldIds() {
    //const bodyContent = document.querySelector("body.sb-show-main").innerHTML; // get array of placeholders from body content
    var placeholders = this.bodyContent.match(/(?:%7B%7B|\{\{)(.*?)(?:}}|%7D%7D)+/g);
    placeholders.forEach((placeholder) => {
      var fieldName = placeholder.replace("{{", "").replace("%7B%7B", "").replace("}}", "").replace("%7D%7D", "");
      this.dataPlacholders.push({
        id: fieldName
      });
    });
    if (this.dataPlacholders.length > 0) {
      // dedupe data fields array
      this.dataPlacholders = this.dataPlacholders.filter(function (item, pos, array) {
        return array.map(function (mapItem) {
          return mapItem["id"];
        }).indexOf(item["id"]) === pos;
      });
    }
    this.replacePlaceholderWithData();
  }
  replacePlaceholderWithData() {
    if (this.dataPlacholders.length > 0) {
      // replace placeholders with real data
      this.dataPlacholders.forEach((dataPlaceholder, index) => {
          var regEx = new RegExp("(?:%7B%7B|{{)" + dataPlaceholder.id + "(?:}}|%7D%7D)", "g");
          this.bodyContent = this.bodyContent.replace(regEx, this.replaceWithDataAttribute(dataPlaceholder.id.trim()));
      })
    }
    document.querySelector("body div:first-child").innerHTML = this.bodyContent;
    this.replaceWithDataAttribute('programFee')
  }
  replaceWithDataAttribute(key) {
    const headerDataVariables = this.attributeParameterElem?.getAttribute('data-variables');
    const arr = JSON.parse(headerDataVariables);
    let obj = arr.find(o => {
      return Object.keys(o)[0] === key
    });
    if(!obj) {
      return;
    }
    if(window.aemfe[obj[key]]) {
      return window.aemfe[obj[key]];
    } else {
      return eval('window.aemfe.'+[obj[key]]);
    }
  }
}