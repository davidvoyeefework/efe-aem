use(function () {

    var leafItem = {};

    var index = properties.selectorIndex;

    //var count = navItemList != null?navItemList.count:0;

    leafItem.selector = request.requestPathInfo.selectors[index];

    leafItem.count = this.count != null?this.count+1:'';

    return leafItem;

});