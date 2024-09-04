(function(document, $) {
    "use strict";

    $(document).on("dialog-ready", function() {
        var titlePathPicker = $("foundation-autocomplete[name='./title']");
        var titleVariationsDropdown = $("coral-select[name='./titleCFVariations']");
        if(titleVariationsDropdown.find('coral-select-item').length === 0)  {
        	titleVariationsDropdown.prop("disabled", true);
        }
        
        var subtitlePathPicker = $("foundation-autocomplete[name='./subtitle']");
        var subtitleVariationsDropdown = $("coral-select[name='./subtitleCFVariations']");
        if(subtitleVariationsDropdown.find('coral-select-item').length === 0)  {
        	subtitleVariationsDropdown.prop("disabled", true);
        }
        
       	var header1PathPicker = $("foundation-autocomplete[name='./header1']");
        var header1VariationsDropdown = $("coral-select[name='./header1CFVariations']");
        if(header1VariationsDropdown.find('coral-select-item').length === 0)  {
        	header1VariationsDropdown.prop("disabled", true);
        }
        
        var header2PathPicker = $("foundation-autocomplete[name='./header2']");
        var header2VariationsDropdown = $("coral-select[name='./header2CFVariations']");
        if(header2VariationsDropdown.find('coral-select-item').length === 0)  {
        	header2VariationsDropdown.prop("disabled", true);
        }
        
        var body1PathPicker = $("foundation-autocomplete[name='./body1']");
        var body1VariationsDropdown = $("coral-select[name='./body1CFVariations']");
        if(body1VariationsDropdown.find('coral-select-item').length === 0)  {
        	body1VariationsDropdown.prop("disabled", true);
        }
        
        var body2PathPicker = $("foundation-autocomplete[name='./body2']");
        var body2VariationsDropdown = $("coral-select[name='./body2CFVariations']");
        if(body2VariationsDropdown.find('coral-select-item').length === 0)  {
        	body2VariationsDropdown.prop("disabled", true);
        }
        
        var footerPathPicker = $("foundation-autocomplete[name='./footer']");
        var footerVariationsDropdown = $("coral-select[name='./footerCFVariations']");
        if(footerVariationsDropdown.find('coral-select-item').length === 0)  {
        	footerVariationsDropdown.prop("disabled", true);
        }
        
        variationsDropdownChanges(titlePathPicker, titleVariationsDropdown, false, "forTitle");
        variationsDropdownChanges(subtitlePathPicker, subtitleVariationsDropdown, false, "forSubtitle");
        variationsDropdownChanges(header1PathPicker, header1VariationsDropdown, false, "forHeader1");
        variationsDropdownChanges(header2PathPicker, header2VariationsDropdown, false, "forHeader2");
        variationsDropdownChanges(body1PathPicker, body1VariationsDropdown, false, "forBody1");
        variationsDropdownChanges(body2PathPicker, body2VariationsDropdown, false, "forBody2");
        variationsDropdownChanges(footerPathPicker, footerVariationsDropdown, false, "forFooter");

        $(titlePathPicker).on("change", function(e) {
            variationsDropdownChanges(titlePathPicker, titleVariationsDropdown, true, "forTitle");
        });
        $(subtitlePathPicker).on("change", function(e) {
            variationsDropdownChanges(subtitlePathPicker, subtitleVariationsDropdown, true, "forSubtitle");
        });
        
        $(header1PathPicker).on("change", function(e) {
            variationsDropdownChanges(header1PathPicker, header1VariationsDropdown, true, "forHeader1");
        });
        
        $(header2PathPicker).on("change", function(e) {
            variationsDropdownChanges(header2PathPicker, header2VariationsDropdown, true, "forHeader2");
        });
        
       	$(body1PathPicker).on("change", function(e) {
            variationsDropdownChanges(body1PathPicker, body1VariationsDropdown, true, "forBody1");
        });
        
        $(body2PathPicker).on("change", function(e) {
            variationsDropdownChanges(body2PathPicker, body2VariationsDropdown, true, "forBody2");
        });
        
        $(footerPathPicker).on("change", function(e) {
            variationsDropdownChanges(footerPathPicker, footerVariationsDropdown, true, "forFooter");
        });
    });
    
    $(document).on("dialog-success", function(event) {
        // Trigger page refresh
        window.location.reload();
    });
})(document, Granite.$);

function variationsDropdownChanges(pathPicker, variationsDropdown, onChange, pickerName) {
    var path = pathPicker.val();
    if (path) {
        // Fetch the variations based on the selected content fragment path
        $.ajax({
            url: "/bin/contentfragment/variations",
            type: "GET",
            data: { path: path },
            dataType: "json",
            success: function(data) {
                variationsDropdown.find('coral-select-item').remove(); // Clear the existing options
                if (data.variations) {
                    variationsDropdown.prop("disabled", false);
                    $.each(data.variations, function(key, value) {
                        var isSelected = ''
                        if(!onChange) {
                           if(pickerName === 'forTitle') {
                               var alreadySelectedTitleVariation = $('iframe').contents().find('#title').attr('data-title-variations');
                               isSelected = value === alreadySelectedTitleVariation ? 'selected' : '';
                           }
                            
                           if(pickerName === 'forSubtitle') {
                               var alreadySelectedSubtitleVariation = $('iframe').contents().find('#subtitle').attr('data-subtitle-variations');
                               isSelected = value === alreadySelectedSubtitleVariation ? 'selected' : '';
                           }
                            
                           if(pickerName === 'forHeader1') {
                               var alreadySelectedHeader1Variation = $('iframe').contents().find('#header1').attr('data-header1-variations');
                               isSelected = value === alreadySelectedHeader1Variation ? 'selected' : '';
                           }
                            
                         	if(pickerName === 'forHeader2') {
                               var alreadySelectedHeader2Variation = $('iframe').contents().find('#header2').attr('data-header2-variations');
                               isSelected = value === alreadySelectedHeader2Variation ? 'selected' : '';
                           }
                            
                            if(pickerName === 'forBody1') {
                               var alreadySelectedBody1Variation = $('iframe').contents().find('#body1').attr('data-body1-variations');
                               isSelected = value === alreadySelectedBody1Variation ? 'selected' : '';
                           }
                            
                            if(pickerName === 'forBody2') {
                               var alreadySelectedBody2Variation = $('iframe').contents().find('#body2').attr('data-body2-variations');
                               isSelected = value === alreadySelectedBody2Variation ? 'selected' : '';
                           }
                            
                            if(pickerName === 'forFooter') {
                               var alreadySelectedFooterVariation = $('iframe').contents().find('#footer').attr('data-footer-variations');
                               isSelected = value === alreadySelectedFooterVariation ? 'selected' : '';
                           }
                            
                        }
                        var option = '<coral-select-item value="' + value + '"' + isSelected + '>' + value + '</coral-select-item>';
                        variationsDropdown.append(option);
                    });
                }
            },
            error: function() {
                console.error("Error fetching content fragment variations.");
            }
        });
    }
}