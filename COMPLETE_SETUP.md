# Commands to Complete Nana Comik Setup

## Quick Start - Run These Commands

Execute these commands in your terminal to complete the setup:

### 1. Generate the Keystore

```bash
cd /workspaces/komikku
keytool -genkey -v -keystore app/nanacomik.keystore \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000 \
  -alias nanacomik \
  -storepass nanacomik123 \
  -keypass nanacomik123 \
  -dname "CN=Nana Comik,O=NanaComik,C=US"
```

### 2. Encode Keystore to Base64

```bash
cat app/nanacomik.keystore | base64 > keystore.b64
cat keystore.b64
```

Copy the entire output and save it.

### 3. Add GitHub Secrets

Go to: **GitHub Repository → Settings → Secrets and variables → Actions → New repository secret**

Add these secrets:

| Secret Name | Value |
|---|---|
| `KEYSTORE_FILE` | Paste the base64 output from Step 2 |
| `KEYSTORE_PASSWORD` | `nanacomik123` |
| `KEY_ALIAS` | `nanacomik` |
| `KEY_PASSWORD` | `nanacomik123` |

### 4. Commit All Changes

```bash
cd /workspaces/komikku

# Stage all changes
git add app/build.gradle.kts
git add i18n/src/commonMain/moko-resources/base/strings.xml
git add .github/workflows/build_push.yml
git add .github/workflows/build_release.yml
git add .gitignore
git add SIGNING_SETUP.md
git add NANA_COMIK_SETUP.md
git add generate-keystore.sh
git add app/nanacomik.keystore

# Commit with message
git commit -m "Add Nana Comik branding with new package name (app.nanacomik) and signing configuration"

# Push to master
git push origin master
```

### 5. Verify

After pushing:
- Check GitHub Actions workflow runs and completes successfully
- Download the signed APK: `Nana-Comik-master-r{commit}.apk`
- Install on your device: `adb install Nana-Comik-master-r{commit}.apk`
- You should now have both Komikku and Nana Comik apps

---

## What Was Changed

✅ **app/build.gradle.kts**
- Changed applicationId: `app.komikku` → `app.nanacomik`
- Added signingConfigs with nanacomik keystore
- Configured release, foss, and preview build types to use signing

✅ **i18n/src/commonMain/moko-resources/base/strings.xml**
- Changed app_name: `Komikku` → `Nana Comik`

✅ **.github/workflows/build_push.yml**
- Added keystore decoding from GitHub Secret
- Updated APK naming to `Nana-Comik-*`
- Configured signing via environment variables

✅ **.github/workflows/build_release.yml**
- Added keystore decoding from GitHub Secret
- Updated all APK variant naming to `Nana-Comik-*`
- Configured signing via environment variables

✅ **.gitignore**
- Added `*.keystore` to prevent accidental commits

✅ **Documentation**
- SIGNING_SETUP.md: Detailed setup instructions
- NANA_COMIK_SETUP.md: Summary of changes
- generate-keystore.sh: Script to regenerate keystore if needed

---

## Troubleshooting

**Q: "APK not found" error in GitHub Actions**
- Ensure GitHub Secrets are properly set
- Check that keystore password is correct

**Q: Two apps can't install simultaneously**
- Verify package names are different: `app.komikku` vs `app.nanacomik`
- Use `adb shell pm list packages | grep komikku` to verify both installed

**Q: Keystore file too large for base64**
- Try: `base64 -w0 app/nanacomik.keystore` (removes line wrapping)

---

## Security Notes

- ⚠️ GitHub Secrets are encrypted and only visible to GitHub Actions
- ⚠️ Do NOT commit the unencoded keystore to sensitive branches
- ✅ Keystore is listed in .gitignore (won't be committed)
- ✅ Only store base64-encoded keystore in GitHub Secrets
