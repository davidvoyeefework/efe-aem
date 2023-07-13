const formToJSON = elements => [].reduce.call(elements, (data, element) => {
    if (element.type != "submit") {
        data[element.name] = element.value;
    }
    return data;
}, {});

export { formToJSON };

export const camelToDashed = (str) => {
   return str.replace(/[A-Z]/g, m => "-" + m.toLowerCase());
}

export const throttle = (callback, limit) => {
    let wait = false;                  // Initially, we're not waiting
    return function () {               // We return a throttled function
        if (!wait) {                   // If we're not waiting
            callback.call();           // Execute users function
            wait = true;               // Prevent future invocations
            setTimeout(function () {   // After a period of time
                wait = false;          // And allow future invocations
            }, limit);
        }
    }
}


export const debounce = (func, wait) => {
    let timeout;
  
    return function executedFunction(...args) {
      const later = () => {
        clearTimeout(timeout);
        func(...args);
      };
  
      clearTimeout(timeout);
      timeout = setTimeout(later, wait);
    };
};

// The helper function to use with dangerouslySetInnerHTML inside React component
export const setInnerHtml = (htmlStr) => {
    return {
        __html: htmlStr
    }
}
export const trapFocus = (element) => {
    var focusableEls = element.querySelectorAll('a[href]:not([disabled]), button:not([disabled]), textarea:not([disabled]), input[type="text"]:not([disabled]), input[type="radio"]:not([disabled]), input[type="checkbox"]:not([disabled]), select:not([disabled])');
    var firstFocusableEl = focusableEls[0];  
    var lastFocusableEl = focusableEls[focusableEls.length - 1];
    var KEYCODE_TAB = 9;
  
    element.addEventListener('keydown', function(e) {
      var isTabPressed = (e.key === 'Tab' || e.keyCode === KEYCODE_TAB);
  
      if (!isTabPressed) { 
        return; 
      }
  
      if ( e.shiftKey ) /* shift + tab */ {
        if (document.activeElement === firstFocusableEl) {
          lastFocusableEl.focus();
            e.preventDefault();
          }
        } else /* tab */ {
        if (document.activeElement === lastFocusableEl) {
          firstFocusableEl.focus();
            e.preventDefault();
          }
        }
    });
  }
  export const getCookie = (name)=> {
    if (typeof document !== 'undefined') {
      var value = '; ' + document.cookie;
      var parts = value.split('; ' + name + '=');
      if (parts.length === 2) {
        return parts.pop().split(';').shift();
      }
    }
  }
  
  export const getFetch = async (url, headers) => {
    return fetch(url, {headers}).then(data=>{
        if(data.status === 200) {
            return data?.json();
        } else {
            console.log(data.statusText,"something went wrong");
            return false;
        }
    });
}