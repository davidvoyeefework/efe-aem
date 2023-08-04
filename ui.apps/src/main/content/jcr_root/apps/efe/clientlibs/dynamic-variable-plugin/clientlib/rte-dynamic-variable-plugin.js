(function($, CUI, $document){
    var GROUP = "efe-aem-dynamic-variable",
        DYNAMIC_VARIABLE_FEATURE = "applyDynamicVariable",
        EFE_APPLY_DYNAMIC_VARIABLE_DIALOG = "efeTouchUIApplyDynamicVariableDialog",
        SENDER = "efe-aem", REQUESTER = "requester", $eaemFontPicker,
        CANCEL_CSS = "[data-foundation-wizard-control-action='cancel']",
        FONT_SELECTOR_URL = "/apps/efe/clientlibs/dynamic-variable-plugin/assetpicker.html",
        url = document.location.pathname;

    if( url.indexOf(FONT_SELECTOR_URL) == 0 ){
        handlePicker();
        return;
    }

    function handlePicker(){
        $document.on("foundation-contentloaded", fillDefaultValues);

        $document.on("click", CANCEL_CSS, sendCancelMessage);

        $document.submit(sentTextAttributes);
    }

    function queryParameters() {
        var result = {}, param,
            params = document.location.search.split(/\?|\&/);

        params.forEach( function(it) {
            if (_.isEmpty(it)) {
                return;
            }

            param = it.split("=");
            result[param[0]] = param[1];
        });

        return result;
    }

    function setWidgetValue(form, selector, value, enable){
        Coral.commons.ready(form.querySelector(selector), function (field) {
            if(field.tagName == "CORAL-CHECKBOX"){
                if(value == "true"){
                    field.checked = true;
                }
            }else{
                field.value = _.isEmpty(value) ? "" : decodeURIComponent(value);
            }

            if(enable){
                delete field.disabled;
            }else{
                field.disabled = "disabled";
            }
        });
    }

    function fillDefaultValues(){
        var queryParams = queryParameters(),
            $form = $("form");

        if(_.isEmpty(queryParams.features)){
            return;
        }

        var features = queryParams.features.split(",");

        setWidgetValue($form[0], "[name='./style']", queryParams.class, true);

        $form.css("background-color", "#fff");
    }

    function sentTextAttributes(){
        var message = {
            sender: SENDER,
            action: "submit",
            data: {}
        }, $form = $("form"), $field;

        _.each($form.find("[name^='./']"), function(field){
            $field = $(field);
            message.data[$field.attr("name").substr(2)] = $field.val();
        });

        getParent().postMessage(JSON.stringify(message), "*");
    }

    function sendCancelMessage(){
        var message = {
            sender: SENDER,
            action: "cancel"
        };

        getParent().postMessage(JSON.stringify(message), "*");
    }

    function getParent() {
        if (window.opener) {
            return window.opener;
        }

        return parent;
    }

    addPlugin();

    addPluginToDefaultUISettings();

    addDialogTemplate();

    function addDialogTemplate(){
        var url = Granite.HTTP.externalize(FONT_SELECTOR_URL) + "?" + REQUESTER + "=" + SENDER;

        var html = "<iframe width='600px' height='450px' frameBorder='0' src='" + url + "'></iframe>";

        if(_.isUndefined(CUI.rte.Templates)){
            CUI.rte.Templates = {};
        }

        if(_.isUndefined(CUI.rte.templates)){
            CUI.rte.templates = {};
        }

        try{
            CUI.rte.templates['dlg-' + EFE_APPLY_DYNAMIC_VARIABLE_DIALOG] = CUI.rte.Templates['dlg-' + EFE_APPLY_DYNAMIC_VARIABLE_DIALOG] = Handlebars.compile(html);
        }catch(err){
            console.log("Ignoring font plugin error", err);
        }
    }

    function addPluginToDefaultUISettings(){
        var groupFeature = GROUP + "#" + DYNAMIC_VARIABLE_FEATURE,
            toolbar = CUI.rte.ui.cui.DEFAULT_UI_SETTINGS.dialogFullScreen.toolbar;

        if(toolbar.includes(groupFeature)){
            return;
        }

        toolbar.splice(3, 0, groupFeature);
    }

    var EFEApplyDynamicVariableDialog = new Class({
        extend: CUI.rte.ui.cui.AbstractDialog,

        toString: "EFEApplyDynamicVariableDialog",

        initialize: function(config) {
            this.exec = config.execute;
        },

        getDataType: function() {
            return EFE_APPLY_DYNAMIC_VARIABLE_DIALOG;
        }
    });

    function addPlugin(){
        var EFETouchUIDynamicVariablePlugin = new Class({
            toString: "EFETouchUIDynamicVariablePlugin",

            extend: CUI.rte.plugins.Plugin,

            pickerUI: null,

            getFeatures: function() {
                return [ DYNAMIC_VARIABLE_FEATURE ];
            },

            initializeUI: function(tbGenerator) {
                var plg = CUI.rte.plugins;

                addPluginToDefaultUISettings();

                if (!this.isFeatureEnabled(DYNAMIC_VARIABLE_FEATURE)) {
                    return;
                }

                this.pickerUI = tbGenerator.createElement(DYNAMIC_VARIABLE_FEATURE, this, false, { title: "Select Font..." });
                tbGenerator.addElement(GROUP, plg.Plugin.SORT_FORMAT, this.pickerUI, 10);

                var groupFeature = GROUP + "#" + DYNAMIC_VARIABLE_FEATURE;
                tbGenerator.registerIcon(groupFeature, "brackets");
            },

            execute: function (pluginCommand, value, envOptions) {
                var context = envOptions.editContext,
                    ek = this.editorKernel;

                if (pluginCommand != DYNAMIC_VARIABLE_FEATURE) {
                    return;
                }

                if(!isValidSelection()){
                    return;
                }

                var selection = CUI.rte.Selection.createProcessingSelection(context),
                    startNode = selection.startNode;

                if ( (selection.startOffset === startNode.length) && (startNode != selection.endNode)) {
                    startNode = startNode.nextSibling;
                }

                var $tag = $(CUI.rte.Common.getTagInPath(context, startNode, "span")),
                    clazz = $tag.attr("class"),size = $tag.css("font-size"),dialog,dm = ek.getDialogManager(),
                    $container = CUI.rte.UIUtils.getUIContainer($(context.root)),
                    propConfig = {
                        'parameters': {
                            'command': this.pluginId + '#' + DYNAMIC_VARIABLE_FEATURE
                        }
                    };

                if(this.EFEApplyDynamicVariableDialog){
                    dialog = this.EFEApplyDynamicVariableDialog;

                    dialog.$dialog.find("iframe").attr("src", this.getPickerIFrameUrl(clazz));
                }else{
                    dialog = new EFEApplyDynamicVariableDialog();

                    dialog.attach(propConfig, $container, this.editorKernel);

                    dialog.$dialog.css("-webkit-transform", "scale(0.9)").css("-webkit-transform-origin", "0 0")
                        .css("-moz-transform", "scale(0.9)").css("-moz-transform-origin", "0px 0px");

                    dialog.$dialog.find("iframe").attr("src",
                        this.getPickerIFrameUrl(clazz));

                    this.EFEApplyDynamicVariableDialog = dialog;
                }

                dm.show(dialog);

                $(window).off('message', receiveMessage).on('message', receiveMessage);

                function isValidSelection(){
                    var winSel = window.getSelection();
                    return winSel && winSel.rangeCount == 1 && winSel.getRangeAt(0).toString().length > 0;
                }

                function receiveMessage(event) {
                    event = event.originalEvent || {};

                    if (_.isEmpty(event.data)) {
                        return;
                    }

                    var message, action;

                    try{
                        message = JSON.parse(event.data);
                    }catch(err){
                        return;
                    }

                    if (!message || message.sender !== SENDER) {
                        return;
                    }

                    action = message.action;

                    if(action === "submit"){
                        ek.relayCmd(pluginCommand, message.data);
                    }

                    dialog.hide();
                }
            },
            showFontModal: function(url){
                var self = this, $iframe = $('<iframe>'),
                    $modal = $('<div>').addClass('eaem-cfm-font-size coral-Modal');

                $iframe.attr('src', url).appendTo($modal);

                $modal.appendTo('body').modal({
                    type: 'default',
                    buttons: [],
                    visible: true
                });

                $eaemFontPicker = $modal;

                $eaemFontPicker.eaemFontPlugin = self;

                $modal.nextAll(".coral-Modal-backdrop").addClass("cfm-coral2-backdrop");
            },

            getPickerIFrameUrl: function(clazz){
                var url = Granite.HTTP.externalize(FONT_SELECTOR_URL) + "?" + REQUESTER + "=" + SENDER;
                if(!_.isEmpty(clazz)){
                    url = url + "&class=" + clazz;
                }

                return url;
            },

            updateState: function(selDef) {
                var hasUC = this.editorKernel.queryState(DYNAMIC_VARIABLE_FEATURE, selDef);

                if (this.pickerUI != null) {
                    this.pickerUI.setSelected(hasUC);
                }
            }
        });

        var EFETouchUIDynamicVariableCmd = new Class({
            toString: "EFETouchUIDynamicVariableCmd",

            extend: CUI.rte.commands.Command,

            isCommand: function (cmdStr) {
                return (cmdStr.toLowerCase() == DYNAMIC_VARIABLE_FEATURE);
            },

            getProcessingOptions: function () {
                var cmd = CUI.rte.commands.Command;
                return cmd.PO_SELECTION | cmd.PO_BOOKMARK | cmd.PO_NODELIST;
            },

            getTagObject: function(textData) {
                var style = "";

                var spanTag = {
                    "tag": "span",
                    "attributes": {
                        "style" : style
                    }
                };

                var clazz = textData.style;

                if(!_.isEmpty(clazz)){
                    spanTag.attributes.class = clazz;
                }
                return spanTag;
                function addClazz(tag, tagClazz){
                    tag.attributes.class = tag.attributes.class ? (tag.attributes.class + " ") : "";
                    tag.attributes.class = tag.attributes.class + tagClazz;
                }
            },

            execute: function (execDef) {
                var textData = execDef.value, selection = execDef.selection,
                    nodeList = execDef.nodeList;

                if (!selection || !nodeList) {
                    return;
                }

                var common = CUI.rte.Common,
                    context = execDef.editContext,
                    tagObj = this.getTagObject(textData);

                if(_.isEmpty(textData.style)){
                    nodeList.removeNodesByTag(execDef.editContext, tagObj.tag, undefined, true);
                    return;
                }

                nodeList.surround(execDef.editContext, tagObj.tag, tagObj.attributes);
                $document.find("."+tagObj.attributes.class).html("{"+tagObj.attributes.class+"}");
                $document.find("."+tagObj.attributes.class).addClass("sponsor-value-hide");
            },

            queryState: function(selectionDef, cmd) {
                return false;
            }
        });

        CUI.rte.commands.CommandRegistry.register(DYNAMIC_VARIABLE_FEATURE, EFETouchUIDynamicVariableCmd);

        CUI.rte.plugins.PluginRegistry.register(GROUP,EFETouchUIDynamicVariablePlugin);
    }
}(jQuery, window.CUI,jQuery(document)));