use(function () {

    var leafItem = {};

    var index = properties.selectorIndex;

    leafItem.selector = request.requestPathInfo.selectors[index];

    return leafItem;

});