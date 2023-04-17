import A11yDialog from 'a11y-dialog'

var dialogEl = document.getElementById('my-dialog')
var dialog = new A11yDialog(dialogEl)

dialog.on('show', function (dialogEl, event) {
  // console.log(dialogEl)
  // console.log(event)
})

// To manually control the dialog:
// dialog.show()
// dialog.hide()
// dialog.destroy()
(function(){
    "use strict";
    function init() {
        const exlusionExtlinks = ['https://www.facebook.com/edelmanfinancialengines/',
                                 'https://www.jobvite.com/edelmanfinancialengines',
                                 'https://twitter.com/edelmanfe',
                                 'https://instagram.com/edelmanfinancialengines',
                                 'https://linkedin.com/company/edelman-financial-engines',
                                 'https://event.on24.com/wcc/r/3650900/14658F6E878B575EFBB30EF0AA357733/3428297'
                                  ]
        let externalLinks = document.querySelectorAll("a"); 
        externalLinks.forEach((extlink)=>{
        let linkhn = extlink.hostname.split('.').reverse();
		let linkHref = linkhn[1] + "." + linkhn[0];
		let domainhn = window.location.hostname.split('.').reverse();
		let domainHref = domainhn[1] + "." + domainhn[0];
        const getLinkHref = extlink.getAttribute("href")
        //console.log(extlink.getAttribute("href"))
        const checkExlcusionLink = exlusionExtlinks.includes(getLinkHref)
        console.log(checkExlcusionLink)
        if(linkHref !== domainHref && !checkExlcusionLink) {
            extlink.addEventListener("click", (e)=>{
                e.preventDefault();
                dialog.show();
                document.querySelector('.dialog-close').focus();
        });
        }
        })
        
        
    }
    if (document.readyState !== "loading") {
        init();
        document.querySelector('#email').focus();
        // dialog.show();
        // document.querySelector('.dialog-content').focus();
      } else {
        document.addEventListener("DOMContentLoaded", init);
        // document.addEventListener("DOMContentLoaded", dialog.show);
        // document.querySelector('#email').focus();
      }
}())