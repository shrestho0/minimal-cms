import { fontFamily } from "tailwindcss/defaultTheme";
import flattenColorPalette from 'tailwindcss/lib/util/flattenColorPalette';

/** @type {import('tailwindcss').Config} */
const config = {
	content: ["./src/**/*.{html,js,svelte,ts}"],
	safelist: ["dark"],
	darkMode: "class",
	theme: {
		container: {
			center: true,
			padding: "2rem",
			screens: {
				"2xl": "1400px"
			}
		},
		extend: {
			colors: {
				"new-accent": "#5F5BF1",
				"new-ui": "#D9D9D9",
				border: "hsl(var(--border) / <alpha-value>)",
				input: "hsl(var(--input) / <alpha-value>)",
				ring: "hsl(var(--ring) / <alpha-value>)",
				background: "hsl(var(--background) / <alpha-value>)",
				foreground: "hsl(var(--foreground) / <alpha-value>)",
				primary: {
					DEFAULT: "hsl(var(--primary) / <alpha-value>)",
					foreground: "hsl(var(--primary-foreground) / <alpha-value>)"
				},
				secondary: {
					DEFAULT: "hsl(var(--secondary) / <alpha-value>)",
					foreground: "hsl(var(--secondary-foreground) / <alpha-value>)"
				},
				destructive: {
					DEFAULT: "hsl(var(--destructive) / <alpha-value>)",
					foreground: "hsl(var(--destructive-foreground) / <alpha-value>)"
				},
				muted: {
					DEFAULT: "hsl(var(--muted) / <alpha-value>)",
					foreground: "hsl(var(--muted-foreground) / <alpha-value>)"
				},
				accent: {
					DEFAULT: "hsl(var(--accent) / <alpha-value>)",
					foreground: "hsl(var(--accent-foreground) / <alpha-value>)"
				},
				popover: {
					DEFAULT: "hsl(var(--popover) / <alpha-value>)",
					foreground: "hsl(var(--popover-foreground) / <alpha-value>)"
				},
				card: {
					DEFAULT: "hsl(var(--card) / <alpha-value>)",
					foreground: "hsl(var(--card-foreground) / <alpha-value>)"
				}
			},
			borderRadius: {
				lg: "var(--radius)",
				md: "calc(var(--radius) - 2px)",
				sm: "calc(var(--radius) - 4px)"
			},
			fontFamily: {
				// sans: [...fontFamily.sans]
				sans: [
					"Poppins", "sans-serif",
					{
						fontFeatureSettings: '"cv11", "ss01"',
						fontVariationSettings: '"opsz" 32'
					},
				],

			},
			animation: {
				// ...other animations
				spotlight: 'spotlight 2s ease .75s 1 forwards',
				scroll:
					'scroll var(--animation-duration, 40s) var(--animation-direction, forwards) linear infinite',

			},
			keyframes: {
				// ... other keyframes
				spotlight: {
					'0%': {
						opacity: 0,
						transform: 'translate(-72%, -62%) scale(0.5)'
					},
					'100%': {
						opacity: 1,
						transform: 'translate(-50%,-40%) scale(1)'
					}
				},
				scroll: {
					to: {
						transform: 'translate(calc(-50% - 0.5rem))'
					}
				}
			}

		}
	},
};


// // This plugin adds each Tailwind color as a global CSS variable, e.g. var(--gray-200).
// function addVariablesForColors({ addBase, theme }: any) {
// 	let allColors = flattenColorPalette(theme('colors'));
// 	let newVars = Object.fromEntries(
// 		Object.entries(allColors).map(([key, val]) => [`--${key}`, val])
// 	);

// 	addBase({
// 		':root': newVars
// 	});
// }

export default config;
