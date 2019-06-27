const t=window.dotcmsFields.h;function e(t,e,n){return{"dot-valid":e,"dot-invalid":!e,"dot-pristine":t.dotPristine,"dot-dirty":!t.dotPristine,"dot-touched":t.dotTouched,"dot-untouched":!t.dotTouched,"dot-required":n}}function n(t){return"string"==typeof t&&!!t}function o(t){return n(t)&&function(t){const e=/([^|,]*)\|([^|,]*)/,n=t.split(",");let o=!0;for(let t=0,r=n.length;t<r;t++)if(!e.test(n[t])){o=!1;break}return o}(t=t.replace(/(?:\\[rn]|[\r\n]+)+/g,","))?t.split(",").filter(t=>!!t.length).map(t=>{const[e,n]=t.split("|");return{label:e,value:n}}):[]}function r(t){return t?void 0:"dot-field__error"}function i(t){const e=p(t);return e?`hint-${e}`:void 0}function a(t){const e=p(t);return t?`dot-${p(e)}`:void 0}function u(t){const e=p(t);return e?`label-${e}`:void 0}function s(t){return{dotValid:void 0===t||t,dotTouched:!1,dotPristine:!0}}function c(t){return t.map(t=>`${t.key}|${t.value}`).join(",")}function l(t,e){return Object.assign({},t,e)}function d(e,o){return e&&n(o)?t("span",{class:"dot-field__error-message"},o):null}function f(e){return n(e)?t("span",{class:"dot-field__hint",id:i(e)},e):null}function p(t){return t?t.toString().toLowerCase().replace(/\s+/g,"-").replace(/[^\w\-]+/g,"").replace(/\-\-+/g,"-").replace(/^-+/,"").replace(/-+$/,""):null}const m=new RegExp("^\\d\\d\\d\\d-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])"),g=new RegExp("^(2[0-3]|[01][0-9]):([0-5][0-9]):([0-5][0-9])$");function h(t){return m.test(t)?t:null}function w(t){return g.test(t)?t:null}function v(t){const[e,n]=t?t.split(" "):"";return{date:h(e),time:w(n)||w(e)}}class y extends Error{constructor(t,e){super(`Warning: Invalid prop "${t.name}" of type "${typeof t.value}" supplied to "${t.field.type}" with the name "${t.field.name}", expected "${e}".\nDoc Reference: https://github.com/dotCMS/core-web/blob/master/projects/dotcms-field-elements/src/components/${t.field.type}/readme.md`),this.propInfo=t}getProps(){return Object.assign({},this.propInfo)}}function b(t){if("string"!=typeof t.value)throw new y(t,"string")}const $={date:function(t){if(!h(t.value.toString()))throw new y(t,"Date")},dateRange:function(t){const[e,n]=t.value.toString().split(",");if(!h(e)||!h(n))throw new y(t,"Date");((t,e,n)=>{if(t>e)throw new y(n,"Date")})(new Date(e),new Date(n),t)},dateTime:function(t){if("string"!=typeof t.value)throw new y(t,"Date/Time");if(e=v(t.value),!((n=t.value)&&(n.split(" ").length>1?function(t){return!!t.date&&!!t.time}(e):function(t){return!!t.date||!!t.time}(e))))throw new y(t,"Date/Time");var e,n},number:function(t){if(isNaN(Number(t.value)))throw new y(t,"Number")},options:b,regexCheck:function(t){try{RegExp(t.value.toString())}catch(e){throw new y(t,"valid regular expression")}},step:b,string:b,time:function(t){if(!w(t.value.toString()))throw new y(t,"Time")},type:b},x={options:"",regexCheck:"",value:"",min:"",max:"",step:"",type:"text"};function D(t,e,n){const o=function(e,n){return{value:t[n],name:n,field:{name:t.name,type:t.el.tagName.toLocaleLowerCase()}}}(0,e);try{return function(t,e){o.value&&$[e||o.name](o)}(0,n),t[e]}catch(t){return console.warn(t.message),x[e]}}export{s as a,D as b,o as c,e as d,i as e,r as f,a as g,f as h,d as i,l as j,n as k,v as l,c as m,u as n};