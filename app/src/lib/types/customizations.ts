



export type StyleJsonType = {
    // HTML Tag Name
    [key: string]: {
        // CSS Property: Attribute
        [key: string]: string
    }
};

export type SingleNavItem = {
    title: string,
    href: string
}

export type SingleSocialItem = {
    href: string,
    title: string
    fa_icon: string, // We'll accept Font Awesome Icons Only
}

export type SiteHeaderType = {
    site_title: string,
    logo_url: string,
    nav_json: SingleNavItem[]
};

export type SiteFooterType = {
    text: string,
    social_json: SingleSocialItem[]
}

export type SiteStyle = {
    fontFamily: string, // Global font
    fontLoadUrl: string, // URL to load font
    styleJson: StyleJsonType
}

export type SiteDataTypes = {
    header: SiteHeaderType,
    footer: SiteFooterType,
    stylesheet_url: string,
}


