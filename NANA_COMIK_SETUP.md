# Nana Comik App Configuration Summary

## Changes Made

### 1. Package Name Change
- **Old**: `app.komikku` (beta: `app.komikku.dev`)
- **New**: `app.nanacomik` (beta: `app.nanacomik.beta`)

### 2. App Display Name
- **Old**: Komikku
- **New**: Nana Comik

### 3. Signing Configuration
- Created new signing keystore: `app/nanacomik.keystore`
- Store Password: `nanacomik123`
- Key Alias: `nanacomik`
- Key Password: `nanacomik123`
- Validity: 10000 days

### 4. Build Configuration (`app/build.gradle.kts`)
- Added `signingConfigs` block for release signing
- Configured `release`, `foss`, and `preview` build types to use release signing
- Environment variables support for CI/CD:
  - `KEYSTORE_PASSWORD`
  - `KEY_ALIAS`
  - `KEY_PASSWORD`

### 5. CI/CD Workflows

#### build_push.yml (Master builds)
- Added keystore decoding from base64-encoded GitHub Secret
- Changed to signed APKs (removed unsigned requirement)
- APK naming: `Nana-Comik-{branch}-r{commit}.apk`
- Passes keystore credentials as environment variables

#### build_release.yml (Release builds)
- Added keystore decoding from base64-encoded GitHub Secret
- Updated all APK variants to use signed versions
- Updated release artifacts naming to `Nana-Comik-*`
- Updated release notes to reference Nana Comik

### 6. GitHub Secrets Required

You need to add these secrets to your GitHub repository:

```
KEYSTORE_FILE = <base64-encoded keystore file>
KEYSTORE_PASSWORD = nanacomik123
KEY_ALIAS = nanacomik
KEY_PASSWORD = nanacomik123
```

See `SIGNING_SETUP.md` for detailed setup instructions.

## Benefits

✅ **Independent Installation**: Both Komikku and Nana Comik can be installed on the same device
✅ **Proper Signing**: APKs are properly signed with a release key, safer for installation
✅ **CI/CD Integration**: Automated signing in GitHub Actions workflows
✅ **PDF Support**: Existing PDF reading implementation continues to work with new package name

## Testing

1. Generate the keystore locally
2. Add GitHub Secrets
3. Commit the keystore file
4. Push to trigger GitHub Actions
5. Download signed APK and test:
   ```bash
   adb install Nana-Comik-master-r{commit}.apk
   ```

## Next Steps

Follow the instructions in `SIGNING_SETUP.md` to complete the setup.
