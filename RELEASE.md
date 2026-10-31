# Production release

Build the signed, optimized Play Store bundle using the signing configuration in `local.properties`:

```powershell
.\gradlew.bat :app:bundleProdRelease
```

The bundle is generated at `app/build/outputs/bundle/prodRelease/app-prod-release.aab`.
Release builds use R8 for code shrinking, optimization and obfuscation, and also shrink resources.
Debug builds remain unoptimized.

Keep `app/build/outputs/mapping/prodRelease/mapping.txt` with each published release so obfuscated stack traces can be decoded.
The Android Gradle Plugin includes the mapping in the app bundle for Google Play.

`app/proguard-rules.pro` preserves fields and constructors used by Gson and Simple XML, plus Retrofit service signatures.
When adding a model serialized through reflection outside the DTO packages, add a targeted keep rule for that model.
Keep JSON field names stable for models saved in DataStore so existing installations can still read their data after updating.
Avoid rules that keep entire application or dependency packages.

Simple XML excludes its transitive StAX and XPP3 dependencies on Android to avoid packaging duplicate platform classes.
The rules suppress only the missing optional StAX types: Simple XML catches unavailable StAX providers and falls back to Android's XML Pull parser.
Simple XML also instantiates its internal label, extractor and parameter classes through reflection. Preserve those classes and their public constructor signatures, as well as the XML annotation interfaces used by runtime proxies, when R8 runs.

Before publishing, test the optimized release through an internal testing track: sign-in, home API data, Google News XML, profile loading/editing, cached profile data after upgrading, and the mandatory update flow.
Confirm the optimization percentages in Play Console after uploading the new bundle; a successful local build does not establish the percentages reported by Google Play.
