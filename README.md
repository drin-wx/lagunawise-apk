# LagunaWISE WebView APK

Full-screen Android WebView application para sa `https://lagunawise.vercel.app` na may splash screen at custom app icon.

## Paano Palitan ang Logo
1. I-rename ang logo PNG mo patungong `logo_lagunawise.png` (lowercase letters, numbers, at underscore lang ang tinatanggap ng Android resource compiler).
2. I-overwrite ang placeholder file sa `app/src/main/res/drawable/logo_lagunawise.png`.

## Paano I-build ang APK sa GitHub Actions
1. I-push ang buong laman ng folder sa `main` branch ng GitHub repository mo.
2. Buksan ang **Actions** tab at hintaying matapos ang **Build LagunaWISE APK**.
3. I-download ang `lagunawise-debug-apk` sa ilalim ng **Artifacts**.
