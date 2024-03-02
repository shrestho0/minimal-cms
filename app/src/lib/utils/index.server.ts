import type { SiteStyle } from "@/types/customizations";


import { fail } from "@sveltejs/kit";
 


export function jsonToCSS(json: SiteStyle) {
    let css = "";
    if (json.fontLoadUrl) {
        css += `@import url('${json.fontLoadUrl}');`;
    }
    if (json.fontFamily) {
        css += `body{font-family: ${json.fontFamily};}`;
    }
    for (const tag in json.styleJson) {
        css += `${tag}{`;
        for (const prop in json.styleJson[tag]) {
            css += `${prop}:${json.styleJson[tag][prop]};`;
        }
        css += "}";
    }

    return css;
}

