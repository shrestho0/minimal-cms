



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
    collectionId: string,
    id: string,
    site_title: string,
    logo: string,
    nav_json: SingleNavItem[]
};

export type SiteFooterType = {
    id: string,
    text: string,
    social_json: SingleSocialItem[]
}

export type SiteStyle = {
    id: string;
    fontFamily: string, // Global font
    fontLoadUrl: string, // URL to load font
    styleJson: StyleJsonType
}

export type SiteDataTypes = {
    header: SiteHeaderType,
    footer: SiteFooterType,
    stylesheet_url: string,
}

