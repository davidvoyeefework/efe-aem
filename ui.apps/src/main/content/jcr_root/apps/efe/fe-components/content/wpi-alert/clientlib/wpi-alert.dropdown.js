(function(document, $) {
    "use strict";

    $(document).on("dialog-ready", function() {
        var headlinePathPicker = $("foundation-autocomplete[name='./Headline']");
        var headlineVariationsDropdown = $("coral-select[name='./HeadlineVariations']");
        if(headlineVariationsDropdown.find('coral-select-item').length === 0)  {
        	headlineVariationsDropdown.prop("disabled", true);
        }
        
        var subHeadlinePathPicker = $("foundation-autocomplete[name='./Subheadline']");
        var subHeadlineVariationsDropdown = $("coral-select[name='./SubheadlineVariations']");
        if(subHeadlineVariationsDropdown.find('coral-select-item').length === 0)  {
        	subHeadlineVariationsDropdown.prop("disabled", true);
        }
        
       	var disclosurePathPicker = $("foundation-autocomplete[name='./Disclosure']");
        var disclosureVariationsDropdown = $("coral-select[name='./DisclosureVariations']");
        if(disclosureVariationsDropdown.find('coral-select-item').length === 0)  {
        	disclosureVariationsDropdown.prop("disabled", true);
        }
        
        variationsDropdownChanges(headlinePathPicker, headlineVariationsDropdown, false, "forHeadlines");
        variationsDropdownChanges(subHeadlinePathPicker, subHeadlineVariationsDropdown, false, "forSubheadlines");
        variationsDropdownChanges(disclosurePathPicker, disclosureVariationsDropdown, false, "forDisclosure");

        $(headlinePathPicker).on("change", function(e) {
            variationsDropdownChanges(headlinePathPicker, headlineVariationsDropdown, true, "forHeadlines");
        });
        $(subHeadlinePathPicker).on("change", function(e) {
            variationsDropdownChanges(subHeadlinePathPicker, subHeadlineVariationsDropdown, true, "forSubheadlines");
        });
        
        $(disclosurePathPicker).on("change", function(e) {
            variationsDropdownChanges(disclosurePathPicker, disclosureVariationsDropdown, true, "forDisclosure");
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
                           if(pickerName === 'forHeadlines') {
                               var alreadySelectedHeadlineVariation = $('iframe').contents().find('#headline').attr('data-headline-variations');
                               isSelected = value === alreadySelectedHeadlineVariation ? 'selected' : '';
                           }
                            
                           if(pickerName === 'forSubheadlines') {
                               var alreadySelectedSubheadlineVariation = $('iframe').contents().find('#subheadline').attr('data-subheadline-variations');
                               isSelected = value === alreadySelectedSubheadlineVariation ? 'selected' : '';
                           }
                            
                           if(pickerName === 'forDisclosure') {
                               var alreadySelectedDisclosureVariation = $('iframe').contents().find('#disclosure').attr('data-disclosure-variations');
                               isSelected = value === alreadySelectedDisclosureVariation ? 'selected' : '';
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